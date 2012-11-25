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

import com.google.common.collect.Lists;
import java.util.List;

/**
 * Default implementation for scanning the input line from an interactive shell.
 *
 * The scanner tokenizes:
 * - alpha numeric literals
 * - integer number literals
 * - and single/double quote delimittered string literals
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
class DefaultScanner implements Scanner {

    /**
     * Scans give line and returns list of recognized tokens.
     *
     * @param line line to scan.
     * @return List of recognized, never null
     * @throws SyntaxException if, syntax error occurred.
     * // CHECKSTYLE:OFF
     * @throws IllegalArgumentException, if line is null.
     * // CHECKSTYLE:ON
     */
    @Override
    public List<Token> scan(final String line) throws SyntaxException {
        if (null == line) {
            throw new IllegalArgumentException("Line must not be null!");
        }

        final List<Token> tokens = Lists.newArrayList();

        if (! line.isEmpty()) {
            scan(tokens, new CharacterStream(line));
        }

        return tokens;
    }

    /**
     * Loops over all characters of stream.
     *
     * @param tokens list to which recognized tokens will be add
     * @param characterStream input line to scan
     * @throws SyntaxException if, syntax error occurred
     */
    private void scan(final List<Token> tokens, final CharacterStream characterStream) throws SyntaxException {
        while (characterStream.hasNext()) {
            final char currentChar = characterStream.next();

            if (CharacterHelper.isAlpha(currentChar)) {
                tokens.add(scanAlphaNum(characterStream));
            } else if (CharacterHelper.isNum(currentChar)) {
                tokens.add(scanNumber(characterStream));
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
    private Token scanAlphaNum(final CharacterStream characterStream) {
        final StringBuilder value = new StringBuilder();
        value.append(characterStream.current());

        while (characterStream.hasNext()) {
            final char currentChar = characterStream.next();

            if (CharacterHelper.isWhiteSpace(currentChar)) {
                break;
            }

            value.append(currentChar);
        }

        final String tokenString = value.toString();

        if (Token.isKeyword(tokenString)) {
            return Token.newKeywordToken(tokenString);
        } else {
            return Token.newLiteralToken(tokenString);
        }
    }

    /**
     * Recognize numeric integer tokens until next white space character.
     *
     * @param characterStream input line to scan
     * @return integer type token
     * @throws SyntaxException if, non numeric character occurred
     */
    private Token scanNumber(final CharacterStream characterStream) throws SyntaxException {
        final StringBuilder value = new StringBuilder();

        value.append(characterStream.current());

        while (characterStream.hasNext()) {
            final char currentChar = characterStream.next();

            if (CharacterHelper.isWhiteSpace(currentChar)) {
                break;
            }

            if (! CharacterHelper.isNum(currentChar)) {
                throw new SyntaxException(String.format("Bad character '%s' in number starting with '%s'!",
                                                        currentChar, value.toString()));
            }

            value.append(currentChar);
        }

        return Token.newNumberToken(Integer.valueOf(value.toString()));
    }

    /**
     * Scan string tokens.
     *
     * String tokens are everything encapsulated in single or double quotes.
     *
     * @param characterStream input line to scan
     * @return integer type token
     * @throws SyntaxException if, string is not terminated by quote character
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

        if (! terminated) {
            throw new SyntaxException(String.format("Unterminated string '%s'!", value.toString()));
        }

        return Token.newStringToken(value.toString());
    }

}