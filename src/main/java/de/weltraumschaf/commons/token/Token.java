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

import com.google.common.base.Objects;
import de.weltraumschaf.commons.Null;

/**
 * Represent a token scanned from interactive shell input.
 *
 * @param <T> Type of the token value.
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
public final class Token<T> {

    /**
     * Type of token.
     */
    private final TokenType type;

    /**
     * Value of the token.
     */
    private final T value;

    /**
     * Hide constructor to force usage of factory methods.
     *
     * @param type Token type.
     * @param value Token value.
     */
    private Token(final TokenType type, final T value) {
        this.type = type;
        this.value = value;
    }

    /**
     * Creates a command keyword token.
     *
     * @param value token value
     * @return new instance
     */
    public static Token<String> newKeywordToken(final String value) {
        return new Token<String>(TokenType.KEYWORD, value);
    }

    /**
     * Creates a string token.
     *
     * @param value token value
     * @return new instance
     */
    public static Token<String> newStringToken(final String value) {
        return new Token<String>(TokenType.STRING, value);
    }

    /**
     * Creates a literal token.
     *
     * @param value token value
     * @return new instance
     */
    public static Token<String> newLiteralToken(final String value) {
        return new Token<String>(TokenType.LITERAL, value);
    }

    /**
     * Creates a number token.
     *
     * @param value token value
     * @return new instance
     */
    public static Token<Integer> newIntegerToken(final Integer value) {
        return new Token<Integer>(TokenType.INTEGER, value);
    }

    /**
     * Creates a float token.
     *
     * @param value token value
     * @return new instance
     */
    public static Token<Float> newFloatToken(final Float value) {
        return new Token<Float>(TokenType.FLOAT, value);
    }

    /**
     * Creates a boolean token.
     *
     * @param value token value
     * @return new instance
     */
    public static Token<Boolean> newBooleanToken(final Boolean value) {
        return new Token<Boolean>(TokenType.BOOLEAN, value);
    }

    /**
     * Creates a end of file token.
     *
     * @return {@link Null} as a placeholder
     */
    public static Token<Null> newEndOfFileToken() {
        return new Token<Null>(TokenType.EOF, Null.getInstance());
    }

    /**
     * Get the tokens type.
     *
     * @return type enum
     */
    public TokenType getType() {
        return type;
    }

    /**
     * Get the tokens value.
     *
     * @return token value.
     */
    public T getValue() {
        return value;
    }

    @Override
    public String toString() {
        return Objects.toStringHelper(this)
                      .add("type", type)
                      .add("value", value)
                      .toString();
    }

}
