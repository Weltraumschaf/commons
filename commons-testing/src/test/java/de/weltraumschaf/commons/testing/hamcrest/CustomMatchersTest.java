package de.weltraumschaf.commons.testing.hamcrest;

import static de.weltraumschaf.commons.testing.hamcrest.CommonsTestingMatchers.closeTo;
import static de.weltraumschaf.commons.testing.hamcrest.CommonsTestingMatchers.hasMessage;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.assertThat;

import org.junit.Test;

/**
 * Tests for {@link CustomMatchers}.
 */

/**
 * Tests for {@link CommonsTestingMatchers}.
 */
public class CustomMatchersTest {

    @Test
    public void integerIscloseTo() {
        assertThat(96, is(not(closeTo(100, 3))));
        assertThat(97, is(closeTo(100, 3)));
        assertThat(98, is(closeTo(100, 3)));
        assertThat(99, is(closeTo(100, 3)));
        assertThat(100, is(closeTo(100, 3)));
        assertThat(101, is(closeTo(100, 3)));
        assertThat(102, is(closeTo(100, 3)));
        assertThat(103, is(closeTo(100, 3)));
        assertThat(104, is(not(closeTo(100, 3))));
    }

    @Test
    public void throwableHasMessage() {
        assertThat(new Exception("failed"), hasMessage(equalTo("failed")));
        assertThat(new RuntimeException("failed"), hasMessage(not(equalTo("wrong"))));
        assertThat(
            new Throwable("wrong and failed"),
            hasMessage(allOf(containsString("wrong"), containsString("failed"))));
    }

}
