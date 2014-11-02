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

import de.weltraumschaf.commons.token.BaseToken.LiteralToken;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import org.junit.Test;

/**
 * Tests for {@link LiteralToken}.
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
public class LiteralTokenTest {

    private final Token sut = Tokens.newLiteralToken(Position.NULL, "foobar", "foobar");
    private final Token sutEmpty = Tokens.newKeywordToken(Position.NULL, "", "");

    @Test
    public void asBoolean() {
        assertThat(sut.asBoolean(), is(true));
        assertThat(sutEmpty.asBoolean(), is(false));
    }

    @Test
    public void asFloat() {
        assertThat(sut.asFloat(), is(1.0f));
        assertThat(sutEmpty.asFloat(), is(0.0f));
    }

    @Test
    public void asInteger() {
        assertThat(sut.asInteger(), is(1));
        assertThat(sutEmpty.asInteger(), is(0));
    }

    @Test
    public void asString() {
        assertThat(sut.asString(), is(equalTo("foobar")));
        assertThat(sutEmpty.asString(), is(equalTo("")));
    }
}
