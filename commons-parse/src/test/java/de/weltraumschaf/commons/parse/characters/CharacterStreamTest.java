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

import de.weltraumschaf.commons.parse.token.Position;
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

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void callEmptyStream() {
        final CharacterStream emptySut = new CharacterStream("");

        thrown.expect(IndexOutOfBoundsException.class);
        thrown.expectMessage("No more next characters!");

        emptySut.next();
    }

    @Test
    public void callNextFirst() {
        final CharacterStream sut = new CharacterStream("foo bar baz");

        assertThat(sut.current(), is('f'));
    }

    @Test
    public void accessCharactersWithNewline() {
        final CharacterStream sutWithNewline = new CharacterStream("foo\n");

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
        final CharacterStream sut = new CharacterStream("foo bar baz");

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
        final CharacterStream sut = new CharacterStream("foo bar baz");

        assertThat(sut.current(), is('f'));
        assertThat(sut.peek(), is('o'));
        assertThat(sut.current(), is('f'));
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void position_empty() {
        final CharacterStream emptySut = new CharacterStream("");

        assertThat(emptySut.position(), is(Position.NULL));

        emptySut.next();

        assertThat(emptySut.position(), is(Position.NULL));
    }

    @Test
    public void position() {
        final CharacterStream sutWithMultilines = new CharacterStream(
            "1234\n" // 1
            + "123456\n" // 2
            + "1\n" // 3
            + "\n" // 4
            + "123\n" // 5
            + "\n" // 6
            + "\n" // 7
            + "\t2345"); // 8

        assertThat(sutWithMultilines.position(), is(Position.NULL));

        sutWithMultilines.next();
        assertThat(sutWithMultilines.position(), is(new Position(1, 1)));
        sutWithMultilines.next();
        assertThat(sutWithMultilines.position(), is(new Position(1, 2)));
        sutWithMultilines.next();
        assertThat(sutWithMultilines.position(), is(new Position(1, 3)));
        sutWithMultilines.next();
        assertThat(sutWithMultilines.position(), is(new Position(1, 4)));
        sutWithMultilines.next();
        assertThat(sutWithMultilines.position(), is(new Position(1, 5)));

        sutWithMultilines.next();
        assertThat(sutWithMultilines.position(), is(new Position(2, 1)));
        sutWithMultilines.next();
        assertThat(sutWithMultilines.position(), is(new Position(2, 2)));
        sutWithMultilines.next();
        assertThat(sutWithMultilines.position(), is(new Position(2, 3)));
        sutWithMultilines.next();
        assertThat(sutWithMultilines.position(), is(new Position(2, 4)));
        sutWithMultilines.next();
        assertThat(sutWithMultilines.position(), is(new Position(2, 5)));
        sutWithMultilines.next();
        assertThat(sutWithMultilines.position(), is(new Position(2, 6)));
        sutWithMultilines.next();
        assertThat(sutWithMultilines.position(), is(new Position(2, 7)));

        sutWithMultilines.next();
        assertThat(sutWithMultilines.position(), is(new Position(3, 1)));
        sutWithMultilines.next();
        assertThat(sutWithMultilines.position(), is(new Position(3, 2)));

        sutWithMultilines.next();
        assertThat(sutWithMultilines.position(), is(new Position(4, 1)));

        sutWithMultilines.next();
        assertThat(sutWithMultilines.position(), is(new Position(5, 1)));
        sutWithMultilines.next();
        assertThat(sutWithMultilines.position(), is(new Position(5, 2)));
        sutWithMultilines.next();
        assertThat(sutWithMultilines.position(), is(new Position(5, 3)));
        sutWithMultilines.next();
        assertThat(sutWithMultilines.position(), is(new Position(5, 4)));

        sutWithMultilines.next();
        assertThat(sutWithMultilines.position(), is(new Position(6, 1)));

        sutWithMultilines.next();
        assertThat(sutWithMultilines.position(), is(new Position(7, 1)));

        sutWithMultilines.next();
        assertThat(sutWithMultilines.position(), is(new Position(8, 1)));
        sutWithMultilines.next();
        assertThat(sutWithMultilines.position(), is(new Position(8, 2)));
        sutWithMultilines.next();
        assertThat(sutWithMultilines.position(), is(new Position(8, 3)));
        sutWithMultilines.next();
        assertThat(sutWithMultilines.position(), is(new Position(8, 4)));
        sutWithMultilines.next();
        assertThat(sutWithMultilines.position(), is(new Position(8, 5)));
    }
}
