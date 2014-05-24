/*
 *  LICENSE
 *
 * "THE BEER-WARE LICENSE" (Revision 43):
 * "Sven Strittmatter" <weltraumschaf@googlemail.com> wrote this file.
 * As long as you retain this notice you can do whatever you want with
 * this stuff. If we meet some day, and you think this stuff is worth it,
 * you can buy me a non alcohol-free beer in return.
 *
 * Copyright (C) 2012 "Sven Strittmatter" <weltraumschaf@googlemail.com>
 */
package de.weltraumschaf.commons.string;

import de.weltraumschaf.commons.validate.Validate;

/**
 * Tools to escape strings.
 *
 * @since 1.0.0
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
public final class StringEscape {

    /**
     * Hidden for pure static class.
     */
    private StringEscape() {
        super();
        throw new UnsupportedOperationException("Do not call by reflection!");
    }

    /**
     * Escapes string for XML.
     *
     * See https://en.wikipedia.org/wiki/List_of_XML_and_HTML_character_entity_references#Predefined_entities_in_XML
     *
     * @param input must not be null
     * @return never {@code null}
     */
    public static String escapeXml(final String input) {
        return Validate.notNull(input, "Parameter 'input' must not be null!")
                .replace("&", "&amp;")
                .replace("<", "&lt;")
                .replace(">", "&gt;")
                .replace("'", "&apos;")
                .replace("\"", "&quot;");
    }

    /**
     * Escapes a string that i is usable as file name.
     * <p>
     * Escaped characters and there replacements:
     * </p>
     *
     * <ul>
     * <li>{@literal ' '} becomes {@literal '_'}</li>
     * <li>{@literal '/'} becomes {@literal '_'}</li>
     * <li>{@literal '\'} becomes {@literal '_'}</li>
     * <li>{@literal ':'} becomes {@literal '_'}</li>
     * </ul>
     *
     * @param input must not be {@code null} or empty
     * @return never {@code null} or empty
     */
    public static String escapeFileName(final String input) {
        return Validate.notEmpty(input, "Parameter 'input' must not be null!")
                .replace(" ", "_")
                .replace("/", "_")
                .replace("\\", "_")
                .replace(":", "_");
    }
}
