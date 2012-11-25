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

import com.google.common.base.Objects;
import com.google.common.collect.Sets;
import java.util.Set;

/**
 * Represent a token scanned from interactive shell input.
 *
 * TODO Make independent from Neuron Main- and SubType.
 *
 * @param <T> Type of the token value.
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
public final class Token<T> {

    /**
     * Holds all keyword strings.
     */
    private static final Set<String> KEYWORDS = Sets.newHashSet();

    static {
        // Initialize keywords with command strings.
        for (final NeuronMainType t : NeuronMainType.values()) {
            KEYWORDS.add(t.toString());
        }
        // Initialize keywords with sub command strings.
        for (final NeuronSubType t : NeuronSubType.values()) {
            if (t == NeuronSubType.NONE) {
                // Has an empty string literal, so ignore.
                continue;
            }
            KEYWORDS.add(t.toString());
        }
    }

    /**
     * Type of token.
     */
    private final TokenType type;

    /**
     * Value of the token.
     */
    private final T value;

    /**
     * Hide constructor to force usage of factory methods.
     *
     * @param type Token type.
     * @param value Token value.
     */
    private Token(final TokenType type, final T value) {
        this.type = type;
        this.value = value;
    }

    /**
     * Creates a command keyword token.
     *
     * @param value token value
     * @return new instance
     */
    public static Token<String> newKeywordToken(final String value) {
        return new Token<String>(TokenType.KEYWORD, value);
    }

    /**
     * Creates a string token.
     *
     * @param value token value
     * @return new instance
     */
    public static Token<String> newStringToken(final String value) {
        return new Token<String>(TokenType.STRING, value);
    }

    /**
     * Creates a literal token.
     *
     * @param value token value
     * @return new instance
     */
    public static Token<String> newLiteralToken(final String value) {
        return new Token<String>(TokenType.LITERAL, value);
    }

    /**
     * Creates a number token.
     *
     * @param value token value
     * @return new instance
     */
    public static Token<Integer> newNumberToken(final Integer value) {
        return new Token<Integer>(TokenType.NUMBER, value);
    }

    /**
     * Get the tokens type.
     *
     * @return type enum
     */
    public TokenType getType() {
        return type;
    }

    /**
     * Get the tokens value.
     *
     * @return token value.
     */
    public T getValue() {
        return value;
    }

    @Override
    public String toString() {
        return Objects.toStringHelper(this)
                      .add("type", type)
                      .add("value", value)
                      .toString();
    }

    /**
     * Determines if a string is a reserved keyword.
     *
     * @param value string to check
     * @return true if the string is a reserved keyword, else false
     */
    public static boolean isKeyword(final String value) {
        return KEYWORDS.contains(value);
    }

}
