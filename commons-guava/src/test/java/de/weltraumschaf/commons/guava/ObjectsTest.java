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
import static org.hamcrest.Matchers.either;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import org.junit.Test;
import org.junit.Ignore;
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
    @Ignore("TODO Imlement test for Objects#equal()")
    public void equal() {

    }

    @Test
    @Ignore("TODO Imlement test for Objects#hashCode(...)")
    public void testHashCode() {

    }

    @Test
    @Ignore("TODO Imlement test for Objects#toStringHelper(Object)")
    public void toStringHelper_object() {
    }

    @Test
    @Ignore("TODO Imlement test for Objects#toStringHelper(Class)")
    public void toStringHelper_class() {
    }

    @Test
    @Ignore("TODO Imlement test for Objects#toStringHelper(String)")
    public void toStringHelper_string() {
    }

    @Test
    @Ignore("TODO Imlement test for Objects#checkNotNull(Object)")
    public void checkNotNull() {
    }

}
