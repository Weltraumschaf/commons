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
package de.weltraumschaf.commons.token;

import de.weltraumschaf.commons.token.Tokens;
import de.weltraumschaf.commons.token.Token;
import de.weltraumschaf.commons.token.Position;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

/**
 * Tests for {@link LiteralToken}.
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
public class LiteralTokenTest {

    @Rule
    //CHECKSTYLE:OFF
    public final ExpectedException thrown = ExpectedException.none();
    //CHECKSTYLE:ON

    private final Token sut = Tokens.newLiteralToken(Position.NULL, "foobar", "foobar");

    @Test
    public void asBoolean() {
        thrown.expect(UnsupportedOperationException.class);
        thrown.expectMessage("Token is not of type BOOLEAN! But is of type LITERAL.");

        sut.asBoolean();
    }

    @Test
    public void asFloat() {
        thrown.expect(UnsupportedOperationException.class);
        thrown.expectMessage("Token is not of type FLOAT! But is of type LITERAL.");

        sut.asFloat();
    }

    @Test
    public void asInteger() {
        thrown.expect(UnsupportedOperationException.class);
        thrown.expectMessage("Token is not of type INTEGER! But is of type LITERAL.");

        sut.asInteger();
    }

    @Test
    public void asString() {
        assertThat(sut.asString(), is(equalTo("foobar")));
    }
}
