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

import java.io.IOException;
import java.io.OutputStream;

/**
 * Captures the written bytes into a string buffer.
 *
 * This type of object is useful in tests to capture output written to {@link System#out}
 * or {@link System#err}:
 *
 * <code>
 * CapturingOutputStream out = new CapturingOutputStream();
 * System.setOut(out);
 *
 * // ... executes to code which calls System.out()
 *
 * String output = out.getCapturedOutput();
 * </code>
 *
 * This capturing output streams buffers everything written to {@link OutputStream#write(int)},
 * if you want to reset the buffer you must create a new instance.
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
public final class CapturingOutputStream extends OutputStream {

    /**
     * Buffers the output.
     */
    private final StringBuilder capturedOutput = new StringBuilder();

    @Override
    public void write(int b) throws IOException {
        final String character = String.valueOf((char) b);
        capturedOutput.append(character);
    }

    /**
     * Get the captured output as string.
     *
     * @return Return the captured string.
     */
    public String getCapturedOutput() {
        return capturedOutput.toString();
    }

    @Override
    public String toString() {
        return String.format("CapturingOutputStream{capturedOutput=%s}", capturedOutput);
    }

    @Override
    public int hashCode() {
        return capturedOutput.toString().hashCode();
    }

    @Override
    public boolean equals(final Object obj) {
        if (!(obj instanceof CapturingOutputStream)) {
            return false;
        }

        final CapturingOutputStream other = (CapturingOutputStream) obj;

        return capturedOutput.toString().equals(other.capturedOutput.toString());
    }

}
