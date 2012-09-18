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

package de.weltraumschaf.commons.system;

/**
 * An exitable gives the interface to exit an program w/o calling {@link System#exit(int)} directly.
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
public interface Exitable {

    /**
     * Exits the program.
     *
     * @param status Status code.
     */
    void exit(int status);

    /**
     * Exits the program.
     *
     * @param status Exit code.
     */
    void exit(final ExitCode status);

}