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

import de.weltraumschaf.commons.testing.rules.Repeater.RepeatStatement;
import de.weltraumschaf.commons.validate.Validate;

/**
 * Tests for {@link Repeater} with {@link RunTimes} annotation.
 */
public class Repeater_WithRunTimesTest {

    @Rule
    public final ExpectedException thrown = ExpectedException.none();

    private final Repeater sut = new Repeater();

    private static Description createDescriptionWithRepeatAnnotation(final int times) {
        return Description.createTestDescription(Repeater_WithRunTimesTest.class, "foo", new RunTimes() {

            @Override
            public int value() {
                return times;
            }

            @Override
            public Class<? extends Annotation> annotationType() {
                return RunTimes.class;
            }
        });
    }

    @Test
    public void apply_throwsExcpetionIfRuleAnnotationWithLessThanOne() {
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("The repeater annotation @RunTimes needs a value greater that 0! You gave 0.");

        sut.apply(new StatementStub(), createDescriptionWithRepeatAnnotation(0));
    }

    @Test
    public void apply_executesBaseStatementOneTime() throws Throwable {
        final StatementStub base = new StatementStub();
        final Statement repeeted = sut.apply(base, createDescriptionWithRepeatAnnotation(1));

        assertThat(repeeted, is(instanceOf(RepeatStatement.class)));
        repeeted.evaluate();
        assertThat(base.getEvaluatedCount(), is(1));
    }

    @Test
    public void apply_executesBaseStatementThreeTimes() throws Throwable {
        final StatementStub base = new StatementStub();
        final Statement repeated = sut.apply(base, createDescriptionWithRepeatAnnotation(3));

        assertThat(repeated, is(instanceOf(RepeatStatement.class)));
        repeated.evaluate();
        assertThat(base.getEvaluatedCount(), is(3));
    }

    @Test
    public void apply_executesBaseStatementTwentyTime() throws Throwable {
        final StatementStub base = new StatementStub();
        final Statement repeated = sut.apply(base, createDescriptionWithRepeatAnnotation(20));

        assertThat(repeated, is(instanceOf(RepeatStatement.class)));
        repeated.evaluate();
        assertThat(base.getEvaluatedCount(), is(20));
    }

    @Test
    public void apply_doesEvaluateAllRunsAlthoughRunFails() throws Throwable {
        final StatementStub base = new StatementStub(2);

        try {
            sut.apply(base, createDescriptionWithRepeatAnnotation(20)).evaluate();
            fail("Expected exception not thrown!");
        } catch (final AssertionError e) {
            assertThat(e.getMessage(), startsWith("There were 10 (50.00 %) errors in 20 runs:"));
        }

        assertThat(base.getEvaluatedCount(), is(20));
    }

    /**
     * Stubs the base statement which will be the executed test statement in real tests.
     */
    private static class StatementStub extends Statement {

        /**
         * How many evaluations should throw an assertion error.
         */
        private final int failFactor;

        /**
         * Count how many times was evaluated.
         */
        private int count;

        /**
         * Initializes {@link #failFactor} with one.
         */
        StatementStub() {
            this(1);
        }

        /**
         * Dedicated constructor.
         *
         * @param failFactor must be greater than 0
         */
        StatementStub(final int failFactor) {
            super();
            Validate.isTrue(failFactor > 0, "Parameter failFactor must be greater than 0!");
            this.failFactor = failFactor;
        }

        @Override
        public void evaluate() throws Throwable {
            ++count;

            if (count % failFactor != 0) {
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
