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
package de.weltraumschaf.commons.characters;

/**
 * Access a string as stream of characters.
 *
 * Example:
 * <code>
 * CharacterStream characterStream = new CharacterStream("...");
 *
 * while (characterStream.hasNext()) {
 *     final char currentChar = characterStream.next();
 *     // Do something with the current char.
 * }
 * </code>
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
public class CharacterStream {

    /**
     * Accessed string.
     */
    private final String input;

    /**
     * Current character position.
     */
    private int index = -1;

    /**
     * Initializes stream with string.
     *
     * @param input Streamed string.
     */
    public CharacterStream(final String input) {
        this.input = input;
    }

    /**
     * Returns next character.
     *
     * @return next character
     * // CHECKSTYLE:OFF
     * @throws IndexOutOfBoundsException if, there are no more characters.
     * // CHECKSTYLE:ON
     */
    public char next() {
        if (!hasNext()) {
            throw new IndexOutOfBoundsException("No more next characters!");
        }

        ++index;
        return current();
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
     *
     * If {@link #next()} not yet called, it is called implicitly.
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
     * @return the peeked character.
     * @throws IndexOutOfBoundsException if there are no more character to peek
     */
    public char peek() {
        if (!hasNext()) {
            throw new IndexOutOfBoundsException("No more next characters!");
        }

        final char peekedCharacter = next();
        --index;
        return peekedCharacter;
    }

}
