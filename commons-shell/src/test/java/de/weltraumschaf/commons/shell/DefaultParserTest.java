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
import de.weltraumschaf.commons.shell.token.TokenType;
import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

/**
 * Tests for {@link DefaultParser}.
 *
 * @author Sven Strittmatter &lt;weltraumschaf@googlemail.com&gt;
 */
public class DefaultParserTest {

    // CHECKSTYLE:OFF
    @Rule
    public ExpectedException thrown = ExpectedException.none();
    // CHECKSTYLE:ON
    private final Parser sut = Parsers.newParser(new LiteralCommandMapStub());

    @Test
    public void parse_comand() throws SyntaxException {
        // Move this code to neuron.
        ShellCommand c = sut.parse("foo");
        assertThat((TestMainType) c.getMainCommand(), is(TestMainType.FOO));
        assertThat((TestSubType) c.getSubCommand(), is(TestSubType.NONE));
        assertThat(c.getArguments().size(), is(0));

        c = sut.parse("bar");
        assertThat((TestMainType) c.getMainCommand(), is(TestMainType.BAR));
        assertThat((TestSubType) c.getSubCommand(), is(TestSubType.NONE));
        assertThat(c.getArguments().size(), is(0));

        c = sut.parse("baz");
        assertThat((TestMainType) c.getMainCommand(), is(TestMainType.BAZ));
        assertThat((TestSubType) c.getSubCommand(), is(TestSubType.NONE));
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

    @Test
    @Ignore("Not used yet!")
    public void parse_comandWithOneArgument() {
    }

    @Test
    @Ignore("Not used yet!")
    public void parse_comandWithTwoArgument() {
    }

    @Test
    public void parse_comandWithSubcommandAndOneArgument() throws SyntaxException {
        ShellCommand c = sut.parse("foo add 1234");
        assertThat((TestMainType) c.getMainCommand(), is(TestMainType.FOO));
        assertThat((TestSubType) c.getSubCommand(), is(TestSubType.ADD));
        assertThat(c.getArguments().size(), is(1));

        ShellToken t =  c.getArguments().get(0);
        assertThat(t.getType(), is(TokenType.INTEGER));
        assertThat(t.asInteger(), is(1234));

        c = sut.parse("bar del 5678");
        assertThat((TestMainType) c.getMainCommand(), is(TestMainType.BAR));
        assertThat((TestSubType) c.getSubCommand(), is(TestSubType.DEL));
        assertThat(c.getArguments().size(), is(1));

        t =  c.getArguments().get(0);
        assertThat(t.getType(), is(TokenType.INTEGER));
        assertThat(t.asInteger(), is(5678));

        c = sut.parse("baz info 5678");
        assertThat((TestMainType) c.getMainCommand(), is(TestMainType.BAZ));
        assertThat((TestSubType) c.getSubCommand(), is(TestSubType.INFO));
        assertThat(c.getArguments().size(), is(1));

        t =  c.getArguments().get(0);
        assertThat(t.getType(), is(TokenType.INTEGER));
        assertThat(t.asInteger(), is(5678));
    }

    private enum TestMainType implements MainCommandType {

        FOO("foo"), BAR("bar"), BAZ("baz");

        private final String literal;

        private TestMainType(final String literal) {
            this.literal = literal;
        }

        @Override
        public String getLiteral() {
            return literal;
        }

    }

    private enum TestSubType implements SubCommandType {

        ADD("add"), DEL("del"), INFO("info"), NONE();

        private final String literal;

        private TestSubType() {
            this("");
        }

        private TestSubType(final String literal) {
            this.literal = literal;
        }

        @Override
        public String getLiteral() {
            return literal;
        }

    }

    private static class LiteralCommandMapStub extends LiteralCommandMap {

        public LiteralCommandMapStub() {
            super(TestSubType.NONE);
        }

        @Override
        protected Class<TestMainType> getMainCommandType() {
            return TestMainType.class;
        }

        @Override
        protected Class<TestSubType> getSubCommandType() {
            return TestSubType.class;
        }

    }

}
