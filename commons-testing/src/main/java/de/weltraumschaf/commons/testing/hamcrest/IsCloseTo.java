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

import org.hamcrest.Description;
import org.hamcrest.Factory;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;

/**
 * Matches a long value with some data.
 *
 * <p>
 * Useful to match time values where you want to accept some delta.
 * </p>
 *
 * <p>
 * Adapted from {@link org.hamcrest.number.IsCloseTo} for use with long values.
 * </p>
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
public class IsCloseTo extends TypeSafeMatcher<Long> {

    /**
     * Accepted delta.
     */
    private final long delta;
    /**
     * Matched value.
     */
    private final long value;

    /**
     * Dedicated constructor.
     *
     * @param value matched value
     * @param error accepted delta
     */
    public IsCloseTo(final long value, final long error) {
        super();
        this.delta = error;
        this.value = value;
    }

    @Override
    public boolean matchesSafely(final Long item) {
        return actualDelta(item) <= 0.0;
    }

    @Override
    public void describeMismatchSafely(final Long item, final Description mismatchDescription) {
        mismatchDescription.appendValue(item)
                .appendText(" differed by ")
                .appendValue(actualDelta(item));
    }

    @Override
    public void describeTo(final Description description) {
        description.appendText("a numeric value within ")
                .appendValue(delta)
                .appendText(" of ")
                .appendValue(value);
    }

    private double actualDelta(final Long item) {
        return (Math.abs((item - value)) - delta);
    }

    /**
     * Creates a matcher of {@link Double}s that matches when an examined double is equal to the specified
     * {@code operand}, within a range of +/- {@code error}.
     *
     * <p>
     * For example:
     * </p>
     * <pre>assertThat(103L, is(closeTo(100L, 3L)))</pre>
     *
     * @param operand the expected value of matching doubles
     * @param error the delta (+/-) within which matches will be allowed
     * @return never {@code null}, always new instance
     */
    @Factory
    public static Matcher<Long> closeTo(final long operand, final long error) {
        return new IsCloseTo(operand, error);
    }
}
