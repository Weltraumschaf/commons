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
import static org.hamcrest.Matchers.closeTo;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

/**
 * Tests for {@link DefaultScanner}.
 *
 * @author Sven Strittmatter &lt;weltraumschaf@googlemail.com&gt;
 */
public class DefaultScannerTest {

    @Rule
    // CHECKSTYLE:OFF
    public final ExpectedException thrown = ExpectedException.none();
    // CHECKSTYLE:ON
    private final Scanner sut = Scanners.newScanner(new LiteralCommandMapStub());

    @Test(expected = NullPointerException.class)
    public void scan_nullArgument() throws SyntaxException {
        sut.scan(null);
    }

    @Test
    public void scan_emptyLine() throws SyntaxException {
        final List<Token> tokens = sut.scan("");
        assertThat(tokens.size(), is(0));
    }

    @Test
    public void scan_keywords() throws SyntaxException {
        final StringBuilder input = new StringBuilder();
        final List<CommandType> types = Lists.newArrayList();

        for (final MainCommandType t : TestMainType.values()) {
            input.append(t.getLiteral()).append(' ');
            types.add(t);
        }

        for (final SubCommandType t : TestSubType.values()) {
            if (t == TestSubType.NONE) {
                continue;
            }

            input.append(t.getLiteral()).append(' ');
            types.add(t);
        }

        final List<Token> tokens = sut.scan(input.toString());
        int tokenId = 0;

        for (final Token token : tokens) {
            assertThat(token.getType(), is(TokenType.KEYWORD));
            assertThat(token.asString(), is(types.get(tokenId).getLiteral()));
            ++tokenId;
        }
    }

    @Test
    public void scan_lineWithSingleKeyword() throws SyntaxException {
        List<Token> tokens = sut.scan(TestMainType.FOO.getLiteral());
        assertThat(tokens.size(), is(1));
        Token token = tokens.get(0);
        assertThat(token.getType(), is(TokenType.KEYWORD));
        assertThat(token.asString(), is(TestMainType.FOO.getLiteral()));

        tokens = sut.scan(TestMainType.BAR.getLiteral());
        assertThat(tokens.size(), is(1));
        token = tokens.get(0);
        assertThat(token.getType(), is(TokenType.KEYWORD));
        assertThat(token.asString(), is(TestMainType.BAR.getLiteral()));

        tokens = sut.scan(TestMainType.BAZ.getLiteral());
        assertThat(tokens.size(), is(1));
        token = tokens.get(0);
        assertThat(token.getType(), is(TokenType.KEYWORD));
        assertThat(token.asString(), is(TestMainType.BAZ.getLiteral()));
    }

    @Test
    public void scan_lineWithSingleString() throws SyntaxException {
        List<Token> tokens = sut.scan("'foo'\n");

        assertThat(tokens.size(), is(1));
        Token token = tokens.get(0);
        assertThat(token.getType(), is(TokenType.STRING));
        assertThat(token.asString(), is("foo"));

        tokens = sut.scan("\"bar\"");
        assertThat(tokens.size(), is(1));
        token = tokens.get(0);
        assertThat(token.getType(), is(TokenType.STRING));
        assertThat(token.asString(), is("bar"));
    }

    @Test
    public void scan_throwExceptionIfStringisUnterminated() throws SyntaxException {
        thrown.expect(SyntaxException.class);
        thrown.expectMessage("Unterminated string 'foo'!");
        sut.scan("'foo");
    }

    @Test
    public void scan_lineWithMultipleStrings() throws SyntaxException {
        final List<Token> tokens = sut.scan("'foo' 'bar' 'baz'\n");

        assertThat(tokens.size(), is(3));
        Token token = tokens.get(0);
        assertThat(token.getType(), is(TokenType.STRING));
        assertThat(token.asString(), is("foo"));

        token = tokens.get(1);
        assertThat(token.getType(), is(TokenType.STRING));
        assertThat(token.asString(), is("bar"));

        token = tokens.get(2);
        assertThat(token.getType(), is(TokenType.STRING));
        assertThat(token.asString(), is("baz"));
    }

    @Test
    public void scan_lineWithLiteralStartingWithInteger() throws SyntaxException {
        final List<Token> tokens = sut.scan("192.168.1.1 127.0.0.1\n");

        assertThat(tokens.size(), is(2));
        Token token = tokens.get(0);
        assertThat(token.getType(), is(TokenType.LITERAL));
        assertThat(token.asString(), is("192.168.1.1")); // NOPMD Only want to test parsing of an ip.

        token = tokens.get(1);
        assertThat(token.getType(), is(TokenType.LITERAL));
        assertThat(token.asString(), is("127.0.0.1")); // NOPMD Only want to test parsing of an ip.
    }

