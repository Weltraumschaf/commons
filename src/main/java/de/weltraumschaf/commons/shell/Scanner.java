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

package de.weltraumschaf.commons.shell;

import java.util.List;

/**
 * Scans the input line from an interactive shell.
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
public interface Scanner {

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
    List<Token> scan(final String line) throws SyntaxException;

}
