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
import java.util.List;

/**
 * Scans the input line from an interactive shell.
 *
 * @since 1.0.0
 * @author Sven Strittmatter &lt;weltraumschaf@googlemail.com&gt;
 * @version $Id: $Id
 */
public interface Scanner {

    /**
     * Scans give line and returns list of recognized tokens.
     *
     * @param line line to scan.
     * @return List of recognized, never null
     * @throws de.weltraumschaf.commons.shell.SyntaxException if, syntax error occurred.
     * // CHECKSTYLE:OFF
     */
    List<Token> scan(final String line) throws SyntaxException;

}
