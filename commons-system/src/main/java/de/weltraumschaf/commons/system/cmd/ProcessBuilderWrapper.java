package de.weltraumschaf.commons.system.cmd;

import java.io.IOException;

/**
 * Interface to abstract the JAva's default process builder for better testability.
 *
 * @since 2.3.0
 * @author Sven Strittmatter &lt;weltraumschaf@googlemail.com&gt;
 */
interface ProcessBuilderWrapper {

    /**
     * Creates and executes a command.
     * <p>
     * For example to execute {@literal ls -la /foo/bar} invoke:
     * </p>
     * <pre>{@code
     * ProcessBuilderWrapper builder = ...;
     * Process proc = builder.start("ls", "-la", "/foo/bar");
     * }</pre>
     *
     * @param command must not be {@code null}
     * @return never {@code null}
     * @throws IOException if an I/O error occurs
     */
    Process start(String... command) throws IOException;
}
