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

import de.weltraumschaf.commons.experimental.token.BaseToken.FloatToken;
import de.weltraumschaf.commons.token.Position;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

/**
 * Tests for {@link FloatToken}.
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
public class FloatTokenTest {

    @Rule
    //CHECKSTYLE:OFF
    public final ExpectedException thrown = ExpectedException.none();
    //CHECKSTYLE:ON

    private final Token sut = Tokens.newFloatToken(Position.NULL, "3.14", 3.14f);

    @Test
    public void asBoolean() {
        thrown.expect(UnsupportedOperationException.class);
        thrown.expectMessage("Token is not of type BOOLEAN! But is of type FLOAT.");

        sut.asBoolean();
    }

    @Test
    public void asFloat() {
        assertEquals(3.14f, sut.asFloat(), 0.0001);
    }

    @Test
    public void asInteger() {
        thrown.expect(UnsupportedOperationException.class);
        thrown.expectMessage("Token is not of type INTEGER! But is of type FLOAT.");

        sut.asInteger();
    }

    @Test
    public void asString() {
        assertThat(sut.asString(), is(equalTo("3.14")));
    }
}
