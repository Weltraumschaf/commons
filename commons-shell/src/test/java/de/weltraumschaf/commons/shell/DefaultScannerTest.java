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
import de.weltraumschaf.commons.token.Token;
import de.weltraumschaf.commons.token.TokenType;
import java.util.List;
import java.util.Map;
import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

/**
 * Tests for {@link DefaultScanner}.
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
public class DefaultScannerTest {

    // CHECKSTYLE:OFF
    @Rule public ExpectedException thrown = ExpectedException.none();
    // CHECKSTYLE:ON
    private final Scanner sut = Scanners.newScanner(new LiteralCommandMapStub());

    @Test(expected = IllegalArgumentException.class)
    public void scan_nullArgument() throws SyntaxException {
        sut.scan(null);
    }

    @Test
    public void scan_emptyLine() throws SyntaxException {
        final List<Token<?>> tokens = sut.scan("");
        assertThat(tokens.size(), is(0));
    }

    @Test
    public void scan_keywords() throws SyntaxException {
        final StringBuilder input = new StringBuilder();
        final List<CommandType> types = Lists.newArrayList();

        for (final MainCommandType t : TestMainType.values()) {
            input.append(t).append(' ');
            types.add(t);
        }

        for (final SubCommandType t : TestSubType.values()) {
            if (t == TestSubType.NONE) {
                continue;
            }
            input.append(t).append(' ');
            types.add(t);
        }

        final List<Token<?>> tokens = sut.scan(input.toString());
        int tokenId = 0;

        for (final Token<?> token : tokens) {
            assertThat(token.getType(), is(TokenType.KEYWORD));
            assertThat(token.getValue().toString(), is(types.get(tokenId).toString()));
            ++tokenId;
        }
    }

    @Test
    @SuppressWarnings("unchecked")
    public void scan_lineWithSingleKeyword() throws SyntaxException {
        List<Token<?>> tokens = sut.scan(TestMainType.FOO.toString());
        assertThat(tokens.size(), is(1));
        Token<String> token = (Token<String>) tokens.get(0);
        assertThat(token.getType(), is(TokenType.KEYWORD));
        assertThat(token.getValue(), is(TestMainType.FOO.toString()));

        tokens = sut.scan(TestMainType.BAR.toString());
        assertThat(tokens.size(), is(1));
        token = (Token<String>) tokens.get(0);
        assertThat(token.getType(), is(TokenType.KEYWORD));
        assertThat(token.getValue(), is(TestMainType.BAR.toString()));

        tokens = sut.scan(TestMainType.BAZ.toString());
        assertThat(tokens.size(), is(1));
        token = (Token<String>) tokens.get(0);
        assertThat(token.getType(), is(TokenType.KEYWORD));
        assertThat(token.getValue(), is(TestMainType.BAZ.toString()));
    }

    @Test
    @SuppressWarnings("unchecked")
    public void scan_lineWithSingleString() throws SyntaxException {
        List<Token<?>> tokens = sut.scan("'foo'\n");

        assertThat(tokens.size(), is(1));
        Token<String> token = (Token<String>) tokens.get(0);
        assertThat(token.getType(), is(TokenType.STRING));
        assertThat(token.getValue(), is("foo"));

        tokens = sut.scan("\"bar\"");
        assertThat(tokens.size(), is(1));
        token = (Token<String>) tokens.get(0);
        assertThat(token.getType(), is(TokenType.STRING));
        assertThat(token.getValue(), is("bar"));
    }

    @Test
    public void scan_throwExceptionIfStringisUnterminated() throws SyntaxException {
        thrown.expect(SyntaxException.class);
        thrown.expectMessage("Unterminated string 'foo'!");
        sut.scan("'foo");
    }

    @Test
    @SuppressWarnings("unchecked")
    public void scan_lineWithMultipleStrings() throws SyntaxException {
        final List<Token<?>> tokens = sut.scan("'foo' 'bar' 'baz'\n");

        assertThat(tokens.size(), is(3));
        Token<String> token = (Token<String>) tokens.get(0);
        assertThat(token.getType(), is(TokenType.STRING));
        assertThat(token.getValue(), is("foo"));

        token = (Token<String>) tokens.get(1);
        assertThat(token.getType(), is(TokenType.STRING));
        assertThat(token.getValue(), is("bar"));

        token = (Token<String>) tokens.get(2);
        assertThat(token.getType(), is(TokenType.STRING));
        assertThat(token.getValue(), is("baz"));
    }

    @Test
    @SuppressWarnings("unchecked")
    public void scan_lineWithLiteralStartingWithNumber() throws SyntaxException {
        final List<Token<?>> tokens = sut.scan("192.168.1.1 127.0.0.1\n");

        assertThat(tokens.size(), is(2));
        Token<String> token = (Token<String>) tokens.get(0);
        assertThat(token.getType(), is(TokenType.LITERAL));
        assertThat(token.getValue(), is("192.168.1.1")); // NOPMD Only want to test parsing of an ip.

        token = (Token<String>) tokens.get(1);
        assertThat(token.getType(), is(TokenType.LITERAL));
        assertThat(token.getValue(), is("127.0.0.1")); // NOPMD Only want to test parsing of an ip.
    }

    @Test
    @SuppressWarnings("unchecked")
    public void scan_lineWithMultipleLiterals() throws SyntaxException {
        final List<Token<?>> tokens = sut.scan("loo l-ar laz_1");

        assertThat(tokens.size(), is(3));

        Token<String> token = (Token<String>) tokens.get(0);
        assertThat(token.getType(), is(TokenType.LITERAL));
        assertThat(token.getValue(), is("loo"));

        token = (Token<String>) tokens.get(1);
        assertThat(token.getType(), is(TokenType.LITERAL));
        assertThat(token.getValue(), is("l-ar"));

        token = (Token<String>) tokens.get(2);
        assertThat(token.getType(), is(TokenType.LITERAL));
        assertThat(token.getValue(), is("laz_1"));
    }

    @Test
    @SuppressWarnings("unchecked")
    public void scan_literalsStartWithSpecialCharacters() throws SyntaxException {
        List<Token<?>> tokens = sut.scan("/foo/bar/baz");
        assertThat(tokens.size(), is(1));
        Token<String> token = (Token<String>) tokens.get(0);
        assertThat(token.getType(), is(TokenType.LITERAL));
        assertThat(token.getValue(), is("/foo/bar/baz"));

        tokens = sut.scan("--foo-bar-baz");
        assertThat(tokens.size(), is(1));
        token = (Token<String>) tokens.get(0);
        assertThat(token.getType(), is(TokenType.LITERAL));
        assertThat(token.getValue(), is("--foo-bar-baz"));

        tokens = sut.scan("/foo/bar/baz --foo-bar-baz");
        assertThat(tokens.size(), is(2));
        token = (Token<String>) tokens.get(0);
        assertThat(token.getType(), is(TokenType.LITERAL));
        assertThat(token.getValue(), is("/foo/bar/baz"));
        token = (Token<String>) tokens.get(1);
        assertThat(token.getType(), is(TokenType.LITERAL));
        assertThat(token.getValue(), is("--foo-bar-baz"));
    }

    @Test
    @SuppressWarnings("unchecked")
    public void scan_lineWithSingleNumber() throws SyntaxException {
        final List<Token<?>> tokens = sut.scan("1234");
        assertThat(tokens.size(), is(1));

        final Token<Integer> token = (Token<Integer>) tokens.get(0);
        assertThat(token.getType(), is(TokenType.INTEGER));
        assertThat(token.getValue(), is(1234));
    }

    @Test
    @SuppressWarnings("unchecked")
    public void scan_lineWithMultipleNumbers() throws SyntaxException {
        final List<Token<?>> tokens = sut.scan("1234 5678 90");
        assertThat(tokens.size(), is(3));

        Token<Integer> token = (Token<Integer>) tokens.get(0);
        assertThat(token.getType(), is(TokenType.INTEGER));
        assertThat(token.getValue(), is(1234));

        token = (Token<Integer>) tokens.get(1);
        assertThat(token.getType(), is(TokenType.INTEGER));
        assertThat(token.getValue(), is(5678));

        token = (Token<Integer>) tokens.get(2);
        assertThat(token.getType(), is(TokenType.INTEGER));
        assertThat(token.getValue(), is(90));
    }

    @Test
    @SuppressWarnings("unchecked")
    public void scan_lineWithLiteralKeywordAndNumbers() throws SyntaxException {
        Token<Integer> intToken;
        Token<String> strToken;
        List<Token<?>> tokens = sut.scan("loo 1234 5678 bar");

        assertThat(tokens.size(), is(4));
        strToken = (Token<String>) tokens.get(0);
        assertThat(strToken.getType(), is(TokenType.LITERAL));
        assertThat(strToken.getValue(), is("loo"));

        intToken = (Token<Integer>) tokens.get(1);
        assertThat(intToken.getType(), is(TokenType.INTEGER));
        assertThat(intToken.getValue(), is(1234));

        intToken = (Token<Integer>) tokens.get(2);
        assertThat(intToken.getType(), is(TokenType.INTEGER));
        assertThat(intToken.getValue(), is(5678));

        strToken = (Token<String>) tokens.get(3);
        assertThat(strToken.getType(), is(TokenType.KEYWORD));
        assertThat(strToken.getValue(), is("bar"));

        tokens = sut.scan("1234 loo bar 5678");
        assertThat(tokens.size(), is(4));

        intToken = (Token<Integer>) tokens.get(0);
        assertThat(intToken.getType(), is(TokenType.INTEGER));
        assertThat(intToken.getValue(), is(1234));

        strToken = (Token<String>) tokens.get(1);
        assertThat(strToken.getType(), is(TokenType.LITERAL));
        assertThat(strToken.getValue(), is("loo"));

        strToken = (Token<String>) tokens.get(2);
        assertThat(strToken.getType(), is(TokenType.KEYWORD));
        assertThat(strToken.getValue(), is("bar"));

        intToken = (Token<Integer>) tokens.get(3);
        assertThat(intToken.getType(), is(TokenType.INTEGER));
        assertThat(intToken.getValue(), is(5678));

    }

    @Test
    @SuppressWarnings("unchecked")
    public void scan_lineWithMaliciousNumber() throws SyntaxException {
        final List<Token<?>> tokens = sut.scan("1234foo");

        assertThat(tokens.size(), is(1));
        final Token<String> strToken = (Token<String>) tokens.get(0);

        assertThat(strToken.getType(), is(TokenType.LITERAL));
        assertThat(strToken.getValue(), is("1234foo"));
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
        NONE();

        @Override
        public String getLiteral() {
            return "";
        }
    }

    private static class LiteralCommandMapStub extends LiteralCommandMap {

        public LiteralCommandMapStub() {
            super(TestSubType.NONE);
        }

        @Override
        protected void initMainCommandMap(final Map<String, MainCommandType> map) {
            for (final MainCommandType t : TestMainType.values()) {
                map.put(t.toString(), t);
            }
        }

        @Override
        protected void initSubCommandMap(final Map<String, SubCommandType> map) {
            for (final SubCommandType t : TestSubType.values()) {
                map.put(t.toString(), t);
            }
        }

        @Override
        protected Class<? extends MainCommandType> getMainCommandType() {
            return TestMainType.class;
        }

        @Override
        protected Class<? extends SubCommandType> getSubCommandType() {
            return TestSubType.class;
        }

    }

}
