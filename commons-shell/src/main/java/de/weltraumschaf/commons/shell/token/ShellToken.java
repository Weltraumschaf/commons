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

package de.weltraumschaf.commons.shell.token;

import de.weltraumschaf.commons.parse.token.Token;

/**
 * Defines a token.
 *
 * @since 1.0.0
 * @author Sven Strittmatter &lt;weltraumschaf@googlemail.com&gt;
 * @version $Id: $Id
 */
public interface ShellToken extends Token {

    /**
     * Get the token type class.
     *
     * @return never {@code null}
     */
    TokenType getType();

    /**
     * Get the boolean typed value.
     *
     * @return never {@code null}
     */
    Boolean asBoolean();

    /**
     * Get the float typed value.
     *
     * @return never {@code null}
     */
    Float asFloat();

    /**
     * Get the integer typed value.
     *
     * @return never {@code null}
     */
    Integer asInteger();

    /**
     * Get the string typed value.
     *
     * @return never {@code null}
     */
    String asString();

}
