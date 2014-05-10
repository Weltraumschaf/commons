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

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;
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
    public void add() {
        sut.add("foo");
        assertThat(sut.isEmpty(), is(false));

        sut.add("bar");
        assertThat(sut.isEmpty(), is(false));
    }

    @Test
    public void get() {
        assertThat(sut.get(), is(nullValue()));

        sut.add("foo");
        assertThat(sut.get(), is(equalTo("foo")));
        assertThat(sut.isEmpty(), is(true));

        assertThat(sut.get(), is(nullValue()));

        sut.add("foo");
        sut.add("bar");
        sut.add("baz");

        assertThat(sut.get(), is(equalTo("foo")));
        assertThat(sut.isEmpty(), is(false));

        assertThat(sut.get(), is(equalTo("bar")));
        assertThat(sut.isEmpty(), is(false));

        assertThat(sut.get(), is(equalTo("baz")));
        assertThat(sut.isEmpty(), is(true));
        assertThat(sut.get(), is(nullValue()));
    }

    @Test
    public void testHashCode() {
        assertThat(sut.hashCode(), is(sut.hashCode()));

        final Queue<String> otherSut = Concurrent.newQueue();

        sut.add("foo");
        assertThat(sut.hashCode(), is(sut.hashCode()));
        assertThat(sut.hashCode(), is(not(otherSut.hashCode())));

        sut.add("bar");
        assertThat(sut.hashCode(), is(sut.hashCode()));
        assertThat(sut.hashCode(), is(not(otherSut.hashCode())));

        otherSut.add("foo");
        assertThat(sut.hashCode(), is(sut.hashCode()));
        assertThat(sut.hashCode(), is(not(otherSut.hashCode())));

        otherSut.add("bar");
        assertThat(sut.hashCode(), is(sut.hashCode()));
        assertThat(sut.hashCode(), is(otherSut.hashCode()));

        otherSut.get();
        otherSut.get();
        // reverse order
        otherSut.add("bar");
        otherSut.add("foo");
        assertThat(sut.hashCode(), is(otherSut.hashCode()));
    }

    @Test
    public void testEquals() {
        assertThat(sut.equals(sut), is(true));

        final Queue<String> otherSut = Concurrent.newQueue();
        assertThat(sut.equals(otherSut), is(true));
        assertThat(otherSut.equals(sut), is(true));

        sut.add("foo");
        assertThat(sut.equals(otherSut), is(false));
        assertThat(otherSut.equals(sut), is(false));

        sut.add("bar");
        assertThat(sut.equals(otherSut), is(false));
        assertThat(otherSut.equals(sut), is(false));

        otherSut.add("foo");
        assertThat(sut.equals(otherSut), is(false));
        assertThat(otherSut.equals(sut), is(false));

        otherSut.add("bar");
        assertThat(sut.equals(otherSut), is(true));
        assertThat(otherSut.equals(sut), is(true));

        otherSut.get();
        otherSut.get();
        // reverse order
        otherSut.add("bar");
        otherSut.add("foo");
        assertThat(sut.equals(otherSut), is(false));
        assertThat(otherSut.equals(sut), is(false));

        assertThat(sut.equals(null), is(false));
        assertThat(sut.equals(""), is(false));
    }

    @Test
    public void testToString() {
        assertThat(sut.toString(), is(equalTo("ConcurrentQueue[]")));

        sut.add("foo");
        assertThat(sut.toString(), is(equalTo("ConcurrentQueue[foo]")));

        sut.add("bar");
        assertThat(sut.toString(), is(equalTo("ConcurrentQueue[foo, bar]")));

        sut.add("baz");
        assertThat(sut.toString(), is(equalTo("ConcurrentQueue[foo, bar, baz]")));
    }
}
