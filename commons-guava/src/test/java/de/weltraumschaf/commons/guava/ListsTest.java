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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

/**
 * Tests for {@link Lists}.
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
public class ListsTest {

    @Rule
    //CHECKSTYLE:OFF
    public final ExpectedException thrown = ExpectedException.none();
    //CHECKSTYLE:ON

    @Test
    public void invokeConstructorByReflectionThrowsException() throws Exception {
        assertThat(Lists.class.getDeclaredConstructors().length, is(1));

        final Constructor<Lists> ctor = Lists.class.getDeclaredConstructor();
        ctor.setAccessible(true);

        thrown.expect(either(instanceOf(UnsupportedOperationException.class))
                .or(instanceOf(InvocationTargetException.class)));
        ctor.newInstance();
    }

    @Test
    public void newArrayList() {
        final List<Object> l1 = Lists.newArrayList();
        assertThat(l1, is(not(nullValue())));
        assertThat(l1, is(instanceOf(ArrayList.class)));

        final List<Object> l2 = Lists.newArrayList();
        assertThat(l2, is(not(nullValue())));
        assertThat(l2, is(instanceOf(ArrayList.class)));
        assertThat(l2, is(not(sameInstance(l1))));

        final List<Object> l3 = Lists.newArrayList();
        assertThat(l3, is(not(nullValue())));
        assertThat(l3, is(instanceOf(ArrayList.class)));
        assertThat(l3, is(not(sameInstance(l1))));
        assertThat(l3, is(not(sameInstance(l2))));
    }

    @Test(expected = NullPointerException.class)
    public void newArrayList_Iterable_nullInput() {
        Lists.newArrayList((Iterable<Object>) null);
    }

    public void newArrayList_Iterable_collectionInput() {
        final List<String> input = Arrays.asList("foo", "bar", "baz");

        final List<String> l1 = Lists.newArrayList(input);
        assertThat(l1, is(not(nullValue())));
        assertThat(l1, is(instanceOf(ArrayList.class)));
        assertThat(l1, is(equalTo(input)));
        assertThat(l1, is(not(sameInstance(input))));

        final List<String> l2 = Lists.newArrayList(input);
        assertThat(l2, is(not(nullValue())));
        assertThat(l2, is(instanceOf(ArrayList.class)));
        assertThat(l2, is(not(sameInstance(l1))));
        assertThat(l2, is(equalTo(input)));
        assertThat(l2, is(not(sameInstance(input))));

        final List<String> l3 = Lists.newArrayList(input);
        assertThat(l3, is(not(nullValue())));
        assertThat(l3, is(instanceOf(ArrayList.class)));
        assertThat(l3, is(not(sameInstance(l1))));
        assertThat(l3, is(not(sameInstance(l2))));
        assertThat(l3, is(equalTo(input)));
        assertThat(l3, is(not(sameInstance(input))));
    }

    public void newArrayList_Iterable_iterableInput() {
        final Iterable<String> input = new IterableStub("foo", "bar", "baz");

        final List<String> l1 = Lists.newArrayList(input);
        assertThat(l1, is(not(nullValue())));
        assertThat(l1, is(instanceOf(ArrayList.class)));
        assertThat(l1, is(equalTo(input)));
        assertThat(l1, is(not(sameInstance(input))));

        final List<String> l2 = Lists.newArrayList(input);
        assertThat(l2, is(not(nullValue())));
        assertThat(l2, is(instanceOf(ArrayList.class)));
        assertThat(l2, is(not(sameInstance(l1))));
        assertThat(l2, is(equalTo(input)));
        assertThat(l2, is(not(sameInstance(input))));

        final List<String> l3 = Lists.newArrayList(input);
        assertThat(l3, is(not(nullValue())));
        assertThat(l3, is(instanceOf(ArrayList.class)));
        assertThat(l3, is(not(sameInstance(l1))));
        assertThat(l3, is(not(sameInstance(l2))));
        assertThat(l3, is(equalTo(input)));
        assertThat(l3, is(not(sameInstance(input))));
    }

    @Test
    public void newArrayList_Iterator() {
        final List<String> input = Arrays.asList("foo", "bar", "baz");

        final List<String> l1 = Lists.newArrayList(new IteratorStub(input.iterator()));
        assertThat(l1, is(not(nullValue())));
        assertThat(l1, is(instanceOf(ArrayList.class)));
        assertThat(l1, is(equalTo(input)));
        assertThat(l1, is(not(sameInstance(input))));

        final List<String> l2 = Lists.newArrayList(new IteratorStub(input.iterator()));
        assertThat(l2, is(not(nullValue())));
        assertThat(l2, is(instanceOf(ArrayList.class)));
        assertThat(l2, is(not(sameInstance(l1))));
        assertThat(l2, is(equalTo(input)));
        assertThat(l2, is(not(sameInstance(input))));

        final List<String> l3 = Lists.newArrayList(new IteratorStub(input.iterator()));
        assertThat(l3, is(not(nullValue())));
        assertThat(l3, is(instanceOf(ArrayList.class)));
        assertThat(l3, is(not(sameInstance(l1))));
        assertThat(l3, is(not(sameInstance(l2))));
        assertThat(l3, is(equalTo(input)));
        assertThat(l3, is(not(sameInstance(input))));
    }

    @Test
    public void newArrayList_VarArgs() {
        final List<String> sut = Lists.newArrayList("foo", "bar", "baz");

        assertThat(sut.get(0), is(equalTo("foo")));
        assertThat(sut.get(1), is(equalTo("bar")));
        assertThat(sut.get(2), is(equalTo("baz")));
    }

    private static final class IterableStub implements Iterable<String> {

        private final Collection<String> data;

        public IterableStub(final String ... values) {
            super();
            this.data = Arrays.asList(values);
        }

        @Override
        public Iterator<String> iterator() {
            return new IteratorStub(data.iterator());
        }

    }

    private static final class IteratorStub implements Iterator<String> {

        private final Iterator<String> it;

        public IteratorStub(final Iterator<String> it) {
            super();
            this.it = it;
        }

        @Override
        public boolean hasNext() {
            return it.hasNext();
        }

        @Override
        public String next() {
            return it.next();
        }

        @Override
        public void remove() {
            it.remove();
        }

    }
}
