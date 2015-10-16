package de.weltraumschaf.commons.testing.hamcrest;

import de.weltraumschaf.commons.system.ExitCode;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import static org.hamcrest.Matchers.either;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import org.junit.Rule;

import org.junit.Test;
import org.junit.rules.ExpectedException;
import static org.mockito.Mockito.mock;

/**
 * Tests for {@link CommonsTestingMatchers}.
 */
public class CommonsTestingMatchersTest {

    @Rule
    //CHECKSTYLE:OFF
    public final ExpectedException thrown = ExpectedException.none();
    //CHECKSTYLE:ON

    @Test
    public void invokeConstructorByReflectionThrowsException() throws Exception {
        assertThat(CommonsTestingMatchers.class.getDeclaredConstructors().length, is(1));

        final Constructor<CommonsTestingMatchers> ctor = CommonsTestingMatchers.class.getDeclaredConstructor();
        ctor.setAccessible(true);

        thrown.expect(either(instanceOf(UnsupportedOperationException.class))
                .or(instanceOf(InvocationTargetException.class)));
        ctor.newInstance();
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
