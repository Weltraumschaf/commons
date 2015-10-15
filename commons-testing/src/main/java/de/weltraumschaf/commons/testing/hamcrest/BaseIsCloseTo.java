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

import de.weltraumschaf.commons.validate.Validate;
import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;

/**
 * Common base class for matchers with matches numbers with a given error.
 *
 * @author Sven Strittmatter &lt;weltraumschaf@googlemail.com&gt;
 * @param <T> matched number type
 */
abstract class BaseIsCloseTo<T extends Number> extends TypeSafeMatcher<T> {

    /**
     * Accepted error.
     */
    private final T error;
    /**
     * Matched value.
     */
    private final T matched;

    /**
     * Dedicated constructor.
     *
     * @param matched must not be {@code null}
     * @param error must not be {@code null}
     */
    BaseIsCloseTo(final T matched, final T error) {
        super();
        this.matched = Validate.notNull(matched, "matched");
        this.error = Validate.notNull(error, "error");
    }

    @Override
    public final boolean matchesSafely(final T item) {
        return actualDelta(item) <= 0;
    }

    /**
     * Get the accepted error.
     *
     * @return never {@code null}
     */
    final T error() {
        return error;
    }

    /**
     * Get the matched value.
     *
     * @return never {@code null}
     */
    final T matched() {
        return matched;
    }

    /**
     * Calculates the actual delta.
     *
     * @param item any long
     * @return any long
     */
    abstract long actualDelta(final T item);

    @Override
    public final void describeMismatchSafely(final T item, final Description mismatchDescription) {
        mismatchDescription.appendValue(item)
            .appendText(" differed by ")
            .appendValue(actualDelta(item));
    }

    @Override
    public final void describeTo(final Description description) {
        description.appendText("a numeric value within ")
            .appendValue(error)
            .appendText(" of ")
            .appendValue(matched);
    }
}
