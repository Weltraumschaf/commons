package de.weltraumschaf.commons.testing.hamcrest;

import static org.hamcrest.Matchers.is;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Rule;
import org.junit.rules.ExpectedException;

/**
 * Tests for {@link IntegerIsCloseTo}.
 */
public class IntegerIsCloseToTest {
@Rule
    //CHECKSTYLE:OFF
    public final ExpectedException thrown = ExpectedException.none();
    //CHECKSTYLE:ON

    @Test
    public void closeTo_matches() {
        assertThat(97, is(IntegerIsCloseTo.closeTo(100, 3)));
        assertThat(98, is(IntegerIsCloseTo.closeTo(100, 3)));
        assertThat(99, is(IntegerIsCloseTo.closeTo(100, 3)));
        assertThat(100, is(IntegerIsCloseTo.closeTo(100, 3)));
        assertThat(101, is(IntegerIsCloseTo.closeTo(100, 3)));
        assertThat(102, is(IntegerIsCloseTo.closeTo(100, 3)));
        assertThat(103, is(IntegerIsCloseTo.closeTo(100, 3)));
    }

    @Test
    public void closeTo_matchesNotLowerBound() {
        thrown.expect(AssertionError.class);
        thrown.expectMessage("\nExpected: is a numeric value within <3> of <100>\n     but: <96> differed by <1L>");

        assertThat(96, is(IntegerIsCloseTo.closeTo(100, 3)));
    }

    @Test
    public void closeTo_matchesNotUpperBound() {
        thrown.expect(AssertionError.class);
        thrown.expectMessage("\nExpected: is a numeric value within <3> of <100>\n     but: <104> differed by <1L>");

        assertThat(104, is(IntegerIsCloseTo.closeTo(100, 3)));
    }

}
