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
package de.weltraumschaf.commons.testing.rules;

import de.weltraumschaf.commons.validate.Validate;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Locale;

import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;

/**
 * Rule which runs tests annotated with {@link RunTimes} or {@link RunMaxTimes} multiple times.
 * <p>
 * The rule does not stop on any exceptions, but save them to throw a combined {@link AssertionError} at the end of the
 * evaluation of the {@link RepeatStatement}. So you will see how many tests failed of how many runs. Also you see the
 * stack trace of all failures.
 * </p>
 *
 * <p>
 * Example:
 * </p>
 *
 * <pre>
 * {@code
 *  public class TestSomething {
 *
 *      &#064;Rule
 *      public final Repeater repeater = new Repeater();
 *
 *      &#064;Test
 *      &#064;RunTimes(10) // Runs the test method ten times.
 *      public void someTestMethod() {
 *          // ...
 *      }
 *
 *      &#064;Test
 *      &#064;RunMaxTimes(3) // Runs the test method until success, max five times.
 *      public void someOtherTestMethod() {
 *          // ...
 *      }
 * }
 * }</pre>
 *
 * <p>
 * Based on
 * <a href="http://www.codeaffine.com/2013/04/10/running-junit-tests-repeatedly-without-loops/">
 * Blog post by Frank Appel</a>.
 * </p>
 *
 * @since 2.1.0
 * @author Sven Strittmatter &lt;weltraumschaf@googlemail.com&gt;
 */
public final class Repeater implements TestRule {

    @Override
    public Statement apply(final Statement base, final Description description) {
        Validate.notNull(base, "base");
        Validate.notNull(description, "description");

        if (hasRunTimesAnnotation(description)) {
            return new RepeatStatement(
                description.getAnnotation(RunTimes.class).value(),
                base);
        }

        if (hasRunMaxTimesAnnotation(description)) {
            return new RepeatUntilSuccessStatement(
                description.getAnnotation(RunMaxTimes.class).value(),
                base);
        }

        return base;
    }

    /**
     * Whether the description has a {@link RunTimes} annotation or not.
     *
     * @param description must not be {@code null}
     * @return {@code true} if present, else {@code false}
     */
     boolean hasRunTimesAnnotation(final Description description) {
        return null != description.getAnnotation(RunTimes.class);
    }

    /**
     * Whether the description has a {@link RunMaxTimes} annotation or not.
     *
     * @param description must not be {@code null}
     * @return {@code true} if present, else {@code false}
     */
    boolean hasRunMaxTimesAnnotation(final Description description) {
        return null != description.getAnnotation(RunMaxTimes.class);
    }

    /**
     * This statement executes the given base statement repeatedly.
     */
    abstract static class BaseRepeatStatement extends Statement {

        /**
         * Used for error rate calculation.
         */
        private static final double HUNDRED_PERCENT = 100.0d;

        /**
         * how many times to repeat.
         */
        private final int times;

        /**
         * Statement to repeat.
         */
        private final Statement base;

        /**
         * Dedicated constructor.
         *
         * @param times must be greater than 0
         * @param base must not be {@code null}
         */
        BaseRepeatStatement(final int times, final Statement base) {
            super();
            Validate.isTrue(times > 0, "Parameter 'times' must be greater than 0!");
            this.times = times;
            this.base = Validate.notNull(base, "base");
        }

        /**
         * How many times to execute the base statement.
         *
         * @return greater than 0
         */
        int times() {
            return times;
        }

        /**
         * The multiple times executed base statement.
         *
         * @return never {@code null}
         */
        Statement base() {
            return base;
        }

        /**
         * Assert that there are no errors during all repetitions.
         * <p>
         * Throws an {@link AssertionError} if list of errors is not empty.
         * </p>
         *
         * @param errors must not be {@code null}
         */
        void assertNoErrors(final Collection<? extends Throwable> errors) { // NOSONAR We want all types here.
            Validate.notNull(errors, "errors");

            if (errors.isEmpty()) {
                return;
            }

            throw new AssertionError(formatErrors(errors, times));
        }

        /**
         * Formats the given errors into one message.
         * <p>
         * The message contains one headline with number of repetitions and number of errors. This is followed by the
         * stack trace of each error.
         * </p>
         *
         * @param errors must not be {@code null}
         * @param times must be greater than 0
         * @return never {@code null}
         */
        static String formatErrors(final Collection<? extends Throwable> errors, final int times) {
            Validate.notNull(errors, "errors");
            final StringBuilder message = new StringBuilder();
            message.append(String.format(Locale.US,
                "There were %d (%.2f %%) errors in %d run",
                errors.size(),
                calculateFailedRepetitionPercentage(times, errors.size()),
                times));

            if (1 < times) {
                message.append('s');
            }

            if (errors.isEmpty()) {
                return message.toString();
            }

            message.append(':');
            int i = 1;

            for (final Throwable t : errors) {
                final Writer stackTrace = new StringWriter();
                t.printStackTrace(new PrintWriter(stackTrace));
                // Use Unix new line because Jenkins does not format properly on Windows machines with \r\n
                message.append(String.format("\n%d. %s", i, stackTrace.toString()));
                ++i;
            }

            return message.toString();
        }

        /**
         * Calculates percentage of errors.
         *
         * @param times must not be less than 1
         * @param errors must not be less than 0
         * @return non negative
         */
        static double calculateFailedRepetitionPercentage(final int times, final int errors) {
            Validate.isTrue(times > 0, "Parameter times must not be less than 1!");
            Validate.isTrue(errors >= 0, "Parameter errors must not be less than 0!");
            return (errors * HUNDRED_PERCENT) / times;
        }

    }

    /**
     * This statement executes the given base statement repeatedly.
     */
    static final class RepeatStatement extends BaseRepeatStatement {

        /**
         * Dedicated constructor.
         *
         * @param times must be greater than 0
         * @param base must not be {@code null}
         */
        private RepeatStatement(final int times, final Statement base) {
            super(times, base);
        }

        @Override
        public void evaluate() throws Throwable {
            final List<Throwable> errors = new ArrayList<>();

            for (int i = 0; i < times(); i++) {
                try {
                    base().evaluate();
                } catch (final Throwable t) { // NOPMD Collect errors to throw one summary error after all repetitions.
                    errors.add(t);
                }
            }

            assertNoErrors(errors);
        }
    }

    /**
     * This statement executes the given base statement repeatedly until it succeeds or reaches times.
     */
    static final class RepeatUntilSuccessStatement extends BaseRepeatStatement {

        /**
         * Dedicated constructor.
         *
         * @param times must be greater than 0
         * @param statement must not be {@code null}
         */
        private RepeatUntilSuccessStatement(final int times, final Statement statement) {
            super(times, statement);
        }

        @Override
        public void evaluate() throws Throwable {
            final List<Throwable> errors = new ArrayList<>();

            boolean success = false;
            for (int i = 0; i < times() && !success; i++) {
                try {
                    base().evaluate();
                    success = true;
                } catch (final Throwable t) { // NOPMD Collect errors to throw one summary error after all repetitions.
                    errors.add(t);
                }
            }

            if (!success) {
                assertNoErrors(errors);
            }
        }
    }
}
