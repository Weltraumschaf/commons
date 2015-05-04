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

import static org.junit.Assert.*;
import org.junit.Test;

/**
 *
 * @author Sven Strittmatter &lt;weltraumschaf@googlemail.com&gt;
 */
public class CharacterHelperTest {

    private static final char[] LOW_ALPHA = {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm',
        'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', };
    private static final char[] UP_ALPHA = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M',
        'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', };
    private static final char[] NUMS = {'1', '2', '3', '4', '5', '6', '7', '8', '9', '0', };
    private static final char[] WHITE_SPACES = {' ', '\n', '\r', '\t', };
    private static final char[] OPERATORS = {'+', '-', '*', '/', '%', '&', '|', '^', '<', '>', '=',
        ':', '?', '(', ')', '{', '}', '[', ']', '!', '~', '@', '#', '$', };

    @Test
    public void isAlpha() {
        for (final char c : LOW_ALPHA) {
            assertTrue(CharacterHelper.isAlpha(c));
        }

        for (final char c : UP_ALPHA) {
            assertTrue(CharacterHelper.isAlpha(c));
        }

        for (final char c : NUMS) {
            assertFalse(CharacterHelper.isAlpha(c));
        }

        for (final char c : WHITE_SPACES) {
            assertFalse(CharacterHelper.isAlpha(c));
        }

        for (final char c : OPERATORS) {
            assertFalse(CharacterHelper.isDoubleQuote(c));
        }
    }

    @Test
    public void isNum() {
        for (final char c : NUMS) {
            assertTrue(CharacterHelper.isNum(c));
        }

        for (final char c : LOW_ALPHA) {
            assertFalse(CharacterHelper.isNum(c));
        }

        for (final char c : UP_ALPHA) {
            assertFalse(CharacterHelper.isNum(c));
        }

        for (final char c : WHITE_SPACES) {
            assertFalse(CharacterHelper.isNum(c));
        }

        for (final char c : OPERATORS) {
            assertFalse(CharacterHelper.isDoubleQuote(c));
        }
    }

    @Test
    public void isAlphaNum() {
        for (final char c : NUMS) {
            assertTrue(CharacterHelper.isAlphaNum(c));
        }

        for (final char c : LOW_ALPHA) {
            assertTrue(CharacterHelper.isAlphaNum(c));
        }

        for (final char c : UP_ALPHA) {
            assertTrue(CharacterHelper.isAlphaNum(c));
        }

        for (final char c : WHITE_SPACES) {
            assertFalse(CharacterHelper.isAlphaNum(c));
        }

        for (final char c : OPERATORS) {
            assertFalse(CharacterHelper.isDoubleQuote(c));
        }
    }

    @Test
    public void isWhiteSpace() {
        for (final char c : WHITE_SPACES) {
            assertTrue(CharacterHelper.isWhiteSpace(c));
        }

        for (final char c : NUMS) {
            assertFalse(CharacterHelper.isWhiteSpace(c));
        }

        for (final char c : LOW_ALPHA) {
            assertFalse(CharacterHelper.isWhiteSpace(c));
        }

        for (final char c : UP_ALPHA) {
            assertFalse(CharacterHelper.isWhiteSpace(c));
        }

        for (final char c : OPERATORS) {
            assertFalse(CharacterHelper.isDoubleQuote(c));
        }
    }

    @Test
    public void isCharInRange() {
        assertTrue(CharacterHelper.isCharInRange('c', 'a', 'f'));
        assertTrue(CharacterHelper.isCharInRange('3', '1', '5'));
        assertFalse(CharacterHelper.isCharInRange('b', 'f', 'z'));
        assertFalse(CharacterHelper.isCharInRange('3', '7', '9'));

        try {
            CharacterHelper.isCharInRange('c', 'f', 'a');
            fail("Expected exception not thrown!");
        } catch (IllegalArgumentException ex) {
            assertEquals("End must be greater or equal than start!", ex.getMessage());
        }
    }

    @Test
    public void isQuote() {
        assertTrue(CharacterHelper.isQuote('"'));
        assertTrue(CharacterHelper.isQuote('\''));

        for (final char c : WHITE_SPACES) {
            assertFalse(CharacterHelper.isQuote(c));
        }

        for (final char c : NUMS) {
            assertFalse(CharacterHelper.isQuote(c));
        }

        for (final char c : LOW_ALPHA) {
            assertFalse(CharacterHelper.isQuote(c));
        }

        for (final char c : UP_ALPHA) {
            assertFalse(CharacterHelper.isQuote(c));
        }

        for (final char c : OPERATORS) {
            assertFalse(CharacterHelper.isDoubleQuote(c));
        }
    }

    @Test
    public void isSingleQuote() {
        assertTrue(CharacterHelper.isSingleQuote('\''));

        for (final char c : WHITE_SPACES) {
            assertFalse(CharacterHelper.isSingleQuote(c));
        }

        for (final char c : NUMS) {
            assertFalse(CharacterHelper.isSingleQuote(c));
        }

        for (final char c :LOW_ALPHA) {
            assertFalse(CharacterHelper.isSingleQuote(c));
        }

        for (final char c : UP_ALPHA) {
            assertFalse(CharacterHelper.isSingleQuote(c));
        }

        for (final char c : OPERATORS) {
            assertFalse(CharacterHelper.isDoubleQuote(c));
        }
    }

    @Test
    public void isDoubleQuote() {
        assertTrue(CharacterHelper.isDoubleQuote('"'));

        for (final char c : WHITE_SPACES) {
            assertFalse(CharacterHelper.isDoubleQuote(c));
        }

        for (final char c : NUMS) {
            assertFalse(CharacterHelper.isDoubleQuote(c));
        }

        for (final char c : LOW_ALPHA) {
            assertFalse(CharacterHelper.isDoubleQuote(c));
        }

        for (final char c : UP_ALPHA) {
            assertFalse(CharacterHelper.isDoubleQuote(c));
        }

        for (final char c : OPERATORS) {
            assertFalse(CharacterHelper.isDoubleQuote(c));
        }
    }

    @Test
    public void isSpecialChar() {
        assertTrue(CharacterHelper.isSpecialChar('-'));
        assertTrue(CharacterHelper.isSpecialChar('/'));
        assertTrue(CharacterHelper.isSpecialChar('\\'));
    }

    @Test
    public void isOperator() {
        for (final char c : OPERATORS) {
            assertTrue(CharacterHelper.isOperator(c));
        }

        for (final char c : LOW_ALPHA) {
            assertFalse(CharacterHelper.isOperator(c));
        }

        for (final char c : UP_ALPHA) {
            assertFalse(CharacterHelper.isOperator(c));
        }

        for (final char c : NUMS) {
            assertFalse(CharacterHelper.isOperator(c));
        }

        for (final char c : WHITE_SPACES) {
            assertFalse(CharacterHelper.isOperator(c));
        }
    }

    @Test
    public void isSign() {
        assertTrue(CharacterHelper.isSign('+'));
        assertTrue(CharacterHelper.isSign('-'));

        for (final char c : OPERATORS) {
            if (c == '+' || c == '-') {
                continue;
            }
            assertFalse(CharacterHelper.isSign(c));
        }

        for (final char c : LOW_ALPHA) {
            assertFalse(CharacterHelper.isSign(c));
        }

        for (final char c : UP_ALPHA) {
            assertFalse(CharacterHelper.isSign(c));
        }

        for (final char c : NUMS) {
            assertFalse(CharacterHelper.isSign(c));
        }

        for (final char c : WHITE_SPACES) {
            assertFalse(CharacterHelper.isSign(c));
        }
    }
}
