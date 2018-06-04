package de.weltraumschaf.commons.system.cmd;


import de.weltraumschaf.commons.validate.Validate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Default implementation which delegates to {@link ProcessBuilder}.
 *
 * @since 2.3.0
 * @author Sven Strittmatter &lt;weltraumschaf@googlemail.com&gt;
 */
final class DefaultProcessBuilderWrapper implements ProcessBuilderWrapper {

    private static final Logger LOG = LoggerFactory.getLogger(DefaultProcessBuilderWrapper.class);

    @Override
    public Process start(final List<String> command) throws IOException {
        Validate.notNull(command, "command");

        if (command.size() < 1) {
            throw new IllegalArgumentException("Parameter 'command' must not be empty!");
        }

        for (int i = 0; i < command.size(); ++i) {
            Validate.notNull(command, i + "in command array");
        }

        LOG.debug("Starting process with command: '{}'", command);
        return new ProcessBuilder(command).start();
    }
}
