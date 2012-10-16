/*
 * LICENSE
 *
 * "THE BEER-WARE LICENSE" (Revision 43):
 * "Sven Strittmatter" <weltraumschaf(at)googlemail(dot)com> wrote this file.
 * As long as you retain this notice you can do whatever you want with
 * this stuff. If we meet some day, and you think this stuff is worth it,
 * you can buy me a non alcohol free beer in return.
 *
 * Copyright (C) 2012 "Sven Strittmatter" <weltraumschaf(at)googlemail(dot)com>
 */
package de.weltraumschaf.commons;

/**
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
public interface IO {

    /**
     * Prints error.
     *
     * @param str String to print.
     */
    void error(final String str);

    /**
     * Prints error line.
     *
     * @param str String to print.
     */
    void errorln(final String str);

    /**
     * Prints string.
     *
     * @param str String to print.
     */
    void print(final String str);

    /**
     * Prints exception stack trace.
     *
     * @param ex Exception to print.
     */
    void printStackTrace(Exception ex);

    /**
     * Prints string line.
     *
     * @param str String to print.
     */
    void println(final String str);

}
