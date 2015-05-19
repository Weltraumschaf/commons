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

import de.weltraumschaf.commons.shell.token.ShellToken;
import de.weltraumschaf.commons.shell.token.Tokens;
import de.weltraumschaf.commons.parse.token.Position;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

/**
 * Tests for {@link BooleanToken}.
 *
 * @author Sven Strittmatter &lt;weltraumschaf@googlemail.com&gt;
 */
public class BooleanTokenTest {

    @Rule
    //CHECKSTYLE:OFF
    public final ExpectedException thrown = ExpectedException.none();
    //CHECKSTYLE:ON

    private final ShellToken sutTrue = Tokens.newBooleanToken(Position.NULL, "true", Boolean.TRUE);
    private final ShellToken sutFalse = Tokens.newBooleanToken(Position.NULL, "true", Boolean.FALSE);

    @Test
    public void asBoolean() {
        assertThat(sutTrue.asBoolean(), is(true));
        assertThat(sutFalse.asBoolean(), is(false));
    }

    @Test
    public void asFloat() {
        assertThat(sutTrue.asFloat(), is(1.0f));
        assertThat(sutFalse.asFloat(), is(0.0f));
    }

    @Test
    public void asInteger() {
        assertThat(sutTrue.asInteger(), is(1));
        assertThat(sutFalse.asInteger(), is(0));
    }

    @Test
    public void asString() {
        assertThat(sutTrue.asString(), is(equalTo("true")));
        assertThat(sutFalse.asString(), is(equalTo("false")));
    }
}
