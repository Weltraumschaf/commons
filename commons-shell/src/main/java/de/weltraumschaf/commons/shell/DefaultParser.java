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

import de.weltraumschaf.commons.guava.Lists;
import de.weltraumschaf.commons.shell.token.ShellToken;
import de.weltraumschaf.commons.shell.token.TokenType;
import de.weltraumschaf.commons.validate.Validate;
import java.util.List;

/**
 * Default parses implementation.
 * <p>
 * Parsed grammar:
 * </p>
 * <pre>
 * imputline  = command { argument } .
 * command    = keyword [ keyword ] .
 * keyword    = character { character } .
 * argument   = literal | number | string | boolean.
 * literal    = alphanum { alphanum } .
 * number     = integer | float .
 * integer    = [ sign ] digit { digit } .
 * float      = [ sign ] digit { digit } '.' digit { digit } [ exponent ] .
 * exponent   = ( 'e' | 'E' ) [ sign ] digit { digit } .
 * boolean    = 'true' | 'false' .
 * string     = '\'' alphanum { whitespace | alphanum } '\''
              | '"' alphanum { whitespace | alphanum } '"' .
 * alphanum   = character
 *            | digit .
 * character  = 'a' .. 'z'
 *            | 'A' .. 'Z' .
 * digit      = '0' .. '9' .
 * sign       = '-' | '+' .
 * whitespace = ' ' .
 * </pre>
 *
 * @since 1.0.0
 * @author Sven Strittmatter &lt;weltraumschaf@googlemail.com&gt;
 */
class DefaultParser implements Parser {

    /**
     * Tokenize the input line.
     */
    private final Scanner scanner;

    /**
     * Verifies the parsed commands.
     */
    private final CommandVerifier verifier;

    /**
     * Map used to determine command keywords.
     */
    private final LiteralCommandMap commandMap;

    /**
     * Dedicated constructor.
     *
     * @param scanner must not be {@code null}
     * @param verifier must not be {@code null}
     * @param commandMap must not be {@code null}
     */
    public DefaultParser(final Scanner scanner, final CommandVerifier verifier, final LiteralCommandMap commandMap) {
        super();
        this.scanner = Validate.notNull(scanner, "scanner");
        this.verifier = Validate.notNull(verifier, "verifier");
        this.commandMap = Validate.notNull(commandMap, "commandMap");
    }

    /**
     * {@inheritDoc}
     *
     * Parses given input line.
     */
    @Override
    public ShellCommand parse(final String input) throws SyntaxException {
        final List<ShellToken> tokens = scanner.scan(input);
        final ShellToken commandtoken = tokens.get(0);

        if (TokenType.KEYWORD != commandtoken.getType()) {
            throw new SyntaxException("Command expected as first input!");
        }

        final MainCommandType command = commandMap.determineCommand(commandtoken);
        SubCommandType subCommand = commandMap.getDefaultSubCommand();
        int argumentBegin = 1;

        if (tokens.size() > 1) {
            final ShellToken secondToken = tokens.get(1);

            if (secondToken.getType() == TokenType.KEYWORD) {
                final ShellToken keyword = secondToken;

                if (! commandMap.isSubCommand(keyword)) {
                    throw new SyntaxException(
                            String.format("Command '%s' followed by bad keyword '%s' as sub command!",
                                          commandtoken.asString(), secondToken.asString()));
                }

                ++argumentBegin;
                subCommand = commandMap.determineSubCommand(keyword);
            }
        }

        final List<ShellToken> arguments;

        if (tokens.size() > argumentBegin) {
            arguments = tokens.subList(argumentBegin, tokens.size());
        } else {
            arguments = Lists.newArrayList();
        }

        final ShellCommand cmd = new ShellCommand(command, subCommand, arguments);
        verifier.verifyCommand(cmd);
        return cmd;
    }

}
