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
 * @since 1.0.0
 * @author Sven Strittmatter &lt;weltraumschaf@googlemail.com&gt;
 * @version $Id: $Id
 */
public final class Parsers {

    /**
     * Hide constructor for factories sake.
     */
    private Parsers() {
        super();
        throw new UnsupportedOperationException("Constructor must not be called by reflection!");
    }

    /**
     * Creates a new parser with default scanner implementation and {@link de.weltraumschaf.commons.shell.NullCommandVerifier}.
     *
     * @param m map key word literals to command types
     * @return new instance
     */
    public static Parser newParser(final LiteralCommandMap m) {
        return newParser(Scanners.newScanner(m), m);
    }

    /**
     * Create new parser with custom scanner and {@link de.weltraumschaf.commons.shell.NullCommandVerifier}.
     *
     * @param s used to tokenize input
     * @param m map key word literals to command types
     * @return new instance
     */
    public static Parser newParser(final Scanner s, final LiteralCommandMap m) {
        return newParser(s, new NullCommandVerifier(), m);
    }

    /**
     * Creates parser with {@link de.weltraumschaf.commons.shell.DefaultScanner}.
     *
     * @param v verifies parsed commands
     * @param m map key word literals to command types
     * @return new instance
     */
    public static Parser newParser(final CommandVerifier v, final LiteralCommandMap m) {
        return newParser(Scanners.newScanner(m), v, m);
    }

    /**
     * Creates parser.
     *
     * @param s used to tokenize input
     * @param v verifies parsed commands
     * @param m map key word literals to command types
     * @return new instance
     */
    public static Parser newParser(final Scanner s, final CommandVerifier v, final LiteralCommandMap m) {
        return new DefaultParser(s, v, m);
    }
}
