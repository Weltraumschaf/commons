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
package de.weltraumschaf.commons.parse.characters;

import de.weltraumschaf.commons.guava.Objects;
import de.weltraumschaf.commons.parse.token.Position;
import de.weltraumschaf.commons.validate.Validate;

/**
 * Access a string as stream of characters.
 *
 * <p>
 * Example:
 * </p>
 * <pre>
 * {@code final CharacterStream characterStream = new CharacterStream("...");
 *
 * while (characterStream.hasNext()) {
 *     final char currentChar = characterStream.next();
 *     // Do something with the current char.
 * }
 * }</pre>
 *
 * @since 1.0.0
 * @author Sven Strittmatter &lt;weltraumschaf@googlemail.com&gt;
 */
public final class CharacterStream {

    /**
     * Used to detect new lines.
     */
    private static final char NL = '\n';

    /**
     * Accessed string.
     */
    private final String input;

    /**
     * Current character position.
     */
    private int index = -1;
    /**
     * Current line.
     */
    private int line;
    /**
     * Current column.
     */
    private int column;
    /**
     * Whether a {@link #NL newline} was seen.
     */
    private boolean newLineSeen;

    /**
     * Initializes stream with string.
     *
     * @param input must not be {@code null}
     */
    public CharacterStream(final String input) {
        super();
        this.input = Validate.notNull(input, "input");
    }

    /**
     * Returns next character.
     *
     * @return next character // CHECKSTYLE:OFF
     * @throws java.lang.IndexOutOfBoundsException if, there are no more characters. // CHECKSTYLE:ON
     */
    public char next() {
        if (!hasNext()) {
            throw new IndexOutOfBoundsException("No more next characters!");
        }

        if (position().at(Position.NULL)) {
            line = 1;
        }

        ++index;
        final char current = current();

        if (newLineSeen) {
            line++;
            column = 1;
            newLineSeen = false;
        } else {
            column++;
        }

        if (NL == current) {
            newLineSeen = true;
        }

        return current;
    }

    /**
     * True if there are more characters.
     *
     * @return True if there are no more characters.
     */
    public boolean hasNext() {
        return index < input.length() - 1;
    }

    /**
     * Returns the current character.
     * <p>
     * If {@link #next()} not yet called, it is called implicitly.
     * </p>
     *
     * @return The current character.
     */
    public char current() {
        if (-1 == index) {
            next();
        }

        return input.charAt(index);
    }

    /**
     * Look ahead one character w/o advancing the internal pointer for the current character.
     *
     * @return the peeked character. // CHECKSTYLE:OFF
     * @throws java.lang.IndexOutOfBoundsException if there are no more character to peek // CHECKSTYLE:ON
     */
    public char peek() {
        if (!hasNext()) {
            throw new IndexOutOfBoundsException("No more next characters!");
        }

        // Backup position.
        final int oldLine = line;
        final int oldColumn = column;
        final boolean oldNewLineSeen = newLineSeen;

        final char peekedCharacter = next();
        --index; // Restore index to previous position.

        // Restore position.
        line = oldLine;
        column = oldColumn;
        newLineSeen = oldNewLineSeen;

        return peekedCharacter;
    }

    /**
     * Get the current index position.
     *
     * @return initial value is -1, after first call of next 0 up to input length - 1
     */
    public int getIndex() {
        return index;
    }

    /**
     * Get the current position in the stream.
     *
     * @return never {@code null}, always new instance
     */
    public Position position() {
        return new Position(line, column);
    }

    @Override
    public String toString() {
        return Objects.toStringHelper(this)
            .add("input", input)
            .add("index", index)
            .add("line", line)
            .add("column", column)
            .add("newLineSeen", newLineSeen)
            .toString();
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(input);
    }

    @Override
    public boolean equals(final Object obj) {
        if (!(obj instanceof CharacterStream)) {
            return false;
        }

        final CharacterStream other = (CharacterStream) obj;
        return Objects.equal(input, other.input);
    }

    /**
     * Whether the stream is empty.
     *
     * @return {@code true} if the underlying input is an empty string, else {@code false}
     */
    public boolean isEmpty() {
        return input.isEmpty();
    }

}
