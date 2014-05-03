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
 * A token position.
 * <p>
 * Usually it is the position where a token begins. But may be used also for other positions in the source code.
 * New lines are treated as increment for the row. The position begins at (1, 1) and not from zero.
 * </p>
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
public final class Position {

    /**
     * The line in the source.
     */
    private final int row;
    /**
     * The column in the source.
     */
    private final int column;

    /**
     * Dedicated constructor.
     *
     * @param row must be greater than 0
     * @param column must be greater than 0
     */
    public Position(final int row, final int column) {
        super();
        this.row = Validate.greaterThan(row, 0, "row");
        this.column = Validate.greaterThan(column, 0, "column");
    }

    /**
     * Get the source code line.
     *
     * @return greater than 0
     */
    public int getRow() {
        return row;
    }

    /**
     * Get the source code column.
     *
     * @return greater than 0
     */
    public int getColumn() {
        return column;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(column, row);
    }

    @Override
    public boolean equals(final Object obj) {
        if (!(obj instanceof Position)) {
            return false;
        }

        final Position other = (Position) obj;
        return Objects.equal(column, other.column) && Objects.equal(row, other.row);
    }

    @Override
    public String toString() {
        return Objects.toStringHelper(this).add("row", row).add("column", column).toString();
    }

}
