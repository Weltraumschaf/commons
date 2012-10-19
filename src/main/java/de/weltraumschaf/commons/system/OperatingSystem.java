/*
 * LICENSE
 *
 * "THE BEER-WARE LICENSE" (Revision 43):
 * "Sven Strittmatter" <weltraumschaf(at)googlemail(dot)com> wrote this file.
 * As long as you retain this notice you can do whatever you want with
 * this stuff. If we meet some day, and you think this stuff is worth it,
 * you can buy me a non alcohol free beer in return.
 *
 * Copyright (C) 2012 "Sven Strittmatter" <weltraumschaf(at)googlemail(dot)com>
 */
package de.weltraumschaf.commons.system;

/**
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
public enum OperatingSystem {

    WINDOWS("win"),
    LINUX("linux"),
    MACOSX("mac os x"),
    UNKNOWN();

    private final String name;

    private OperatingSystem() {
        this("");
    }

    private OperatingSystem(final String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public static OperatingSystem determine(final String osNameProperty) {
        final String normalizedOsNameProperty = osNameProperty.toLowerCase();

        if (normalizedOsNameProperty.indexOf("linux") >= 0) {
            return LINUX;
        }

        if (normalizedOsNameProperty.indexOf("mac os x") >= 0) {
            return MACOSX;
        }

        if (normalizedOsNameProperty.indexOf("win") >= 0) {
            return WINDOWS;
        }

        return UNKNOWN;
    }

}
