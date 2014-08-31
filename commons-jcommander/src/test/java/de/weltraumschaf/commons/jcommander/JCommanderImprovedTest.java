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

package de.weltraumschaf.commons.jcommander;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import org.junit.Test;

/**
 * Tests for {@link JCommanderImproved}.
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
public class JCommanderImprovedTest {

    private final JCommanderImproved<Object> sut = new JCommanderImproved("name", Object.class);

    @Test
    public void spaces() {
        assertThat(JCommanderImproved.spaces(-1), is(equalTo("")));
        assertThat(JCommanderImproved.spaces(0), is(equalTo("")));
        assertThat(JCommanderImproved.spaces(1), is(equalTo(" ")));
        assertThat(JCommanderImproved.spaces(2), is(equalTo("  ")));
        assertThat(JCommanderImproved.spaces(3), is(equalTo("   ")));
    }

    @Test
    public void pad() {
        assertThat(JCommanderImproved.pad(null, 10), is(equalTo("          ")));
        assertThat(JCommanderImproved.pad("", 10), is(equalTo("          ")));
        assertThat(JCommanderImproved.pad("foo", 10), is(equalTo("foo       ")));
    }

    @Test
    public void lineBreak() {
        assertThat(JCommanderImproved.lineBreak(null, 10, 5), is(equalTo("")));
        assertThat(JCommanderImproved.lineBreak("", 10, 5), is(equalTo("")));
        assertThat(JCommanderImproved.lineBreak("foo bar baz", 10, 5), is(equalTo("foo bar baz")));
        assertThat(JCommanderImproved.lineBreak("foo bar baz foo bar baz foo bar baz foo bar baz", 10, 5),
                is(equalTo("foo bar baz\n     foo bar baz\n     foo bar baz\n     foo bar baz")));
    }

    @Test
    public void helpMessage() {
        sut.gatherOptions(new String[0]);
        assertThat(sut.helpMessage("foo", "bar" ,"baz"), is(equalTo(
                "Usage: name foo\n"
                + "\n"
                + "bar\n"
                + "\n"
                + "Example\n"
                + "\n"
                + "  baz\n"
                + "\n")));
    }
}