    @Test
    public void scan_lineWithMultipleLiterals() throws SyntaxException {
        final List<Token> tokens = sut.scan("loo l-ar laz_1");

        assertThat(tokens.size(), is(3));

        Token token = tokens.get(0);
        assertThat(token.getType(), is(TokenType.LITERAL));
        assertThat(token.asString(), is("loo"));

        token = tokens.get(1);
        assertThat(token.getType(), is(TokenType.LITERAL));
        assertThat(token.asString(), is("l-ar"));

        token = tokens.get(2);
        assertThat(token.getType(), is(TokenType.LITERAL));
        assertThat(token.asString(), is("laz_1"));
    }

    @Test
    public void scan_literalsStartWithSpecialCharacters() throws SyntaxException {
        List<Token> tokens = sut.scan("/foo/bar/baz");
        assertThat(tokens.size(), is(1));
        Token token = tokens.get(0);
        assertThat(token.getType(), is(TokenType.LITERAL));
        assertThat(token.asString(), is("/foo/bar/baz"));

        tokens = sut.scan("--foo-bar-baz");
        assertThat(tokens.size(), is(1));
        token = tokens.get(0);
        assertThat(token.getType(), is(TokenType.LITERAL));
        assertThat(token.asString(), is("--foo-bar-baz"));

        tokens = sut.scan("/foo/bar/baz --foo-bar-baz");
        assertThat(tokens.size(), is(2));
        token = tokens.get(0);
        assertThat(token.getType(), is(TokenType.LITERAL));
        assertThat(token.asString(), is("/foo/bar/baz"));
        token = tokens.get(1);
        assertThat(token.getType(), is(TokenType.LITERAL));
        assertThat(token.asString(), is("--foo-bar-baz"));
    }

    @Test
    public void scan_lineWithSingleIntegerWithoutSign() throws SyntaxException {
        final List<Token> tokens = sut.scan("1234");
        assertThat(tokens.size(), is(1));

        final Token token = tokens.get(0);
        assertThat(token.getType(), is(TokenType.INTEGER));
        assertThat(token.asInteger(), is(1234));
    }

    @Test
    public void scan_lineWithSingleIntegerWithoutPositiveSign() throws SyntaxException {
        final List<Token> tokens = sut.scan("+1234");
        assertThat(tokens.size(), is(1));

        final Token token = tokens.get(0);
        assertThat(token.getType(), is(TokenType.INTEGER));
        assertThat(token.asInteger(), is(1234));
    }

    @Test
    public void scan_lineWithSingleIntegerWithoutNegativeSign() throws SyntaxException {
        final List<Token> tokens = sut.scan("-1234");
        assertThat(tokens.size(), is(1));

        final Token token = tokens.get(0);
        assertThat(token.getType(), is(TokenType.INTEGER));
        assertThat(token.asInteger(), is(-1234));
    }

    @Test
    public void scan_lineWithMultipleIntegers() throws SyntaxException {
        final List<Token> tokens = sut.scan("1234 +5678 -90");
        assertThat(tokens.size(), is(3));

        Token token = tokens.get(0);
        assertThat(token.getType(), is(TokenType.INTEGER));
        assertThat(token.asInteger(), is(1234));

        token = tokens.get(1);
        assertThat(token.getType(), is(TokenType.INTEGER));
        assertThat(token.asInteger(), is(5678));

        token = tokens.get(2);
        assertThat(token.getType(), is(TokenType.INTEGER));
        assertThat(token.asInteger(), is(-90));
    }

    @Test
    public void scan_lineWithLiteralKeywordAndIntegers() throws SyntaxException {
        Token intToken;
        Token strToken;
        List<Token> tokens = sut.scan("loo 1234 -5678 bar");

        assertThat(tokens.size(), is(4));
        strToken = tokens.get(0);
        assertThat(strToken.getType(), is(TokenType.LITERAL));
        assertThat(strToken.asString(), is("loo"));

        intToken = tokens.get(1);
        assertThat(intToken.getType(), is(TokenType.INTEGER));
        assertThat(intToken.asInteger(), is(1234));

        intToken = tokens.get(2);
        assertThat(intToken.getType(), is(TokenType.INTEGER));
        assertThat(intToken.asInteger(), is(-5678));

        strToken = tokens.get(3);
        assertThat(strToken.getType(), is(TokenType.KEYWORD));
        assertThat(strToken.asString(), is("bar"));

        tokens = sut.scan("-1234 loo bar 5678");
        assertThat(tokens.size(), is(4));

        intToken = tokens.get(0);
        assertThat(intToken.getType(), is(TokenType.INTEGER));
        assertThat(intToken.asInteger(), is(-1234));

        strToken = tokens.get(1);
        assertThat(strToken.getType(), is(TokenType.LITERAL));
        assertThat(strToken.asString(), is("loo"));

        strToken = tokens.get(2);
        assertThat(strToken.getType(), is(TokenType.KEYWORD));
        assertThat(strToken.asString(), is("bar"));

        intToken = tokens.get(3);
        assertThat(intToken.getType(), is(TokenType.INTEGER));
        assertThat(intToken.asInteger(), is(5678));

    }

