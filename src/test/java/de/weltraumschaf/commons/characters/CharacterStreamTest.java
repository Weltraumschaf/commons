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
package de.weltraumschaf.commons.characters;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;
import org.junit.Test;

/**
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
public class CharacterStreamTest {

    private final CharacterStream sut = new CharacterStream("foo bar baz");

    @Test
    public void callNextFirst() {
        assertThat(sut.current(), is('f'));
    }

    @Test
    public void accessCharacters() {
        assertThat(sut.next(), is('f'));
        assertThat(sut.current(), is('f'));
        assertThat(sut.current(), is('f'));
        assertThat(sut.hasNext(), is(true));

        assertThat(sut.next(), is('o'));
        assertThat(sut.current(), is('o'));
        assertThat(sut.current(), is('o'));
        assertThat(sut.hasNext(), is(true));

        assertThat(sut.next(), is('o'));
        assertThat(sut.current(), is('o'));
        assertThat(sut.current(), is('o'));
        assertThat(sut.current(), is('o'));
        assertThat(sut.hasNext(), is(true));

        assertThat(sut.next(), is(' '));
        assertThat(sut.current(), is(' '));
        assertThat(sut.hasNext(), is(true));

        assertThat(sut.next(), is('b'));
        assertThat(sut.current(), is('b'));
        assertThat(sut.hasNext(), is(true));

        assertThat(sut.next(), is('a'));
        assertThat(sut.current(), is('a'));
        assertThat(sut.hasNext(), is(true));

        assertThat(sut.next(), is('r'));
        assertThat(sut.current(), is('r'));
        assertThat(sut.hasNext(), is(true));

        assertThat(sut.next(), is(' '));
        assertThat(sut.current(), is(' '));
        assertThat(sut.hasNext(), is(true));

        assertThat(sut.next(), is('b'));
        assertThat(sut.current(), is('b'));
        assertThat(sut.hasNext(), is(true));

        assertThat(sut.next(), is('a'));
        assertThat(sut.current(), is('a'));
        assertThat(sut.hasNext(), is(true));

        assertThat(sut.next(), is('z'));
        assertThat(sut.current(), is('z'));
        assertThat(sut.hasNext(), is(false));

        try {
            sut.next();
            fail("Expected exception not thrown!");
        } catch (IndexOutOfBoundsException ex) {
            assertThat(ex.getMessage(), is("No more next characters!"));
        }

        assertThat(sut.current(), is('z'));
    }

    @Test
    public void peekCharacter() {
        assertThat(sut.current(), is('f'));
        assertThat(sut.peek(), is('o'));
        assertThat(sut.current(), is('f'));
    }
}
