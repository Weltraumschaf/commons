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

import com.google.common.collect.Lists;
import java.util.List;

/**
 * Default parses implementation.
 *
 * Parsed grammar:
 * <pre>
 * imputline  = command { argument } .
 * command    = keyword [ keyword ] .
 * keyword    = character { character } .
 * argument   = literal | number | string .
 * literal    = alphanum { alphanum } .
 * number     = digit { digit } .
 * string     = '\'' alphanum { whitespace | alphanum } '\''
              | '"' alphanum { whitespace | alphanum } '"' .
 * alphanum   = character
 *            | digit .
 * character  = 'a' .. 'z'
 *            | 'A' .. 'Z' .
 * digit      = '0' .. '9' .
 * whitespace = ' ' .
 * </pre>
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
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
     * USes a {@link NullCommandVerifier} as command verifier.
     *
     * @param scanner to scan input line
     */
    public DefaultParser(final Scanner scanner) {
        this(scanner, new NullCommandVerifier());
    }

    /**
     * Dedicated constructor.
     *
     * @param scanner to scan input line
     * @param verifier verifies parsed commands
     */
    public DefaultParser(final Scanner scanner, final CommandVerifier verifier) {
        super();
        this.scanner = scanner;
        this.verifier = verifier;
    }

    /**
     * Parses given input line.
     *
     * @param input line to parse
     * @return recognized shell command
     * @throws SyntaxException if, the parsed line has syntax errors
     */
    @Override
    public ShellCommand parse(final String input) throws SyntaxException {
        final List<Token> tokens = scanner.scan(input);
        final Token commandtoken = tokens.get(0);

        if (TokenType.KEYWORD != commandtoken.getType()) {
            throw new SyntaxException("Command expected as first input!");
        }

        final NeuronMainType command = ShellCommand.determineCommand(commandtoken);
        NeuronSubType subCommand = NeuronSubType.NONE;
        int argumentBegin = 1;

        if (tokens.size() > 1) {
            final Token secondToken = tokens.get(1);

            if (secondToken.getType() == TokenType.KEYWORD) {
                if (! ShellCommand.isSubCommand(secondToken)) {
                    throw new SyntaxException(
                            String.format("Command '%s' followed by bad keyword '%s' as sub command!",
                                          commandtoken.getValue(), secondToken.getValue()));
                }
                ++argumentBegin;
                subCommand = ShellCommand.determineSubCommand(secondToken);
            }
        }

        List<Token> arguments;

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
