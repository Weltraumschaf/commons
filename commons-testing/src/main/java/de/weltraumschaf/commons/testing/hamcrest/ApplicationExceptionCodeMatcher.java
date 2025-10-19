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

import de.weltraumschaf.commons.application.ApplicationException;
import de.weltraumschaf.commons.system.ExitCode;
import de.weltraumschaf.commons.validate.Validate;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;

/**
 * Matcher to test for {@link de.weltraumschaf.commons.application.ApplicationException#getExitCode()}.
 *
 * @since 1.1.0
 * @param <T> type of exception to match its code
 * @author Sven Strittmatter &lt;weltraumschaf@googlemail.com&gt;
 */
public final class ApplicationExceptionCodeMatcher<T extends ApplicationException> extends TypeSafeMatcher<T> {

    /**
     * Exit code to verify against it.
     */
    private final int expectedExitCodeNumber;
    /**
     * May be {@code null}.
     */
    private ExitCode expectedExitCode;

    /**
     * Convenience constructor.
     *
     * @param expectedExitCode must not be {@code null}
     * @deprecated Use {@link CommonsTestingMatchers#hasExitCode(de.weltraumschaf.commons.system.ExitCode)} instead
     */
    @Deprecated
    public ApplicationExceptionCodeMatcher(final ExitCode expectedExitCode) {
        this(Validate.notNull(expectedExitCode, "expectedExitCode").getCode());
        this.expectedExitCode = expectedExitCode;
    }

    /**
     * Dedicated constructor.
     *
     * @param expectedExitCode any int
     */
    private ApplicationExceptionCodeMatcher(final int expectedExitCode) {
        super();
        this.expectedExitCodeNumber = expectedExitCode;
    }

    /**
     * Get the expected exit code as number.
     *
     * @return any int
     */
    int getExpectedExitCodeNumber() {
        return expectedExitCodeNumber;
    }

    /**
     * Get the expected exit code as type.
     *
     * @return may be {@code null}
     */
    ExitCode getExpectedExitCode() {
        return expectedExitCode;
    }


    @Override
    public void describeTo(final Description description) {
        description.appendText("exception with exit code ");

        if (null == expectedExitCode) {
            description.appendValue(expectedExitCodeNumber);
        } else {
            description.appendValue(expectedExitCode);
        }
    }

    @Override
    protected boolean matchesSafely(final T item) {
        return expectedExitCodeNumber == item.getExitCode().getCode();
    }

    @Override
    protected void describeMismatchSafely(final T item, final Description mismatch) {
        mismatch.appendText("exit code ");
        mismatch.appendText(item.getExitCode().toString());
    }

    /**
     * Static factory method.
     *
     * @param <T> type of matched exception
     * @param expectedExitCode code to match, must not be {@code null}
     * @return never {@code null}, always new instance
     */
    public static <T extends ApplicationException> Matcher<T> hasExitCode(final ExitCode expectedExitCode) {
        return new ApplicationExceptionCodeMatcher<>(expectedExitCode);
    }

    /**
     * Static factory method.
     *
     * @since 2.1.0
     * @param <T> type of matched exception
     * @param expectedExitCode code to match}
     * @return never {@code null}, always new instance
     */
    public static <T extends ApplicationException> Matcher<T> hasExitCode(final int expectedExitCode) {
        return new ApplicationExceptionCodeMatcher<>(expectedExitCode);
    }
}
