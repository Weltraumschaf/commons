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
import static org.hamcrest.CoreMatchers.containsString;
import org.hamcrest.Matcher;
import org.junit.rules.ExternalResource;

/**
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
public final class CapturedOutput extends ExternalResource {

    private final CapturingPrintStream out = new CapturingPrintStream();
    private final CapturingPrintStream err = new CapturingPrintStream();
    private PrintStream outBackup;
    private PrintStream errBackup;

    @Override
    protected void before() throws Throwable {
        redirectOutputStreams();
    }

    private void redirectOutputStreams() {
        outBackup = System.out;
        System.setOut(out.reset());
        errBackup = System.err;
        System.setErr(err.reset());
    }

    @Override
    protected void after() {
        restoreOutputStreams();
    }

    private void restoreOutputStreams() {
        System.setOut(outBackup);
        System.setErr(errBackup);
    }

    public void expectOut(final String substring) {
        expectOut(containsString(substring));
    }

    public void expectOut(final Matcher<String> matcher) {
        throw new UnsupportedOperationException("Not implemented yet!");
    }

    public void expectErr(final String substring) {
        expectErr(containsString(substring));
    }

    public void expectErr(final Matcher<String> matcher) {
        throw new UnsupportedOperationException("Not implemented yet!");
    }

}
