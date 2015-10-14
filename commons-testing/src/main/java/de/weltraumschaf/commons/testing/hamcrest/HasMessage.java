package de.weltraumschaf.commons.testing.hamcrest;

import de.weltraumschaf.commons.validate.Validate;
import org.hamcrest.Description;
import org.hamcrest.Factory;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;

/**
 * Does have a {@link Throwable} a message, which {@link Matcher matches something}?
 *
 * @author Sven Strittmatter &lt;weltraumschaf@googlemail.com&gt;
 * @since 1.0.0
 */
final class HasMessage extends TypeSafeMatcher<Throwable> {

    private final Matcher<? super String> messageTextMatcher;

    private HasMessage(final Matcher<? super String> messageTextMatcher) {
        super();
        this.messageTextMatcher
            = Validate.notNull(
                messageTextMatcher,
                "messageTextMatcher");
    }

    @Override
    public void describeTo(final Description description) {
        description.appendText(" with the message ")
            .appendDescriptionOf(messageTextMatcher);
    }

    @Override
    protected boolean matchesSafely(final Throwable item) {
        return messageTextMatcher.matches(item.getMessage());
    }

    /**
     * Creates a matcher of {@link Throwable}s that matches when its message matched be <em>messageTextMatcher</em>.
     * <p>
     * For example:
     * </p>
     *
     * <pre>
     * {@code
     * assertThat(exception, hasMessage(equalTo(&quot;Parameter is required.&quot;)))
     * }</pre>
     *
     * @param messageTextMatcher matcher for actual message., not {@code null}.
     * @return never {@code null}
     */
    @Factory
    public static Matcher<Throwable> hasMessage(final Matcher<? super String> messageTextMatcher) {
        return new HasMessage(messageTextMatcher);
    }
}
