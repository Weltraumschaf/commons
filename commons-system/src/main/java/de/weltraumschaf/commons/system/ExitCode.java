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
package de.weltraumschaf.commons.system;

/**
 * Defines an exit code.
 *
 * <p>
 * Usually you will implement this interface from an enum:
 * </p>
 *
 * <pre>{@code
 * public enum ExitCodeImpl implements ExitCode {
 *
 *      OK(0), ERROR(1), FATAL(-1);
 *
 *      private final int code;
 *
 *      public ExitCodeImpl(final int code) {
 *          this.code = code;
 *      }
 *
 *      &#064;Override
 *      public int getCode() [
 *          return code;
 *      }
 *
 * }
 * code}</pre>
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
public interface ExitCode {

    /**
     * Returns the integer representation of the exit code.
     *
     * A integer of 0 means everything ok. Everything else
     * signals an erroneous exit code.
     *
     * @return Return integer.
     */
    int getCode();

}
