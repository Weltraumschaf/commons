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
import de.weltraumschaf.commons.system.Exitable;

/**
 * An invokable is an application object invokable by command line interface.
 *
 * @since 1.0.0
 * @author Sven Strittmatter &lt;weltraumschaf@googlemail.com&gt;
 */
public interface Invokable {

    /**
     * Initializes the invokable.
     */
    void init();

    /**
     * Template method for main application code.
     *
     * @throws java.lang.Exception If something goes wrong.
     */
    void execute() throws Exception;

    /**
     * Get stream object for I/O.
     *
     * @return never {@code null}, always same instance until it was
     *        {@link #setIoStreams(de.weltraumschaf.commons.application.IO) reset}
     */
    IO getIoStreams();

    /**
     * Set stream object for I/O.
     *
     * @param ioStreams must not be {@code null}
     */
    void setIoStreams(IO ioStreams);

    /**
     * Exits the invokable.
     *
     * @param status must not be {@code null}
     */
    void exit(ExitCode status);

    /**
     * Exits the invokable.
     *
     * @param status any int
     */
    void exit(int status);

    /**
     * Set an {@code exiter} to handle exit calls.
     *
     * @param exiter must not be {@code null}
     */
    void setExiter(Exitable exiter);

    /**
     * <p>isDebugEnabled.</p>
     *
     * @return a boolean.
     */
    boolean isDebugEnabled();

}
