/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright (c) 2010-2013 Oracle and/or its affiliates. All rights reserved.
 *
 * The contents of this file are subject to the terms of either the GNU
 * General Public License Version 2 only ("GPL") or the Common Development
 * and Distribution License("CDDL") (collectively, the "License").  You
 * may not use this file except in compliance with the License.  You can
 * obtain a copy of the License at
 * http://glassfish.java.net//CDDL+GPL_1_1.html
 * or packager/legal/LICENSE.txt.  See the License for the specific
 * language governing permissions and limitations under the License.
 *
 * When distributing the software, include this License Header Notice in each
 * file and include the License file at packager/legal/LICENSE.txt.
 *
 * GPL Classpath Exception:
 * Oracle designates this particular file as subject to the "Classpath"
 * exception as provided by Oracle in the GPL Version 2 section of the License
 * file that accompanied this code.
 *
 * Modifications:
 * If applicable, add the following below the License Header, with the fields
 * enclosed by brackets [] replaced by your own identifying information:
 * "Portions Copyright [year] [name of copyright owner]"
 *
 * Contributor(s):
 * If you wish your version of this file to be governed by only the CDDL or
 * only the GPL Version 2, indicate your decision by adding "[Contributor]
 * elects to include this software in this distribution under the [CDDL or GPL
 * Version 2] license."  If you don't indicate a single choice of license, a
 * recipient has the option to distribute your version of this file under
 * either the CDDL, the GPL Version 2 or to extend the choice of license to
 * its licensees as provided above.  However, if you add GPL Version 2 code
 * and therefore, elected the GPL Version 2 license, then the option applies
 * only if the new code is made subject to such option by the copyright
 * holder.
 */
package de.weltraumschaf.commons.uri;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

/**
 * A URI template parser that parses JAX-RS specific URI templates.
 *
 * @author Paul Sandoz
 */
class UriTemplateParser {
    static final int[] EMPTY_INT_ARRAY = new int[0];
    private static final Set<Character> RESERVED_REGEX_CHARACTERS = initReserved();

    private static Set<Character> initReserved() {
        final char[] reserved = {
            '.', '^', '&', '!',
            '?', '-', ':', '<',
            '(', '[', '$', '=',
            ')', ']', ',', '>',
            '*', '+', '|',
        };

        final Set<Character> s = new HashSet<>(reserved.length);
        for (char c : reserved) {
            s.add(c);
        }
        return s;
    }

    private static final Pattern TEMPLATE_VALUE_PATTERN = Pattern.compile("[^/]+?");
    private static final String[] HEX_TO_UPPERCASE_REGEX = initHexToUpperCaseRegex();

    private final String template;
    private final StringBuffer regex = new StringBuffer();
    private final StringBuffer normalizedTemplate = new StringBuffer();
    private final StringBuffer literalCharactersBuffer = new StringBuffer();
    private final Pattern pattern;
    private final List<String> names = new ArrayList<>();
    private final List<Integer> groupCounts = new ArrayList<>();
    private final Map<String, Pattern> nameToPattern = new HashMap<>();
    private int numOfExplicitRegexes;
    private int literalCharacters;

    /**
     * Parse a template.
     *
     * @param template the template.
     * @throws IllegalArgumentException if the template is null, an empty string or does not conform to a JAX-RS URI
     * template.
     */
    UriTemplateParser(final String template) throws IllegalArgumentException {
        if (template == null || template.length() == 0) {
            throw new IllegalArgumentException();
        }

        this.template = template;
        parse(new CharacterIterator(template));
        try {
            pattern = Pattern.compile(regex.toString());
        } catch (PatternSyntaxException ex) {
            throw new IllegalArgumentException("Invalid syntax for the template expression '"
                + regex + "'",
                ex);
        }
    }

    /**
     * Get the template.
     *
     * @return the template.
     */
    final String getTemplate() {
        return template;
    }

