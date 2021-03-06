package de.weltraumschaf.commons.system.cmd;

import de.weltraumschaf.commons.validate.Validate;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * Common base class for commands.
 * <p>
 * See this <a href="http://examples.javacodegeeks.com/core-java/lang/processbuilder/java-lang-processbuilder-example/">
 * tutorial</a> for more information.
 * </p>
 *
 * @author Sven Strittmatter &lt;weltraumschaf@googlemail.com&gt;
 * @since 2.3.0
 */
public final class BaseCommand implements Command {

    /**
     * Command to execute.
     */
    private final String command;
    /**
     * Command arguments.
     */
    private final Collection<String> arguments;
    /**
     * Abstracts the process builder for testability.
     */
    private final ProcessBuilderWrapper builder;

    /**
     * Convenience constructor for no arguments.
     *
     * @param command the command itself, must not be {@code null} or empty
     */
    public BaseCommand(final String command) {
        this(command, Collections.emptyList());
    }

    /**
     * Convenience constructor which initializes the {@link ProcessBuilderWrapper}.
     *
     * @param command   the command itself, must not be {@code null} or empty
     * @param arguments must not be {@code null}, maybe empty
     */
    public BaseCommand(final String command, final Collection<String> arguments) {
        this(command, arguments, new DefaultProcessBuilderWrapper());
    }

    /**
     * Dedicated constructor.
     *
     * @param command   the command itself, must not be {@code null} or empty
     * @param arguments must not be {@code null}, maybe empty
     * @param builder   must not be {@code null}
     */
    public BaseCommand(final String command, final Collection<String> arguments, final ProcessBuilderWrapper builder) {
        super();
        this.command = Validate.notEmpty(command, "command");
        this.arguments = Validate.notNull(arguments, "arguments");
        this.builder = Validate.notNull(builder, "builder");
    }

    @Override
    public final CommandResult execute() throws IOException, InterruptedException {
        final List<String> commandWithArguments = new ArrayList<>();
        commandWithArguments.add(command);
        commandWithArguments.addAll(arguments);
        final Process process = builder.start(commandWithArguments);
        final InputThreadHandler stdout = prepareInputHandler(process.getInputStream());
        final InputThreadHandler stderr = prepareInputHandler(process.getErrorStream());

        final int exitCode = process.waitFor();
        stdout.join();
        stderr.join();

        if (exitCode == 0) {
            return CommandResult.ok(stdout.getOutput());
        }

        return CommandResult.error(exitCode, stderr.getOutput());
    }

    private InputThreadHandler prepareInputHandler(final InputStream input) {
        final InputThreadHandler handler = new InputThreadHandler(input);
        handler.start();
        return handler;
    }

}
