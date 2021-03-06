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
package de.weltraumschaf.commons.application;

import java.io.InputStream;
import java.io.PrintStream;

/**
 * Implementations provide an aggregate object which contains STDIN, STDOUT and STDERR streams.
 *
 * @since 1.0.0
 * @author Sven Strittmatter &lt;weltraumschaf@googlemail.com&gt;
 */
public interface IO {

    /**
     * Prints error.
     *
     * @param str string to print
     */
    void error(final String str);

    /**
     * Prints error line.
     *
     * @param str string to print
     */
    void errorln(final String str);

    /**
     * Prints string.
     *
     * @param str string to print
     */
    void print(final String str);

    /**
     * Prints exception stack trace.
     *
     * @param ex exception to print
     */
    void printStackTrace(Throwable ex);

    /**
     * Prints string line.
     *
     * @param str string to print
     */
    void println(final String str);

    /**
     * Get standard errorln output stream.
     *
     * @return never {@code null}
     */
    PrintStream getStderr();

    /**
     * Get standard input stream.
     *
     * @return never {@code null}
     */
    InputStream getStdin();

    /**
     * Get standard output stream.
     *
     * @return never {@code null}
     */
    PrintStream getStdout();

}
