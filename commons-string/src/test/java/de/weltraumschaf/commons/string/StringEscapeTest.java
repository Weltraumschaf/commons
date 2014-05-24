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
package de.weltraumschaf.commons.string;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.Rule;
import org.junit.rules.ExpectedException;

/**
 * Tests for {@link StringEscape}.
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
public class StringEscapeTest {

    @Rule
    public final ExpectedException thrown = ExpectedException.none();

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
