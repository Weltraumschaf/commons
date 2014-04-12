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

import de.weltraumschaf.commons.guava.Maps;
import de.weltraumschaf.commons.token.Token;
import java.util.Map;

/**
 * Maps the literal string of an command to its enum type.
 *
 * This class is not thread safe!
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
public abstract class LiteralCommandMap {

    /**
     * Map the literal command string to corresponding type enum.
     */
    private final Map<String, MainCommandType> commands = Maps.newHashMap();

    /**
     * Map the literal sub command string to corresponding type enum.
     */
    private final Map<String, SubCommandType> subCommands = Maps.newHashMap();

    /**
     * Default sub command for commands w/o sub commands.
     */
    private final SubCommandType defaultSubCommand;

    /**
     * Calls the template methods {@link #initCommandMap(java.util.Map)}
     * and {@link #initSubCommandMap(java.util.Map)}.
     *
     * @param defaultSubCommand sub command used for commands w/o sub command, usualy a NONE type
     */
    public LiteralCommandMap(final SubCommandType defaultSubCommand) {
        super();
        this.defaultSubCommand = defaultSubCommand;
        init();
    }

    /**
     * Determines if the string literal value of the token is a main command.
     *
     * @param token token to check
     * @return true if the token is a command else false
     */
    public final boolean isCommand(final Token<String> token) {
        return isCommand(token.getValue());
    }

    /**
     * Determines if the string literal value is a main command.
     *
     * @param token token string to check
     * @return true if the token is a command else false
     */
    public final boolean isCommand(final String token) {
        return commands.containsKey(token);
    }

    /**
     * Determines the appropriate main command type for given string token.
     *
     * Determining a non command token string will result in an exception.
     *
     * @param t token to check
     * @return command main type
     * // CHECKSTYLE:OFF
     * @throws IllegalArgumentException if, token is not a main command
     * // CHECKSTYLE:ON
     */
    public final MainCommandType determineCommand(final Token<String> t) {
        if (isCommand(t)) {
            return commands.get(t.getValue());
        }
        throw new IllegalArgumentException(String.format("'%s' is not a command!", t.getValue()));
    }

    /**
     * Determines if the string literal value of the token is a sub command.
     *
     * @param token token to check
     * @return true if the token is a sub command else false
     */
    public final boolean isSubCommand(final Token<String> token) {
        return isSubCommand(token.getValue());
    }

    /**
     * Determines if the string literal value is a sub command.
     *
     * @param token token string to check
     * @return true if the token is a sub command else false
     */
    public final boolean isSubCommand(final String token) {
        return subCommands.containsKey(token);
    }

    /**
     * Determines the appropriate sub command type for given string token.
     *
     * Determining a non sub command token string will result in an exception.
     *
     * @param t token to check
     * @return command sub type
     * // CHECKSTYLE:OFF
     * @throws IllegalArgumentException if, token is not a sub command
     * // CHECKSTYLE:ON
     */
    public final SubCommandType determineSubCommand(final Token<String> t) {
        if (isSubCommand(t)) {
            return subCommands.get(t.getValue());
        }
        throw new IllegalArgumentException(String.format("'%s' is not a sub command!", t.getValue()));
    }

    /**
     * Get the default sub command for commands w/o sub commands.
     *
     * Usually your sub command enum should specify a special type for a non
     * sub command.
     *
     * @return the enum type of the "none" sub command
     */
    public SubCommandType getDefaultSubCommand() {
        return defaultSubCommand;
    }

    /**
     * Initializes the command map.
     *
     * @param map map to initialize
     */
    protected abstract void initCommandMap(final Map<String, MainCommandType> map);

    /**
     * Initializes the sub command map.
     *
     * @param map map to initialize
     */
    protected abstract void initSubCommandMap(final Map<String, SubCommandType> map);

    /**
     * Not overideable template method called in constructor.
     *
     * Invokes first {@link #initCommandMap(java.util.Map)} and second {@link #initSubCommandMap(java.util.Map)}.
     */
    private void init() {
        initCommandMap(commands);
        initSubCommandMap(subCommands);
    }

}
