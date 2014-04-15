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

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.Locale;

/**
 * Captures the written bytes instead of printing them to any output stream.
 *
 * <p>
 * This type of object is useful in tests to capture output written to {@link System#out}
 * or {@link System#err}:
 * </p>
 *
 * <pre>{@code
 * final CapturingPrintStream out = new CapturingPrintStream();
 * System.setOut(out);
 *
 * // ... executes to code which calls System.out()
 *
 * String output = out.getCapturedOutput();
 * }</pre>
 *
 * <p>
 * This capturing output streams buffers everything written to {@link OutputStream#write(int)},
 * if you want to reset the buffer you must create a new instance.
 * </p>
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
public final class CapturingPrintStream extends PrintStream {

    /**
     * Used to collect captured output.
     */
    private final ByteArrayOutputStream capturedOutput = new ByteArrayOutputStream();
    /**
     * used to do the real work.
     */
    private PrintStream delegate = new PrintStream(capturedOutput);

    /**
     * Only for testing.
     *
     * @return never {@code null}
     */
    PrintStream getDelegate() {
        return delegate;
    }

    void setDelegate(final PrintStream delegate) {
        if (null == delegate) {
            throw new NullPointerException("Parameter 'delegate' must not be null!");
        }

        this.delegate = delegate;
    }

    /**
     * Dedicated constructor.
     *
     * <p>
     * Initializes parents output stream with a dummy stream.
     * </p>
     */
    public CapturingPrintStream() {
        super(new OutputStream() {
            @Override
            public void write(int b) throws IOException {
                // We ignore everything here.
            }
        });
    }

    /**
     * Returns the whole string of everything written to this stream.
     *
     * @return never {@code null}
     */
    public String getCapturedOutput() {
        return capturedOutput.toString();
    }

    /**
     * Resets the captured output.
     *
     * @return self for chaining
     */
    public CapturingPrintStream reset() {
        capturedOutput.reset();
        return this;
    }

    @Override
    public void flush() {
        delegate.flush();
    }

    @Override
    public void close() {
        delegate.close();
    }

    @Override
    public boolean checkError() {
        return delegate.checkError();
    }

    @Override
    public void write(int b) {
        delegate.write(b);
    }

    @Override
    public void write(final byte buf[], final int off, final int len) {
        delegate.write(buf, off, len);
    }

    @Override
    public void print(final boolean b) {
        delegate.print(b);
    }

    @Override
    public void print(final char c) {
        delegate.print(c);
    }

    @Override
    public void print(final int i) {
        delegate.print(i);
    }

    @Override
    public void print(final long l) {
        delegate.print(l);
    }

    @Override
    public void print(final float f) {
        delegate.print(f);
    }

    @Override
    public void print(final double d) {
        delegate.print(d);
    }

    @Override
    public void print(final char s[]) {
        delegate.print(s);
    }

    @Override
    public void print(final String s) {
        delegate.print(s);
    }

    @Override
    public void print(final Object obj) {
        delegate.print(obj);
    }

    @Override
    public void println() {
        delegate.println();
    }

    @Override
    public void println(boolean x) {
        delegate.println(x);
    }

    @Override
    public void println(char x) {
        delegate.println(x);
    }

    @Override
    public void println(final int x) {
        delegate.println(x);
    }

    @Override
    public void println(final long x) {
        delegate.println(x);
    }

    @Override
    public void println(final float x) {
        delegate.println(x);
    }

    @Override
    public void println(double x) {
        delegate.println(x);
    }

    @Override
    public void println(final char x[]) {
        delegate.println(x);
    }

    @Override
    public void println(final String x) {
        delegate.println(x);
    }

    @Override
    public void println(final Object x) {
        delegate.println(x);
    }

    @Override
    public PrintStream printf(final String format, final Object... args) {
        return delegate.printf(format, args);
    }

    @Override
    public PrintStream printf(final Locale l, final String format, final Object... args) {
        return delegate.printf(l, format, args);
    }

    @Override
    public PrintStream format(final String format, final Object... args) {
        return delegate.format(format, args);
    }

    @Override
    public PrintStream format(final Locale l, final String format, final Object... args) {
        return delegate.format(l, format, args);
    }

    @Override
    public PrintStream append(final CharSequence csq) {
        return delegate.append(csq);
    }

    @Override
    public PrintStream append(final CharSequence csq, final int start, final int end) {
        return delegate.append(csq, start, end);
    }

    @Override
    public PrintStream append(final char c) {
        return delegate.append(c);
    }
}
