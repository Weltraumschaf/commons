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

import java.util.List;

/**
 * Verifies the commands.
 *
 * TODO Move back into neuron project.
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
public class NeuronCommandVerifier implements CommandVerifier {


    /**
     * Required number of arguments for message command.
     */
    private static final int MESSAGE_ARGS_COUNT = 3;

    /**
     * Verifies parsed command of consistency.
     *
     * Consistency checks are:
     * - correct sub command type
     * - correct number of arguments
     *
     * @param cmd command to verify
     * @throws SyntaxException if, verification has failed
     */
    @Override
    public void verifyCommand(final ShellCommand cmd) throws SyntaxException {
        switch (cmd.getCommand()) {
            case EXIT:
            case HELP:
            case RESET:
                if (cmd.getSubCommand() != ShellCommand.NeuronSubType.NONE) {
                    throw new SyntaxException(String.format("Command '%s' does not support subcommand '%s'!",
                                                            cmd.getCommand(), cmd.getSubCommand()));
                }
                if (! cmd.getArguments().isEmpty()) {
                    throw new SyntaxException(String.format("Command '%s' does not support arguments!",
                                                            cmd.getCommand()));
                }
                break;
            case NODE:
                verifyNodeCommand(cmd);
                break;
            case MESSAGE:
                verifyMessageCommand(cmd);
                break;
            case SAMPLE:
                verifySampleCommand(cmd);
                break;
            case DUMP:
                verifyDumpCommand(cmd);
                break;
            default:
                // Nothing to do here.
        }
    }

    /**
     * Verify commands of main command type {@link NeuronMainType#NODE}.
     *
     * Consistency checks are:
     * - correct number of arguments for sub command type
     *
     * @param cmd command to verify
     * @throws SyntaxException if, wrong number of arguments or unsupported subcommand was parsed
     */
    private void verifyNodeCommand(final ShellCommand cmd) throws SyntaxException {
        final int argumentCount = cmd.getArguments().size();

        if (cmd.getSubCommand() == ShellCommand.NeuronSubType.NONE) {
            throw new SyntaxException(String.format("Command '%s' must have sub command!", cmd.getCommand()));
        }

        switch (cmd.getSubCommand()) {
            case LIST:
                if (argumentCount != 0) {
                    throw new SyntaxException(String.format("Command '%s %s' support no arguments!",
                                                            cmd.getCommand(),
                                                            cmd.getSubCommand()));
                }
                break;
            case ADD:
                if (argumentCount > 1) {
                    throw new SyntaxException(String.format("Command '%s %s' wants one or zero arguments!",
                                                            cmd.getCommand(),
                                                            cmd.getSubCommand()));
                }
                break;
            case DEL:
            case INFO:
            case LISTEN:
            case UNLISTEN:
                if (argumentCount != 1) {
                    throw new SyntaxException(String.format("Command '%s %s' require one argument!",
                                                            cmd.getCommand(),
                                                            cmd.getSubCommand()));
                }
                break;
            case CONNECT:
            case DISCONNECT:
                if (argumentCount != 2) {
                    throw new SyntaxException(String.format("Command '%s %s' require two arguments!",
                                                            cmd.getCommand(),
                                                            cmd.getSubCommand()));
                }
                break;
            default:
                throw new SyntaxException(String.format("Command %s does not support subcommand %s!",
                                                        cmd.getCommand(),
                                                        cmd.getSubCommand()));

        }

    }

    /**
     * Verify commands of main command type {@link NeuronMainType#MESSAGE}.
     *
     * Consistency checks are:
     * - correct number and type of arguments
     *
     * @param cmd command to verify
     * @throws SyntaxException if, wrong number or type of arguments
     */
    private void verifyMessageCommand(final ShellCommand cmd) throws SyntaxException {
        final List<Token> args = cmd.getArguments();

        if (args.size() != MESSAGE_ARGS_COUNT) {
            throw new SyntaxException(String.format("Command '%s' requires 3 arguments!", cmd.getCommand()));
        }

        if (args.get(0).getType() != TokenType.NUMBER) {
            throw new SyntaxException(String.format("First argument of command '%s' must be a number!",
                                                    cmd.getCommand()));
        }

        if (args.get(1).getType() != TokenType.NUMBER) {
            throw new SyntaxException(String.format("Second argument of command '%s' must be a number!",
                                                    cmd.getCommand()));
        }

        if (args.get(2).getType() != TokenType.STRING) {
            throw new SyntaxException(String.format("Third argument of command '%s' must be a string!",
                                                    cmd.getCommand()));
        }
    }

    /**
     * Verify commands of main command type {@link NeuronMainType#SAMPLE}.
     *
     * Consistency checks are:
     * - correct sub commands
     * - correct number and type of arguments
     *
     * @param cmd command to verify
     * @throws SyntaxException if, wrong number or type of arguments
     */
    private void verifySampleCommand(final ShellCommand cmd) throws SyntaxException {
        // TODO implement method
    }

    /**
     * Verify commands of main command type {@link de.weltraumschaf.commons.shell.ShellCommand.NeuronMainType#DUMP}.
     *
     * Consistency checks are:
     * - correct sub commands
     * - correct number and type of arguments
     *
     * @param cmd command to verify
     * @throws SyntaxException if, wrong number or type of arguments
     */
    private void verifyDumpCommand(final ShellCommand cmd) throws SyntaxException {
        // TODO implement method
    }

}
