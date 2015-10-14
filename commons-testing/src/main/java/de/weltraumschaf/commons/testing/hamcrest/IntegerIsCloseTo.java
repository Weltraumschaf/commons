package de.weltraumschaf.commons.testing.hamcrest;

import org.hamcrest.Description;
import org.hamcrest.Factory;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;

/**
 * Is the value a number equal to a value within some range of acceptable error?
 *
 * @author Sven Strittmatter &lt;weltraumschaf@googlemail.com&gt;
 * @since 2.1.0
 */
final class IntegerIsCloseTo extends TypeSafeMatcher<Integer> {

    /**
     * Accepted delta.
     */
    private final int delta;
    /**
     * Matched value.
     */
    private final int value;

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
        super();
        this.delta = error;
        this.value = value;
    }

    @Override
    public boolean matchesSafely(final Integer item) {
        return actualDelta(item) <= 0;
    }

    @Override
    public void describeMismatchSafely(final Integer item, final Description mismatchDescription) {
        mismatchDescription.appendValue(item)
            .appendText(" differed by ")
            .appendValue(actualDelta(item));
    }

    @Override
    public void describeTo(final Description description) {
        description.appendText("a numeric value within ")
            .appendValue(this.delta).appendText(" of ")
            .appendValue(this.value);
    }

    private double actualDelta(final Integer item) {
        return (Math.abs((item - this.value)) - this.delta);
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
