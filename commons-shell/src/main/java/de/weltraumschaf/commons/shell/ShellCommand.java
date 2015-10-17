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
package de.weltraumschaf.commons.shell;

import de.weltraumschaf.commons.shell.token.ShellToken;
import de.weltraumschaf.commons.guava.Lists;
import de.weltraumschaf.commons.guava.Objects;
import de.weltraumschaf.commons.validate.Validate;
import java.util.Collections;
import java.util.List;

/**
 * Describes a parsed mainCommand of the interactive shell.
 * <p>
 * Commands consist always of a mainCommand and may have a subcommand. This type is immutable.
 * </p>
 *
 * @since 1.0.0
 * @author Sven Strittmatter &lt;weltraumschaf@googlemail.com&gt;
 */
public class ShellCommand {

    /**
     * Obligatory main command.
     */
    private final MainCommandType mainCommand;

    /**
     * Optional sub mainCommand.
     * <p>
     * If the shell mainCommand does not support sub commands this will be the value of
     * {@link LiteralCommandMap#getDefaultSubCommand()}.
     * </p>
     */
    private final SubCommandType subCommand;

    /**
     * Optional arguments.
     * <p>
     * If the mainCommand has no arguments this will be an empty list.
     * </p>
     */
    private final List<ShellToken> arguments;

    /**
     * Dedicated constructor.
     *
     * @param mainCommand must not be {@code null}
     * @param subCommand must not be {@code null}
     * @param arguments must not be {@code null}, may be an empty list
     */
    public ShellCommand(
        final MainCommandType mainCommand,
        final SubCommandType subCommand,
        final List<ShellToken> arguments) {
        super();
        this.mainCommand = Validate.notNull(mainCommand, "mainCommand");
        this.subCommand = Validate.notNull(subCommand, "subCommand");
        this.arguments = Lists.newArrayList(Validate.notNull(arguments, "arguments")); // Defense copy
    }

    /**
     * Get mainCommand main type.
     *
     * @return main type of mainCommand
     */
    public MainCommandType getMainCommand() {
        return mainCommand;
    }

    /**
     * Get optional mainCommand sub type.
     * <p>
     * If the main type does not support any sub type the same value as
     * {@link de.weltraumschaf.commons.shell.LiteralCommandMap#getDefaultSubCommand()} will be returned.
     * </p>
     *
     * @return sub type of mainCommand
     */
    public SubCommandType getSubCommand() {
        return subCommand;
    }

    /**
     * Get arguments.
     * <p>
     * This method will always return a list. If no arguments are present an empty list will be returned.
     * </p>
     *
     * @return Returns a defense copy.
     */
    public List<ShellToken> getArguments() {
        return Collections.unmodifiableList(arguments);
    }

    @Override
    public String toString() {
        return Objects.toStringHelper(this)
            .add("mainCommand", mainCommand)
            .add("subCommand", subCommand)
            .add("arguments", arguments)
            .toString();
    }

}
