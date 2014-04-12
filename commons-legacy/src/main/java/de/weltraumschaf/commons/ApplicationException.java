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
package de.weltraumschaf.commons;

import de.weltraumschaf.commons.system.ExitCode;

/**
 * Used to signal abnormal exceptional application states.
 *
 * If an exception if this type is thrown should cause program termination with the exit code.
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
public class ApplicationException extends Exception {

    /**
     * Exit code used to return to JVM.
     */
    private final ExitCode exitCode;

    /**
     * Dedicated constructor.
     *
     * @param exitCode must not be {@code null}
     * @param message passed to {@link RuntimeException#RuntimeException(java.lang.String, java.lang.Throwable)}
     * @param cause passed to {@link RuntimeException#RuntimeException(java.lang.String, java.lang.Throwable)}
     */
    public ApplicationException(final ExitCode exitCode, final String message, final Throwable cause) {
        super(message, cause);

        if (null == exitCode) {
            throw new NullPointerException("Parameter exitCode must not be null!");
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

}
