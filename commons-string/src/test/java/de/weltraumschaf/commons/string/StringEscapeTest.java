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
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.Matchers.either;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import org.junit.Test;
import org.junit.Rule;
import org.junit.rules.ExpectedException;

/**
 * Tests for {@link StringEscape}.
 *
 * @author Sven Strittmatter &lt;weltraumschaf@googlemail.com&gt;
 */
public class StringEscapeTest {

    @Rule
    //CHECKSTYLE:OFF
    public final ExpectedException thrown = ExpectedException.none();
    //CHECKSTYLE:ON

    @Test
    public void invokeConstructorByReflectionThrowsException() throws Exception {
        assertThat(StringEscape.class.getDeclaredConstructors().length, is(1));

        final Constructor<StringEscape> ctor = StringEscape.class.getDeclaredConstructor();
        ctor.setAccessible(true);

        thrown.expect(either(instanceOf(UnsupportedOperationException.class))
                .or(instanceOf(InvocationTargetException.class)));
        ctor.newInstance();
    }

    @Test(expected = NullPointerException.class)
    public void escapeXml_nullGiven() {
        StringEscape.escapeXml(null);
    }

    @Test
    public void escapeXml_singleOcurrence() {
        assertThat(StringEscape.escapeXml("f<o&o'b\"a>r"), is(equalTo("f&lt;o&amp;o&apos;b&quot;a&gt;r")));
    }

    @Test
    public void escapeXml_multiOcurrence() {
        assertThat(StringEscape.escapeXml("f>>>r"), is(equalTo("f&gt;&gt;&gt;r")));
    }

    @Test(expected = NullPointerException.class)
    public void escapeFileName_nullGiven() {
        StringEscape.escapeFileName(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void escapeFileName_emptyGiven() {
        StringEscape.escapeFileName("");
    }

    @Test
    public void escapeFileName_singleOcurrence() {
        assertThat(StringEscape.escapeFileName("foo/bar baz:do\\mundo"), is(equalTo("foo_bar_baz_do_mundo")));
    }

    @Test
    public void escapeFileName_multiOcurrence() {
        assertThat(StringEscape.escapeFileName("foo///bar   ba/z:do\\\\mundo"), is(equalTo("foo___bar___ba_z_do__mundo")));
    }
}
