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
package de.weltraumschaf.commons.token;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

/**
 * Tests for {@link FloatToken}.
 *
 * @author Sven Strittmatter &lt;weltraumschaf@googlemail.com&gt;
 */
public class FloatTokenTest {

    @Rule
    //CHECKSTYLE:OFF
    public final ExpectedException thrown = ExpectedException.none();
    //CHECKSTYLE:ON

    private final Token sut = Tokens.newFloatToken(Position.NULL, "3.14", 3.14f);
    private final Token sutZero = Tokens.newFloatToken(Position.NULL, "0.0", 0.0f);

    @Test
    public void asBoolean() {
        assertThat(sut.asBoolean(), is(true));
        assertThat(sutZero.asBoolean(), is(false));
    }

    @Test
    public void asFloat() {
        assertThat((double) sut.asFloat(), is(closeTo(3.14, 0.0001)));
        assertThat(sutZero.asFloat(), is(0.0f));
    }

    @Test
    public void asInteger() {
        assertThat(sut.asInteger(), is(3));
        assertThat(sutZero.asInteger(), is(0));
    }

    @Test
    public void asString() {
        assertThat(sut.asString(), is(equalTo("3.14")));
        assertThat(sutZero.asString(), is(equalTo("0.0")));
    }
}
