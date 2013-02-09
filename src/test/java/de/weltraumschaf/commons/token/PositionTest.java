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

import org.junit.Test;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.is;

/**
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
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

}
