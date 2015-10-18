package de.weltraumschaf.commons.testing.rules;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.startsWith;
import static org.junit.Assert.assertThat;

import java.lang.annotation.Annotation;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;

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

        sut.apply(new BaseStatement(1), createDescriptionWithRepeatAnnotation(0));
    }

    @Test
    public void apply_executesBaseStatementOneTime() throws Throwable {
        final BaseStatement base = new BaseStatement(1);

        sut.apply(base, createDescriptionWithRepeatAnnotation(1)).evaluate();

        assertThat(base.getEvaluatedCount(), is(1));
    }

    @Test
    public void apply_executesBaseStatementThreeTimes() throws Throwable {
        final BaseStatement base = new BaseStatement(1);

        sut.apply(base, createDescriptionWithRepeatAnnotation(3)).evaluate();

        assertThat(base.getEvaluatedCount(), is(3));
    }

    @Test
    public void apply_executesBaseStatementTwentyTime() throws Throwable {
        final BaseStatement base = new BaseStatement(1);

        sut.apply(base, createDescriptionWithRepeatAnnotation(20)).evaluate();

        assertThat(base.getEvaluatedCount(), is(20));
    }

    @Test
    public void apply_doesEvaluateAllRunsAlthoughRunFails() throws Throwable {
        thrown.expect(AssertionError.class);
        thrown.expectMessage(startsWith("There were 10 (50.00 %) errors in 20 runs:"));
        final BaseStatement base = new BaseStatement(2);

        sut.apply(base, createDescriptionWithRepeatAnnotation(20)).evaluate();

        assertThat(base.getEvaluatedCount(), is(20));
    }

    /**
     * Stubs the base statement which will be the executed test statement in real tests.
     */
    private static class BaseStatement extends Statement {

        /**
         * How many evaluations should throw an assertion error.
         */
        private final int failFactor;

        /**
         * Count how many times was evaluated.
         */
        private int count;

        /**
         * Dedicated constructor.
         *
         * @param failFactor must be greater than 0
         */
        BaseStatement(final int failFactor) {
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
