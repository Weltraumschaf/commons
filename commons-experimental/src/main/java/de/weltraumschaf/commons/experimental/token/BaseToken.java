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
package de.weltraumschaf.commons.experimental.token;

import de.weltraumschaf.commons.guava.Objects;
import de.weltraumschaf.commons.validate.Validate;

/**
 * Base token implementation with generic value.
 *
 * @param <T> type of the recognized value
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
abstract class BaseToken<T> implements Token {

    /**
     * Format string for unsupported value methods.
     */
    private static final String FORMAT = "Token is not of type %s! But is of type %s.";
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
    public TokenType getType() {
        return type;
    }

    @Override
    public Position getPosition() {
        return position;
    }

    @Override
    public String getRaw() {
        return raw;
    }

    /**
     * Get the typed value.
     *
     * @return must not be {@code null}
     */
    T getValue() {
        return value;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(type, position, raw, value);
    }

    @Override
    public boolean equals(final Object obj) {
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
    public String toString() {
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
     * Throws always an exception.
     * <p>
     * Used to signal that one of the {@code as...()} methods was called, but the
     * value type does not match.
     * </p>
     * <p>
     * This method always throws {@link UnsupportedOperationException}.
     * </p>
     *
     * @param unsupported the unsupported type
     */
    protected void raiseUnsupportedValueOperationError(final TokenType unsupported) {
        throw new UnsupportedOperationException(String.format(FORMAT, unsupported, getType()));
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
            raiseUnsupportedValueOperationError(TokenType.FLOAT);
            return null; // Never reached, because exception thrown before.
        }

        @Override
        public Integer asInteger() {
            raiseUnsupportedValueOperationError(TokenType.INTEGER);
            return null; // Never reached, because exception thrown before.
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
            raiseUnsupportedValueOperationError(TokenType.BOOLEAN);
            return null; // Never reached, because exception thrown before.
        }

        @Override
        public Float asFloat() {
            raiseUnsupportedValueOperationError(TokenType.FLOAT);
            return null; // Never reached, because exception thrown before.
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
            raiseUnsupportedValueOperationError(TokenType.BOOLEAN);
            return null; // Never reached, because exception thrown before.
        }

        @Override
        public Float asFloat() {
            return getValue();
        }

        @Override
        public Integer asInteger() {
            raiseUnsupportedValueOperationError(TokenType.INTEGER);
            return null; // Never reached, because exception thrown before.
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
            raiseUnsupportedValueOperationError(TokenType.BOOLEAN);
            return null; // Never reached, because exception thrown before.
        }

        @Override
        public Float asFloat() {
            raiseUnsupportedValueOperationError(TokenType.FLOAT);
            return null; // Never reached, because exception thrown before.
        }

        @Override
        public Integer asInteger() {
            raiseUnsupportedValueOperationError(TokenType.INTEGER);
            return null; // Never reached, because exception thrown before.
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
