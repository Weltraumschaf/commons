package de.weltraumschaf.commons.system.cmd;

import de.weltraumschaf.commons.validate.Validate;

import java.io.IOException;
import java.io.InputStream;

/**
 * Common base class for commands.
 * <p>
 * See this <a href="http://examples.javacodegeeks.com/core-java/lang/processbuilder/java-lang-processbuilder-example/">
 * tutorial</a> for more information.
 * </p>
 *
 * @since 2.3.0
 * @author Sven Strittmatter &lt;weltraumschaf@googlemail.com&gt;
 */
final class BaseCommand implements Command {

    /**
     * Command to execute.
     */
    private final String command;
    /**
     * Command arguments.
     */
    private final String arguments;
    /**
     * Abstracts the process builder for testability.
     */
    private final ProcessBuilderWrapper builder;

    BaseCommand(final String command) {
        this(command, "");
    }

    BaseCommand(final String command, final String arguments) {
        this(command, arguments, new DefaultProcessBuilderWrapper());
    }

    /**
     * Dedicated constructor.
     *
     * @param command   the command itself, must not be {@code null} or empty
     * @param arguments must not be {@code null}, maybe empty
     * @param builder   must not be {@code null}
     */
    BaseCommand(final String command, final String arguments, final ProcessBuilderWrapper builder) {
        super();
        this.command = Validate.notEmpty(command, "command");
        this.arguments = Validate.notNull(arguments, "arguments");
        this.builder = Validate.notNull(builder, "builder");
    }

    @Override
    public final CommandResult execute() throws IOException, InterruptedException {
        final Process process = builder.start(command, arguments);
        final InputThreadHandler stdout = prepareInputHandler(process.getInputStream());
        final InputThreadHandler stderr = prepareInputHandler(process.getErrorStream());

        final int errCode = process.waitFor();
        stdout.join();
        stderr.join();

        return new CommandResult(errCode, stdout.getOutput(), stderr.getOutput());
    }

    private InputThreadHandler prepareInputHandler(final InputStream input) {
        final InputThreadHandler handler = new InputThreadHandler(input);
        handler.start();
        return handler;
    }

}
