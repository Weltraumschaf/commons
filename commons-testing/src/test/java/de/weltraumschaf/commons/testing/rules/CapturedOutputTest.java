/*
 *  LICENSE
 *
 * "THE BEER-WARE LICENSE" (Revision 43):
 * "Sven Strittmatter" &lt;weltraumschaf@googlemail.com&gt; wrote this file.
 * As long as you retain this notice you can do whatever you want with
 * this stuff. If we meet some day, and you think this stuff is worth it,
 * you can buy me a non alcohol-free beer in return.
 *
 * Copyright (C) 2012 "Sven Strittmatter" &lt;weltraumschaf@googlemail.com&gt;
 */
package de.weltraumschaf.commons.testing.rules;

import de.weltraumschaf.commons.application.IOStreams;
import java.io.UnsupportedEncodingException;
import static org.hamcrest.Matchers.endsWith;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.startsWith;
import org.junit.Test;
import org.junit.Rule;
import org.junit.rules.ExpectedException;

/**
 * Tests for {@link CapturedOutput}.
 *
 * @author Sven Strittmatter &lt;weltraumschaf@googlemail.com&gt;
 */
public class CapturedOutputTest {

    @Rule
    //CHECKSTYLE:OFF
    public final CapturedOutput sut = new CapturedOutput();
    //CHECKSTYLE:ON

    @Rule
    //CHECKSTYLE:OFF
    public final ExpectedException thrown = ExpectedException.none();
    //CHECKSTYLE:ON

    public CapturedOutputTest() throws UnsupportedEncodingException {
        super();
    }

    @Test
    public void captureOut() {
        sut.expectOut("foobar");
        sut.expectOut(startsWith("foo"));
        sut.expectOut(not(startsWith("bar")));
        sut.expectOut(endsWith("bar"));
        sut.expectOut(not("snafu"));

        System.out.print("foobar");
    }

    @Test
    public void captureOut_withIo() throws UnsupportedEncodingException {
        sut.expectOut("foobar");
        sut.expectOut(startsWith("foo"));
        sut.expectOut(not(startsWith("bar")));
        sut.expectOut(endsWith("bar"));
        sut.expectOut(not("snafu"));

        IOStreams.newDefault().print("foobar");
    }

    @Test
    public void captureErr() {
        sut.expectErr("foobar");
        sut.expectErr(startsWith("foo"));
        sut.expectErr(not(startsWith("bar")));
        sut.expectErr(endsWith("bar"));
        sut.expectErr(not("snafu"));

        System.err.print("foobar");
    }

    @Test
    public void captureErr_withIo() throws UnsupportedEncodingException {
        sut.expectErr("foobar");
        sut.expectErr(startsWith("foo"));
        sut.expectErr(not(startsWith("bar")));
        sut.expectErr(endsWith("bar"));
        sut.expectErr(not("snafu"));

        IOStreams.newDefault().error("foobar");
    }

    @Test
    public void captureErr_withOnlyOneMatcher() {
        sut.expectErr("foo");

        System.err.println("foo");
    }

    @Test
    public void captureOut_withOnlyOneMatcher() {
        sut.expectOut("foo");

        System.out.println("foo");
    }

    @Test
    public void captureErr_doesNotMatch() {
        thrown.expect(AssertionError.class);
        thrown.expectMessage("\n"
            + "Expected: a string containing \"foo\"\n"
            + "     but: was \"bar\n"
            + "\"");

        sut.expectErr("foo");

        System.err.println("bar");
    }

    @Test
    public void captureOut_doesNotMatch() {
        thrown.expect(AssertionError.class);
        thrown.expectMessage("\n"
            + "Expected: a string containing \"foo\"\n"
            + "     but: was \"bar\n"
            + "\"");

        sut.expectOut("foo");

        System.out.println("bar");
    }
}
