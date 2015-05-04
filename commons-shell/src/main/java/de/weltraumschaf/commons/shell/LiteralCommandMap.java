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
import de.weltraumschaf.commons.validate.Validate;
import java.util.Map;

/**
 * Maps the literal string of an command to its enum type.
 *
 * This class is not thread safe!
 *
 * @since 1.0.0
 * @author Sven Strittmatter &lt;weltraumschaf@googlemail.com&gt;
 * @version $Id: $Id
 */
public abstract class LiteralCommandMap {

    /**
     * Map the literal command string to corresponding type enum.
     */
    private final Map<String, MainCommandType> mainCommands = Maps.newHashMap();

    /**
     * Map the literal sub command string to corresponding type enum.
     */
    private final Map<String, SubCommandType> subCommands = Maps.newHashMap();

    /**
     * Default sub command for commands w/o sub commands.
     */
    private final SubCommandType defaultSubCommand;

    /**
     * Dedicated constructor.
     *
     * @param defaultSubCommand sub command used for commands w/o sub command,
     *                          usually a NONE type, must not be {@code null}
     */
    public LiteralCommandMap(final SubCommandType defaultSubCommand) {
        super();
        this.defaultSubCommand = Validate.notNull(defaultSubCommand, "defaultSubCommand");
        init();
    }

    /**
     * Determines if the string literal value of the token is a main command.
     *
     * @param token token to check
     * @return true if the token is a command else false
     */
    public final boolean isCommand(final Token token) {
        return isCommand(token.asString());
    }

    /**
     * Determines if the string literal value is a main command.
     *
     * @param token token string to check
     * @return true if the token is a command else false
     */
    public final boolean isCommand(final String token) {
        return mainCommands.containsKey(token);
    }

    /**
     * Determines the appropriate main command type for given string token.
     *
     * Determining a non command token string will result in an exception.
     *
     * @param t token to check
     * @return command main type
     * // CHECKSTYLE:OFF
     * @throws java.lang.IllegalArgumentException if, token is not a main command
     * // CHECKSTYLE:ON
     */
    public final MainCommandType determineCommand(final Token t) {
        if (isCommand(t)) {
            return mainCommands.get(t.asString());
        }
        throw new IllegalArgumentException(String.format("'%s' is not a command!", t.asString()));
    }

    /**
     * Determines if the string literal value of the token is a sub command.
     *
     * @param token token to check
     * @return true if the token is a sub command else false
     */
    public final boolean isSubCommand(final Token token) {
        return isSubCommand(token.asString());
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
     * @throws java.lang.IllegalArgumentException if, token is not a sub command
     * // CHECKSTYLE:ON
     */
    public final SubCommandType determineSubCommand(final Token t) {
        if (isSubCommand(t)) {
            return subCommands.get(t.asString());
        }
        throw new IllegalArgumentException(String.format("'%s' is not a sub command!", t.asString()));
    }

    /**
     * Get the default sub command for commands w/o sub commands.
     *
     * Usually your sub command enum should specify a special type for a non
     * sub command.
     *
     * @return the enum type of the "none" sub command
     */
    public final SubCommandType getDefaultSubCommand() {
        return defaultSubCommand;
    }


    /**
     * Return here the enum type which declares your main commands.
     *
     * @return never {@code null}
     */
    protected abstract Class<? extends MainCommandType> getMainCommandType();

    /**
     * Initializes the command map.
     */
    private void initMainCommandMap() {
        final Class<? extends MainCommandType> mainCommandTypes = getMainCommandType();

        if (!mainCommandTypes.isEnum()) {
            throw new IllegalArgumentException(
                String.format("Not an java.lang.Enum type returned by %s#getMainCommandType()!", getClass().getName()));
        }

        for (final MainCommandType t : mainCommandTypes.getEnumConstants()) {
            mainCommands.put(t.getLiteral(), t);
        }
    }

    /**
     * Initializes the sub command map.
     */
    private void initSubCommandMap() {
        final Class<? extends SubCommandType> subCommandTypes = getSubCommandType();

        if (!subCommandTypes.isEnum()) {
            throw new IllegalArgumentException(
                String.format("Not an java.lang.Enum type returned by %s#getSubCommandType()!", getClass().getName()));
        }

        for (final SubCommandType t : subCommandTypes.getEnumConstants()) {
            if (t.getLiteral().isEmpty()) {
                continue; // Ignore to do not recognize empty strings as sub command, e.g. ythe NONE.
            }

            subCommands.put(t.getLiteral(), t);
        }
    }

    /**
     * Return here the enum type which declares your sub commands.
     *
     * @return never {@code null}
     */
    protected abstract Class<? extends SubCommandType> getSubCommandType();

    /**
     * Not overideable template method called in constructor.
     */
    private void init() {
        initMainCommandMap();
        initSubCommandMap();
    }

}
