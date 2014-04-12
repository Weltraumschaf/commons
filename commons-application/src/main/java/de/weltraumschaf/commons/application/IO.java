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
    void printStackTrace(Throwable ex);

    /**
     * Prints string line.
     *
     * @param str String to print.
     */
    void println(final String str);

    /**
     * Get standard errorln output stream.
     *
     * @return Print stream object.
     */
    PrintStream getStderr();

    /**
     * Get standard input stream.
     *
     * @return Input stream object.
     */
    InputStream getStdin();

    /**
     * Get standard output stream.
     *
     * @return Print stream object.
     */
    PrintStream getStdout();

}
