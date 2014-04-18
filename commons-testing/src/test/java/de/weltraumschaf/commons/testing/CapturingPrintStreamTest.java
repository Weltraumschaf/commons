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

package de.weltraumschaf.commons.testing;

import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.util.Locale;
import org.junit.Test;
import static org.junit.Assert.assertThat;
import static org.hamcrest.Matchers.*;
import org.junit.Before;
import org.junit.Ignore;
import static org.mockito.Mockito.*;

/**
 * Tests for {@link CapturingPrintStream}.
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
public class CapturingPrintStreamTest {

    private final CapturingPrintStream sut = new CapturingPrintStream();
    private PrintStream delegate;

    public CapturingPrintStreamTest() throws UnsupportedEncodingException {
        super();
    }

    @Before
    public void prepareDelegateSpy() {
        delegate = spy(sut.getDelegate());
        sut.setDelegate(delegate);
    }

    @Test
    public void getCapturedOutput() throws UnsupportedEncodingException {
        assertThat(sut.getCapturedOutput(), is(equalTo("")));
        sut.print("foobar");
        assertThat(sut.getCapturedOutput(), is(equalTo("foobar")));
    }

    @Test
    public void reset() throws UnsupportedEncodingException {
        sut.print("foobar");
        assertThat(sut.getCapturedOutput(), is(equalTo("foobar")));
        sut.reset();
        assertThat(sut.getCapturedOutput(), is(equalTo("")));
    }

    @Test
    public void flush() {
        sut.flush();
        verify(delegate, times(1)).flush();
    }

    @Test
    @Ignore("Throws java.lang.IllegalStateException.")
    public void close() {
        sut.close();
        verify(delegate, times(1)).close();
    }

    @Test
    public void checkError() {
        sut.checkError();
        verify(delegate, times(1)).checkError();
    }

    @Test
    public void write_int() {
        sut.write(42);
        verify(delegate, times(1)).write(42);
    }

    @Test
    public void write_3args() {
        final byte buf[] = new byte[] {1, 2, 3};
        final int off = 0;
        final int len = 3;
        sut.write(buf, off, len);
        verify(delegate, times(1)).write(buf, off, len);
    }

    @Test
    public void print_boolean() {
        sut.print(true);
        verify(delegate, times(1)).print(true);
    }

    @Test
    public void print_char() {
        sut.print('c');
        verify(delegate, times(1)).print('c');
    }

    @Test
    public void print_int() {
        sut.print(42);
        verify(delegate, times(1)).print(42);
    }

    @Test
    public void print_long() {
        sut.print(42L);
        verify(delegate, times(1)).print(42L);
    }

    @Test
    public void print_float() {
        sut.print(3.14f);
        verify(delegate, times(1)).print(3.14f);
    }

    @Test
    public void print_double() {
        sut.print(3.14d);
        verify(delegate, times(1)).print(3.14d);
    }

    @Test
    public void print_charArr() {
        sut.print(new char[] {'f', 'o', 'o'});
        verify(delegate, times(1)).print(new char[] {'f', 'o', 'o'});
    }

    @Test
    public void print_String() {
        sut.print("foobar");
        verify(delegate, times(1)).print("foobar");
    }

    @Test
    public void print_Object() {
        final Object o = new Object();
        sut.print(o);
        verify(delegate, times(1)).print(o);
    }

    @Test
    public void println_0args() {
        sut.println();
        verify(delegate, times(1)).println();
    }

    @Test
    public void println_boolean() {
        sut.println(true);
        verify(delegate, times(1)).println(true);
    }

    @Test
    public void println_char() {
        sut.println('c');
        verify(delegate, times(1)).println('c');
    }

    @Test
    public void println_int() {
        sut.println(42);
        verify(delegate, times(1)).println(42);
    }

    @Test
    public void println_long() {
        sut.println(42L);
        verify(delegate, times(1)).println(42L);
    }

    @Test
    public void println_float() {
        sut.println(3.14f);
        verify(delegate, times(1)).println(3.14f);
    }

    @Test
    public void println_double() {
        sut.println(3.14d);
        verify(delegate, times(1)).println(3.14d);
    }

    @Test
    public void println_charArr() {
        sut.println(new char[] {'f', 'o', 'o'});
        verify(delegate, times(1)).println(new char[] {'f', 'o', 'o'});
    }

    @Test
    public void println_String() {
        sut.println("foobar");
        verify(delegate, times(1)).println("foobar");
    }

    @Test
    public void println_Object() {
        final Object o = new Object();
        sut.println(o);
        verify(delegate, times(1)).println(o);
    }

    @Test
    public void printf_String_ObjectArr() {
        final Object o1 = new Object();
        final Object o2 = new Object();
        sut.printf("format", o1, o2);
        verify(delegate, times(1)).printf("format", o1, o2);
    }

    @Test
    public void printf_3args() {
        final Object o1 = new Object();
        final Object o2 = new Object();
        sut.printf(Locale.UK, "format", o1, o2);
        verify(delegate, times(1)).printf(Locale.UK, "format", o1, o2);
    }

    @Test
    public void format_String_ObjectArr() {
        final Object o1 = new Object();
        final Object o2 = new Object();
        sut.format("format", o1, o2);
        verify(delegate, times(1)).format("format", o1, o2);
    }

    @Test
    public void format_3args() {
        final Object o1 = new Object();
        final Object o2 = new Object();
        sut.format(Locale.UK, "format", o1, o2);
        verify(delegate, times(1)).format(Locale.UK, "format", o1, o2);
    }

    @Test
    public void append_CharSequence() {
        sut.append("foobar");
        verify(delegate, times(1)).append("foobar");
    }

    @Test
    public void append_3args() {
        sut.append("foobar", 1, 2);
        verify(delegate, times(1)).append("foobar", 1, 2);
    }

    @Test
    public void append_char() {
        sut.append('c');
        verify(delegate, times(1)).append('c');
    }

}
