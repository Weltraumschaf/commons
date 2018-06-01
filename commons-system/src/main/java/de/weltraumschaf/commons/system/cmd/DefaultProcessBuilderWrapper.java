package de.weltraumschaf.commons.system.cmd;


import de.weltraumschaf.commons.validate.Validate;

import java.io.IOException;
import java.util.Arrays;

/**
 * Default implementation which delegates to {@link ProcessBuilder}.
 *
 * @since 2.3.0
 * @author Sven Strittmatter &lt;weltraumschaf@googlemail.com&gt;
 */
final class DefaultProcessBuilderWrapper implements ProcessBuilderWrapper {

    @Override
    public Process start(final String... command) throws IOException {
        Validate.notNull(command, "command");

        if (command.length < 1) {
            throw new IllegalArgumentException("Parameter 'command' must not be empty!");
        }

        for (int i = 0; i < command.length; ++i) {
            Validate.notNull(command, i + "in command array");
        }

        return new ProcessBuilder(command).start();
    }
}
