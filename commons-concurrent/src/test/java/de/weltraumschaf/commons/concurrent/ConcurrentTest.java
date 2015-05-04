/*
 *  LICENSE
 *
 * "THE BEER-WARE LICENSE" (Revision 43):
 * "Sven Strittmatter" &lt;weltraumschaf@googlemail.com&gt; wrote this file.
 * As long as you retain this notice you can do whatever you want with
 * this stuff. If we meet some day, and you think this stuff is worth it,
 * you can buy me a non alcohol-free beer in return.
 *
 * Copyright (C) 2012 "Sven Strittmatter" &lt;weltraumschaf@googlemail.com&gt;
 */
package de.weltraumschaf.commons.concurrent;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import org.junit.Test;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;
import org.junit.Rule;
import org.junit.rules.ExpectedException;

/**
 * Tests for {@link Concurrent}.
 *
 * @author Sven Strittmatter &lt;weltraumschaf@googlemail.com&gt;
 */
public class ConcurrentTest {

    @Rule
    //CHECKSTYLE:OFF
    public final ExpectedException thrown = ExpectedException.none();
    //CHECKSTYLE:ON

    @Test
    public void invokeConstructorByReflectionThrowsException() throws Exception {
        assertThat(Concurrent.class.getDeclaredConstructors().length, is(1));

        final Constructor<Concurrent> ctor = Concurrent.class.getDeclaredConstructor();
        ctor.setAccessible(true);

        thrown.expect(either(instanceOf(UnsupportedOperationException.class))
                .or(instanceOf(InvocationTargetException.class)));
        ctor.newInstance();
    }

    @Test
    public void newStack_neverNull() {
        assertThat(Concurrent.newStack(), is(not(nullValue())));
        assertThat(Concurrent.newStack(), is(not(nullValue())));
        assertThat(Concurrent.newStack(), is(not(nullValue())));
    }

    @Test
    public void newStack_alwaysNewInstance() {
        final Stack<Object> one = Concurrent.newStack();
        final Stack<Object> two = Concurrent.newStack();
        final Stack<Object> three = Concurrent.newStack();

        assertThat(one, is(not(sameInstance(two))));
        assertThat(one, is(not(sameInstance(three))));
        assertThat(two, is(not(sameInstance(three))));
    }

    @Test
    public void newQueue_neverNull() {
        assertThat(Concurrent.newQueue(), is(not(nullValue())));
        assertThat(Concurrent.newQueue(), is(not(nullValue())));
        assertThat(Concurrent.newQueue(), is(not(nullValue())));
    }

    @Test
    public void newQueue_alwaysNewInstance() {
        final Queue<Object> one = Concurrent.newQueue();
        final Queue<Object> two = Concurrent.newQueue();
        final Queue<Object> three = Concurrent.newQueue();

        assertThat(one, is(not(sameInstance(two))));
        assertThat(one, is(not(sameInstance(three))));
        assertThat(two, is(not(sameInstance(three))));
    }
}
