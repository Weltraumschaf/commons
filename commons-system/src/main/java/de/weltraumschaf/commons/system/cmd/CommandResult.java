package de.weltraumschaf.commons.system.cmd;

import de.weltraumschaf.commons.validate.Validate;

import java.util.Objects;

/**
 * CommandResult of a {@link Command shell command}.
 *
 * @since 2.3.0
 * @author Sven Strittmatter &lt;weltraumschaf@googlemail.com&gt;
 */
public final class CommandResult {

    /**
     * Exit code of command.
     */
    private final int exitCode;
    /**
     * Command output from STDOUT.
     */
    private final String stdout;
    /**
     * Command output from STDERR.
     */
    private final String stderr;

    /**
     * Use either {@link #ok(String)} or {@link #error(int, String)} factory methods.
     *
     * @param exitCode any int
     * @param stdout must not be {@code null}
     * @param stderr must not be {@code null}
     */
    private CommandResult(final int exitCode, final String stdout, final String stderr) {
        super();
        this.exitCode = exitCode;
        this.stdout = Validate.notNull(stdout, "stdout");
        this.stderr = Validate.notNull(stderr, "stderr");
    }

    /**
     * Factory method to create a result for successful command.
     *
     * @param exitCode not zero (will throw {@link IllegalArgumentException})
     * @param stderr not {@code null}
     * @return never {@code null}
     */
    public static CommandResult error(final int exitCode, final String stderr) {
        if (exitCode == 0) {
            throw new IllegalArgumentException("An error result must not have an error code 0!");
        }

        return new CommandResult(exitCode, "", stderr);
    }

    /**
     * Factory method to create a result for failed command.
     *
     * @param stdout not {@code null}
     * @return never {@code null}
     */
    public static CommandResult ok(final String stdout) {
        return new CommandResult(0, stdout, "");
    }

    /**
     * Get the commands exit code.
     *
     * @return any integer
     */
    public int getExitCode() {
        return exitCode;
    }

    /**
     * Short hand for {@code getExitCode() == 0}.
     *
     * @return {@code false} if command had an error, else {@code true}
     */
    public boolean isSuccessful() {
        return exitCode == 0;
    }

    public boolean isFailed() {
        return !isSuccessful();
    }

    /**
     * Get the output from STDOUT.
     *
     * @return never {@code null}
     */
    public String getStdout() {
        return stdout;
    }

    /**
     * Get the output from STDERR.
     *
     * @return never {@code null}
     */
    public String getStderr() {
        return stderr;
    }

    @Override
    public int hashCode() {
        return Objects.hash(exitCode, stdout, stderr);
    }

    @Override
    public boolean equals(final Object obj) {
        if (!(obj instanceof CommandResult)) {
            return false;
        }

        final CommandResult other = (CommandResult) obj;
        return this.exitCode == other.exitCode
            && Objects.equals(this.stdout, other.stdout)
            && Objects.equals(this.stderr, other.stderr);
    }

    @Override
    public String toString() {
        return "CommandResult{"
            + "exitCode=" + exitCode + ", "
            + "stdout=" + stdout + ", "
            + "stderr=" + stderr
            + '}';
    }

}
