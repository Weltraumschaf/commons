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
package de.weltraumschaf.commons.string;

import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import org.junit.Test;

/**
 * Tests for {@link StringJoiner}.
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
public class StringJoinerTest {

    @Test
    public void join_genericElements() {
        assertThat(StringJoiner.join((Object[]) null), is(""));
        assertThat(StringJoiner.join(new Object[0]), is(""));
        assertThat(StringJoiner.join(new Object[]{null}), is(""));
        assertThat(StringJoiner.join(new Object[]{"a", "b", "c"}), is("abc"));
        assertThat(StringJoiner.join(new Object[]{null, "", "a"}), is("a"));
    }

    @Test
    public void join_objectArray() {
        assertThat(StringJoiner.join((Object[]) null, '*'), is(""));
        assertThat(StringJoiner.join(new Object[0], '*'), is(""));
        assertThat(StringJoiner.join(new Object[]{null}, '*'), is(""));
        assertThat(StringJoiner.join(new Object[]{"a", "b", "c"}, ';'), is("a;b;c"));
        assertThat(StringJoiner.join(new Object[]{"a", "b", "c"}, (char) 0), is("abc"));
        assertThat(StringJoiner.join(new Object[]{null, "", "a"}, ';'), is(";;a"));
    }

    @Test
    public void join_longArray() {
        assertThat(StringJoiner.join((long[]) null, '*'), is(""));
        assertThat(StringJoiner.join(new long[0], '*'), is(""));
        assertThat(StringJoiner.join(new long[]{1, 2, 3}, ';'), is("1;2;3"));
        assertThat(StringJoiner.join(new long[]{1, 2, 3}, (char) 0), is("123"));
    }

    @Test
    public void join_intArray() {
        assertThat(StringJoiner.join((int[]) null, '*'), is(""));
        assertThat(StringJoiner.join(new int[0], '*'), is(""));
        assertThat(StringJoiner.join(new int[]{1, 2, 3}, ';'), is("1;2;3"));
        assertThat(StringJoiner.join(new int[]{1, 2, 3}, (char) 0), is("123"));
    }

    @Test
    public void join_shortArray() {
        assertThat(StringJoiner.join((short[]) null, '*'), is(""));
        assertThat(StringJoiner.join(new short[0], '*'), is(""));
        assertThat(StringJoiner.join(new short[]{1, 2, 3}, ';'), is("1;2;3"));
        assertThat(StringJoiner.join(new short[]{1, 2, 3}, (char) 0), is("123"));
    }

    @Test
    public void join_byteArray() {
        assertThat(StringJoiner.join((byte[]) null, '*'), is(""));
        assertThat(StringJoiner.join(new byte[0], '*'), is(""));
        assertThat(StringJoiner.join(new byte[]{1, 2, 3}, ';'), is("1;2;3"));
        assertThat(StringJoiner.join(new byte[]{1, 2, 3}, (char) 0), is("123"));
    }

    @Test
    public void join_charArray() {
        assertThat(StringJoiner.join((char[]) null, '*'), is(""));
        assertThat(StringJoiner.join(new char[0], '*'), is(""));
        assertThat(StringJoiner.join(new char[]{65, 66, 67}, ';'), is("A;B;C"));
        assertThat(StringJoiner.join(new char[]{65, 66, 67}, (char) 0), is("ABC"));
    }

    @Test
    public void join_floatArray() {
        assertThat(StringJoiner.join((float[]) null, '*'), is(""));
        assertThat(StringJoiner.join(new float[0], '*'), is(""));
        assertThat(StringJoiner.join(new float[]{1, 2, 3}, ';'), is("1.0;2.0;3.0"));
        assertThat(StringJoiner.join(new float[]{1, 2, 3}, (char) 0), is("1.02.03.0"));
    }

    @Test
    public void join_doubleArray() {
        assertThat(StringJoiner.join((double[]) null, '*'), is(""));
        assertThat(StringJoiner.join(new double[0], '*'), is(""));
        assertThat(StringJoiner.join(new double[]{1, 2, 3}, ';'), is("1.0;2.0;3.0"));
        assertThat(StringJoiner.join(new double[]{1, 2, 3}, (char) 0), is("1.02.03.0"));
    }

    @Test
    public void join_iterableWithStringSeparator() {
        assertThat(StringJoiner.join((Iterable)null, ""), is(""));
        assertThat(StringJoiner.join((Iterable)null, ";"), is(""));
        assertThat(StringJoiner.join(Collections.emptyList(), ""), is(""));
        assertThat(StringJoiner.join(Collections.emptyList(), ";"), is(""));
        assertThat(StringJoiner.join(Arrays.asList("a", "b", "c"), ""), is("abc"));
        assertThat(StringJoiner.join(Arrays.asList("a", "b", "c"), ";"), is("a;b;c"));
    }

    @Test
    public void join_iterablewithCharSeparator() {
        assertThat(StringJoiner.join((Iterable)null, (char)0), is(""));
        assertThat(StringJoiner.join((Iterable)null, ';'), is(""));
        assertThat(StringJoiner.join(Collections.emptyList(), (char)0), is(""));
        assertThat(StringJoiner.join(Collections.emptyList(), ';'), is(""));
        assertThat(StringJoiner.join(Arrays.asList("a", "b", "c"), (char)0), is("abc"));
        assertThat(StringJoiner.join(Arrays.asList("a", "b", "c"), ';'), is("a;b;c"));
    }

    @Test
    public void join_iteratorWithStringSeparator() {
        assertThat(StringJoiner.join((Iterator)null, ""), is(""));
        assertThat(StringJoiner.join((Iterator)null, ";"), is(""));
        assertThat(StringJoiner.join(Collections.emptyIterator(), ""), is(""));
        assertThat(StringJoiner.join(Collections.emptyIterator(), ";"), is(""));
        assertThat(StringJoiner.join(Arrays.asList("a", "b", "c").iterator(), ""), is("abc"));
        assertThat(StringJoiner.join(Arrays.asList("a", "b", "c").iterator(), ";"), is("a;b;c"));
    }

    @Test
    public void join_iteratorWithCharSeparator() {
        assertThat(StringJoiner.join((Iterator)null, (char)0), is(""));
        assertThat(StringJoiner.join((Iterator)null, ';'), is(""));
        assertThat(StringJoiner.join(Collections.emptyIterator(), (char)0), is(""));
        assertThat(StringJoiner.join(Collections.emptyIterator(), ';'), is(""));
        assertThat(StringJoiner.join(Arrays.asList("a", "b", "c").iterator(), (char)0), is("abc"));
        assertThat(StringJoiner.join(Arrays.asList("a", "b", "c").iterator(), ';'), is("a;b;c"));
    }
}
