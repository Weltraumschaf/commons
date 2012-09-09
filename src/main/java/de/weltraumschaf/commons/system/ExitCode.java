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
 * Defines an exit code.
 *
 * Usually you will implement this interface from an enum:
 *
 * <code>
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
 * </code>
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
