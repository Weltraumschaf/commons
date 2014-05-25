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

import de.weltraumschaf.commons.application.IOStreams;
import java.io.UnsupportedEncodingException;
import static org.hamcrest.Matchers.endsWith;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.startsWith;
import org.junit.Test;
import org.junit.Rule;

/**
 * Tests for {@link CapturedOutput}.
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
public class CapturedOutputTest {

    @Rule
    public final CapturedOutput sut = new CapturedOutput();

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

}
