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

/**
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
public final class Token {

    private TokenType type;
    private Position position;
    private String raw;
    private String value;
    private ValueType valuetype;

    public TokenType getType() {
        return type;
    }

    public Position getPosition() {
        return position;
    }

    public String asString() {
        return value;
    }

    public Boolean asBoolean() {
        if (valuetype != ValueType.BOOLEAN) {
            throw new ValueCastException();
        }

        return Boolean.parseBoolean(value);
    }

    public Integer asInteger() {
        if (valuetype != ValueType.INTEGER) {
            throw new ValueCastException();
        }

        return Integer.parseInt(value, 10);
    }

    public Float asFloat() {
        if (valuetype != ValueType.FLOAT) {
            throw new ValueCastException();
        }

        return Float.parseFloat(value);
    }

    public final class Position {
        private int row;
        private int column;
    }

    public final class ValueCastException extends RuntimeException {}
}
