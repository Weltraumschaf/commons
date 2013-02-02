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
 * Represents a null value.
 *
 * Used instead of {@code null} to prevent null pointer exceptions.
 * Implemented as singleton w/o any state.
 *
 * @author "Sven Strittmatter" <weltraumschaf@googlemail.com>
 */
public final class Null {

    /**
     * The only instance.
     */
    private static final Null INSTANCE = new Null();

    /**
     * Hidden to prevent multiple instances.
     */
    private Null() {
        super();
    }

    /**
     * Getter to obtain the instance.
     *
     * @return always same instance
     */
    public static Null getInstance() {
        return INSTANCE;
    }
}
