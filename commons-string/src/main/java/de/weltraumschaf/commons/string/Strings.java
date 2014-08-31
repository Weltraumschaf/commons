/*
 *  LICENSE
 *
 * "THE BEER-WARE LICENSE" (Revision 43):
 * "Sven Strittmatter" <weltraumschaf@googlemail.com> wrote this file.
 * As long as you retain this notice you can do whatever you want with
 * this stuff. If we meet some day, and you think this stuff is worth it,
 * you can buy me a non alcohol-free beer in return.
 *
 * Copyright (C) 2012 "Sven Strittmatter" <weltraumschaf@googlemail.com>
 */

package de.weltraumschaf.commons.string;

/**
 * Various string utility methods.
 *
 * @since 1.0.1
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
public final class Strings {

    /**
     * Hidden for pure static utility class.
     */
    private Strings() {
        super();
        throw new UnsupportedOperationException("Do not call by reflection!");
    }

    /**
     * Trims passed in string and does not throw {@link NullPointerException} if passed in string is {@code null}.
     *
     * @param fileName may be {@code null}
     * @return never {@code null}
     */
    public static String nullAwareTrim(final String fileName) {
        return null == fileName ? "" : fileName.trim();
    }

}
