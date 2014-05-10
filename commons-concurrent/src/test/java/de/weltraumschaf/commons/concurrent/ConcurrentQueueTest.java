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
package de.weltraumschaf.commons.concurrent;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import org.junit.Ignore;
import org.junit.Test;

/**
 * Tests for {@link ConcurrentQueue}.
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
public class ConcurrentQueueTest {

    private final Queue<String> sut = Concurrent.newQueue();

    @Test
    public void isEmpty() {
        assertThat(sut.isEmpty(), is(true));
    }

    @Test
    @Ignore
    public void add() {

    }

    @Test
    @Ignore
    public void get() {

    }

    @Test
    @Ignore
    public void testHashCode() {

    }

    @Test
    @Ignore
    public void testEquals() {

    }

    @Test
    @Ignore
    public void testToString() {

    }
}