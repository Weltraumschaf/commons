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

import org.hamcrest.Factory;
import org.hamcrest.Matcher;

/**
 * Matches a long value with some data.
 *
 * <p>
 * Useful to match time values where you want to accept some delta.
 * </p>
 *
 * <p>
 * Adapted from {@code org.hamcrest.number.IsCloseTo} for use with long values.
 * </p>
 *
 * @author Sven Strittmatter &lt;weltraumschaf@googlemail.com&gt;
 * @since 2.1.0
 */
class LongIsCloseTo extends BaseIsCloseTo<Long> {

    /**
     * Dedicated constructor.
     * <p>
     * Use {@link #closeTo(long, long)} to create new instances.
     * </p>
     *
     * @param value matched value
     * @param error accepted delta
     */
    public LongIsCloseTo(final long value, final long error) {
        super(value, error);
    }

    @Override
    long actualDelta(final Long item) {
        return (Math.abs((item - matched())) - error());
    }

    /**
     * Creates a matcher of {@link java.lang.Double}s that matches when an examined double is equal to the specified
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
        return new LongIsCloseTo(operand, error);
    }
}
