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

import com.google.common.collect.Sets;
import java.util.Set;

/**
 * Helper class to verify if a given character belong to a specified group or range of characters.
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
public final class CharacterHelper {

    /**
     * Special characters allowed in literals.
     */
    private static final Set<Character> SPECIAL_CHARS = Sets.newHashSet('/', '\\', '-');

    /**
     * Private constructor for pure static utility class.
     */
    private CharacterHelper() {
        super();
    }

    /**
     * Checks whether a character is inside a given character range (included).
     *
     * Throws IllegalArgumentException if end is less than start.
     *
     * @param character character to check
     * @param start including range
     * @param end including range
     * @return true if character is in range, unless false
     */
    public static boolean isCharInRange(final char character, final char start, final char end) {
        if (end < start) {
            throw new IllegalArgumentException("End must be greater or equal than start!");
        }

        return start <= character && character <= end;
    }

    /**
     * Checks whether a character is a letter [a-zA-Z].
     *
     * @param character a single character
     * @return true if character is a letter, unless false
     */
    public static boolean isAlpha(final char character) {
        return isCharInRange(character, 'a', 'z') || isCharInRange(character, 'A', 'Z');
    }

    /**
     * Checks whether a character is a number [0-9].
     *
     * @param character a single character
     *
     * @return true if character is a number, unless false
     */
    public static boolean isNum(final char character) {
        return isCharInRange(character, '0', '9');
    }

    /**
     * Checks whether a character is a number or alpha [0-9a-zA-Z].
     *
     * @param character a single character
     *
     * @return true if character is a letter or number, unless false
     */
    public static boolean isAlphaNum(final char character) {
        return isAlpha(character) || isNum(character);
    }

    /**
     * Checks whether a character is a whitespace.
     *
     * White spaces are \t, \n, \r, and ' '.
     *
     * @param character a single character
     * @return true if character is a whitespace character, unless false
     */
    public static boolean isWhiteSpace(final char character) {
        return ' ' == character || '\t' == character || '\n' == character || '\r' == character;
    }

    /**
     * Checks whether a character is a quote character.
     *
     * Quotes are ' and ".
     *
     * @param character a single character
     * @return true if character is a quote character, unless false
     */
    public static boolean isQuote(final char character) {
        return '\'' == character || '"' == character;
    }

    /**
     * Checks whether a character is a special character.
     *
     * See {@link #SPECIAL_CHARS} for all allowed special characters.
     *
     * @param character a single character
     * @return true if character is a special character, unless false
     */
    public static boolean isSpecialChar(final char character) {
        return SPECIAL_CHARS.contains(character);
    }
}
