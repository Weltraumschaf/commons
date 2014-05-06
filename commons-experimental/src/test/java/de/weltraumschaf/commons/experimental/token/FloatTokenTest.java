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
import org.junit.Ignore;
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
    @Ignore
    public void asBoolean() {
        thrown.expect(UnsupportedOperationException.class);
    }

    @Test
    @Ignore
    public void asFloat() {

    }

    @Test
    @Ignore
    public void asInteger() {
        thrown.expect(UnsupportedOperationException.class);
    }

    @Test
    @Ignore
    public void asString() {

    }
}
