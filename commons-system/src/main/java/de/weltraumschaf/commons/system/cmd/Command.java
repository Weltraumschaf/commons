package de.weltraumschaf.commons.system.cmd;

import java.io.IOException;

/**
 * A executable command.
 *
 * @since 2.3.0
 * @author Sven Strittmatter &lt;weltraumschaf@googlemail.com&gt;
 */
public interface Command {

    /**
     * Executes the command.
     *
     * @return never {@code null}
     * @throws IOException on any IO error
     * @throws InterruptedException on any thread error
     */
    CommandResult execute() throws IOException, InterruptedException;

}
