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

import de.weltraumschaf.commons.token.Token;
import de.weltraumschaf.commons.guava.Lists;
import de.weltraumschaf.commons.guava.Objects;
import java.util.List;

/**
 * Describes a parsed command of the interactive shell.
 *
 * Commands consist always of a command and may have a subcommand.
 * This type is immutable.
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
public class ShellCommand {

    /**
     * Obligatory main command.
     */
    private final MainCommandType command;

    /**
     * Optional sub command.
     *
     * If the shell command does not support sub commands this will be the value of
     * {@link LiteralCommandMap#getDefaultSubCommand()}.
     */
    private final SubCommandType subCommand;

    /**
     * Optional arguments.
     *
     * If the command has no arguments this will be an empty list.
     */
    private final List<Token<?>> arguments;

    /**
     * Dedicated constructor.
     *
     * @param command main command
     * @param subCommand sub command
     * @param arguments command arguments, may be an empty list
     */
    public ShellCommand(
            final MainCommandType command,
            final SubCommandType subCommand,
            final List<Token<?>> arguments) {
        super();
        this.command    = command;
        this.subCommand = subCommand;
        this.arguments  = Lists.newArrayList(arguments); // Defense copy
    }

    /**
     * Get command main type.
     *
     * @return main type of command
     */
    public MainCommandType getCommand() {
        return command;
    }

    /**
     * Get optional command sub type.
     *
     * If the main type does not support any sub type the same value as {@link LiteralCommandMap#getDefaultSubCommand()}
     * will be returned.
     *
     * @return sub type of command
     */
    public SubCommandType getSubCommand() {
        return subCommand;
    }

    /**
     * Get arguments.
     *
     * This method will always return a list. If no arguments are present an empty list will be returned.
     *
     * @return Returns a defense copy.
     */
    public List<Token<?>> getArguments() {
        return Lists.newArrayList(arguments); // Defense copy
    }

    @Override
    public String toString() {
        return Objects.toStringHelper(this)
                      .add("mainCommand", command)
                      .add("subCommand", subCommand)
                      .add("arguments", arguments)
                      .toString();
    }

}
