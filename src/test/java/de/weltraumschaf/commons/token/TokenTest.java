/*
 * LICENSE
 *
 * "THE BEER-WARE LICENSE" (Revision 43):
 * "Sven Strittmatter" <weltraumschaf(at)googlemail(dot)com> wrote this file.
 * As long as you retain this notice you can do whatever you want with
 * this stuff. If we meet some day, and you think this stuff is worth it,
 * you can buy me a non alcohol-free beer in return.
 *
 * Copyright (C) 2012 "Sven Strittmatter" <weltraumschaf(at)googlemail(dot)com>
 */
package de.weltraumschaf.commons.token;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;
import org.junit.Test;

/**
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
public class TokenTest {

    @Test
    public void testToString_literal() {
        Token t = Token.newLiteralToken("foo");
        assertThat(t.toString(), is("Token{type=LITERAL, value=foo}"));
    }

    @Test
    public void testToString_string() {
        final Token t = Token.newStringToken("foo");
        assertThat(t.toString(), is("Token{type=STRING, value=foo}"));
    }

    @Test
    public void testToString_keyword() {
        final Token t = Token.newKeywordToken("foo");
        assertThat(t.toString(), is("Token{type=KEYWORD, value=foo}"));
    }

    @Test
    public void testToString_Number() {
        final Token t = Token.newNumberToken(123);
        assertThat(t.toString(), is("Token{type=NUMBER, value=123}"));
    }

    @Test
    public void testToString_number() {
        final Token t = Token.newNumberToken(123);
        assertThat(t.toString(), is("Token{type=NUMBER, value=123}"));
    }

    @Test
    public void testToString_float() {
        final Token t = Token.newFloatToken(3.14f);
        assertThat(t.toString(), is("Token{type=FLOAT, value=3.14}"));
    }

    @Test
    public void testToString_boolean() {
        final Token t = Token.newBooleanToken(true);
        assertThat(t.toString(), is("Token{type=BOOLEAN, value=true}"));
    }

}
