package de.weltraumschaf.commons.testing.hamcrest;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import org.junit.Test;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

/**
 * Tests for {@link HasMessage}.
 */
public class HasMessageTest {

    private final Matcher<String> messageTextMatcher = is("foo");
    private final Matcher<Throwable> sut = HasMessage.hasMessage(messageTextMatcher);

    @Test(expected = NullPointerException.class)
    public void hasMessage_noNullAllowed() {
        HasMessage.hasMessage(null);
    }

    @Test
    public void matchesSafely_matches() {
        assertThat(sut.matches(new Exception("foo")), is(true));
    }

    @Test
    public void matchesSafely_matchesNot() {
        assertThat(sut.matches(new Exception("bar")), is(false));
    }

    @Test
    public void describeTo() {
        final Description desc = mock(Description.class);

        sut.describeTo(desc);

        verify(desc, times(1)).appendText(" with the message ");
        verify(desc, times(1)).appendDescriptionOf(messageTextMatcher);
    }
}
