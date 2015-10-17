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
package de.weltraumschaf.commons.shell.token;

import net.jcip.annotations.ThreadSafe;

/**
 * Represents a null value.
 * <p>
 * Used instead of {@code null} to prevent null pointer exceptions.
 * Implemented as singleton w/o any state, so thread safe.
 * </p>
 *
 * @since 1.0.0
 */
@ThreadSafe
public final class Null {

    /**
     * The only reusable instance.
     */
    public static final Null NULL = new Null();

    /**
     * Hidden to prevent multiple instances.
     */
    private Null() {
        super();
    }

}
