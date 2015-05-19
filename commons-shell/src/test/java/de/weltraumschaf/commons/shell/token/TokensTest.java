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
import de.weltraumschaf.commons.shell.token.TokenType;
import de.weltraumschaf.commons.parse.token.Position;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;
import org.junit.Test;
import org.junit.Rule;
import org.junit.rules.ExpectedException;

/**
 * Tests for {@link Tokens}.
 *
 * @author Sven Strittmatter &lt;weltraumschaf@googlemail.com&gt;
 */
public class TokensTest {

    @Rule
    //CHECKSTYLE:OFF
    public final ExpectedException thrown = ExpectedException.none();
    //CHECKSTYLE:ON

    private final Position pos = new Position(1, 1);

    @Test
    public void invokeConstructorByReflectionThrowsException() throws Exception {
        assertThat(Tokens.class.getDeclaredConstructors().length, is(1));

        final Constructor<Tokens> ctor = Tokens.class.getDeclaredConstructor();
        ctor.setAccessible(true);

        thrown.expect(either(instanceOf(UnsupportedOperationException.class))
                .or(instanceOf(InvocationTargetException.class)));
        ctor.newInstance();
    }

    @Test
    public void newBooleanToken() {
        final ShellToken t = Tokens.newBooleanToken(pos, "true", Boolean.TRUE);

        assertThat(t, is(not(nullValue())));
        assertThat(t.getType(), is(TokenType.BOOLEAN));
        assertThat(Tokens.newBooleanToken(pos, "true", Boolean.TRUE), is(not(sameInstance(t))));
    }

    @Test
    public void newIntegerToken() {
        final ShellToken t = Tokens.newIntegerToken(pos, "42", Integer.valueOf(42));

        assertThat(t, is(not(nullValue())));
        assertThat(t.getType(), is(TokenType.INTEGER));
        assertThat(Tokens.newIntegerToken(pos, "42", Integer.valueOf(42)), is(not(sameInstance(t))));
    }

    @Test
    public void newFloatToken() {
        final ShellToken t = Tokens.newFloatToken(pos, "3.14", Float.valueOf(3.14f));

        assertThat(t, is(not(nullValue())));
        assertThat(t.getType(), is(TokenType.FLOAT));
        assertThat(Tokens.newFloatToken(pos, "3.14", Float.valueOf(3.14f)), is(not(sameInstance(t))));
    }

    @Test
    public void newStringToken() {
        final ShellToken t = Tokens.newStringToken(pos, "foo", "foo");

        assertThat(t, is(not(nullValue())));
        assertThat(t.getType(), is(TokenType.STRING));
        assertThat(Tokens.newStringToken(pos, "foo", "foo"), is(not(sameInstance(t))));
    }

    @Test
    public void newKeywordToken() {
        final ShellToken t = Tokens.newKeywordToken(pos, "bar", "bar");

        assertThat(t, is(not(nullValue())));
        assertThat(t.getType(), is(TokenType.KEYWORD));
        assertThat(Tokens.newKeywordToken(pos, "bar", "bar"), is(not(sameInstance(t))));
    }

    @Test
    public void newLiteralToken() {
        final ShellToken t = Tokens.newLiteralToken(pos, "baz", "baz");

        assertThat(t, is(not(nullValue())));
        assertThat(t.getType(), is(TokenType.LITERAL));
        assertThat(Tokens.newLiteralToken(pos, "baz", "baz"), is(not(sameInstance(t))));
    }

}
