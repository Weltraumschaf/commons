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
package de.weltraumschaf.commons.parse.characters;

import de.weltraumschaf.commons.parse.characters.CharacterStream;
import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

/**
 *
 * @author Sven Strittmatter &lt;weltraumschaf@googlemail.com&gt;
 */
public class CharacterStreamTest {

    @Rule public ExpectedException thrown = ExpectedException.none();

    private final CharacterStream sut = new CharacterStream("foo bar baz");
    private final CharacterStream sutWithNewline = new CharacterStream("foo\n");
    private final CharacterStream emptySut = new CharacterStream("");

    @Test public void callEmptyStream() {
        thrown.expect(IndexOutOfBoundsException.class);
        thrown.expectMessage("No more next characters!");
        emptySut.next();
    }

    @Test
    public void callNextFirst() {
        assertThat(sut.current(), is('f'));
    }

    @Test
    public void accessCharactersWithNewline() {
        assertThat(sutWithNewline.next(), is('f'));
        assertThat(sutWithNewline.current(), is('f'));
        assertThat(sutWithNewline.hasNext(), is(true));

        assertThat(sutWithNewline.next(), is('o'));
        assertThat(sutWithNewline.current(), is('o'));
        assertThat(sutWithNewline.hasNext(), is(true));

        assertThat(sutWithNewline.next(), is('o'));
        assertThat(sutWithNewline.current(), is('o'));
        assertThat(sutWithNewline.hasNext(), is(true));

        assertThat(sutWithNewline.next(), is('\n'));
        assertThat(sutWithNewline.current(), is('\n'));
        assertThat(sutWithNewline.hasNext(), is(false));
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

        thrown.expect(IndexOutOfBoundsException.class);
        thrown.expectMessage("No more next characters!");
        sut.next();

        assertThat(sut.current(), is('z'));
    }

    @Test
    public void peekCharacter() {
        assertThat(sut.current(), is('f'));
        assertThat(sut.peek(), is('o'));
        assertThat(sut.current(), is('f'));
    }
}
