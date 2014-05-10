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
    public void toStringHelper_object_namedValues() {
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

        sut.add("boolean", true);
        assertThat(sut.toString(), is(equalTo("Object{foo=bar, snafu=null, num=42, arr=[], boolean=true}")));

        sut.add("char", 'c');
        assertThat(sut.toString(), is(equalTo("Object{foo=bar, snafu=null, num=42, arr=[], boolean=true, char=c}")));

        sut.add("double", 3.14d);
        assertThat(sut.toString(), is(equalTo("Object{foo=bar, snafu=null, num=42, arr=[], boolean=true, char=c, double=3.14}")));

        sut.add("float", 2.7f);
        assertThat(sut.toString(), is(equalTo("Object{foo=bar, snafu=null, num=42, arr=[], boolean=true, char=c, double=3.14, float=2.7}")));

        sut.add("long", 23L);
        assertThat(sut.toString(), is(equalTo("Object{foo=bar, snafu=null, num=42, arr=[], boolean=true, char=c, double=3.14, float=2.7, long=23}")));
    }

    @Test
    public void toStringHelper_object_unnamedValues() {
        final Objects.ToStringHelper sut = Objects.toStringHelper(new Object());
        assertThat(sut.toString(), is(equalTo("Object{}")));

        sut.addValue("foo");
        assertThat(sut.toString(), is(equalTo("Object{foo}")));

        sut.addValue(null);
        assertThat(sut.toString(), is(equalTo("Object{foo, null}")));

        sut.addValue(42);
        assertThat(sut.toString(), is(equalTo("Object{foo, null, 42}")));

        sut.addValue(new ArrayList<Object>());
        assertThat(sut.toString(), is(equalTo("Object{foo, null, 42, []}")));

        sut.addValue(true);
        assertThat(sut.toString(), is(equalTo("Object{foo, null, 42, [], true}")));

        sut.addValue('c');
        assertThat(sut.toString(), is(equalTo("Object{foo, null, 42, [], true, c}")));

        sut.addValue(3.14d);
        assertThat(sut.toString(), is(equalTo("Object{foo, null, 42, [], true, c, 3.14}")));

        sut.addValue(2.7f);
        assertThat(sut.toString(), is(equalTo("Object{foo, null, 42, [], true, c, 3.14, 2.7}")));

        sut.addValue(23L);
        assertThat(sut.toString(), is(equalTo("Object{foo, null, 42, [], true, c, 3.14, 2.7, 23}")));
    }

    @Test
    public void toStringHelper_class_namedValues() {
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

        sut.add("boolean", true);
        assertThat(sut.toString(), is(equalTo("Object{foo=bar, snafu=null, num=42, arr=[], boolean=true}")));

        sut.add("char", 'c');
        assertThat(sut.toString(), is(equalTo("Object{foo=bar, snafu=null, num=42, arr=[], boolean=true, char=c}")));

        sut.add("double", 3.14d);
        assertThat(sut.toString(), is(equalTo("Object{foo=bar, snafu=null, num=42, arr=[], boolean=true, char=c, double=3.14}")));

        sut.add("float", 2.7f);
        assertThat(sut.toString(), is(equalTo("Object{foo=bar, snafu=null, num=42, arr=[], boolean=true, char=c, double=3.14, float=2.7}")));

        sut.add("long", 23L);
        assertThat(sut.toString(), is(equalTo("Object{foo=bar, snafu=null, num=42, arr=[], boolean=true, char=c, double=3.14, float=2.7, long=23}")));
    }

    @Test
    public void toStringHelper_class_unnamedValues() {
        final Objects.ToStringHelper sut = Objects.toStringHelper(Object.class);
        assertThat(sut.toString(), is(equalTo("Object{}")));

        sut.addValue("foo");
        assertThat(sut.toString(), is(equalTo("Object{foo}")));

        sut.addValue(null);
        assertThat(sut.toString(), is(equalTo("Object{foo, null}")));

        sut.addValue(42);
        assertThat(sut.toString(), is(equalTo("Object{foo, null, 42}")));

        sut.addValue(new ArrayList<Object>());
        assertThat(sut.toString(), is(equalTo("Object{foo, null, 42, []}")));

        sut.addValue(true);
        assertThat(sut.toString(), is(equalTo("Object{foo, null, 42, [], true}")));

        sut.addValue('c');
        assertThat(sut.toString(), is(equalTo("Object{foo, null, 42, [], true, c}")));

        sut.addValue(3.14d);
        assertThat(sut.toString(), is(equalTo("Object{foo, null, 42, [], true, c, 3.14}")));

        sut.addValue(2.7f);
        assertThat(sut.toString(), is(equalTo("Object{foo, null, 42, [], true, c, 3.14, 2.7}")));

        sut.addValue(23L);
        assertThat(sut.toString(), is(equalTo("Object{foo, null, 42, [], true, c, 3.14, 2.7, 23}")));
    }

    @Test
    public void toStringHelper_string_namedValues() {
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

        sut.add("boolean", true);
        assertThat(sut.toString(), is(equalTo("Test{foo=bar, snafu=null, num=42, arr=[], boolean=true}")));

        sut.add("char", 'c');
        assertThat(sut.toString(), is(equalTo("Test{foo=bar, snafu=null, num=42, arr=[], boolean=true, char=c}")));

        sut.add("double", 3.14d);
        assertThat(sut.toString(), is(equalTo("Test{foo=bar, snafu=null, num=42, arr=[], boolean=true, char=c, double=3.14}")));

        sut.add("float", 2.7f);
        assertThat(sut.toString(), is(equalTo("Test{foo=bar, snafu=null, num=42, arr=[], boolean=true, char=c, double=3.14, float=2.7}")));

        sut.add("long", 23L);
        assertThat(sut.toString(), is(equalTo("Test{foo=bar, snafu=null, num=42, arr=[], boolean=true, char=c, double=3.14, float=2.7, long=23}")));
    }

    @Test
    public void toStringHelper_string_unnamedValues() {
        final Objects.ToStringHelper sut = Objects.toStringHelper("Test");
        assertThat(sut.toString(), is(equalTo("Test{}")));

        sut.addValue("foo");
        assertThat(sut.toString(), is(equalTo("Test{foo}")));

        sut.addValue(null);
        assertThat(sut.toString(), is(equalTo("Test{foo, null}")));

        sut.addValue(42);
        assertThat(sut.toString(), is(equalTo("Test{foo, null, 42}")));

        sut.addValue(new ArrayList<Object>());
        assertThat(sut.toString(), is(equalTo("Test{foo, null, 42, []}")));

        sut.addValue(true);
        assertThat(sut.toString(), is(equalTo("Test{foo, null, 42, [], true}")));

        sut.addValue('c');
        assertThat(sut.toString(), is(equalTo("Test{foo, null, 42, [], true, c}")));

        sut.addValue(3.14d);
        assertThat(sut.toString(), is(equalTo("Test{foo, null, 42, [], true, c, 3.14}")));

        sut.addValue(2.7f);
        assertThat(sut.toString(), is(equalTo("Test{foo, null, 42, [], true, c, 3.14, 2.7}")));

        sut.addValue(23L);
        assertThat(sut.toString(), is(equalTo("Test{foo, null, 42, [], true, c, 3.14, 2.7, 23}")));
    }

@Test
    public void toStringHelper_ommitNullValues() {
        final Objects.ToStringHelper sut = Objects.toStringHelper("Test");
        sut.omitNullValues();
        sut.addValue("foo");
        sut.addValue(null);
        sut.addValue(42);

        assertThat(sut.toString(), is(equalTo("Test{foo, 42}")));
    }
}
