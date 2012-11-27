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

import java.util.Map;
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
    private final Parser sut = Parsers.newParser(new LiteralCommandMapStub());

    @Test @Ignore("TODO USe stub implementaion as inner classes")
    public void parse_comand() throws SyntaxException {
        // Move this code to neuron.
        ShellCommand c = sut.parse("help");
//        assertThat(c.getCommand(), is(NeuronMainType.HELP));
//        assertThat(c.getSubCommand(), is(NeuronSubType.NONE));
//        assertThat(c.getArguments().size(), is(0));
//
//        c = sut.parse("reset");
//        assertThat(c.getCommand(), is(NeuronMainType.RESET));
//        assertThat(c.getSubCommand(), is(NeuronSubType.NONE));
//        assertThat(c.getArguments().size(), is(0));
//
//        c = sut.parse("exit");
//        assertThat(c.getCommand(), is(NeuronMainType.EXIT));
//        assertThat(c.getSubCommand(), is(NeuronSubType.NONE));
//        assertThat(c.getArguments().size(), is(0));
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

    @Test @Ignore("TODO USe stub implementaion as inner classes")
    public void parse_comandWithSubcommandAndOneArgument() throws SyntaxException {
        // Move this code to neuron.
//        ShellCommand c = sut.parse("node add 1234");
//        assertThat(c.getCommand(), is(NeuronMainType.NODE));
//        assertThat(c.getSubCommand(), is(NeuronSubType.ADD));
//        assertThat(c.getArguments().size(), is(1));
//        Token<Integer> t = c.getArguments().get(0);
//        assertThat(t.getType(), is(TokenType.NUMBER));
//        assertThat(t.getValue(), is(1234));
//
//        c = sut.parse("node del 5678");
//        assertThat(c.getCommand(), is(NeuronMainType.NODE));
//        assertThat(c.getSubCommand(), is(NeuronSubType.DEL));
//        assertThat(c.getArguments().size(), is(1));
//        t = c.getArguments().get(0);
//        assertThat(t.getType(), is(TokenType.NUMBER));
//        assertThat(t.getValue(), is(5678));
//
//        c = sut.parse("node info 5678");
//        assertThat(c.getCommand(), is(NeuronMainType.NODE));
//        assertThat(c.getSubCommand(), is(NeuronSubType.INFO));
//        assertThat(c.getArguments().size(), is(1));
//        t = c.getArguments().get(0);
//        assertThat(t.getType(), is(TokenType.NUMBER));
//        assertThat(t.getValue(), is(5678));
    }

    private static class LiteralCommandMapStub extends LiteralCommandMap {

        public LiteralCommandMapStub() {
            super(null);
        }

        @Override
        protected void initCommandMap(Map<String, MainCommandType> map) {

        }

        @Override
        protected void initSubCommandMap(Map<String, SubCommandType> map) {

        }

    }

}
