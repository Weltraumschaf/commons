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

import de.weltraumschaf.commons.guava.Objects;

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
     * Position of the token in the source.
     */
    private final Position position;

    /**
     * Hide constructor to force usage of factory methods.
     *
     * @param type Token type.
     * @param value Token value.
     * @param pos source position of token
     */
    Token(final TokenType type, final T value, final Position pos) {
        super();
        this.type = type;
        this.value = value;
        this.position = pos;
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

    /**
     * Get the token position.
     *
     * @return position instance
     */
    public Position getPosition() {
        return position;
    }

    @Override
    public String toString() {
        return Objects.toStringHelper(this)
                      .add("type", type)
                      .add("value", value)
                      .toString();
    }

}
