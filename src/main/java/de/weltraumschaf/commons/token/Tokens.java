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
package de.weltraumschaf.commons.token;

import de.weltraumschaf.commons.Null;

/**
 * Factory to create tokens.
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
public final class Tokens {

    /**
     * Default position.
     */
    private static final Position NULL_POSITION = new Position(0, 0);

    /**
     * Hidden for pure static factory.
     */
    private Tokens() {
        super();
    }

    /**
     * Creates a comment token.
     *
     * @param value operator string
     * @return new instance
     */
    public static Token<String> newOperatorToken(final String value) {
        return newOperatorToken(value, NULL_POSITION);
    }

    /**
     * Creates a comment token.
     *
     * @param value operator string
     * @param pos source position of token
     * @return new instance
     */
    public static Token<String> newOperatorToken(final String value, final Position pos) {
        return new Token<String>(TokenType.OPERATOR, value, pos);
    }

    /**
     * Creates a end of file token.
     *
     * @return {@link Null} as a placeholder
     */
    public static Token<Null> newEndOfFileToken() {
        return newEndOfFileToken(NULL_POSITION);
    }

    /**
     * Creates a end of file token.
     *
     * @param pos source position of token
     * @return {@link Null} as a placeholder
     */
    public static Token<Null> newEndOfFileToken(final Position pos) {
        return new Token<Null>(TokenType.EOF, Null.getInstance(), pos);
    }

    /**
     * Creates a command keyword token.
     *
     * @param value token value
     * @return new instance
     */
    public static Token<String> newKeywordToken(final String value) {
        return newKeywordToken(value, NULL_POSITION);
    }

    /**
     * Creates a command keyword token.
     *
     * @param value token value
     * @param pos source position of token
     * @return new instance
     */
    public static Token<String> newKeywordToken(final String value, final Position pos) {
        return new Token<String>(TokenType.KEYWORD, value, pos);
    }

    /**
     * Creates a number token.
     *
     * @param value token value
     * @return new instance
     */
    public static Token<Integer> newIntegerToken(final Integer value) {
        return newIntegerToken(value, NULL_POSITION);
    }

    /**
     * Creates a number token.
     *
     * @param value token value
     * @param pos source position of token
     * @return new instance
     */
    public static Token<Integer> newIntegerToken(final Integer value, final Position pos) {
        return new Token<Integer>(TokenType.INTEGER, value, pos);
    }

    /**
     * Creates a float token.
     *
     * @param value token value
     * @return new instance
     */
    public static Token<Float> newFloatToken(final Float value) {
        return newFloatToken(value, NULL_POSITION);
    }

    /**
     * Creates a float token.
     *
     * @param value token value
     * @param pos source position of token
     * @return new instance
     */
    public static Token<Float> newFloatToken(final Float value, final Position pos) {
        return new Token<Float>(TokenType.FLOAT, value, pos);
    }

    /**
     * Creates a comment token.
     *
     * @param value comment string
     * @return new instance
     */
    public static Token<String> newCommentToken(final String value) {
        return newCommentToken(value, NULL_POSITION);
    }

    /**
     * Creates a comment token.
     *
     * @param value comment string
     * @param pos source position of token
     * @return new instance
     */
    public static Token<String> newCommentToken(final String value, final Position pos) {
        return new Token<String>(TokenType.COMMENT, value, pos);
    }

    /**
     * Creates a end of line token.
     *
     * @return {@link Null} as a placeholder
     */
    public static Token<Null> newEndOfLineToken() {
        return newEndOfLineToken(NULL_POSITION);
    }

    /**
     * Creates a end of line token.
     *
     * @param pos source position of token
     * @return {@link Null} as a placeholder
     */
    public static Token<Null> newEndOfLineToken(final Position pos) {
        return new Token<Null>(TokenType.EOL, Null.getInstance(), pos);
    }

    /**
     * Creates a boolean token.
     *
     * @param value token value
     * @return new instance
     */
    public static Token<Boolean> newBooleanToken(final Boolean value) {
        return newBooleanToken(value, NULL_POSITION);
    }

    /**
     * Creates a boolean token.
     *
     * @param value token value
     * @param pos source position of token
     * @return new instance
     */
    public static Token<Boolean> newBooleanToken(final Boolean value, final Position pos) {
        return new Token<Boolean>(TokenType.BOOLEAN, value, pos);
    }

    /**
     * Creates a null token.
     *
     * @return new instance
     */
    public static Token<Null> newNullToken() {
        return newNullToken(NULL_POSITION);
    }

    /**
     * Creates a null token.
     *
     * @param pos source position of token
     * @return new instance
     */
    public static Token<Null> newNullToken(final Position pos) {
        return new Token<Null>(TokenType.NULL, Null.getInstance(), pos);
    }

    /**
     * Creates a literal token.
     *
     * @param value token value
     * @return new instance
     */
    public static Token<String> newLiteralToken(final String value) {
        return new Token<String>(TokenType.LITERAL, value, NULL_POSITION);
    }

    /**
     * Creates a literal token.
     *
     * @param value token value
     * @param pos source position of token
     * @return new instance
     */
    public static Token<String> newLiteralToken(final String value, final Position pos) {
        return new Token<String>(TokenType.LITERAL, value, pos);
    }

    /**
     * Creates a string token.
     *
     * @param value token value
     * @return new instance
     */
    public static Token<String> newStringToken(final String value) {
        return newStringToken(value, NULL_POSITION);
    }

    /**
     * Creates a string token.
     *
     * @param value token value
     * @param pos source position of token
     * @return new instance
     */
    public static Token<String> newStringToken(final String value, final Position pos) {
        return new Token<String>(TokenType.STRING, value, pos);
    }

}
