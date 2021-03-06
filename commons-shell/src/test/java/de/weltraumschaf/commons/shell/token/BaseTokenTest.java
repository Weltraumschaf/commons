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
package de.weltraumschaf.commons.shell.token;

import de.weltraumschaf.commons.shell.token.BaseToken;
import de.weltraumschaf.commons.shell.token.TokenType;
import de.weltraumschaf.commons.parse.token.Position;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

/**
 * Tests for {@link BaseToken}.
 *
 * @author Sven Strittmatter &lt;weltraumschaf@googlemail.com&gt;
 */
public class BaseTokenTest {

    @Rule
    //CHECKSTYLE:OFF
    public final ExpectedException thrown = ExpectedException.none();
    //CHECKSTYLE:ON

    private final BaseToken<String> sut = new BaseTokenStub<String>(TokenType.STRING, Position.NULL, "foobar", "snafu");

    @Test
    public void constructWithTypeIsNullThrowsException() {
        thrown.expect(NullPointerException.class);
        thrown.expectMessage("type");

        new BaseTokenStub<String>(null, Position.NULL, "foobar", "snafu");
    }

    @Test
    public void constructWithPositionIsNullThrowsException() {
        thrown.expect(NullPointerException.class);
        thrown.expectMessage("position");

        new BaseTokenStub<String>(TokenType.STRING, null, "foobar", "snafu");
    }

    @Test
    public void constructWithRawIsNullThrowsException() {
        thrown.expect(NullPointerException.class);
        thrown.expectMessage("raw");

        new BaseTokenStub<String>(TokenType.STRING, Position.NULL, null, "snafu");
    }

    @Test
    public void constructWithValueIsNullThrowsException() {
        thrown.expect(NullPointerException.class);
        thrown.expectMessage("value");

        new BaseTokenStub<String>(TokenType.STRING, Position.NULL, "foobar", null);
    }

    @Test
    public void getType() {
        assertThat(sut.getType(), is(TokenType.STRING));
    }

    @Test
    public void getPosition() {
        assertThat(sut.getPosition(), is(equalTo(Position.NULL)));
    }

    @Test
    public void getRaw() {
        assertThat(sut.getRaw(), is(equalTo("foobar")));
    }

    @Test
    public void getValue() {
        assertThat(sut.getValue(), is(equalTo("snafu")));
    }

    @Test
    public void testToString() {
        assertThat(sut.toString(), is(equalTo("BaseTokenStub{type=STRING, position=(0, 0), raw=foobar, value=snafu}")));
    }

    private static final class BaseTokenStub<T> extends BaseToken<T> {

        public BaseTokenStub(final TokenType type, final Position position, final String raw, final T value) {
            super(type, position, raw, value);
        }

        @Override
        public Boolean asBoolean() {
            throw new UnsupportedOperationException("Not relevant for this test.");
        }

        @Override
        public Float asFloat() {
            throw new UnsupportedOperationException("Not relevant for this test.");
        }

        @Override
        public Integer asInteger() {
            throw new UnsupportedOperationException("Not relevant for this test.");
        }
    }
}
