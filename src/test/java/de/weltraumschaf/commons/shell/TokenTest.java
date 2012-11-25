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
package de.weltraumschaf.commons.shell;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;
import org.junit.Test;

/**
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
public class TokenTest {

    @Test
    public void testToString() {
        Token t = Token.newLiteralToken("foo");
        assertThat(t.toString(), is("Token{type=LITERAL, value=foo}"));

        t = Token.newStringToken("foo");
        assertThat(t.toString(), is("Token{type=STRING, value=foo}"));

        t = Token.newKeywordToken("foo");
        assertThat(t.toString(), is("Token{type=KEYWORD, value=foo}"));

        t = Token.newNumberToken(123);
        assertThat(t.toString(), is("Token{type=NUMBER, value=123}"));

        t = Token.newNumberToken(123);
        assertThat(t.toString(), is("Token{type=NUMBER, value=123}"));
    }

}
