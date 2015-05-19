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

package de.weltraumschaf.commons.parse.token;

import de.weltraumschaf.commons.parse.token.Position;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

/**
 *
 * @author Sven Strittmatter &lt;weltraumschaf@googlemail.com&gt;
 */
public class PositionTest {

    @Test public void toString_withoutFileName() {
        final Position position = new Position(5, 10);
        assertThat(position.toString(), is("(5, 10)"));
    }

    @Test public void toString_withFileName() {
        final Position position = new Position(7, 8, "/foo/bar/baz.ebnf");
        assertThat(position.toString(), is("/foo/bar/baz.ebnf (7, 8)"));
    }

    @Test public void hashCode_withoutFileName() {
        final Position position1 = new Position(5, 10);
        final Position position2 = new Position(5, 10);
        final Position position3 = new Position(5, 5);

        assertThat(position1.hashCode(), is(position1.hashCode()));
        assertThat(position1.hashCode(), is(position2.hashCode()));
        assertThat(position2.hashCode(), is(position1.hashCode()));
        assertThat(position2.hashCode(), is(position2.hashCode()));

        assertThat(position3.hashCode(), is(position3.hashCode()));
        assertThat(position3.hashCode(), is(not(position1.hashCode())));
        assertThat(position3.hashCode(), is(not(position2.hashCode())));
    }

    @Test public void hashCode_withFileName() {
        final Position position1 = new Position(5, 10, "/foo/bar/baz.ebnf");
        final Position position2 = new Position(5, 10, "/foo/bar/baz.ebnf");
        final Position position3 = new Position(5, 5, "/foo/bar/baz.ebnf");

        assertThat(position1.hashCode(), is(position1.hashCode()));
        assertThat(position1.hashCode(), is(position2.hashCode()));
        assertThat(position2.hashCode(), is(position1.hashCode()));
        assertThat(position2.hashCode(), is(position2.hashCode()));

        assertThat(position3.hashCode(), is(position3.hashCode()));
        assertThat(position3.hashCode(), is(not(position1.hashCode())));
        assertThat(position3.hashCode(), is(not(position2.hashCode())));
    }

    @Test public void equals_withoutFileName() {
        final Position position1 = new Position(5, 10);
        final Position position2 = new Position(5, 10);
        final Position position3 = new Position(5, 5);

        assertThat(position1.equals(position1), is(true));
        assertThat(position1.equals(position2), is(true));
        assertThat(position2.equals(position1), is(true));
        assertThat(position2.equals(position2), is(true));

        assertThat(position3.equals(position3), is(true));
        assertThat(position3.equals(position1), is(not(true)));
        assertThat(position3.equals(position2), is(not(true)));
    }

    @Test public void equals_withFileName() {
        final Position position1 = new Position(5, 10, "/foo/bar/baz.ebnf");
        final Position position2 = new Position(5, 10, "/foo/bar/baz.ebnf");
        final Position position3 = new Position(5, 5, "/foo/bar/baz.ebnf");

        assertThat(position1.equals(position1), is(true));
        assertThat(position1.equals(position2), is(true));
        assertThat(position2.equals(position1), is(true));
        assertThat(position2.equals(position2), is(true));

        assertThat(position3.equals(position3), is(true));
        assertThat(position3.equals(position1), is(not(true)));
        assertThat(position3.equals(position2), is(not(true)));
    }

}
