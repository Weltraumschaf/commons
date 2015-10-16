package de.weltraumschaf.commons.testing.hamcrest;

import de.weltraumschaf.commons.system.ExitCode;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import org.junit.Ignore;

import org.junit.Test;
import static org.mockito.Mockito.mock;

/**
 * Tests for {@link CommonsTestingMatchers}.
 */
public class CommonsTestingMatchersTest {

    @Test
    @Ignore
    public void constructorThrowsException() {
    }

    @Test
    public void closeTo_returnsLongMatcher() {
        assertThat(
            CommonsTestingMatchers.closeTo(1L, 1L) instanceof LongIsCloseTo,
            is(true));
    }

    @Test
    public void closeTo_returnsIntegerMatcher() {
        assertThat(
            CommonsTestingMatchers.closeTo(1, 1) instanceof IntegerIsCloseTo,
            is(true));
    }

    @Test
    public void hasMessage_returnsHashMessageMatcher() {
        assertThat(
            CommonsTestingMatchers.hasMessage(is("foo")) instanceof HasMessage,
            is(true));
    }

    @Test
    public void hasExitCode_returnsHashExitCodeMatcher() {
        assertThat(
            CommonsTestingMatchers.hasExitCode(mock(ExitCode.class)) instanceof ApplicationExceptionCodeMatcher,
            is(true));
    }

}
