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

/**
 * Factory to create parsers.
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
public final class Parsers {

    /**
     * Hide constructor for factories sake.
     */
    private Parsers() {
        super();
    }

    /**
     * Creates a new parser with default scanner implementation.
     *
     * @return new instance
     */
    public static Parser newParser() {
        return newParser(Scanners.newScanner());
    }

    /**
     * Create new parser with custom scanner.
     *
     * @param s used to tokenize input
     * @return new instance
     */
    public static Parser newParser(final Scanner s) {
        return new DefaultParser(s);
    }

}
