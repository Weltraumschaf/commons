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

package de.weltraumschaf.config;

import de.weltraumschaf.commons.validate.Validate;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.util.Properties;

/**
 * This configuration is a Java properties file based configuration, which reloads its content
 * if the properties file has changed.
 * <p>
 * This configuration does not need you to force an application restart, if you changed the
 * properties file.
 * </p>
 * <p>
 * Based on this <a href"http://www.javacodegeeks.com/2014/10/dead-simple-configuration.html">blog post</a>.
 * </p>
 * <p>
 * Example:
 * </p>
 * <pre>
 * {@code
 * final class MyAppConfig extends ReloadingPropertiesConfiguration {
 *
 *     public MyAppConfig(final File file) {
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
 *
 * @since 1.1.0
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
public abstract class ReloadingPropertiesConfiguration {

    private final Properties properties = new Properties();
    private final File configFile;
    private long lastLoad = -1L;

    public ReloadingPropertiesConfiguration(final String filename) {
        this(new File(filename));
    }

    public ReloadingPropertiesConfiguration(final Path file) {
        this(file.toFile());
    }

    public ReloadingPropertiesConfiguration(final File file) {
        super();
        this.configFile = Validate.notNull(file, "file");
    }

    public final String getProperty(final String propertyName, final String defaultValue) {
        Validate.notNull(defaultValue, "defaultValue");
        final String result = getProperty(propertyName);

        if (result == null) {
            return defaultValue;
        }

        return result;
    }

    public final String getRequiredProperty(final String propertyName) {
        final String result = getProperty(propertyName);

        if (result == null) {
            throw new RuntimeException(String.format("Missing property with name '%s'!", propertyName));
        }

        return result;
    }

    public final boolean getFlag(final String propertyName, final boolean defaultValue) {
        return Boolean.parseBoolean(getProperty(propertyName, String.valueOf(defaultValue)));
    }

    public final int getInteger(final String propertyName, final int defaultValue) {
        return Integer.parseInt(getProperty(propertyName, String.valueOf(defaultValue)), 10);
    }

    private String getProperty(final String propertyName) {
        ensureConfigurationIsFresh();
        return properties.getProperty(Validate.notEmpty(propertyName, "propertyName"));
    }

    private synchronized void ensureConfigurationIsFresh() {
        final long lastModified = configFile.lastModified();

        if (lastModified <= lastLoad) {
            return;
        }

        try (final InputStream inputStream = new FileInputStream(configFile)) {
            properties.clear();
            lastLoad = System.currentTimeMillis();
            properties.load(inputStream);
        } catch (final IOException ex) {
            throw new RuntimeException(String.format("Failed to load configuration file '%s'!", configFile), ex);
        }
    }
}
