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
package de.weltraumschaf.commons.shell;

import static org.junit.Assert.*;
import org.junit.Test;

/**
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
public class CharacterHelperTest {

    private static final char[] LOW_ALPHA = {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm',
        'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', };
    private static final char[] UP_ALPHA = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M',
        'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', };
    private static final char[] NUMS = {'1', '2', '3', '4', '5', '6', '7', '8', '9', '0', };
    private static final char[] WHITE_SPACES = {' ', '\n', '\r', '\t', };

    @Test
    public void isAlpha() {
        for (int i = 0; i < LOW_ALPHA.length; ++i) {
            assertTrue(CharacterHelper.isAlpha(LOW_ALPHA[i]));
        }

        for (int i = 0; i < UP_ALPHA.length; ++i) {
            assertTrue(CharacterHelper.isAlpha(UP_ALPHA[i]));
        }

        for (int i = 0; i < NUMS.length; ++i) {
            assertFalse(CharacterHelper.isAlpha(NUMS[i]));
        }

        for (int i = 0; i < WHITE_SPACES.length; ++i) {
            assertFalse(CharacterHelper.isAlpha(WHITE_SPACES[i]));
        }
    }

    @Test
    public void isNum() {
        for (int i = 0; i < NUMS.length; ++i) {
            assertTrue(CharacterHelper.isNum(NUMS[i]));
        }

        for (int i = 0; i < LOW_ALPHA.length; ++i) {
            assertFalse(CharacterHelper.isNum(LOW_ALPHA[i]));
        }

        for (int i = 0; i < UP_ALPHA.length; ++i) {
            assertFalse(CharacterHelper.isNum(UP_ALPHA[i]));
        }

        for (int i = 0; i < WHITE_SPACES.length; ++i) {
            assertFalse(CharacterHelper.isNum(WHITE_SPACES[i]));
        }
    }

    @Test
    public void isAlphaNum() {
        for (int i = 0; i < NUMS.length; ++i) {
            assertTrue(CharacterHelper.isAlphaNum(NUMS[i]));
        }

        for (int i = 0; i < LOW_ALPHA.length; ++i) {
            assertTrue(CharacterHelper.isAlphaNum(LOW_ALPHA[i]));
        }

        for (int i = 0; i < UP_ALPHA.length; ++i) {
            assertTrue(CharacterHelper.isAlphaNum(UP_ALPHA[i]));
        }

        for (int i = 0; i < WHITE_SPACES.length; ++i) {
            assertFalse(CharacterHelper.isAlphaNum(WHITE_SPACES[i]));
        }
    }

    @Test
    public void isWhiteSpace() {
        for (int i = 0; i < WHITE_SPACES.length; ++i) {
            assertTrue(CharacterHelper.isWhiteSpace(WHITE_SPACES[i]));
        }

        for (int i = 0; i < NUMS.length; ++i) {
            assertFalse(CharacterHelper.isWhiteSpace(NUMS[i]));
        }

        for (int i = 0; i < LOW_ALPHA.length; ++i) {
            assertFalse(CharacterHelper.isWhiteSpace(LOW_ALPHA[i]));
        }

        for (int i = 0; i < UP_ALPHA.length; ++i) {
            assertFalse(CharacterHelper.isWhiteSpace(UP_ALPHA[i]));
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

        for (int i = 0; i < WHITE_SPACES.length; ++i) {
            assertFalse(CharacterHelper.isQuote(WHITE_SPACES[i]));
        }

        for (int i = 0; i < NUMS.length; ++i) {
            assertFalse(CharacterHelper.isQuote(NUMS[i]));
        }

        for (int i = 0; i < LOW_ALPHA.length; ++i) {
            assertFalse(CharacterHelper.isQuote(LOW_ALPHA[i]));
        }

        for (int i = 0; i < UP_ALPHA.length; ++i) {
            assertFalse(CharacterHelper.isQuote(UP_ALPHA[i]));
        }
    }

    @Test
    public void isSpecialChar() {
        assertTrue(CharacterHelper.isSpecialChar('-'));
        assertTrue(CharacterHelper.isSpecialChar('/'));
        assertTrue(CharacterHelper.isSpecialChar('\\'));
    }
}
