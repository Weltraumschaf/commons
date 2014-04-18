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
package de.weltraumschaf.commons.guava;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashSet;
import java.util.Set;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.junit.Assert.assertThat;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

/**
 * Tests for {@link Sets}.
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
public class SetsTest {

    @Rule
    //CHECKSTYLE:OFF
    public final ExpectedException thrown = ExpectedException.none();
    //CHECKSTYLE:ON

    @Test
    public void invokeConstructorByReflectionThrowsException() throws Exception {
        assertThat(Sets.class.getDeclaredConstructors().length, is(1));

        final Constructor<Sets> ctor = Sets.class.getDeclaredConstructor();
        ctor.setAccessible(true);

        thrown.expect(either(instanceOf(UnsupportedOperationException.class))
                .or(instanceOf(InvocationTargetException.class)));
        ctor.newInstance();
    }

    @Test
    public void newHashSet() {
        final Set<String> l1 = Sets.newHashSet("foo", "bar", "baz");
        assertThat(l1, is(not(nullValue())));
        assertThat(l1, is(instanceOf(HashSet.class)));
        assertThat(l1, containsInAnyOrder("foo", "bar", "baz"));

        final Set<String> l2 = Sets.newHashSet("foo", "bar", "baz");
        assertThat(l2, is(not(nullValue())));
        assertThat(l2, is(instanceOf(HashSet.class)));
        assertThat(l2, containsInAnyOrder("foo", "bar", "baz"));
        assertThat(l2, is(not(sameInstance(l1))));
        assertThat(l1, is(equalTo(l1)));

        final Set<String> l3 = Sets.newHashSet("foo", "bar", "baz");
        assertThat(l3, is(not(nullValue())));
        assertThat(l3, is(instanceOf(HashSet.class)));
        assertThat(l3, containsInAnyOrder("foo", "bar", "baz"));
        assertThat(l3, is(not(sameInstance(l1))));
        assertThat(l3, is(equalTo(l1)));
        assertThat(l3, is(not(sameInstance(l2))));
        assertThat(l3, is(equalTo(l2)));
    }

    @Test
    public void newHashSetWithExpectedSize() {
        final Set<Object> l1 = Sets.newHashSetWithExpectedSize(5);

        assertThat(l1, is(not(nullValue())));
        assertThat(l1, is(instanceOf(HashSet.class)));

        final Set<Object> l2 = Sets.newHashSetWithExpectedSize(5);
        assertThat(l2, is(not(nullValue())));
        assertThat(l2, is(instanceOf(HashSet.class)));
        assertThat(l2, is(not(sameInstance(l1))));

        final Set<Object> l3 = Sets.newHashSetWithExpectedSize(5);
        assertThat(l3, is(not(nullValue())));
        assertThat(l3, is(instanceOf(HashSet.class)));
        assertThat(l3, is(not(sameInstance(l1))));
        assertThat(l3, is(not(sameInstance(l2))));
    }

    @Test(expected = IllegalArgumentException.class)
    public void capacity_negative() {
        Sets.capacity(-1);
    }

    @Test
    public void capacity() {
        assertThat(Sets.capacity(0), is(1));
        assertThat(Sets.capacity(1), is(2));
        assertThat(Sets.capacity(2), is(3));
        assertThat(Sets.capacity(3), is(4));
        assertThat(Sets.capacity(4), is(5));
        assertThat(Sets.capacity(5), is(6));
        assertThat(Sets.capacity(6), is(8));
        assertThat(Sets.capacity(7), is(9));
        assertThat(Sets.capacity(8), is(10));
        assertThat(Sets.capacity(9), is(12));
        assertThat(Sets.capacity(10), is(13));
        assertThat(Sets.capacity(11), is(14));
        assertThat(Sets.capacity(12), is(16));
        assertThat(Sets.capacity(13), is(17));
        assertThat(Sets.capacity(14), is(18));
        assertThat(Sets.capacity(15), is(20));
        assertThat(Sets.capacity(20), is(26));
        assertThat(Sets.capacity(23), is(30));
        assertThat(Sets.capacity(30), is(40));
        assertThat(Sets.capacity(40), is(53));
        assertThat(Sets.capacity(42), is(56));
        assertThat(Sets.capacity(50), is(66));
        assertThat(Sets.capacity(1000), is(1333));
        assertThat(Sets.capacity(1024), is(1365));
        assertThat(Sets.capacity(1073741824), is(Integer.MAX_VALUE));
    }
}
