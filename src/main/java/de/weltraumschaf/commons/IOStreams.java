/*
 * LICENSE
 *
 * "THE BEER-WARE LICENSE" (Revision 43):
 * "Sven Strittmatter" <weltraumschaf(at)googlemail(dot)com> wrote this file.
 * As long as you retain this notice you can do whatever you want with
 * this stuff. If we meet some day, and you think this stuff is worth it,
 * you can buy me a non alcohol free beer in return.
 *
 * Copyright (C) 2012 "Sven Strittmatter" <weltraumschaf(at)googlemail(dot)com>
 */
package de.weltraumschaf.commons;

import java.io.InputStream;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;

/**
 * Immutable aggregate object which contains STDIN, STDOUT and STDERR streams.
 *
 * It is not good practice to clutter production code with calls to
 * {@link System#out}, {@link System#err}, and {@link System#in}. But on the
 * other hand most applications must do I/O to the user. This aggregate object
 * contains I/O streams to pass around as injected dependency. It is only
 * necessary to the systems IO only at the main applications entry point:
 *
 * <code>
 * public void main(final String[] args) {
 *      IOStreams io = new IOStreams(System.in, System.out, System.err);
 *
 *      // Pass around the io object to your client code.
 *
 *      System.exit(0);
 * }
 * </code>
 *
 * In the test code it will be easy to replace the I/O with mocks:
 *
 * <code>
 * &#064;Test public void someTestWithIO() {
 *      IOStreams io = new IOStreams(mock(InputStream.class),
 *                                   mock(PrintStream.class),
 *                                   mock(PrintStream.class));
 *
 *      // Pass around the io object to your client code and assert something.
 * }
 * </code>
 *
 * As a convenience method for creating an I/O streams object with the default I/O streams
 * of {@link System} you can use {@link IOStreams#newDefault()}.
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
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
     * @param stdin Input stream.
     * @param stdout Output stream.
     * @param stderr Error stream.
     */
    public IOStreams(final InputStream stdin, final PrintStream stdout, final PrintStream stderr) {
        this.stdin  = stdin;
        this.stdout = stdout;
        this.stderr = stderr;
    }

    /**
     * Get standard errorln output stream.
     *
     * @return Print stream object.
     */
    @Override
    public PrintStream getStderr() {
        return stderr;
    }

    /**
     * Get standard input stream.
     *
     * @return Input stream object.
     */
    @Override
    public InputStream getStdin() {
        return stdin;
    }

    /**
     * Get standard output stream.
     *
     * @return Print stream object.
     */
    @Override
    public PrintStream getStdout() {
        return stdout;
    }

    /**
     * Creates same as {@link #newDefault(java.lang.String)} but with {@link #DEFAULT_ENCODING} as encoding.
     *
     * @return Return always new instance.
     * @throws UnsupportedEncodingException If system does not support {@link #DEFAULT_ENCODING encoding}.
     */
    public static IOStreams newDefault() throws UnsupportedEncodingException {
        return newDefault(DEFAULT_ENCODING);
    }

    /**
     * Creates a new streams object initialized with {@link System#in}, {@link System#out}, and {@link System#err}.
     *
     * The output {@link PrintStream "print streams"} get {@link #DEFAULT_ENCODING} as encoding set.
     * Also the {@link System#out}, and {@link System#err} are changed with a copy of the original
     * print stream with the encoding.
     *
     * @param encoding Character encoding.
     * @return Return always new instance.
     * @throws UnsupportedEncodingException If system does not support passed encoding.
     */
    public static IOStreams newDefault(final String encoding) throws UnsupportedEncodingException {
        final PrintStream out = new PrintStream(System.out, true, encoding);
        final PrintStream err = new PrintStream(System.err, true, encoding);
        System.setOut(out);
        System.setErr(err);
        return new IOStreams(System.in, out, err);
    }

    /**
     * Prints exception stack trace.
     *
     * @param ex Exception to print.
     */
    @Override
    public void printStackTrace(Throwable ex) {
        ex.printStackTrace(getStderr());
    }

    /**
     * Prints error line.
     *
     * @param str String to print.
     */
    @Override
    public void errorln(final String str) {
        getStderr().println(str);
    }

    /**
     * Prints error.
     *
     * @param str String to print.
     */
    @Override
    public void error(final String str) {
        getStderr().print(str);
    }

    /**
     * Prints string line.
     *
     * @param str String to print.
     */
    @Override
    public void println(final String str) {
        getStdout().println(str);
    }

    /**
     * Prints string.
     *
     * @param str String to print.
     */
    @Override
    public void print(final String str) {
        getStdout().print(str);
    }

}
