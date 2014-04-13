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

package de.weltraumschaf.commons.guava;

import java.util.ArrayList;
import java.util.List;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.CoreMatchers.sameInstance;
import static org.junit.Assert.assertThat;
import org.junit.Test;

/**
 * Tests for {@link Lists}.
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
public class ListsTest {

    @Test
    public void newArrayList() {
        final List<Object> l1 = Lists.newArrayList();
        assertThat(l1, is(not(nullValue())));
        assertThat(l1, is(instanceOf(ArrayList.class)));

        final List<Object> l2 = Lists.newArrayList();
        assertThat(l2, is(not(nullValue())));
        assertThat(l2, is(instanceOf(ArrayList.class)));
        assertThat(l2, is(not(sameInstance(l1))));

        final List<Object> l3 = Lists.newArrayList();
        assertThat(l3, is(not(nullValue())));
        assertThat(l3, is(instanceOf(ArrayList.class)));
        assertThat(l3, is(not(sameInstance(l1))));
        assertThat(l3, is(not(sameInstance(l2))));
    }

    @Test
    public void newArrayList_Iterable() {
    }

    @Test
    public void newArrayList_Iterator() {
    }

}
