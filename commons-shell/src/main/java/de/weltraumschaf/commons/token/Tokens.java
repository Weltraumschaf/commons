/*
 *  LICENSE
 *
 * "THE BEER-WARE LICENSE" (Revision 43):
 * "Sven Strittmatter" &lt;weltraumschaf@googlemail.com&gt; wrote this file.
 * As long as you retain this notice you can do whatever you want with
 * this stuff. If we meet some day, and you think this stuff is worth it,
 * you can buy me a non alcohol-free beer in return.
 *
 * Copyright (C) 2012 "Sven Strittmatter" &lt;weltraumschaf@googlemail.com&gt;
 */
package de.weltraumschaf.commons.token;

/**
 * Factory to create tokens.
 *
 * @since 1.0.0
 * @author Sven Strittmatter &lt;weltraumschaf@googlemail.com&gt;
 * @version $Id: $Id
 */
public final class Tokens {

    /**
     * Hidden for pure static factory.
     */
    private Tokens() {
        super();
        throw new UnsupportedOperationException("Don't call from reflection!");
    }

    /**
     * Creates a new {@link de.weltraumschaf.commons.token.TokenType#BOOLEAN boolean} token.
     *
     * @param position start position of token, must not be {@code null}
     * @param raw raw value of the token, must not be {@code null}
     * @param value typed value of the token, must not be {@code null}
     * @return always new instance, never {@code null}
     */
    public static Token newBooleanToken(final Position position, final String raw, final Boolean value) {
        return new BaseToken.BooleanToken(position, raw, value);
    }

    /**
     * Creates a new {@link de.weltraumschaf.commons.token.TokenType#INTEGER integer} token.
     *
     * @param position start position of token, must not be {@code null}
     * @param raw raw value of the token, must not be {@code null}
     * @param value typed value of the token, must not be {@code null}
     * @return always new instance, never {@code null}
     */
    public static Token newIntegerToken(final Position position, final String raw, final Integer value) {
        return new BaseToken.IntegerToken(position, raw, value);
    }

    /**
     * Creates a new {@link de.weltraumschaf.commons.token.TokenType#FLOAT float} token.
     *
     * @param position start position of token, must not be {@code null}
     * @param raw raw value of the token, must not be {@code null}
     * @param value typed value of the token, must not be {@code null}
     * @return always new instance, never {@code null}
     */
    public static Token newFloatToken(final Position position, final String raw, final Float value) {
        return new BaseToken.FloatToken(position, raw, value);
    }

    /**
     * Creates a new {@link de.weltraumschaf.commons.token.TokenType#STRING string} token.
     *
     * @param position start position of token, must not be {@code null}
     * @param raw raw value of the token, must not be {@code null}
     * @param value typed value of the token, must not be {@code null}
     * @return always new instance, never {@code null}
     */
    public static Token newStringToken(final Position position, final String raw, final String value) {
        return new BaseToken.StringToken(position, raw, value);
    }

    /**
     * Creates a new {@link de.weltraumschaf.commons.token.TokenType#KEYWORD keyword} token.
     *
     * @param position start position of token, must not be {@code null}
     * @param raw raw value of the token, must not be {@code null}
     * @param value typed value of the token, must not be {@code null}
     * @return always new instance, never {@code null}
     */
    public static Token newKeywordToken(final Position position, final String raw, final String value) {
        return new BaseToken.KeywordToken(position, raw, value);
    }

    /**
     * Creates a new {@link de.weltraumschaf.commons.token.TokenType#LITERAL literal} token.
     *
     * @param position start position of token, must not be {@code null}
     * @param raw raw value of the token, must not be {@code null}
     * @param value typed value of the token, must not be {@code null}
     * @return always new instance, never {@code null}
     */
    public static Token newLiteralToken(final Position position, final String raw, final String value) {
        return new BaseToken.LiteralToken(position, raw, value);
    }
}
