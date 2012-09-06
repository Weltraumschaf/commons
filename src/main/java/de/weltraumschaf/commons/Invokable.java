/*
 * LICENSE
 *
 * "THE BEER-WARE LICENSE" (Revision 42):
 * "Sven Strittmatter" <weltraumschaf@googlemail.com> wrote this file.
 * As long as you retain this notice you can do whatever you want with
 * this stuff. If we meet some day, and you think this stuff is worth it,
 * you can buy me a beer in return.
 *
 */

package de.weltraumschaf.commons;

/**
 * An invokable is an application object invokable by command line interface.
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
public interface Invokable {

    /**
     * Initializes the invokable.
     *
     * @throws Exception If something goes wrong.
     */
    void init() throws Exception;

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
    IOStreams getIoStreams();

    /**
     * Set stream object for I/O.
     *
     * @param ioStreams Stream object.
     */
    void setIoStreams(IOStreams ioStreams);

}
