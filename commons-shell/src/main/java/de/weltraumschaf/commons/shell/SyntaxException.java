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
 * Signals syntax errors in the input scanned from an interactive shell.
 *
 * @since 1.0.0
 * @author Sven Strittmatter &lt;weltraumschaf@googlemail.com&gt;
 * @version $Id: $Id
 */
public final class SyntaxException extends Exception {

    /**
     * Default exception message.
     */
    private static final String DEFAULT_MSG = "Syntax error!";

    /**
     * Default constructor.
     */
    public SyntaxException() {
        this(DEFAULT_MSG);
    }

    /**
     * Exception with custom error message.
     *
     * @param message exception message
     */
    public SyntaxException(final String message) {
        this(message, null);
    }

    /**
     * Exception with default message.
     *
     * @param cause previously thrown exception
     */
    public SyntaxException(final Throwable cause) {
        this(DEFAULT_MSG, cause);
    }

    /**
     * Dedicated constructor.
     *
     * @param message exception message
     * @param cause previously thrown exception
     */
    public SyntaxException(final String message, final Throwable cause) {
        super(message, cause);
    }

}
