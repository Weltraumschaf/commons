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

package de.weltraumschaf.commons.testing.hamcrest;

import org.junit.Test;
import static org.junit.Assert.assertThat;
import static org.hamcrest.Matchers.is;
import static de.weltraumschaf.commons.testing.hamcrest.IsCloseTo.closeTo;
import static org.hamcrest.Matchers.not;

/**
 * Tests for {@link IsCloseTo}.
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
public class IsCloseToTest {

    @Test
    public void closeTo_() {
        assertThat(96L, is(not(closeTo(100L, 3L))));
        assertThat(97L, is(closeTo(100L, 3L)));
        assertThat(98L, is(closeTo(100L, 3L)));
        assertThat(99L, is(closeTo(100L, 3L)));
        assertThat(100L, is(closeTo(100L, 3L)));
        assertThat(101L, is(closeTo(100L, 3L)));
        assertThat(102L, is(closeTo(100L, 3L)));
        assertThat(103L, is(closeTo(100L, 3L)));
        assertThat(104L, is(not(closeTo(100L, 3L))));
    }

}
