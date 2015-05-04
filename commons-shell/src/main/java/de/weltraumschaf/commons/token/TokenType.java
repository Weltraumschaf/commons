/*
 *  LICENSE
 *
 * "THE BEER-WARE LICENSE" (Revision 43):
 * "Sven Strittmatter" &lt;weltraumschaf@googlemail.com&gt; wrote this file.
 * As long as you retain this notice you can do whatever you want with
 * this stuff. If we meet some day, and you think this stuff is worth it,
 * you can buy me a non alcohol-free beer in return.
 *
 * Copyright (C) 2012 "Sven Strittmatter" &lt;weltraumschaf@googlemail.com&gt;
 */

package de.weltraumschaf.commons.token;

/**
 * Defines the token classes.
 *
 * @since 1.0.0
 * @author Sven Strittmatter &lt;weltraumschaf@googlemail.com&gt;
 */
public enum TokenType {

    /**
     * Token class with a types value of type {@link java.lang.Boolean}.
     */
    BOOLEAN,
    /**
     * Token class with a types value of type {@link java.lang.Integer}.
     */
    INTEGER,
    /**
     * Token class with a types value of type {@link java.lang.Float}.
     */
    FLOAT,
    /**
     * Token class with a types value of type {@link java.lang.String}.
     */
    KEYWORD,
    /**
     * Token class with a types value of type {@link java.lang.String}.
     */
    LITERAL,
    /**
     * Token class with a types value of type {@link java.lang.String}.
     */
    STRING;
}
