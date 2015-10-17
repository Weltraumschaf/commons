/*
 *  LICENSE
 *
 * "THE BEER-WARE LICENSE" (Revision 43):
 * "Sven Strittmatter" &lt;weltraumschaf@googlemail.com&gt; wrote this file.
 * As long as you retain this notice you can do whatever you want with
 * this stuff. If we meet some day, and you think this stuff is worth it,
 * you can buy me a non alcohol-free beer in return.
 *
 * Copyright (C) 2012 "Sven Strittmatter" &lt;weltraumschaf@googlemail.com&gt;
 */
package de.weltraumschaf.commons.config;

import de.weltraumschaf.commons.validate.Validate;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Properties;

/**
 * This configuration is a Java properties file based configuration, which reloads its content if the properties file
 * has changed.
 * <p>
 * This configuration does not need you to force an application restart, if you changed the properties file.
 * </p>
 * <p>
 * Based on this <a href="http://www.javacodegeeks.com/2014/10/dead-simple-configuration.html">blog post</a>.
 * </p>
 * <p>
 * Example:
 * </p>
 * <pre>
 * {@code
 * final class MyAppConfig extends ReloadingPropertiesConfiguration {
 *
 *     public MyAppConfig(final Path file) {
 *         super(file);
 *     }
 *
 *     public String getServiceUrl() {
 *          return getRequiredProperty("service.url");
 *     }
 *
 *     public boolean getShouldStartSlow() {
 *          return getFlag("start-slow", false);
 *     }
 *
 *     public int getHttpPort(final int defaultPort) {
 *          return getInteger("myapp.http.port", defaultPort);
 *     }
 *
 * }
 * }
 * </pre>
 * <p>
 * This class is thread safe because the method reading the file is synchronized.
 * </p>
 *
 * @since 1.1.0
 * @author Sven Strittmatter &lt;weltraumschaf@googlemail.com&gt;
 */
public abstract class ReloadingPropertiesConfiguration {

    /**
     * Holds the configuration values.
     */
    private final Properties properties = new Properties();
    /**
     * Where the configuration is loaded from.
     */
    private final Path configFile;
    /**
     * Time in milliseconds.
     * <p>
     * Initial {@code -1} so that file will be read anyway on first property access.
     * </p>
     */
    private long lastLoad = -1L;

    /**
     * Convenience constructor for file name as string.
     *
     * @param filename must not be {@code null} or empty
     */
    public ReloadingPropertiesConfiguration(final String filename) {
        this(new File(Validate.notEmpty(filename, "filename")));
    }

    /**
     * Convenience constructor for file.
     *
     * @param file must not be {@code null}
     */
    public ReloadingPropertiesConfiguration(final File file) {
        this(file.toPath());
    }

    /**
     * Dedicated constructor.
     *
     * @param file must not be {@code null}
     */
    public ReloadingPropertiesConfiguration(final Path file) {
        super();
        this.configFile = Validate.notNull(file, "file");
    }

    /**
     * Get a property by name.
     * <p>
     * If the property does not exist then the default value will be returned.
     * </p>
     *
     * @param propertyName must not be {@code null} or empty
     * @param defaultValue must not be {@code null}
     * @return never {@code null}
     */
    public final String getProperty(final String propertyName, final String defaultValue) {
        Validate.notNull(defaultValue, "defaultValue");
        final String result = getProperty(propertyName);

        if (result == null) {
            return defaultValue;
        }

        return result;
    }

    /**
     * Get a property by name.
     * <p>
     * Throws a {@link java.lang.RuntimeException} if property does not exists.
     * </p>
     *
     * @param propertyName must not be {@code null} or empty
     * @return never {@code null}
     */
    public final String getRequiredProperty(final String propertyName) {
        final String result = getProperty(propertyName);

        if (result == null) {
            throw new RuntimeException(String.format("Missing property with name '%s'!", propertyName));
        }

        return result;
    }

    /**
     * Get a boolean property by name.
     * <p>
     * This method calls {@link #getProperty(java.lang.String, java.lang.String)}. The result will be
     * passed through {@link java.lang.Boolean#parseBoolean(java.lang.String)}.
     * </p>
     *
     * @param propertyName must not be {@code null} or empty
     * @param defaultValue must not be {@code null}
     * @return never {@code null}
     */
    public final boolean getFlag(final String propertyName, final boolean defaultValue) {
        return Boolean.parseBoolean(getProperty(propertyName, String.valueOf(defaultValue)));
    }

    /**
     * Get a integer property by name.
     * <p>
     * This method calls {@link #getProperty(java.lang.String, java.lang.String)}. The result will be
     * passed through {@link java.lang.Integer#parseInt(java.lang.String, int)} with radix of {@code 10}.
     * </p>
     *
     * @param propertyName must not be {@code null} or empty
     * @param defaultValue must not be {@code null}
     * @return never {@code null}
     */
    public final int getInteger(final String propertyName, final int defaultValue) {
        return Integer.parseInt(getProperty(propertyName, String.valueOf(defaultValue)));
    }

    /**
     * Get the property from the underlying object and ensures it is fresh loaded.
     *
     * @param propertyName must not be {@code null} or empty
     * @return never {@code null}
     */
    private String getProperty(final String propertyName) {
        ensureConfigurationIsFresh();
        return properties.getProperty(Validate.notEmpty(propertyName, "propertyName"));
    }

    /**
     * Reads the property file from disk if the files modification date is after the last one read.
     */
    private synchronized void ensureConfigurationIsFresh() {
        try {
            final long lastModified = Files.getLastModifiedTime(configFile).toMillis();

            if (lastLoad > lastModified) {
                return;
            }

            lastLoad = lastModified;

            try (final InputStream inputStream = new FileInputStream(configFile.toFile())) {
                properties.clear();
                properties.load(inputStream);
            }
        } catch (final IOException ex) {
            throw new RuntimeException(String.format("Failed to load configuration file '%s'!", configFile), ex);
        }
    }
}
