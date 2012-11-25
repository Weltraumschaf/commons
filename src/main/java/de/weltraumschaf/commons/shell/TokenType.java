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

/**
 * Type of tokens scanned from interactive shell.
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
public enum TokenType {

    /**
     * A command or subcommand literal.
     */
    KEYWORD,
    /**
     * Defines a quote delimited string.
     *
     * '...' or "..."
     */
    STRING,
    /**
     * Defines literal string token type.
     *
     * [a-zA-Z][a-zA-Z0-9]+
     */
    LITERAL,
    /**
     * Defines integer token type.
     *
     * [0-9]+
     */
    NUMBER;

}