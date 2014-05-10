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

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertThat;
import org.junit.Test;

/**
 * Tests for {@link ConcurrentStack}.
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
public class ConcurrentStackTest {

    private final Stack<String> sut = Concurrent.newStack();

    @Test
    public void isEmpty() {
        assertThat(sut.isEmpty(), is(true));
    }

    @Test
    public void peek() {
        sut.push("foo");
        sut.push("bar");

        assertThat(sut.peek(), is(equalTo("bar")));
        assertThat(sut.pop(), is(equalTo("bar")));
        assertThat(sut.peek(), is(equalTo("foo")));
        assertThat(sut.pop(), is(equalTo("foo")));
    }

    @Test
    public void pop() {
        sut.push("foo");
        sut.push("bar");
        sut.push("baz");

        assertThat(sut.pop(), is(equalTo("baz")));
        assertThat(sut.pop(), is(equalTo("bar")));
        assertThat(sut.pop(), is(equalTo("foo")));
        assertThat(sut.isEmpty(), is(true));
    }

    @Test
    public void push() {
        sut.push("foo");
        assertThat(sut.isEmpty(), is(false));
    }

    @Test
    public void testHashCode() {
        assertThat(sut.hashCode(), is(sut.hashCode()));

        final Stack<String> otherSut = Concurrent.newStack();

        sut.push("foo");
        assertThat(sut.hashCode(), is(sut.hashCode()));
        assertThat(sut.hashCode(), is(not(otherSut.hashCode())));

        sut.push("bar");
        assertThat(sut.hashCode(), is(sut.hashCode()));
        assertThat(sut.hashCode(), is(not(otherSut.hashCode())));

        otherSut.push("foo");
        assertThat(sut.hashCode(), is(sut.hashCode()));
        assertThat(sut.hashCode(), is(not(otherSut.hashCode())));

        otherSut.push("bar");
        assertThat(sut.hashCode(), is(sut.hashCode()));
        assertThat(sut.hashCode(), is(otherSut.hashCode()));

        otherSut.pop();
        otherSut.pop();
        // reverse order
        otherSut.push("bar");
        otherSut.push("foo");
        assertThat(sut.hashCode(), is(otherSut.hashCode()));
    }

    @Test
    public void testEquals() {
        assertThat(sut.equals(sut), is(true));

        final Stack<String> otherSut = Concurrent.newStack();
        assertThat(sut.equals(otherSut), is(true));
        assertThat(otherSut.equals(sut), is(true));

        sut.push("foo");
        assertThat(sut.equals(otherSut), is(false));
        assertThat(otherSut.equals(sut), is(false));

        sut.push("bar");
        assertThat(sut.equals(otherSut), is(false));
        assertThat(otherSut.equals(sut), is(false));

        otherSut.push("foo");
        assertThat(sut.equals(otherSut), is(false));
        assertThat(otherSut.equals(sut), is(false));

        otherSut.push("bar");
        assertThat(sut.equals(otherSut), is(true));
        assertThat(otherSut.equals(sut), is(true));

        otherSut.pop();
        otherSut.pop();
        // reverse order
        otherSut.push("bar");
        otherSut.push("foo");
        assertThat(sut.equals(otherSut), is(false));
        assertThat(otherSut.equals(sut), is(false));

        assertThat(sut.equals(null), is(false));
        assertThat(sut.equals(""), is(false));
    }

    @Test
    public void testToString() {
        assertThat(sut.toString(), is(equalTo("ConcurrentStack[]")));

        sut.push("foo");
        assertThat(sut.toString(), is(equalTo("ConcurrentStack[foo]")));

        sut.push("bar");
        assertThat(sut.toString(), is(equalTo("ConcurrentStack[bar, foo]")));

        sut.push("baz");
        assertThat(sut.toString(), is(equalTo("ConcurrentStack[baz, bar, foo]")));
    }
}
