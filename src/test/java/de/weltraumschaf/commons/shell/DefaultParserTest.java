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

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

/**
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
public class DefaultParserTest {

    // CHECKSTYLE:OFF
    @Rule public ExpectedException thrown = ExpectedException.none();
    // CHECKSTYLE:ON
    private final DefaultParser sut = new DefaultParser(new DefaultScanner());

    @Test
    public void parse_comand() throws SyntaxException {
        ShellCommand c = sut.parse("help");
        assertThat(c.getCommand(), is(NeuronMainType.HELP));
        assertThat(c.getSubCommand(), is(NeuronSubType.NONE));
        assertThat(c.getArguments().size(), is(0));

        c = sut.parse("reset");
        assertThat(c.getCommand(), is(NeuronMainType.RESET));
        assertThat(c.getSubCommand(), is(NeuronSubType.NONE));
        assertThat(c.getArguments().size(), is(0));

        c = sut.parse("exit");
        assertThat(c.getCommand(), is(NeuronMainType.EXIT));
        assertThat(c.getSubCommand(), is(NeuronSubType.NONE));
        assertThat(c.getArguments().size(), is(0));
    }

    @Test
    public void parse_exceptionIfFirstTokenIsNotLiteral() throws SyntaxException {
        thrown.expect(SyntaxException.class);
        thrown.expectMessage("Command expected as first input!");
        sut.parse("1234");
    }

    @Test
    public void parse_exceptionIfFirstLiteralIsNotACommand() throws SyntaxException {
        thrown.expect(SyntaxException.class);
        thrown.expectMessage("Command expected as first input!");
        sut.parse("foobar");
    }

    @Test @Ignore("Not used yet!")
    public void parse_comandWithOneArgument() {
    }

    @Test @Ignore("Not used yet!")
    public void parse_comandWithTwoArgument() {
    }

    @Test
    public void parse_comandWithSubcommandAndOneArgument() throws SyntaxException {
        ShellCommand c = sut.parse("node add 1234");
        assertThat(c.getCommand(), is(NeuronMainType.NODE));
        assertThat(c.getSubCommand(), is(NeuronSubType.ADD));
        assertThat(c.getArguments().size(), is(1));
        Token<Integer> t = c.getArguments().get(0);
        assertThat(t.getType(), is(TokenType.NUMBER));
        assertThat(t.getValue(), is(1234));

        c = sut.parse("node del 5678");
        assertThat(c.getCommand(), is(NeuronMainType.NODE));
        assertThat(c.getSubCommand(), is(NeuronSubType.DEL));
        assertThat(c.getArguments().size(), is(1));
        t = c.getArguments().get(0);
        assertThat(t.getType(), is(TokenType.NUMBER));
        assertThat(t.getValue(), is(5678));

        c = sut.parse("node info 5678");
        assertThat(c.getCommand(), is(NeuronMainType.NODE));
        assertThat(c.getSubCommand(), is(NeuronSubType.INFO));
        assertThat(c.getArguments().size(), is(1));
        t = c.getArguments().get(0);
        assertThat(t.getType(), is(TokenType.NUMBER));
        assertThat(t.getValue(), is(5678));
    }

    @Test @Ignore("TODO Move into neuron projct.")
    public void parse_throwExceptionIfExitHasSubcommand() throws SyntaxException {
        thrown.expect(SyntaxException.class);
        thrown.expectMessage("Command 'exit' does not support subcommand 'add'!");
        sut.parse("exit add");
    }

    @Test @Ignore("TODO Move into neuron projct.")
    public void parse_throwExceptionIfExitHasArguments() throws SyntaxException {
        thrown.expect(SyntaxException.class);
        thrown.expectMessage("Command 'exit' does not support arguments!");
        sut.parse("exit 123");
    }

    @Test @Ignore("TODO Move into neuron projct.")
    public void parse_throwExceptionIfHelpHasSubcommand() throws SyntaxException {
        thrown.expect(SyntaxException.class);
        thrown.expectMessage("Command 'help' does not support subcommand 'add'!");
        sut.parse("help add");
    }

    @Test @Ignore("TODO Move into neuron projct.")
    public void parse_throwExceptionIfHelpHasArguments() throws SyntaxException {
        thrown.expect(SyntaxException.class);
        thrown.expectMessage("Command 'help' does not support arguments!");
        sut.parse("help 123");
    }

    @Test @Ignore("TODO Move into neuron projct.")
    public void parse_throwExceptionIfResetHasSubcommand() throws SyntaxException {
        thrown.expect(SyntaxException.class);
        thrown.expectMessage("Command 'reset' does not support subcommand 'add'!");
        sut.parse("reset add");
    }

    @Test @Ignore("TODO Move into neuron projct.")
    public void parse_throwExceptionIfResetHasArguments() throws SyntaxException {
        thrown.expect(SyntaxException.class);
        thrown.expectMessage("Command 'reset' does not support arguments!");
        sut.parse("reset 123");
    }

    @Test @Ignore("TODO Move into neuron projct.")
    public void parse_throwExceptionIfNodeListHasArguments() throws SyntaxException {
        thrown.expect(SyntaxException.class);
        thrown.expectMessage("Command 'node list' support no arguments!");
        sut.parse("node list 123");
    }

    @Test @Ignore("TODO Move into neuron projct.")
    public void parse_throwExceptionIfNodeAddHasMoreThan1Argumnet() throws SyntaxException {
        thrown.expect(SyntaxException.class);
        thrown.expectMessage("Command 'node add' wants one or zero arguments!");
        sut.parse("node add 1 2 3");
    }

    @Test @Ignore("TODO Move into neuron projct.")
    public void parse_throwExceptionIfNodeDelHasNoArgument() throws SyntaxException {
        thrown.expect(SyntaxException.class);
        thrown.expectMessage("Command 'node del' require one argument!");
        sut.parse("node del");
    }

    @Test @Ignore("TODO Move into neuron projct.")
    public void parse_throwExceptionIfNodeDelHasMoreThanOneArgument() throws SyntaxException {
        thrown.expect(SyntaxException.class);
        thrown.expectMessage("Command 'node del' require one argument!");
        sut.parse("node del 123 456");
    }

    @Test @Ignore("TODO Move into neuron projct.")
    public void parse_throwExceptionIfNodeInfoHasNoArgument() throws SyntaxException {
        thrown.expect(SyntaxException.class);
        thrown.expectMessage("Command 'node info' require one argument!");
        sut.parse("node info");
    }

    @Test @Ignore("TODO Move into neuron projct.")
    public void parse_throwExceptionIfNodeInfoHasMoreThanOneArgument() throws SyntaxException {
        thrown.expect(SyntaxException.class);
        thrown.expectMessage("Command 'node info' require one argument!");
        sut.parse("node info 123 456");
    }

    @Test @Ignore("TODO Move into neuron projct.")
    public void parse_throwExceptionIfNodeListenHasNoArgument() throws SyntaxException {
        thrown.expect(SyntaxException.class);
        thrown.expectMessage("Command 'node listen' require one argument!");
        sut.parse("node listen");
    }

    @Test @Ignore("TODO Move into neuron projct.")
    public void parse_throwExceptionIfNodeListenHasMoreThanOneArgument() throws SyntaxException {
        thrown.expect(SyntaxException.class);
        thrown.expectMessage("Command 'node listen' require one argument!");
        sut.parse("node listen 123 456");
    }

    @Test @Ignore("TODO Move into neuron projct.")
    public void parse_throwExceptionIfNodeUnlistenHasNoArgument() throws SyntaxException {
        thrown.expect(SyntaxException.class);
        thrown.expectMessage("Command 'node unlisten' require one argument!");
        sut.parse("node unlisten");
    }

    @Test @Ignore("TODO Move into neuron projct.")
    public void parse_throwExceptionIfNodeUnlistenHasMoreThanOneArgument() throws SyntaxException {
        thrown.expect(SyntaxException.class);
        thrown.expectMessage("Command 'node unlisten' require one argument!");
        sut.parse("node unlisten 123 456");
    }

    @Test @Ignore("TODO Move into neuron projct.")
    public void parse_throwExceptionIfNodeConnectHasNoArgument() throws SyntaxException {
        thrown.expect(SyntaxException.class);
        thrown.expectMessage("Command 'node connect' require two arguments!");
        sut.parse("node connect");
    }

    @Test @Ignore("TODO Move into neuron projct.")
    public void parse_throwExceptionIfNodeConnectHasOneArgument() throws SyntaxException {
        thrown.expect(SyntaxException.class);
        thrown.expectMessage("Command 'node connect' require two arguments!");
        sut.parse("node connect 123");
    }

    @Test @Ignore("TODO Move into neuron projct.")
    public void parse_throwExceptionIfNodeConnectHasMoreThanTwoArguments() throws SyntaxException {
        thrown.expect(SyntaxException.class);
        thrown.expectMessage("Command 'node connect' require two arguments!");
        sut.parse("node connect 123 456 789");
    }

    @Test @Ignore("TODO Move into neuron projct.")
    public void parse_throwExceptionIfNodeDisconnectHasNoArgument() throws SyntaxException {
        thrown.expect(SyntaxException.class);
        thrown.expectMessage("Command 'node disconnect' require two arguments!");
        sut.parse("node disconnect");
    }

    @Test @Ignore("TODO Move into neuron projct.")
    public void parse_throwExceptionIfNodeDisconnectHasOneArgument() throws SyntaxException {
        thrown.expect(SyntaxException.class);
        thrown.expectMessage("Command 'node disconnect' require two arguments!");
        sut.parse("node disconnect 123");
    }

    @Test @Ignore("TODO Move into neuron projct.")
    public void parse_throwExceptionIfNodeDisconnectHasMoreThanTwoArguments() throws SyntaxException {
        thrown.expect(SyntaxException.class);
        thrown.expectMessage("Command 'node disconnect' require two arguments!");
        sut.parse("node disconnect 123 456 789");
    }

    @Test @Ignore("TODO Move into neuron projct.")
    public void parse_throwExceptionIfNodeHasNoSubcommand() throws SyntaxException {
        thrown.expect(SyntaxException.class);
        thrown.expectMessage("Command 'node' must have sub command!");
        sut.parse("node");
    }

}
