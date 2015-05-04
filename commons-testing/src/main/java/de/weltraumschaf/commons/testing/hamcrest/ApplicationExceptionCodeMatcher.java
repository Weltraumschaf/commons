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
import org.hamcrest.Factory;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;

/**
 * Matcher to test for {@link ApplicationException#getExitCode()}.
 *
 * @since 1.1.0
 * @param <T> type of exception to match its code
 * @author Sven Strittmatter &lt;weltraumschaf@googlemail.com&gt;
 */
public final class ApplicationExceptionCodeMatcher<T extends ApplicationException> extends TypeSafeMatcher<T> {

    /**
     * Exit code to verify against it.
     */
    private final ExitCode expectedExitCode;

    /**
     * Dedicated constructor.
     *
     * @param expectedExitCode must not be {@code null}
     */
    public ApplicationExceptionCodeMatcher(final ExitCode expectedExitCode) {
        super();
        this.expectedExitCode = Validate.notNull(expectedExitCode, "expectedExitCode");
    }

    @Override
    public void describeTo(final Description description) {
        description.appendText("exception with exit code ");
        description.appendText(expectedExitCode.toString());
    }

    @Override
    protected boolean matchesSafely(final T item) {
        return expectedExitCode.getCode() == item.getExitCode().getCode();
    }

    @Override
    protected void describeMismatchSafely(final T item, final Description mismatch) {
        mismatch.appendText("exit code ");
        mismatch.appendText(item.getExitCode().toString());
    }

    /**
     * Static factory method.
     *
     * @param <T> type of exception to match its code
     * @param expectedExitCode code to match, must not be {@code null}
     * @return never {@code null}, always new instance
     */
    @Factory
    public static <T extends ApplicationException> Matcher<T> hasExitCode(final ExitCode expectedExitCode) {
        return new ApplicationExceptionCodeMatcher<>(expectedExitCode);
    }

}
