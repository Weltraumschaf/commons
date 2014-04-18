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
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;
import org.junit.Test;
import org.junit.Rule;
import org.junit.rules.ExpectedException;

/**
 * Tests for {@link Objects}.
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
public class ObjectsTest {

    @Rule
    //CHECKSTYLE:OFF
    public final ExpectedException thrown = ExpectedException.none();
    //CHECKSTYLE:ON

    @Test
    public void invokeConstructorByReflectionThrowsException() throws Exception {
        assertThat(Objects.class.getDeclaredConstructors().length, is(1));

        final Constructor<Objects> ctor = Objects.class.getDeclaredConstructor();
        ctor.setAccessible(true);

        thrown.expect(either(instanceOf(UnsupportedOperationException.class))
                .or(instanceOf(InvocationTargetException.class)));
        ctor.newInstance();
    }

    @Test
    public void equal() {
        assertThat(Objects.equal(null, null), is(true));
        assertThat(Objects.equal(new Object(), new Object()), is(false));

        final Object o = new Object();
        assertThat(Objects.equal(o, null), is(false));
        assertThat(Objects.equal(null, o), is(false));
        assertThat(Objects.equal(o, o), is(true));

        assertThat(Objects.equal("foo", null), is(false));
        assertThat(Objects.equal(null, "foo"), is(false));
        assertThat(Objects.equal("foo", "foo"), is(true));
        assertThat(Objects.equal("foo", "bar"), is(false));

        assertThat(Objects.equal("foo", o), is(false));

        assertThat(Objects.equal("foo", 1), is(false));
        assertThat(Objects.equal(1, 2), is(false));
        assertThat(Objects.equal(1, 1), is(true));
    }

    @Test
    public void testHashCode() {
        assertThat(Objects.hashCode(1, true, "foobar"), is(Objects.hashCode(1, true, "foobar")));
        assertThat(Objects.hashCode(1, true, "foobar"), is(not(Objects.hashCode(1, false, "foobar"))));
    }

    @Test
    public void toStringHelper_object() {
        final Objects.ToStringHelper sut = Objects.toStringHelper(new Object());
        assertThat(sut.toString(), is(equalTo("Object{}")));

        sut.add("foo", "bar");
        assertThat(sut.toString(), is(equalTo("Object{foo=bar}")));

        sut.add("snafu", null);
        assertThat(sut.toString(), is(equalTo("Object{foo=bar, snafu=null}")));

        sut.add("num", 42);
        assertThat(sut.toString(), is(equalTo("Object{foo=bar, snafu=null, num=42}")));

        sut.add("arr", new ArrayList<Object>());
        assertThat(sut.toString(), is(equalTo("Object{foo=bar, snafu=null, num=42, arr=[]}")));
    }

    @Test
    public void toStringHelper_class() {
        final Objects.ToStringHelper sut = Objects.toStringHelper(Object.class);
        assertThat(sut.toString(), is(equalTo("Object{}")));

        sut.add("foo", "bar");
        assertThat(sut.toString(), is(equalTo("Object{foo=bar}")));

        sut.add("snafu", null);
        assertThat(sut.toString(), is(equalTo("Object{foo=bar, snafu=null}")));

        sut.add("num", 42);
        assertThat(sut.toString(), is(equalTo("Object{foo=bar, snafu=null, num=42}")));

        sut.add("arr", new ArrayList<Object>());
        assertThat(sut.toString(), is(equalTo("Object{foo=bar, snafu=null, num=42, arr=[]}")));
    }

    @Test
    public void toStringHelper_string() {
        final Objects.ToStringHelper sut = Objects.toStringHelper("Test");
        assertThat(sut.toString(), is(equalTo("Test{}")));

        sut.add("foo", "bar");
        assertThat(sut.toString(), is(equalTo("Test{foo=bar}")));

        sut.add("snafu", null);
        assertThat(sut.toString(), is(equalTo("Test{foo=bar, snafu=null}")));

        sut.add("num", 42);
        assertThat(sut.toString(), is(equalTo("Test{foo=bar, snafu=null, num=42}")));

        sut.add("arr", new ArrayList<Object>());
        assertThat(sut.toString(), is(equalTo("Test{foo=bar, snafu=null, num=42, arr=[]}")));
    }

    @Test(expected = NullPointerException.class)
    public void checkNotNull_throwsExcpetionForNull() {
        Objects.checkNotNull(null);
    }

    @Test
    public void checkNotNull() {
        final Object object = new Object();
        assertThat(Objects.checkNotNull(object), is(sameInstance(object)));
    }

}
