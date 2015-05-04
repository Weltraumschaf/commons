/*
 * LICENSE
 *
 * "THE BEER-WARE LICENSE" (Revision 43):
 * "Sven Strittmatter" <weltraumschaf(at)googlemail(dot)com> wrote this file.
 * As long as you retain this notice you can do whatever you want with
 * this stuff. If we meet some day, and you think this stuff is worth it,
 * you can buy me a non alcohol-free beer in return.
 *
 * Copyright (C) 2012 "Sven Strittmatter" <weltraumschaf(at)googlemail(dot)com>
 */
package de.weltraumschaf.commons.shell;

import de.weltraumschaf.commons.token.Token;
import de.weltraumschaf.commons.characters.CharacterStream;
import de.weltraumschaf.commons.characters.CharacterHelper;
import de.weltraumschaf.commons.guava.Lists;
import de.weltraumschaf.commons.token.Position;
import de.weltraumschaf.commons.token.Tokens;
import de.weltraumschaf.commons.validate.Validate;
import java.util.List;

/**
 * Default implementation for scanning the input line from an interactive shell.
 * <p>
 * The scanner tokenizes:
 * </p>
 * <ul>
 * <li>alpha numeric literals</li>
 * <li>(signed) integer number literals</li>
 * <li>(signed) float number literals</li>
 * <li>boolean literals (true/false)</li>
 * <li>and single/double quote delimited string literals</li>
 * </ul>
 *
 * @since 1.0.0
 * @author Sven Strittmatter &lt;weltraumschaf@googlemail.com&gt;
 */
class DefaultScanner implements Scanner {

    /**
     * Maps command literal strings to command types.
     */
    private final LiteralCommandMap commandMap;

    /**
     * Dedicated constructor.
     *
     * @param commandMap map key word literals to command types
     */
    public DefaultScanner(final LiteralCommandMap commandMap) {
        super();
        this.commandMap = commandMap;
    }

    /**
     * {@inheritDoc}
     *
     * Scans give line and returns list of recognized tokens.
     */
    @Override
    public List<Token> scan(final String line) throws SyntaxException {
        Validate.notNull(line, "line");
        final List<Token> tokens = Lists.newArrayList();

        if (line.isEmpty()) {
            return tokens;
        }

        scan(tokens, new CharacterStream(line));
        return tokens;
    }

    /**
     * Loops over all characters of stream.
     *
     * @param tokens list to which recognized tokens will be add
     * @param characterStream input line to scan
     * @throws SyntaxException if string is not correct encapsulated by quotes
     */
    private void scan(final List<Token> tokens, final CharacterStream characterStream) throws SyntaxException {
        while (characterStream.hasNext()) {
            final char currentChar = characterStream.next();

            if (CharacterHelper.isSign(currentChar) && CharacterHelper.isNum(characterStream.peek())) {
                tokens.add(scanNumber(characterStream));
            } else if (CharacterHelper.isNum(currentChar)) {
                tokens.add(scanNumber(characterStream));
            } else if (CharacterHelper.isAlpha(currentChar) || CharacterHelper.isSpecialChar(currentChar)) {
                tokens.add(scanLiteral(characterStream));
            } else if (CharacterHelper.isQuote(currentChar)) {
                tokens.add(scanString(characterStream));
            }
        }
    }

    /**
     * Recognize alpha numeric string tokens until next white space character.
     *
     * @param characterStream input line to scan
     * @return Return string type token
     */
    private Token scanLiteral(final CharacterStream characterStream) {
        return scanLiteralOrKeyword(characterStream, new StringBuilder());
    }

    /**
     * Recognize alpha numeric string tokens until next white space character.
     *
     * @param characterStream input line to scan
     * @param value collects the token string, must not be {@code null}
     * @return Return string type token
     */
    private Token scanLiteralOrKeyword(final CharacterStream characterStream, final StringBuilder value) {
        value.append(characterStream.current());

        while (characterStream.hasNext()) {
            final char currentChar = characterStream.next();

            if (CharacterHelper.isWhiteSpace(currentChar)) {
                break;
            }

            value.append(currentChar);
        }

        final String tokenString = value.toString();

        if ("true".equals(tokenString) || "false".equals(tokenString)) {
            return Tokens.newBooleanToken(Position.NULL, tokenString, Boolean.parseBoolean(tokenString));
        } else if (isKeyword(tokenString)) {
            return Tokens.newKeywordToken(Position.NULL, tokenString, tokenString);
        } else {
            return Tokens.newLiteralToken(Position.NULL, tokenString, tokenString);
        }
    }

