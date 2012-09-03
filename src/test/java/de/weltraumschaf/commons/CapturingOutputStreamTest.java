/*
 * LICENSE
 *
 * "THE BEER-WARE LICENSE" (Revision 42):
 * "Sven Strittmatter" <weltraumschaf@googlemail.com> wrote this file.
 * As long as you retain this notice you can do whatever you want with
 * this stuff. If we meet some day, and you think this stuff is worth it,
 * you can buy me a beer in return.
 *
 */

package de.weltraumschaf.commons;

import java.io.PrintStream;
import org.junit.After;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

/**
 * Test cases for {@link CapturingOutputStream}.
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
public class CapturingOutputStreamTest {

    private final CapturingOutputStream sut = new CapturingOutputStream();
    private PrintStream backup;

    @Before public void changeSystemOut() {
        backup = System.out;
        System.setOut(new PrintStream(sut));
    }

    @After public void restoreSystemOut() {
        System.setOut(backup);
    }

    @Test public void writeAndGetCapturedOutput() {
        assertEquals("", sut.getCapturedOutput());
        System.out.print("foo");
        assertEquals("foo", sut.getCapturedOutput());
        System.out.print("bar");
        assertEquals("foobar", sut.getCapturedOutput());
    }
}
