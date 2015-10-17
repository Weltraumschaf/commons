/*
 * LICENSE
 *
 * "THE BEER-WARE LICENSE" (Revision 43):
 * "Sven Strittmatter" <weltraumschaf(at)googlemail(dot)com> wrote this file.
 * As long as you retain this notice you can do whatever you want with
 * this stuff. If we meet some day, and you think this stuff is worth it,
 * you can buy me a non alcohol-free beer in return.
 *
 * Copyright (C) 2012 "Sven Strittmatter" <weltraumschaf(at)googlemail(dot)com>
 */
package de.weltraumschaf.commons.application;

import de.weltraumschaf.commons.validate.Validate;
import java.io.InputStream;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;

/**
 * Immutable aggregate object which contains STDIN, STDOUT and STDERR streams.
 *
 * <p>
 * It is not good practice to clutter production code with calls to
 * {@link java.lang.System#out}, {@link java.lang.System#err}, and {@link java.lang.System#in}. But on the other hand
 * most applications must do I/O to the user. This aggregate object contains I/O streams to pass around as injected
 * dependency. It is only necessary to the systems IO only at the main applications entry point:
 * </p>
 *
 * <pre>{@code
 * public void main(final String[] args) {
 *      final IOStreams io = new IOStreams(System.in, System.out, System.err);
 *
 *      // Pass around the io object to your client code.
 *
 *      System.exit(0);
 * }
 * }</pre>
 *
 * <p>
 * In the test code it will be easy to replace the I/O with mocks:
 * </p>
 *
 * <pre>{@code
 * &#064;Test public void someTestWithIO() {
 *      final IOStreams io = new IOStreams(mock(InputStream.class),
 *                                   mock(PrintStream.class),
 *                                   mock(PrintStream.class));
 *
 *      // Pass around the io object to your client code and assert something.
 * }
 * }</pre>
 *
 * <p>
 * As a convenience method for creating an I/O streams object with the default I/O streams of {@link java.lang.System}
 * you can use {@link de.weltraumschaf.commons.application.IOStreams#newDefault()}.
 * </p>
 *
 * @since 1.0.0
 * @author Sven Strittmatter &lt;weltraumschaf@googlemail.com&gt;
 */
public final class IOStreams implements IO {

    /**
     * Default character encoding for out put print streams.
     */
    public static final String DEFAULT_ENCODING = "UTF-8";

    /**
     * Standard input stream.
     */
    private final InputStream stdin;

    /**
     * Standard output stream.
     */
    private final PrintStream stdout;

    /**
     * Standard error stream.
     */
    private final PrintStream stderr;

    /**
     * Initializes the streams.
     *
     * @param stdin must not be {@code null}
     * @param stdout must not be {@code null}
     * @param stderr must not be {@code null}
     */
    public IOStreams(final InputStream stdin, final PrintStream stdout, final PrintStream stderr) {
        super();
        this.stdin = Validate.notNull(stdin, "stdin");
        this.stdout = Validate.notNull(stdout, "stdout");
        this.stderr = Validate.notNull(stderr, "stderr");
    }

    @Override
    public PrintStream getStderr() {
        return stderr;
    }

    @Override
    public InputStream getStdin() {
        return stdin;
    }

    @Override
    public PrintStream getStdout() {
        return stdout;
    }

    /**
     * Creates same as {@link #newDefault(java.lang.String)} but with {@link #DEFAULT_ENCODING} as encoding.
     *
     * @return never {@code null} always new instance.
     * @throws UnsupportedEncodingException if system does not support {@link #DEFAULT_ENCODING encoding}
     */
    public static IOStreams newDefault() throws UnsupportedEncodingException {
        return newDefault(DEFAULT_ENCODING);
    }

    /**
     * Creates a new streams object initialized with {@link java.lang.System#in}, {@link java.lang.System#out}, and
     * {@link java.lang.System#err}.
     * <p>
     * The output {@link PrintStream "print streams"} get {@link #DEFAULT_ENCODING} as encoding set. Also the
     * {@link java.lang.System#out}, and {@link java.lang.System#err} are changed with a copy of the original print
     * stream with the encoding.
     * </p>
     *
     * @param encoding must not be {@code null} or empty
     * @return never {@code null}, always new instance
     * @throws UnsupportedEncodingException if system does not support passed encoding
     */
    public static IOStreams newDefault(final String encoding) throws UnsupportedEncodingException {
        Validate.notEmpty(encoding, "encoding");
        final PrintStream out = new PrintStream(System.out, true, encoding);
        final PrintStream err = new PrintStream(System.err, true, encoding);
        System.setOut(out);
        System.setErr(err);
        return new IOStreams(System.in, out, err);
    }

    @Override
    public void printStackTrace(final Throwable ex) {
        Validate.notNull(ex, "ex").printStackTrace(getStderr());
    }

    @Override
    public void errorln(final String str) {
        getStderr().println(str);
    }

    @Override
    public void error(final String str) {
        getStderr().print(str);
    }

    @Override
    public void println(final String str) {
        getStdout().println(str);
    }

    @Override
    public void print(final String str) {
        getStdout().print(str);
    }

}
