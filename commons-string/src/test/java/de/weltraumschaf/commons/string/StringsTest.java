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

import org.junit.Test;
import static org.junit.Assert.assertThat;
import static org.hamcrest.Matchers.*;

/**
 * Tests for {@link Strings}.
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
public class StringsTest {

    @Test
    public void nullAwareTrim() {
        assertThat(Strings.nullAwareTrim(null), is(equalTo("")));
        assertThat(Strings.nullAwareTrim(""), is(equalTo("")));
        assertThat(Strings.nullAwareTrim("foo"), is(equalTo("foo")));
        assertThat(Strings.nullAwareTrim("foo  "), is(equalTo("foo")));
        assertThat(Strings.nullAwareTrim("  foo"), is(equalTo("foo")));
    }

}
