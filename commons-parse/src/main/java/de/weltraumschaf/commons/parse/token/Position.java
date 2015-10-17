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
package de.weltraumschaf.commons.parse.token;

import de.weltraumschaf.commons.guava.Objects;
import de.weltraumschaf.commons.validate.Validate;
import net.jcip.annotations.ThreadSafe;

/**
 * Represents a token position in the source string.
 * <p>
 * The position contains the line, column and filename where the
 * token occurred. The file name is optional.
 * </p>
 * <p>
 * This class is immutable by design.
 * </p>
 *
 * @since 1.0.0
 * @author Sven Strittmatter &lt;weltraumschaf@googlemail.com&gt;
 */
@ThreadSafe
public final class Position {

    /**
     * A position for line 0 and column 0.
     */
    public static final Position NULL = new Position(0, 0);

    /**
     * File of the source string.
     */
    private final String file;

    /**
     * Line of occurrence.
     */
    private final int line;

    /**
     * Column of occurrence.
     */
    private final int column;

    /**
     * Initializes without file.
     *
     * @param line Line of occurrence.
     * @param column Column of occurrence.
     */
    public Position(final int line, final int column) {
        this(line, column, "");
    }

    /**
     * Dedicated constructor initializes immutable object.
     *
     * File is optional e.g. if string is parsed directly without any file.
     *
     * @param line must be greater than -1
     * @param column must be greater than -1
     * @param file must not be {@code null}, may be empty
     */
    public Position(final int line, final int column, final String file) {
        super();
        this.line   = Validate.greaterThan(line, -1, "line");
        this.column = Validate.greaterThan(column, -1, "column");
        this.file   = Validate.notNull(file, "file");
    }

    /**
     * Returns the file name of the source.
     *
     * May be null.
     *
     * @return Filename as string.
     */
    public String getFile() {
        return file;
    }

    /**
     * Returns line of occurrence in source.
     *
     * @return Line position.
     */
    public int getLine() {
        return line;
    }

    /**
     * Returns column of occurrence in source.
     *
     * @return Column position.
     */
    public int getColumn() {
        return column;
    }

    @Override
    public String toString() {
        final StringBuilder str = new StringBuilder();

        if (!getFile().isEmpty()) {
            str.append(getFile()).append(' ');
        }

        str.append(String.format("(%s, %s)", getLine(), getColumn()));
        return str.toString();
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(line, column, file);
    }

    @Override
    public boolean equals(final Object obj) {
        if (! (obj instanceof Position)) {
            return false;
        }

        final Position other = (Position) obj;
        return Objects.equal(line, other.line)
                && Objects.equal(column, other.column)
                && Objects.equal(file, other.file);
    }

    /**
     * Test if this position is at a particular position.
     * <p>
     * Similar to {@link #equals(java.lang.Object)} but does not check for {@link #getFile() file}.
     * </p>
     *
     * @param p must not be {@code null}
     * @return {@code true} if line and column is same, else {@code false}
     */
    public boolean at(final Position p) {
        Validate.notNull(p, "p");
        return line == p.line && column == p.column;
    }

    /**
     * Increments the column by one.
     *
     * @return never {@code null}, new instance with incremented column
     */
    public Position incColumn() {
        return new Position(line, column + 1, file);
    }

    /**
     * Increments the line by one.
     *
     * @return never {@code null}, new instance with incremented line
     */
    public Position incLine() {
        return new Position(line + 1, column, file);
    }

    /**
     * Sets the column.
     *
     * @param col any int
     * @return never {@code null}, new instance with new column
     */
    public Position column(final int col) {
        return new Position(line, col, file);
    }

}
