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
package de.weltraumschaf.commons.token;

/**
 * Type of tokens scanned from interactive shell.
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
public enum TokenType {

    /**
     * A command or subcommand literal.
     *
     * Any literal starting with a alphabetical character: keyword = literal .
     */
    KEYWORD,
    /**
     * Defines a quote delimited string.
     *
     * Any characters between matching quotes:
     * <pre>
     * string = ''' any-character { any-character } '''
     *        | '"' any-character { any-character } '"' .
     * </pre>
     */
    STRING,
    /**
     * Defines literal string token type.
     *
     * Any literal starting with a alphabetical character:
     * <pre>
     * literal = a..Z { a..Z | 0..9] } .
     * </pre>
     */
    LITERAL,
    /**
     * Defines integer token type.
     *
     * Any literal only containing digits: number = 0..9 { 0..9 } .
     */
    NUMBER,
    /**
     * Defines floating point number token type.
     *
     * Any literal numbers and .
     * <pre>
     * float = [ 0..9 ] '.' { 0..9 } .
     * </pre>
     */
    FLOAT,
    /**
     * Defines boolean token type.
     */
    BOOLEAN;

}
