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

import de.weltraumschaf.commons.system.ExitCode;
import de.weltraumschaf.commons.validate.Validate;

/**
 * Used to signal abnormal exceptional application states.
 *
 * <p>
 * If an exception if this type is thrown should cause program termination with the exit code.
 * </p>
 *
 * @since 1.0.0
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
public class ApplicationException extends Exception {

    /**
     * Exit code used to return to JVM.
     */
    private final ExitCode exitCode;

    /**
     * Initializes the cause with {@code null}.
     *
     * @param exitCode must not be {@code null}
     * @param message must not be {@code null} or empty
     */
    public ApplicationException(final ExitCode exitCode, final String message) {
        this(exitCode, message, null);
    }

    /**
     * Dedicated constructor.
     *
     * @param exitCode must not be {@code null}
     * @param message must not be {@code null} or empty
     * @param cause may be {@code null}
     */
    public ApplicationException(final ExitCode exitCode, final String message, final Throwable cause) {
        super(Validate.notEmpty(message, "Parameter 'message' mut not be null or empty"), cause);

        if (null == exitCode) {
            throw new NullPointerException("Parameter 'exitCode' must not be null!");
        }

        this.exitCode = exitCode;
    }

    /**
     * Get program exit code.
     *
     * @return never {@code null}
     */
    public ExitCode getExitCode() {
        return exitCode;
    }

    @Override
    public String toString() {
        return super.toString() + " (" + exitCode + ')';
    }

}
