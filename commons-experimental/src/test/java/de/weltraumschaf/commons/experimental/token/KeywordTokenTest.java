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
package de.weltraumschaf.commons.experimental.token;

import de.weltraumschaf.commons.token.Position;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

/**
 * Tests for {@link KeywordToken}.
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
public class KeywordTokenTest {

    @Rule
    //CHECKSTYLE:OFF
    public final ExpectedException thrown = ExpectedException.none();
    //CHECKSTYLE:ON

    private final Token sut = Tokens.newKeywordToken(Position.NULL, "foobar", "foobar");

    @Test
    public void asBoolean() {
        thrown.expect(UnsupportedOperationException.class);
        thrown.expectMessage("Token is not of type BOOLEAN! But is of type KEYWORD.");

        sut.asBoolean();
    }

    @Test
    public void asFloat() {
        thrown.expect(UnsupportedOperationException.class);
        thrown.expectMessage("Token is not of type FLOAT! But is of type KEYWORD.");

        sut.asFloat();
    }

    @Test
    public void asInteger() {
        thrown.expect(UnsupportedOperationException.class);
        thrown.expectMessage("Token is not of type INTEGER! But is of type KEYWORD.");

        sut.asInteger();
    }

    @Test
    public void asString() {
        assertThat(sut.asString(), is(equalTo("foobar")));
    }
}
