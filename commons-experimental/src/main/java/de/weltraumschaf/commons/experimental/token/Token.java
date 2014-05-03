/*
 *  LICENSE
 *
 * "THE BEER-WARE LICENSE" (Revision 43):
 * "Sven Strittmatter" <weltraumschaf@googlemail.com> wrote this file.
 * As long as you retain this notice you can do whatever you want with
 * this stuff. If we meet some day, and you think this stuff is worth it,
 * you can buy me a non alcohol-free beer in return.
 *
 * Copyright (C) 2012 "Sven Strittmatter" <weltraumschaf@googlemail.com>
 */

package de.weltraumschaf.commons.experimental.token;

/**
 * Defines a token.
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
public interface Token {

    /**
     * Start position of the token.
     *
     * @return never {@code null}
     */
    Position getPosition();

    /**
     * The raw string recognized by the scanner.
     * <p>
     * For example a string token {@code "foo"} has the raw value {@code "foo"} in contrast
     * it's typed {@link #asString() string value} will be {@code foo}.
     * </p>
     *
     * @return never {@code null}
     */
    String getRaw();

    /**
     * Get the token type class.
     *
     * @return never {@code null}
     */
    TokenType getType();

    /**
     * Get the boolean typed value.
     * <p>
     * This method throws {@link UnsupportedOperationException} if the token's {@link #getType() token class}
     * is not of type {@link TokenType#BOOLEAN}.
     * </p>
     *
     * @return never {@code null}
     */
    Boolean asBoolean();

    /**
     * Get the float typed value.
     * <p>
     * This method throws {@link UnsupportedOperationException} if the token's {@link #getType() token class}
     * is not of type {@link TokenType#FLOAT}.
     * </p>
     *
     * @return never {@code null}
     */
    Float asFloat();

    /**
     * Get the integer typed value.
     * <p>
     * This method throws {@link UnsupportedOperationException} if the token's {@link #getType() token class}
     * is not of type {@link TokenType#INTEGER}.
     * </p>
     *
     * @return never {@code null}
     */
    Integer asInteger();

    /**
     * Get the string typed value.
     * <p>
     * This method never throws {@link UnsupportedOperationException}.
     * It always calls {@link Object#toString()} on the recognized typed value.
     * </p>
     *
     * @return never {@code null}
     */
    String asString();

}
