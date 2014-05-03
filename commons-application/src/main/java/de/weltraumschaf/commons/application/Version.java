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
package de.weltraumschaf.commons.application;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Reads the current version from a property file.
 *
 * <p>
 * The property files only defines one property. Import is to switch resource filtering on in your POM
 * for that properties file.
 * </p>
 * <pre>
 *  version=1.0.0
 * </pre>
 *
 * <p>
 * This property file may be processed by a Maven filter to provide the verison from
 * the pom.xml:
 * </p>
 * <pre>
 *  version=${pom.version}
 * </pre>
 *
 * <p>
 * If you save the file in <kbd>src/main/resources/foo/bar/version.properties</kbd>
 * you can use the version this way:
 * </p>
 *
 * <pre>{@code
 * Version version = new Version("/foo/bar/version.properties");
 * version.load()
 * println(version.getVersion);
 * }</pre>
 *
 * @since 1.0.0
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
public final class Version {

    /**
     * Location of property file.
     */
    private final String propertyFileName;

    /**
     * Indicates whether the properties are already loaded or not.
     */
    private boolean propertiesLoaded;

    /**
     * Properties.
     */
    private final Properties properties = new Properties();

    /**
     * Private constructor for singleton.
     *
     * @param propertyFileName Location of file, e.g. /de/weltraumschaf/commons/version.properties.
     */
    public Version(final String propertyFileName) {
        super();
        this.propertyFileName = propertyFileName;
    }

    /**
     * Opens the properties file and loads it.
     *
     * Only loads th property file once.
     *
     * @throws IOException On IO errors of the property file.
     */
    public void load() throws IOException {
        if (!propertiesLoaded) {
            loadImpl();
            propertiesLoaded = true;
        }
    }

    /**
     * Encapsulates the file loading.
     *
     * @throws IOException On IO errors of the property file.
     */
    void loadImpl() throws IOException {
        InputStream in = null;

        try {
            in = getClass().getResourceAsStream(propertyFileName);
            properties.load(in);
            in.close();
        } finally {
            if (null != in) {
                in.close();
            }
        }
    }

    /**
     * Get the version string.
     *
     * @return The version string.
     */
    public String getVersion() {
        return properties.getProperty(PropertyNames.VERSION.toString(),
                                      PropertyNames.VERSION.getDefaultValue());
    }

    /**
     * Returns the version string.
     *
     * @return Same as {@link #getVersion()}.
     */
    @Override
    public String toString() {
        return getVersion();
    }

    /**
     * Available properties in the file.
     */
    enum PropertyNames {

        /**
         * Version property.
         */
        VERSION("version", "n/a");

        /**
         * Property name.
         */
        private final String name;

        /**
         * Default value.
         */
        private final String defaultValue;

        /**
         * Initializes the enum with the property name.
         *
         * @param name The property name.
         * @param defaultValue Fall back value.
         */
        PropertyNames(final String name, final String defaultValue) {
            this.name = name;
            this.defaultValue = defaultValue;
        }

        /**
         * Returns the property name.
         *
         * @return String containing the property name.
         */
        @Override
        public String toString() {
            return name;
        }

        /**
         * Get the fallback value.
         *
         * @return Return default value string.
         */
        public String getDefaultValue() {
            return defaultValue;
        }

    }
}
