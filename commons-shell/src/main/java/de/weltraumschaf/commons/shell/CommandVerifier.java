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
 * Implementors verifies the passed command.
 * <p>
 * Verification means:
 * </p>
 * <ul>
 * <li>Check if a main command has a legal sub command.</li>
 * <li>Check if a command has legal arguments.</li>
 * </ul>
 * <p>
 * If a check fails throw {@link de.weltraumschaf.commons.shell.SyntaxException}.
 * </p>
 *
 * @since 1.0.0
 * @author Sven Strittmatter &lt;weltraumschaf@googlemail.com&gt;
 */
public interface CommandVerifier {

    /**
     * Verifies parsed command of consistency.
     * <p>
     * Consistency checks are:
     * </p>
     * <ul>
     * <li>correct sub command type</li>
     * <li>correct number of arguments</li>
     * </ul>
     *
     * @param cmd command to verify
     * @throws de.weltraumschaf.commons.shell.SyntaxException if, verification has failed
     */
    void verifyCommand(ShellCommand cmd) throws SyntaxException;

}
