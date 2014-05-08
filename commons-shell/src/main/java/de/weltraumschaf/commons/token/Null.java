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
package de.weltraumschaf.commons.token;

/**
 * Represents a null value.
 * <p>
 * Used instead of {@code null} to prevent null pointer exceptions.
 * Implemented as singleton w/o any state.
 * </p>
 *
 * @since 1.0.0
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
