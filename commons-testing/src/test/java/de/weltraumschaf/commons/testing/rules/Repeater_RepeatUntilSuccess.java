package de.weltraumschaf.commons.testing.rules;

import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.startsWith;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

import java.lang.annotation.Annotation;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;

import de.weltraumschaf.commons.testing.rules.Repeater.RepeatUntilSuccessStatement;
import de.weltraumschaf.commons.validate.Validate;

/**
 * Tests for {@link Repeater} with {@link RunMaxTimes} annotation.
 */
public class Repeater_RepeatUntilSuccess {

    @Rule
    public final ExpectedException thrown = ExpectedException.none();

    private final Repeater sut = new Repeater();

    private static Description createDescriptionWithRepeatUntilSuccessAnnotation(final int times) {
        return Description.createTestDescription(Repeater_RepeatUntilSuccess.class, "foo", new RunMaxTimes() {

            @Override
            public int value() {
                return times;
            }

            @Override
            public Class<? extends Annotation> annotationType() {
                return RunMaxTimes.class;
            }
        });
    }

    @Test
    public void apply_throwsExcpetionIfRuleAnnotationWithLessThanOne() {
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("Parameter times must be greater than 0!");
        sut.apply(new StatementStub(), createDescriptionWithRepeatUntilSuccessAnnotation(0));
    }

    @Test
    public void apply_executesBaseStatementOneTimeIfItDoesNotFail() throws Throwable {
        final StatementStub base = new StatementStub(0);
        final Statement repeeted = sut.apply(base, createDescriptionWithRepeatUntilSuccessAnnotation(5));
        assertThat(repeeted, is(instanceOf(RepeatUntilSuccessStatement.class)));
        repeeted.evaluate();
        assertThat(base.getEvaluatedCount(), is(1));
    }

    @Test
    public void apply_executesBaseStatementThreeTimesIfItDoesNotFailOnThirdTime() throws Throwable {
        final StatementStub base = new StatementStub(2);
        final Statement repeated = sut.apply(base, createDescriptionWithRepeatUntilSuccessAnnotation(5));

        assertThat(repeated, is(instanceOf(RepeatUntilSuccessStatement.class)));
        repeated.evaluate();
        assertThat(base.getEvaluatedCount(), is(3));
    }

    @Test
    public void apply_executesBaseStatementFiveTimesAndFailsIfStatementFails() throws Throwable {
        final StatementStub base = new StatementStub(5);
        final Statement repeated = sut.apply(base, createDescriptionWithRepeatUntilSuccessAnnotation(5));

        assertThat(repeated, is(instanceOf(RepeatUntilSuccessStatement.class)));
        try {
            repeated.evaluate();
            fail("Expected exception not thrown!");
        } catch (final AssertionError e) {
            assertThat(e.getMessage(), startsWith("There were 5 (100.00 %) errors in 5 runs:"));
        }
        assertThat(base.getEvaluatedCount(), is(5));
    }

    /**
     * Stubs the base statement which will be the executed test statement in real tests.
     */
    private static class StatementStub extends Statement {

        /**
         * How many evaluations should throw an assertion error.
         */
        private final int failTimes;

        /**
         * Count how many times was evaluated.
         */
        private int count;

        /**
         * Initializes {@link #failTimes} with one.
         */
        StatementStub() {
            this(1);
        }

        /**
         * Dedicated constructor.
         *
         * @param failTimes must be greater or equal 0
         */
        StatementStub(final int failTimes) {
            super();
            Validate.isTrue(failTimes >= 0, "Parameter failTimes must be greater than 0!");
            this.failTimes = failTimes;
        }

        @Override
        public void evaluate() throws Throwable {
            ++count;

            if (count <= failTimes) {
                throw new AssertionError();
            }
        }

        /**
         * Returns how many times {@link #evaluate()} was invoked.
         *
         * @return not negative
         */
        public int getEvaluatedCount() {
            return count;
        }
    }
}
