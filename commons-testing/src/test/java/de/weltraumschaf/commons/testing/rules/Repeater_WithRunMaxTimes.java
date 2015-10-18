package de.weltraumschaf.commons.testing.rules;

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

import de.weltraumschaf.commons.validate.Validate;

/**
 * Tests for {@link Repeater} with {@link RunMaxTimes} annotation.
 */
public class Repeater_WithRunMaxTimes {

    @Rule
    public final ExpectedException thrown = ExpectedException.none();

    private final Repeater sut = new Repeater();

    private static Description createDescriptionWithRepeatUntilSuccessAnnotation(final int times) {
        return Description.createTestDescription(Repeater_WithRunMaxTimes.class, "foo", new RunMaxTimes() {

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
        thrown.expectMessage("The repeater annotation @RunMaxTimes needs a value greater that 0! You gave 0.");

        sut.apply(new BaseStatement(1), createDescriptionWithRepeatUntilSuccessAnnotation(0));
    }

    @Test
    public void apply_executesBaseStatementOneTimeIfItDoesNotFail() throws Throwable {
        final BaseStatement base = new BaseStatement(0);

        sut.apply(base, createDescriptionWithRepeatUntilSuccessAnnotation(5)).evaluate();

        assertThat(base.getEvaluatedCount(), is(1));
    }

    @Test
    public void apply_executesBaseStatementThreeTimesIfItDoesNotFailOnThirdTime() throws Throwable {
        final BaseStatement base = new BaseStatement(2);

        sut.apply(base, createDescriptionWithRepeatUntilSuccessAnnotation(5)).evaluate();

        assertThat(base.getEvaluatedCount(), is(3));
    }

    @Test
    public void apply_executesBaseStatementFiveTimesAndFailsIfStatementFails() throws Throwable {
        thrown.expect(AssertionError.class);
        thrown.expectMessage(startsWith("There were 5 (100.00 %) errors in 5 runs:"));
        final BaseStatement base = new BaseStatement(5);

        sut.apply(base, createDescriptionWithRepeatUntilSuccessAnnotation(5)).evaluate();

        assertThat(base.getEvaluatedCount(), is(5));
    }

    /**
     * Stubs the base statement which will be the executed test statement in real tests.
     */
    private static final class BaseStatement extends Statement {

        /**
         * How many evaluations should throw an assertion error.
         */
        private final int fails;

        /**
         * Count how many times was evaluated.
         */
        private int count;

        /**
         * Dedicated constructor.
         *
         * @param fails must be greater or equal 0
         */
        BaseStatement(final int fails) {
            super();
            Validate.isTrue(fails >= 0, "Parameter 'fails' must be greater equal than 0!");
            this.fails = fails;
        }

        @Override
        public void evaluate() throws Throwable {
            ++count;

            if (count <= fails) {
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
