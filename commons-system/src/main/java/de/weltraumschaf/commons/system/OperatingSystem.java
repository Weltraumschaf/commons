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

import de.weltraumschaf.commons.validate.Validate;

/**
 * Defines and determines the operating system the JVM runs on.
 *
 * <p>
 * Example:
 * </p>
 *
 * <pre>{@code
 * final String osName = System.getProperty(OperatingSystem.OS_SYSTEM_PROPERTY, "");
 * final OperatingSystem os = OperatingSystem.determine(osName);
 * }</pre>
 *
 * @author Sven Strittmatter &lt;weltraumschaf@googlemail.com&gt;
 */
public enum OperatingSystem {

    /**
     * Microsoft Windows.
     */
    WINDOWS("win"),
    /**
     * GNU/Linux.
     */
    LINUX("linux"),
    /**
     * Mac OS X.
     */
    MACOSX("mac os x"),
    /**
     * Unknown OS.
     */
    UNKNOWN();

    /**
     * Name to get OS name via {@link System#getProperty(java.lang.String)}.
     */
    public static final String OS_SYSTEM_PROPERTY = "os.name";

    /**
     * OS osName.
     */
    private final String osName;

    /**
     * Unknown OS osName.
     */
    private OperatingSystem() {
        this("");
    }

    /**
     * Dedicated constructor.
     *
     * @param osName must not be {@code null}
     */
    private OperatingSystem(final String osName) {
        this.osName = Validate.notNull(osName, "osName");
    }

    /**
     * Get the OS osName.
     *
     * @return OS osName as string
     */
    public String getName() {
        return osName;
    }

    /**
     * Inspect the given OS osName property and determines the OS.
     *
     * @param osNameProperty property e.g. System.getProperty("os.osName", ""), must not be {@code null}
     * @return the best matching OS
     */
    public static OperatingSystem determine(final String osNameProperty) {
        final String normalizedOsNameProperty = Validate.notNull(osNameProperty, osNameProperty).toLowerCase();

        if (normalizedOsNameProperty.contains(LINUX.osName)) {
            return LINUX;
        }

        if (normalizedOsNameProperty.contains(MACOSX.osName)) {
            return MACOSX;
        }

        if (normalizedOsNameProperty.contains(WINDOWS.osName)) {
            return WINDOWS;
        }

        return UNKNOWN;
    }

}
