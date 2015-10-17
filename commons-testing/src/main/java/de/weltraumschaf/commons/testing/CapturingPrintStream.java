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
package de.weltraumschaf.commons.testing;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.Locale;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;

/**
 * Captures the written bytes instead of printing them to any output stream.
 *
 * <p>
 * This type of object is useful in tests to capture output written to {@link java.lang.System#out} or
 * {@link java.lang.System#err}:
 * </p>
 *
 * <pre>{@code
 * final CapturingPrintStream out = new CapturingPrintStream();
 * System.setOut(out);
 *
 * // ... executes to code which calls System.out()
 *
 * final String output = out.getCapturedOutput();
 * }</pre>
 *
 * <p>
 * This capturing output streams buffers everything written to {@link java.io.OutputStream#write(int)}, if you want to
 * reset the buffer you must create a new instance.
 * </p>
 *
 * @since 1.0.0
 * @author Sven Strittmatter &lt;weltraumschaf@googlemail.com&gt;
 * @version $Id: $Id
 */
public final class CapturingPrintStream extends PrintStream {

    /**
     * Used to collect captured output.
     */
    private final ByteArrayOutputStream capturedOutput = new ByteArrayOutputStream();
    /**
     * used to do the real work.
     */
    private PrintStream delegate;
    /**
     * Encoding of the captured bytes.
     */
    private final String encoding;

    /**
     * Creates the print stream with platform encoding.
     *
     * @throws java.io.UnsupportedEncodingException if the platform encoding is not supported
     */
    public CapturingPrintStream() throws UnsupportedEncodingException {
        this(Charset.defaultCharset().name());
    }

    /**
     * Dedicated constructor.
     *
     * <p>
     * Initializes parents output stream with a dummy stream.
     * </p>
     *
     * @param encoding must not be {@code null} or empty
     * @throws java.io.UnsupportedEncodingException if the platform encoding is not supported
     */
    public CapturingPrintStream(final String encoding) throws UnsupportedEncodingException {
        super(new OutputStream() {
            @Override
            public void write(int b) throws IOException {
                // We ignore everything here.
            }
        }, true, encoding);
        delegate = new PrintStream(capturedOutput, true, encoding);
        this.encoding = encoding;
    }

    /**
     * Only for testing.
     *
     * @return never {@code null}
     */
    PrintStream getDelegate() {
        return delegate;
    }

    /**
     * Injection point for testing.
     *
     * @param delegate must not be {@code null}
     */
    void setDelegate(final PrintStream delegate) {
        if (null == delegate) {
            throw new NullPointerException("Parameter 'delegate' must not be null!");
        }

        this.delegate = delegate;
    }

    /**
     * Returns the whole string of everything written to this stream.
     *
     * @return never {@code null}
     * @throws java.io.UnsupportedEncodingException if the platform encoding is not supported
     */
    public String getCapturedOutput() throws UnsupportedEncodingException {
        return capturedOutput.toString(encoding);
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

    /**
     * {@inheritDoc}
     */
    @Override
    public void flush() {
        delegate.flush();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void close() {
        delegate.close();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean checkError() {
        return delegate.checkError();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void write(int b) {
        delegate.write(b);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void write(final byte[] buf, final int off, final int len) {
        delegate.write(buf, off, len);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void print(final boolean b) {
        delegate.print(b);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void print(final char c) {
        delegate.print(c);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void print(final int i) {
        delegate.print(i);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void print(final long l) {
        delegate.print(l);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void print(final float f) {
        delegate.print(f);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void print(final double d) {
        delegate.print(d);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void print(final char[] s) {
        delegate.print(s);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void print(final String s) {
        delegate.print(s);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void print(final Object obj) {
        delegate.print(obj);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void println() {
        delegate.println();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void println(boolean x) {
        delegate.println(x);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void println(char x) {
        delegate.println(x);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void println(final int x) {
        delegate.println(x);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void println(final long x) {
        delegate.println(x);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void println(final float x) {
        delegate.println(x);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void println(double x) {
        delegate.println(x);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void println(final char[] x) {
        delegate.println(x);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void println(final String x) {
        delegate.println(x);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void println(final Object x) {
        delegate.println(x);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PrintStream printf(final String format, final Object... args) {
        return delegate.printf(format, args);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PrintStream printf(final Locale l, final String format, final Object... args) {
        return delegate.printf(l, format, args);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PrintStream format(final String format, final Object... args) {
        return delegate.format(format, args);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PrintStream format(final Locale l, final String format, final Object... args) {
        return delegate.format(l, format, args);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PrintStream append(final CharSequence csq) {
        return delegate.append(csq);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PrintStream append(final CharSequence csq, final int start, final int end) {
        return delegate.append(csq, start, end);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PrintStream append(final char c) {
        return delegate.append(c);
    }
}
