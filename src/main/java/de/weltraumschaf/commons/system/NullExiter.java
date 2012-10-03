/*
 * LICENSE
 *
 * "THE BEER-WARE LICENSE" (Revision 43):
 * "Sven Strittmatter" <weltraumschaf(at)googlemail(dot)com> wrote this file.
 * As long as you retain this notice you can do whatever you want with
 * this stuff. If we meet some day, and you think this stuff is worth it,
 * you can buy me a non alcohol free beer in return.
 *
 * Copyright (C) 2012 "Sven Strittmatter" <weltraumschaf(at)googlemail(dot)com>
 */
package de.weltraumschaf.commons.system;

/**
 * Does nothing instead of calling {@link System#exit(int)}.
 *
 * Useful in tests where you don't want to exit the JVM.
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
public final class NullExiter extends ExitableAdapter {

    @Override
    public void exit(final int status) {
        // Do nothing here.
    }

}
