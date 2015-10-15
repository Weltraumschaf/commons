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
 * Is the value a number equal to a value within some range of acceptable error?
 *
 * <p>
 * Useful to match time values where you want to accept some delta.
 * </p>
 *
 * <p>
 * Adapted from {@code org.hamcrest.number.IsCloseTo} for use with int values.
 * </p>
 *
 * @author Sven Strittmatter &lt;weltraumschaf@googlemail.com&gt;
 * @since 2.1.0
 */
final class IntegerIsCloseTo extends BaseIsCloseTo<Integer> {

    /**
     * Dedicated constructor.
     * <p>
     * Use {@link #closeTo(int, int)} to create new instances.
     * </p>
     *
     * @param value matched value
     * @param error accepted delta
     */
    private IntegerIsCloseTo(final int value, final int error) {
        super(value, error);
    }

    @Override
    long actualDelta(final Integer item) {
        return (Math.abs((item - matched())) - error());
    }

    /**
     * Creates a matcher of {@link Integer}s that matches when an examined intFF
     * is equal to the specified {@code operand}, within a range of +/-
     * {@code error}.
     * <p>
     * For example:
     * </p>
     *
     * <pre>
     * {@code
     * assertThat(103, is(closeTo(100, 3)))
     * }</pre>
     *
     * @param operand the expected value of matching doubles
     * @param error the delta (+/-) within which matches will be allowed
     * @return never {@code null}
     */
    @Factory
    public static Matcher<Integer> closeTo(final int operand, final int error) {
        return new IntegerIsCloseTo(operand, error);
    }
}