    /**
     * Get the pattern.
     *
     * @return the pattern.
     */
    final Pattern getPattern() {
        return pattern;
    }

    /**
     * Get the normalized template.
     * <p>
     * A normalized template is a template without any explicit regular expressions.
     *
     * @return the normalized template.
     */
    final String getNormalizedTemplate() {
        return normalizedTemplate.toString();
    }

    /**
     * Get the map of template names to patterns.
     *
     * @return the map of template names to patterns.
     */
    final Map<String, Pattern> getNameToPattern() {
        return nameToPattern;
    }

    /**
     * Get the list of template names.
     *
     * @return the list of template names.
     */
    final List<String> getNames() {
        return names;
    }

    /**
     * Get the capturing group counts for each template variable.
     *
     * @return the capturing group counts.
     */
    final List<Integer> getGroupCounts() {
        return groupCounts;
    }

    /**
     * Get the group indexes to capturing groups.
     * <p>
     * Any nested capturing groups will be ignored and the the group index will refer to the top-level capturing groups
     * associated with the templates variables.
     *
     * @return the group indexes to capturing groups.
     */
    final int[] getGroupIndexes() {
        if (names.isEmpty()) {
            return EMPTY_INT_ARRAY;
        }

        final int[] indexes = new int[names.size() + 1];
        indexes[0] = 1;
        for (int i = 1; i < indexes.length; i++) {
            indexes[i] = indexes[i - 1] + groupCounts.get(i - 1);
        }
        for (int i = 0; i < indexes.length; i++) {
            if (indexes[i] != i + 1) {
                return indexes;
            }
        }
        return EMPTY_INT_ARRAY;
    }

    /**
     * Get the number of explicit regular expressions.
     *
     * @return the number of explicit regular expressions.
     */
    final int getNumberOfExplicitRegexes() {
        return numOfExplicitRegexes;
    }

    /**
     * Get the number of literal characters.
     *
     * @return the number of literal characters.
     */
    final int getNumberOfLiteralCharacters() {
        return literalCharacters;
    }

    /**
     * Encode literal characters of a template.
     *
     * @param characters the literal characters
     * @return the encoded literal characters.
     */
    protected String encodeLiteralCharacters(final String characters) {
        return characters;
    }

    private void parse(final CharacterIterator ci) {
        try {
            while (ci.hasNext()) {
                final char c = ci.next();
                if (c == '{') {
                    processLiteralCharacters();
                    parseName(ci);
                } else {
                    literalCharactersBuffer.append(c);
                }
            }
            processLiteralCharacters();
        } catch (NoSuchElementException ex) {
            throw new IllegalArgumentException(
                String.format("Invalid syntax in the template \"%s\". Check if a path parameter is terminated with a \"}\".",
                    template), ex);
        }
    }

    private void processLiteralCharacters() {
        if (literalCharactersBuffer.length() > 0) {
            literalCharacters += literalCharactersBuffer.length();

            final String s = encodeLiteralCharacters(literalCharactersBuffer.toString());

            normalizedTemplate.append(s);

            // Escape if reserved regex character
            for (int i = 0; i < s.length(); i++) {
                final char c = s.charAt(i);
                if (RESERVED_REGEX_CHARACTERS.contains(c)) {
                    regex.append("\\");
                    regex.append(c);
                } else if (c == '%') {
                    final char c1 = s.charAt(i + 1);
                    final char c2 = s.charAt(i + 2);
                    if (UriComponent.isHexCharacter(c1) && UriComponent.isHexCharacter(c2)) {
                        regex.append("%").append(HEX_TO_UPPERCASE_REGEX[c1]).append(HEX_TO_UPPERCASE_REGEX[c2]);
                        i += 2;
                    }
                } else {
                    regex.append(c);
                }
            }
            literalCharactersBuffer.setLength(0);
        }
    }

