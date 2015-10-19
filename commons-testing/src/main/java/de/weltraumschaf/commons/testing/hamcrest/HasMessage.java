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

import de.weltraumschaf.commons.validate.Validate;
import org.hamcrest.Description;
import org.hamcrest.Factory;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;

/**
 * Does have a {@link Throwable} a message, which {@link Matcher matches something}.
 * <p>
 * Example:
 * </p>
 * <pre>
 * {@code
 *  public class MyTest {
 *
 *      private final SomeClass sut = new SomeClass();
 *
 *      &#064;Test
 *      public void throwsFooException() {
 *          final Throwable result = sut.makeError("foobar");
 *
 *          assertThat(result, hasMessage(is("foobar")));
 *      }
 *  }
 * }</pre>
 *
 * @author Sven Strittmatter &lt;weltraumschaf@googlemail.com&gt;
 * @since 1.0.0
 */
final class HasMessage extends TypeSafeMatcher<Throwable> {

    /**
     * String matcher for expected message.
     */
    private final Matcher<? super String> messageTextMatcher;

    /**
     * Dedicated constructor.
     * <p>
     * Use {@link #hasMessage(org.hamcrest.Matcher)} to create new instances.
     * </p>
     *
     * @param messageTextMatcher must not be {@code null}
     */
    private HasMessage(final Matcher<? super String> messageTextMatcher) {
        super();
        this.messageTextMatcher
            = Validate.notNull(
                messageTextMatcher,
                "messageTextMatcher");
    }

    @Override
    public void describeTo(final Description description) {
        description.appendText(" with the message ");
        description.appendDescriptionOf(messageTextMatcher);
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
