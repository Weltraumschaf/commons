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

import java.util.HashSet;
import java.util.Set;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.CoreMatchers.sameInstance;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.junit.Assert.assertThat;
import org.junit.Ignore;
import org.junit.Test;

/**
 * Tests for {@link Sets}.
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
public class SetsTest {

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

    @Test
    @Ignore("TODO: Implement test for Sets#capacity()")
    public void capacity() {

    }
}