    private static String[] initHexToUpperCaseRegex() {
        final String[] table = new String[0x80];
        for (int i = 0; i < table.length; i++) {
            table[i] = String.valueOf((char) i);
        }

        for (char c = 'a'; c <= 'f'; c++) {
            // initialize table values: table[a] = ([aA]) ...
            table[c] = new StringBuffer().append("[").append(c).append((char) (c - 'a' + 'A')).append("]").toString();
        }

        for (char c = 'A'; c <= 'F'; c++) {
            // initialize table values: table[A] = ([aA]) ...
            table[c] = new StringBuffer().append("[").append((char) (c - 'A' + 'a')).append(c).append("]").toString();
        }
        return table;
    }

    private void parseName(final CharacterIterator ci) {
        char c = consumeWhiteSpace(ci);

        final StringBuilder nameBuffer = new StringBuilder();
        if (Character.isLetterOrDigit(c) || c == '_') {
            // Template name character
            nameBuffer.append(c);
        } else {
            throw new IllegalArgumentException(
                String.format("Illegal character \"%s\" at position %s is not allowed as a start of a name in a path template \"%s\".",
                    c, ci.pos(), template));
        }

        String nameRegexString = "";
        while (true) {
            c = ci.next();
            // "\\{(\\w[-\\w\\.]*)
            if (Character.isLetterOrDigit(c) || c == '_' || c == '-' || c == '.') {
                // Template name character
                nameBuffer.append(c);
            } else if (c == ':') {
                nameRegexString = parseRegex(ci);
                break;
            } else if (c == '}') {
                break;
            } else if (c == ' ') {
                c = consumeWhiteSpace(ci);

                if (c == ':') {
                    nameRegexString = parseRegex(ci);
                    break;
                } else if (c == '}') {
                    break;
                } else {
                    // Error
                    throw new IllegalArgumentException(
                        String.format("Illegal character \"%s\" at position %s is not allowed after a name in a path template \"%s\".",
                            c, ci.pos(), template));
                }
            } else {
                throw new IllegalArgumentException(
                    String.format(
                        "Illegal character \"%s\" at position %s is not allowed as a part of a name in a path template \"%s\".",
                        c, ci.pos(), template));
            }
        }
        final String name = nameBuffer.toString();
        names.add(name);

        try {
            if (nameRegexString.length() > 0) {
                numOfExplicitRegexes++;
            }
            final Pattern namePattern = (nameRegexString.length() == 0)
                ? TEMPLATE_VALUE_PATTERN : Pattern.compile(nameRegexString);
            if (nameToPattern.containsKey(name)) {
                if (!nameToPattern.get(name).equals(namePattern)) {
                    throw new IllegalArgumentException(
                        String.format(
                            "The name \"%s\" is declared more than once with different regular expressions in a path template \"%s\".",
                            name, template));
                }
            } else {
                nameToPattern.put(name, namePattern);
            }

            // Determine group count of pattern
            final Matcher m = namePattern.matcher("");
            final int g = m.groupCount();
            groupCounts.add(g + 1);

            regex.append('(').
                append(namePattern).
                append(')');
            normalizedTemplate.append('{').
                append(name).
                append('}');
        } catch (PatternSyntaxException ex) {
            throw new IllegalArgumentException(
                String.format("Invalid syntax in the template \"%s\". Check if a path parameter is terminated with a \"}\".",
                    nameRegexString, name, template), ex);
        }
    }

    private String parseRegex(final CharacterIterator ci) {
        final StringBuilder regexBuffer = new StringBuilder();

        int braceCount = 1;
        while (true) {
            final char c = ci.next();
            if (c == '{') {
                braceCount++;
            } else if (c == '}') {
                braceCount--;
                if (braceCount == 0) {
                    break;
                }
            }
            regexBuffer.append(c);
        }

        return regexBuffer.toString().trim();
    }

    private char consumeWhiteSpace(final CharacterIterator ci) {
        char c;
        do {
            c = ci.next();
        } while (Character.isWhitespace(c));

        return c;
    }
}
