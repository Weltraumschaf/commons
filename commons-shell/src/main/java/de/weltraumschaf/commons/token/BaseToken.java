/*
 *  LICENSE
 *
 * "THE BEER-WARE LICENSE" (Revision 43):
 * "Sven Strittmatter" <weltraumschaf@googlemail.com> wrote this file.
 * As long as you retain this notice you can do whatever you want with
 * this stuff. If we meet some day, and you think this stuff is worth it,
 * you can buy me a non alcohol-free beer in return.
 *
 * Copyright (C) 2012 "Sven Strittmatter" <weltraumschaf@googlemail.com>
 */
package de.weltraumschaf.commons.token;

import de.weltraumschaf.commons.guava.Objects;
import de.weltraumschaf.commons.validate.Validate;

/**
 * Base token implementation with generic value.
 *
 * @since 1.0.0
 * @param <T> type of the recognized value
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
abstract class BaseToken<T> implements Token {

    /**
     * Token class type.
     */
    private final TokenType type;
    /**
     * Start position of token.
     */
    private final Position position;
    /**
     * Raw scanned lexeme.
     */
    private final String raw;
    /**
     * Recognized typed token value.
     */
    private final T value;

    /**
     * Dedicated constructor .
     *
     * @param type must not be {@code null}
     * @param position must not be {@code null}
     * @param raw must not be {@code null}
     * @param value must not be {@code null}
     */
    public BaseToken(final TokenType type, final Position position, final String raw, final T value) {
        super();
        this.type = Validate.notNull(type, "type");
        this.position = Validate.notNull(position, "position");
        this.raw = Validate.notNull(raw, "raw");
        this.value = Validate.notNull(value, "value");
    }

    @Override
    public final TokenType getType() {
        return type;
    }

    @Override
    public final Position getPosition() {
        return position;
    }

    @Override
    public final String getRaw() {
        return raw;
    }

    /**
     * Get the typed value.
     *
     * @return must not be {@code null}
     */
    final T getValue() {
        return value;
    }

    @Override
    public final int hashCode() {
        return Objects.hashCode(type, position, raw, value);
    }

    @Override
    public final boolean equals(final Object obj) {
        if (!(obj instanceof BaseToken)) {
            return false;
        }

        final BaseToken<?> other = (BaseToken<?>) obj;
        return Objects.equal(type, other.type)
                && Objects.equal(position, other.position)
                && Objects.equal(raw, other.raw)
                && Objects.equal(value, other.value);
    }

    @Override
    public final String toString() {
        return Objects.toStringHelper(this)
                .add("type", type)
                .add("position", position)
                .add("raw", raw)
                .add("value", value)
                .toString();
    }

    @Override
    public String asString() {
        return value.toString();
    }

    /**
     * Concrete implementation of boolean token.
     */
    static final class BooleanToken extends BaseToken<Boolean> {

        /**
         * Dedicated constructor.
         *
         * @param position must not be {@code null}
         * @param raw must not be {@code null}
         * @param value must not be {@code null}
         */
        public BooleanToken(final Position position, final String raw, final Boolean value) {
            super(TokenType.BOOLEAN, position, raw, value);
        }

        @Override
        public Boolean asBoolean() {
            return getValue();
        }

        @Override
        public Float asFloat() {
            return getValue() ? 1.0f : 0.0f;
        }

        @Override
        public Integer asInteger() {
            return getValue() ? 1 : 0;
        }

    }

    /**
     * Concrete implementation of boolean token.
     */
    static final class IntegerToken extends BaseToken<Integer> {

        /**
         * Dedicated constructor.
         *
         * @param position must not be {@code null}
         * @param raw must not be {@code null}
         * @param value must not be {@code null}
         */
        public IntegerToken(final Position position, final String raw, final Integer value) {
            super(TokenType.INTEGER, position, raw, value);
        }

        @Override
        public Boolean asBoolean() {
            return getValue() != 0;
        }

        @Override
        public Float asFloat() {
            return getValue() + 0.0f;
        }

        @Override
        public Integer asInteger() {
            return getValue();
        }

    }

    /**
     * Concrete implementation of boolean token.
     */
    static final class FloatToken extends BaseToken<Float> {

        /**
         * Dedicated constructor.
         *
         * @param position must not be {@code null}
         * @param raw must not be {@code null}
         * @param value must not be {@code null}
         */
        public FloatToken(final Position position, final String raw, final Float value) {
            super(TokenType.FLOAT, position, raw, value);
        }

        @Override
        public Boolean asBoolean() {
            return getValue() != 0.0f;
        }

        @Override
        public Float asFloat() {
            return getValue();
        }

        @Override
        public Integer asInteger() {
            return getValue().intValue();
        }

    }

    /**
     * Concrete implementation of boolean token.
     */
    static class StringToken extends BaseToken<String> {

        /**
         * Dedicated constructor.
         *
         * @param position must not be {@code null}
         * @param raw must not be {@code null}
         * @param value must not be {@code null}
         */
        public StringToken(final Position position, final String raw, final String value) {
            this(TokenType.STRING, position, raw, value);
        }

        /**
         * Constructor for sub classes.
         *
         * @param type must not be {@code null}
         * @param position must not be {@code null}
         * @param raw must not be {@code null}
         * @param value must not be {@code null}
         */
        private StringToken(final TokenType type, final Position position, final String raw, final String value) {
            super(type, position, raw, value);
        }

        @Override
        public Boolean asBoolean() {
            return !getValue().isEmpty();
        }

        @Override
        public Float asFloat() {
            return getValue().isEmpty() ? 0.0f : 1.0f;
        }

        @Override
        public Integer asInteger() {
            return getValue().isEmpty() ? 0 : 1;
        }

    }

    /**
     * Concrete implementation of boolean token.
     */
    static final class KeywordToken extends StringToken {

        /**
         * Dedicated constructor.
         *
         * @param position must not be {@code null}
         * @param raw must not be {@code null}
         * @param value must not be {@code null}
         */
        public KeywordToken(final Position position, final String raw, final String value) {
            super(TokenType.KEYWORD, position, raw, value);
        }
    }

    /**
     * Concrete implementation of boolean token.
     */
    static final class LiteralToken extends StringToken {

        /**
         * Dedicated constructor.
         *
         * @param position must not be {@code null}
         * @param raw must not be {@code null}
         * @param value must not be {@code null}
         */
        public LiteralToken(final Position position, final String raw, final String value) {
            super(TokenType.LITERAL, position, raw, value);
        }
    }
}
