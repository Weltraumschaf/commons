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
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
public interface Invokable {

    /**
     * Initializes the invokable.
     */
    void init();

    /**
     * Template method for main application code.
     *
     * @throws Exception If something goes wrong.
     */
    void execute() throws Exception;

    /**
     * Get stream object for I/O.
     *
     * @return Return always same instance until it was
     *        {@link #setIoStreams(de.weltraumschaf.commons.IOStreams) reset}.
     */
    IO getIoStreams();

    /**
     * Set stream object for I/O.
     *
     * @param ioStreams Stream object.
     */
    void setIoStreams(IO ioStreams);

    /**
     * Exits the invokable.
     *
     * @param status exit status.
     */
    void exit(ExitCode status);

    /**
     * Exits the invokable.
     *
     * @param status exit status.
     */
    void exit(int status);

    /**
     * Set an exiter to handle exit calls.
     *
     * @param exiter Object which handles exit calls.
     */
    void setExiter(Exitable exiter);

}
