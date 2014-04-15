/*
 *  LICENSE
 *
 * "THE BEER-WARE LICENSE" (Revision 43):
 * "Sven Strittmatter" <weltraumschaf@googlemail.com> wrote this file.
 * As long as you retain this notice you can do whatever you want with
 * this stuff. If we meet some day, and you think this stuff is worth it,
 * you can buy me a non alcohol-free beer in return.
 *
 * Copyright (C) 2012 "Sven Strittmatter" <weltraumschaf@googlemail.com>
 */
package de.weltraumschaf.commons.testing;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import static org.hamcrest.CoreMatchers.allOf;
import static org.hamcrest.CoreMatchers.containsString;
import org.hamcrest.Matcher;
import static org.hamcrest.MatcherAssert.assertThat;
import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;

/**
 * This rule captures all output written to {@link System#out} and {@link System#err}.
 *
 * <p>
 * This rule redirects the constant print streams for out/err to an {@link CapturingPrintStream} before each test and
 * backups the origin streams after each test.
 * </p>
 *
 * <pre>{@code
 * public class OutputTest {
 *
 *     &#064;Rule
 *     public final CapturedOutput output = new CapturedOutput();
 *
 *     &#064;Test
 *     public void captureOut() {
 *         output.expectOut("foobar");
 *         output.expectOut(not("snafu"));
 *
 *         System.out.print("foobar");
 *     }
 *
 *     &#064;Test
 *     public void captureErr() {
 *         output.expectErr("foobar");
 *         output.expectErr(not("snafu"));
 *
 *         System.err.print("foobar");
 *     }
 *
 * }
 * }</pre>
 *
 * @since 1.0.0
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
public final class CapturedOutput implements TestRule {

    /**
     * Build the matchers for STDOUT expectations.
     */
    private final CapturedOutputMatcherBuilder outMatcherBuilder = new CapturedOutputMatcherBuilder();
    /**
     * Build the matchers for STDERR expectations.
     */
    private final CapturedOutputMatcherBuilder errMatcherBuilder = new CapturedOutputMatcherBuilder();
    /**
     * Captures the data written to STDOUT.
     */
    private final CapturingPrintStream out = new CapturingPrintStream();
    /**
     * Captures the data written to STDERR.
     */
    private final CapturingPrintStream err = new CapturingPrintStream();
    /**
     * Holds the original STDOUT from before test method.
     */
    private PrintStream outBackup;
    /**
     * Holds the original STDERR from before test method.
     */
    private PrintStream errBackup;

    /**
     * Adds to the list of requirements for any output printed to STDOUT that it should <em>contain</em> string
     * {@code substring}.
     *
     * @param substring must not be {@code null}
     */
    public void expectOut(final String substring) {
        expectOut(containsString(notNull(substring, "substring")));
    }

    /**
     * Adds to the list of requirements for any output printed to STDOUT.
     *
     * @param matcher must not be {@code null}
     */
    public void expectOut(final Matcher<String> matcher) {
        outMatcherBuilder.add(notNull(matcher, "matcher"));
    }

    /**
     * Adds to the list of requirements for any output printed to STDERR that it should <em>contain</em> string
     * {@code substring}.
     *
     * @param substring must not be {@code null}
     */
    public void expectErr(final String substring) {
        expectErr(containsString(notNull(substring, "substring")));
    }

    /**
     * Adds to the list of requirements for any output printed to STDERR.
     *
     * @param matcher must not be {@code null}
     */
    public void expectErr(final Matcher<String> matcher) {
        errMatcherBuilder.add(notNull(matcher, "matcher"));
    }

    @Override
    public Statement apply(final Statement base, final Description description) {
        return new Statement() {
            @Override
            public void evaluate() throws Throwable {
                redirectOutputStreams();

                try {
                    base.evaluate();
                    assertCapturedOut();
                    assertCapturedErr();
                } finally {
                    restoreOutputStreams();
                }
            }
        };
    }

    /**
     * Set and backup STDERR/STDOUT print streams.
     */
    private void redirectOutputStreams() {
        outBackup = System.out;
        System.setOut(out.reset());
        errBackup = System.err;
        System.setErr(err.reset());
    }

    /**
     * Applies matchers on captured error output if there are any matchers.
     */
    private void assertCapturedErr() {
        if (errMatcherBuilder.expectsSomething()) {
            assertThat(err.getCapturedOutput(), errMatcherBuilder.build());
        }
    }

    /**
     * Applies matchers on captured standard output if there are any matchers.
     */
    private void assertCapturedOut() {
        if (outMatcherBuilder.expectsSomething()) {
            assertThat(out.getCapturedOutput(), outMatcherBuilder.build());
        }
    }

    /**
     * Restore STDERR/STDOUT print streams.
     */
    private void restoreOutputStreams() {
        System.setOut(outBackup);
        System.setErr(errBackup);
    }

    /**
     * Validates that given subject is not {@code null}.
     *
     * <p>
     * Will throw {@link NullPointerException} if subject is {@code null}.
     * </p>
     *
     * @param <T> type of subject
     * @param subject tested subject
     * @param description name of tested subject for exception message
     * @return the subject, if not {@code null}
     */
    private static <T> T notNull(final T subject, final String description) {
        if (null == subject) {
            throw new NullPointerException(String.format("Parameter '%s' must not be null!", description));
        }

        return subject;
    }

    /**
     * Builds string matchers.
     */
    private static final class CapturedOutputMatcherBuilder {

        /**
         * Hold all matchers.
         */
        private final List<Matcher<String>> matchers = new ArrayList<Matcher<String>>();

        /**
         * Adds a matcher.
         *
         * @param matcher must not be {@code null}
         */
        void add(final Matcher<String> matcher) {
            matchers.add(matcher);
        }

        /**
         * Whether the builder has any matcher.
         *
         * @return {@code true} if there are matchers, else {@code false}
         */
        boolean expectsSomething() {
            return !matchers.isEmpty();
        }

        /**
         * Returns the combined matcher.
         *
         * @return never {@code null}
         */
        Matcher<String> build() {
            if (matchers.size() == 1) {
                return matchers.get(0);
            }

            return allOf(castedMatchers());
        }

        @SuppressWarnings({"unchecked", "rawtypes"})
        private List<Matcher<? super String>> castedMatchers() {
            return new ArrayList<Matcher<? super String>>((List) matchers);
        }

    }
}
