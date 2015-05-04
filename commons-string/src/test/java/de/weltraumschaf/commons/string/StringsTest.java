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

package de.weltraumschaf.commons.string;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import org.junit.Test;
import static org.junit.Assert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;
import org.junit.Rule;
import org.junit.rules.ExpectedException;

/**
 * Tests for {@link Strings}.
 *
 * @author Sven Strittmatter &lt;weltraumschaf@googlemail.com&gt;
 */
public class StringsTest {

    @Rule
    //CHECKSTYLE:OFF
    public final ExpectedException thrown = ExpectedException.none();
    //CHECKSTYLE:ON

    @Test
    public void invokeConstructorByReflectionThrowsException() throws Exception {
        assertThat(Strings.class.getDeclaredConstructors().length, is(1));

        final Constructor<Strings> ctor = Strings.class.getDeclaredConstructor();
        ctor.setAccessible(true);

        thrown.expect(either(instanceOf(UnsupportedOperationException.class))
                .or(instanceOf(InvocationTargetException.class)));
        ctor.newInstance();
    }

    @Test
    public void nullAwareTrim() {
        assertThat(Strings.nullAwareTrim(null), is(equalTo("")));
        assertThat(Strings.nullAwareTrim(""), is(equalTo("")));
        assertThat(Strings.nullAwareTrim("foo"), is(equalTo("foo")));
        assertThat(Strings.nullAwareTrim("foo  "), is(equalTo("foo")));
        assertThat(Strings.nullAwareTrim("  foo"), is(equalTo("foo")));
    }

}
