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


import de.weltraumschaf.commons.guava.Sets;
import java.util.Set;

/**
 * Helper class to verify if a given character belong to a specified group or range of characters.
 *
 * @author Sven Strittmatter &lt;weltraumschaf@googlemail.com&gt;
 * @version $Id: $Id
 */
public final class CharacterHelper {

    /**
     * Special characters allowed in literals.
     */
    private static final Set<Character> SPECIAL_CHARS = Sets.newHashSet('/', '\\', '-');
    /**
     * Operator characters.
     */
    private static final Set<Character> OPERATORS = Sets.newHashSet('+', '-', '*', '/', '%', '&',
        '|', '^', '<', '>', '=', ':', '?', '(', ')', '{', '}', '[', ']', '!', '~', '@', '#', '$');
    /**
     * Number sign characters.
     */
    private static final Set<Character> SIGN_CHARS = Sets.newHashSet('+', '-');

    /**
     * Private constructor for pure static utility class.
     */
    private CharacterHelper() {
        super();
        throw new UnsupportedOperationException("Don't call via reflection!");
    }

    /**
     * Checks whether a character is inside a given character range (included).
     *
     * Throws IllegalArgumentException if end is less than start.
     *
     * @param character character to check
     * @param start including range
     * @param end including range
     * @return {@code true} if character is in range, unless {@code false}
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
     * @return {@code true} if character is a letter, unless {@code false}
     */
    public static boolean isAlpha(final char character) {
        return isCharInRange(character, 'a', 'z') || isCharInRange(character, 'A', 'Z');
    }

    /**
     * Checks whether a character is a number [0-9].
     *
     * @param character a single character
     * @return {@code true} if character is a number, unless {@code false}
     */
    public static boolean isNum(final char character) {
        return isCharInRange(character, '0', '9');
    }

    /**
     * Checks whether a character is a number or alpha [0-9a-zA-Z].
     *
     * @param character a single character
     * @return {@code true} if character is a letter or number, unless {@code false}
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
     * @return {@code true} if character is a whitespace character, unless {@code false}
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
     * @return {@code true} if character is a quote character, unless {@code false}
     */
    public static boolean isQuote(final char character) {
        return isSingleQuote(character) || isDoubleQuote(character);
    }

    /**
     * Checks whether a character is a single quote character.
     *
     * A single quotes is '.
     *
     * @param character a single character
     * @return {@code true} if character is a single quote character, unless {@code false}
     */
    public static boolean isSingleQuote(final char character) {
        return '\'' == character;
    }

    /**
     * Checks whether a character is a double quote character.
     *
     * A single quotes is ".
     *
     * @param character a single character
     * @return {@code true} if character is a double quote character, unless {@code false}
     */
    public static boolean isDoubleQuote(final char character) {
        return '"' == character;
    }

    /**
     * Checks whether a character is a special character.
     *
     * See {@link #SPECIAL_CHARS} for all allowed special characters.
     *
     * @param character a single character
     * @return {@code true} if character is a special character, unless {@code false}
     */
    public static boolean isSpecialChar(final char character) {
        return SPECIAL_CHARS.contains(character);
    }

    /**
     * Checks whether a character is a operator character.
     *
     * See {@link #OPERATORS} for all allowed operator characters.
     *
     * @param character a single character
     * @return {@code true} if character is a operator character, unless {@code false}
     */
    public static boolean isOperator(final char character) {
        return OPERATORS.contains(character);
    }

    /**
     * Checks whether a character is a sign character.
     *
     * See {@link #SIGN_CHARS} for all allowed operator characters.
     *
     * @param character a single character
     * @return {@code true} if character is a operator character, unless {@code false}
     */
    public static boolean isSign(final char character) {
        return SIGN_CHARS.contains(character);
    }

}
