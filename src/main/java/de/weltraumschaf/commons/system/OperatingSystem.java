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
package de.weltraumschaf.commons.system;

/**
 * Defines and determines the operating system the JVM runs on.
 *
 * Example:
 * <code>
 * OperatingSystem os = OperatingSystem.determine(System.getProperty("os.name", ""));
 * </code>
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
public enum OperatingSystem {

    /** Microsoft WIndows. */
    WINDOWS("win"),
    /** GNU/Linux. */
    LINUX("linux"),
    /** Mac OS X. */
    MACOSX("mac os x"),
    /** Unknown os. */
    UNKNOWN();

    /**
     * OS name.
     */
    private final String name;

    /**
     * Unknown OS name.
     */
    private OperatingSystem() {
        this("");
    }

    /**
     * Dedicated constructor.
     *
     * @param name the OS name
     */
    private OperatingSystem(final String name) {
        this.name = name;
    }

    /**
     * Get the OS name.
     *
     * @return OS name as string
     */
    public String getName() {
        return name;
    }

    /**
     * Inspect the given OS name property and determines the OS.
     *
     * @param osNameProperty property e.g. System.getProperty("os.name", "")
     * @return the best matching OS
     */
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
