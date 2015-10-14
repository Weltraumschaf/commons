package de.weltraumschaf.commons.testing.hamcrest;

import de.weltraumschaf.commons.application.ApplicationException;
import de.weltraumschaf.commons.system.ExitCode;
import org.hamcrest.Matcher;

/**
 * Factory for custom Hamcrest matchers.
 *
 * @author Sven Strittmatter &lt;weltraumschaf@googlemail.com&gt;
 * @since 1.0.0
 */
public final class CommonsTestingMatchers {

    /**
     * Hidden for pure static class.
     */
    private CommonsTestingMatchers() {
        super();
        throw new UnsupportedOperationException("Do not call by reflection!");
    }

    /**
     * Creates matcher to check if an integer is close to expected.
     *
     * @see IntegerIsCloseTo
     * @param operand the expected value of matching doubles
     * @param error the delta (+/-) within which matches will be allowed
     * @return never {@code null}
     */
    public static Matcher<Integer> closeTo(final int operand, final int error) {
        return IntegerIsCloseTo.closeTo(operand, error);
    }

    /**
     * Creates matcher to check if an long is close to expected.
     *
     * @see LongIsCloseTo
     * @param operand the expected value of matching doubles
     * @param error the delta (+/-) within which matches will be allowed
     * @return never {@code null}
     */
    public static Matcher<Long> closeTo(final long operand, final long error) {
        return LongIsCloseTo.closeTo(operand, error);
    }

    /**
     * @see HasMessage
     * @param messageTextMatcher matcher for actual message., not {@code null}.
     * @return never {@code null}
     */
    public static Matcher<Throwable> hasMessage(final Matcher<? super String> messageTextMatcher) {
        return HasMessage.hasMessage(messageTextMatcher);
    }

    public static <T extends ApplicationException> Matcher<T> hasExitCode(final ExitCode expectedExitCode) {
        return ApplicationExceptionCodeMatcher.hasExitCode(expectedExitCode);
    }
}