    @Test
    public void scan_lineWithMaliciousInteger() throws SyntaxException {
        final List<Token> tokens = sut.scan("1234foo");

        assertThat(tokens.size(), is(1));
        final Token strToken = tokens.get(0);

        assertThat(strToken.getType(), is(TokenType.LITERAL));
        assertThat(strToken.asString(), is("1234foo"));
    }

    @Test
    public void scan_booleanTrue() throws SyntaxException {
        final List<Token> tokens = sut.scan("true");

        assertThat(tokens.size(), is(1));
        final Token token = tokens.get(0);

        assertThat(token.getType(), is(TokenType.BOOLEAN));
        assertThat(token.asBoolean(), is(true));
    }

    @Test
    public void scan_booleanFalse() throws SyntaxException {
        final List<Token> tokens = sut.scan("false");

        assertThat(tokens.size(), is(1));
        final Token token = tokens.get(0);

        assertThat(token.getType(), is(TokenType.BOOLEAN));
        assertThat(token.asBoolean(), is(false));
    }

    @Test
    public void scan_lineWithMultipleBooleans() throws SyntaxException {
        final List<Token> tokens = sut.scan("false true true false");

        assertThat(tokens.size(), is(4));
        Token token = tokens.get(0);

        assertThat(token.getType(), is(TokenType.BOOLEAN));
        assertThat(token.asBoolean(), is(false));

        token = tokens.get(1);
        assertThat(token.getType(), is(TokenType.BOOLEAN));
        assertThat(token.asBoolean(), is(true));

        token = tokens.get(2);
        assertThat(token.getType(), is(TokenType.BOOLEAN));
        assertThat(token.asBoolean(), is(true));

        token = tokens.get(3);
        assertThat(token.getType(), is(TokenType.BOOLEAN));
        assertThat(token.asBoolean(), is(false));
    }

    @Test
    public void scan_floats() throws SyntaxException {
        final List<Token> tokens = sut.scan("3.14 -3.14");

        assertThat(tokens.size(), is(2));
        Token token = tokens.get(0);

        assertThat(token.getType(), is(TokenType.FLOAT));
        assertThat((double) token.asFloat(), is(closeTo(3.14, 0.0001)));

        token = tokens.get(1);
        assertThat(token.getType(), is(TokenType.FLOAT));
        assertThat((double) token.asFloat(), is(closeTo(-3.14, 0.0001)));
    }

    @Test
    public void scan_floatsWithExponent() throws SyntaxException {
        final List<Token> tokens = sut.scan("3.14e3 -3.14E3");

        assertThat(tokens.size(), is(2));
        Token token = tokens.get(0);

        assertThat(token.getType(), is(TokenType.FLOAT));
        assertThat((double) token.asFloat(), is(closeTo(3140.0, 0.0001)));

        token = tokens.get(1);
        assertThat(token.getType(), is(TokenType.FLOAT));
        assertThat((double) token.asFloat(), is(closeTo(-3140.0, 0.0001)));
    }

    @Test
    public void scan_floatsWithNegativeExponent() throws SyntaxException {
        final List<Token> tokens = sut.scan("3.14e-3 -3.14E-3");

        assertThat(tokens.size(), is(2));
        Token token = tokens.get(0);

        assertThat(token.getType(), is(TokenType.FLOAT));
        assertThat((double) token.asFloat(), is(closeTo(0.00314, 0.0001)));

        token = tokens.get(1);
        assertThat(token.getType(), is(TokenType.FLOAT));
        assertThat((double) token.asFloat(), is(closeTo(-0.00314, 0.0001)));
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
        protected Class<? extends MainCommandType> getMainCommandType() {
            return TestMainType.class;
        }

        @Override
        protected Class<? extends SubCommandType> getSubCommandType() {
            return TestSubType.class;
        }

    }

}
