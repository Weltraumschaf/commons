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

package de.weltraumschaf.commons.testing.hamcrest;

import org.junit.Test;
import static org.junit.Assert.assertThat;
import static org.hamcrest.Matchers.is;
import org.junit.Rule;
import org.junit.rules.ExpectedException;

/**
 * Tests for {@link IsCloseTo}.
 *
 * @author Sven Strittmatter &lt;weltraumschaf@googlemail.com&gt;
 */
@SuppressWarnings("deprecation")
public class IsCloseToTest {

    @Rule
    //CHECKSTYLE:OFF
    public final ExpectedException thrown = ExpectedException.none();
    //CHECKSTYLE:ON

    @Test
    public void closeTo_matches() {
        assertThat(97L, is(IsCloseTo.closeTo(100L, 3L)));
        assertThat(98L, is(IsCloseTo.closeTo(100L, 3L)));
        assertThat(99L, is(IsCloseTo.closeTo(100L, 3L)));
        assertThat(100L, is(IsCloseTo.closeTo(100L, 3L)));
        assertThat(101L, is(IsCloseTo.closeTo(100L, 3L)));
        assertThat(102L, is(IsCloseTo.closeTo(100L, 3L)));
        assertThat(103L, is(IsCloseTo.closeTo(100L, 3L)));
    }

    @Test
    public void closeto_matchesNotLowerBound() {
        try {
            assertThat(96L, is(IsCloseTo.closeTo(100L, 3L)));
        } catch(final AssertionError ex) {
            assertThat(ex.getMessage(),
                is("\nExpected: is a numeric value within <3L> of <100L>\n     but: <96L> differed by <1L>"));
        }
    }

    @Test
    public void closeto_matchesNotUpperBound() {
        try {
            assertThat(104L, is(IsCloseTo.closeTo(100L, 3L)));
        } catch(final AssertionError ex) {
            assertThat(ex.getMessage(),
                is("\nExpected: is a numeric value within <3L> of <100L>\n     but: <104L> differed by <1L>"));
        }
    }

}
