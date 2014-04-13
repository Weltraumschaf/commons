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
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.CoreMatchers.sameInstance;
import static org.junit.Assert.assertThat;
import org.junit.Test;

/**
 * Tests for {@link Maps}.
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
public class MapsTest {

    @Test
    public void newHashMap() {
        final Map<Object, Object> m1 = Maps.newHashMap();
        assertThat(m1, is(not(nullValue())));
        assertThat(m1, is(instanceOf(HashMap.class)));

        final Map<Object, Object> m2 = Maps.newHashMap();
        assertThat(m2, is(not(nullValue())));
        assertThat(m2, is(instanceOf(HashMap.class)));
        assertThat(m2, is(not(sameInstance(m1))));

        final Map<Object, Object> m3 = Maps.newHashMap();
        assertThat(m3, is(not(nullValue())));
        assertThat(m3, is(instanceOf(HashMap.class)));
        assertThat(m3, is(not(sameInstance(m1))));
        assertThat(m3, is(not(sameInstance(m2))));
    }

    @Test
    public void newHashMap_mapInput() {
        final Map<String, Integer> input = Maps.newHashMap();
        input.put("foo", 1);
        input.put("bar", 2);
        input.put("baz", 3);

        final Map<String, Integer> m1 = Maps.newHashMap(input);
        assertThat(m1, is(not(nullValue())));
        assertThat(m1, is(instanceOf(HashMap.class)));
        assertThat(m1, is(equalTo(input)));
        assertThat(m1, is(not(sameInstance(input))));

        final Map<String, Integer> m2 = Maps.newHashMap(input);
        assertThat(m2, is(not(nullValue())));
        assertThat(m2, is(instanceOf(HashMap.class)));
        assertThat(m2, is(not(sameInstance(m1))));
        assertThat(m2, is(equalTo(input)));
        assertThat(m2, is(not(sameInstance(input))));

        final Map<String, Integer> m3 = Maps.newHashMap(input);
        assertThat(m3, is(not(nullValue())));
        assertThat(m3, is(instanceOf(HashMap.class)));
        assertThat(m3, is(not(sameInstance(m1))));
        assertThat(m3, is(not(sameInstance(m2))));
        assertThat(m3, is(equalTo(input)));
        assertThat(m3, is(not(sameInstance(input))));
    }

}
