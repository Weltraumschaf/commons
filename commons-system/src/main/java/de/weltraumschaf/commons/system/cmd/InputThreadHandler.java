package de.weltraumschaf.commons.system.cmd;

import de.weltraumschaf.commons.validate.Validate;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Scanner;

/**
 * Reads the output from the process in separate thread.
 *
 * @since 2.3.0
 * @author Sven Strittmatter &lt;weltraumschaf@googlemail.com&gt;
 */
final class InputThreadHandler extends Thread {

    /**
     * Input to read from.
     */
    private final InputStream input;
    /**
     * Collects the read input.
     */
    private final StringBuilder buffer = new StringBuilder();

    /**
     * Dedicated constructor.
     *
     * @param input must not be {@code null}
     */
    InputThreadHandler(final InputStream input) {
        super();
        this.input = Validate.notNull(input, "input");
    }

    @Override
    public void run() {
        try (final Scanner scanner = new Scanner(new InputStreamReader(input))) {
            while (scanner.hasNextLine()) {
                buffer.append(scanner.nextLine());

                if (scanner.hasNextLine()) {
                    buffer.append('\n');
                }
            }
        }
    }

    /**
     * Get the collected output.
     *
     * @return never {@code null}
     */
    String getOutput() {
        return buffer.toString();
    }

}
