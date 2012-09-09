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
 * Does nothing instead of calling {@link System#exit(int)}.
 *
 * Useful in tests where you don't want to exit the JVM.
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
public class NullExiter extends ExitableAdapter {

    @Override
    public void exit(final int status) {
        // Do nothing here.
    }

}
