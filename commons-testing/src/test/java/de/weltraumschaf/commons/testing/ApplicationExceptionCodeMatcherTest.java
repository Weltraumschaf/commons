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

import de.weltraumschaf.commons.application.ApplicationException;
import de.weltraumschaf.commons.system.ExitCode;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import org.junit.Test;
import static org.mockito.Mockito.*;

/**
 * Tests for {@link ApplicationExceptionCodeMatcher}.
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
public class ApplicationExceptionCodeMatcherTest {

    @Test
    public void matchesSafely() {
        final Matcher<ApplicationException> sut =
                ApplicationExceptionCodeMatcher.<ApplicationException>hasExitCode(ExitCodeImpl.FOO);

        assertThat(sut.matches(new ApplicationException(ExitCodeImpl.FOO, "foo")), is(true));
        assertThat(sut.matches(new ApplicationException(ExitCodeImpl.BAR, "bar")), is(false));
        assertThat(sut.matches(new ApplicationException(ExitCodeImpl.BAZ, "baz")), is(false));
    }

    @Test
    public void describeTo() {
        final Description desc = mock(Description.class);
        final Matcher<ApplicationException> sut =
                ApplicationExceptionCodeMatcher.<ApplicationException>hasExitCode(ExitCodeImpl.FOO);

        sut.describeTo(desc);

        verify(desc, times(1)).appendText("exception with exit code ");
        verify(desc, times(1)).appendText("FOO");
    }

    @Test
    public void describeMismatchSafely() {
        final Description desc = mock(Description.class);
        final Matcher<ApplicationException> sut =
                ApplicationExceptionCodeMatcher.<ApplicationException>hasExitCode(ExitCodeImpl.FOO);

        sut.describeMismatch(new ApplicationException(ExitCodeImpl.BAR, "bar"), desc);

        verify(desc, times(1)).appendText("exit code ");
        verify(desc, times(1)).appendText("BAR");
    }

    private static enum ExitCodeImpl implements ExitCode {
        FOO(0), BAR(1), BAZ(2);

        private final int code;

        private ExitCodeImpl(final int code) {
            this.code = code;
        }

        @Override
        public int getCode() {
            return code;
        }
    }
}