    /**
     * Recognize numeric tokens until next white space character.
     * <p>
     * If this method recognizes a dot it will try to scan a float.
     * If any not number character is detected it will treat the token as a literal/keyword.
     * </p>
     *
     * @param characterStream input line to scan
     * @return integer or float or literal or keyword type token
     */
    private Token scanNumber(final CharacterStream characterStream) {
        final StringBuilder value = new StringBuilder();
        value.append(characterStream.current());

        while (characterStream.hasNext()) {
            final char currentChar = characterStream.next();

            if (CharacterHelper.isWhiteSpace(currentChar)) {
                break;
            }

            if ('.' == currentChar) {
                return scanFloat(characterStream, value);
            }

            if (!CharacterHelper.isNum(currentChar)) {
                return scanLiteralOrKeyword(characterStream, value);
            }

            value.append(currentChar);
        }

        final String literal = value.toString();
        return Tokens.newIntegerToken(Position.NULL, literal, Integer.valueOf(literal));
    }

    /**
     * Recognize float tokens until next white space character.
     * <p>
     * If any not number character is detected it will treat the token as a literal/keyword.
     * </p>
     *
     * @param characterStream input line to scan
     * @param value accumulates the token literal string
     * @return float or literal or keyword type token
     */
    private Token scanFloat(final CharacterStream characterStream, final StringBuilder value) {
        value.append(characterStream.current());

        while (characterStream.hasNext()) {
            final char currentChar = characterStream.next();

            if (CharacterHelper.isWhiteSpace(currentChar)) {
                break;
            }

            if (!CharacterHelper.isNum(currentChar) && !isAllowedInFloat(currentChar)) {
                return scanLiteralOrKeyword(characterStream, value);
            }

            value.append(currentChar);
        }

        final String literal = value.toString();
        return Tokens.newFloatToken(Position.NULL, literal, Float.valueOf(literal));
    }

    /**
     * Scan string tokens.
     *
     * String tokens are everything encapsulated in single or double quotes.
     *
     * @param characterStream input line to scan
     * @return integer type token
     * @throws SyntaxException if string is not correct encapsulated by quotes
     */
    private Token scanString(final CharacterStream characterStream) throws SyntaxException {
        final StringBuilder value = new StringBuilder();
        final char startQuote = characterStream.current();
        boolean terminated = false;

        while (characterStream.hasNext()) {
            final char currentChar = characterStream.next();

            if (currentChar == startQuote) {
                terminated = true;

                if (characterStream.hasNext()) {
                    // Skip closing quote, if there are more characters.
                    characterStream.next();
                }

                break;
            }

            value.append(currentChar);
        }

        if (!terminated) {
            throw new SyntaxException(String.format("Unterminated string '%s'!", value.toString()));
        }

        return Tokens.newStringToken(Position.NULL, value.toString(), value.toString());
    }

    /**
     * Determines if a token string is a keyword.
     *
     * Checks the string against {@link LiteralCommandMap#isCommand(java.lang.String)} and
     * {@link LiteralCommandMap#isSubCommand(java.lang.String)}.
     *
     * @param token string to check
     * @return true if given string is a keyword else false
     */
    private boolean isKeyword(String token) {
        return commandMap.isCommand(token) || commandMap.isSubCommand(token);
    }

    /**
     * Whether an character is allowed in float literals.
     * <p>
     * Allowed are signs (+|-) and the exponential characters (E|e).
     * </p>
     *
     * @param c any character
     * @return {@code true} if c is allowed, else {@code false}
     */
    private boolean isAllowedInFloat(final char c) {
        return CharacterHelper.isSign(c) || 'e' == c || 'E' == c;
    }
}
