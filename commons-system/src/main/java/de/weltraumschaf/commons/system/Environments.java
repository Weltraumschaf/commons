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
package de.weltraumschaf.commons.system;

import de.weltraumschaf.commons.validate.Validate;

/**
 * Provides instances to access environment variables.
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
public final class Environments {

    /**
     * Hidden for static factory.
     */
    private Environments() {
        super();
        throw new UnsupportedOperationException("Constructor must not be called by reflection!");
    }

    /**
     * Get a default implementation.
     *
     * @return never {@code null}, always new instance
     */
    public static Env defaultEnv() {
        return new DefaultEnv();
    }

    /**
     * Implementations provide access to read environment variables.
     */
    public interface Env {

        /**
         * Get a environment variable.
         *
         * @param name must not be {@code null} or empty
         * @return never {@code null}, maybe empty
         */
        String get(String name);

        /**
         * Get a environment variable.
         *
         * @deprecated Will be remove in next version.
         * @param name must not be {@code null}
         * @return never {@code null}, maybe empty
         */
        @Deprecated
        String get(Names name);

        /**
         * Get a environment variable.
         *
         * @since 1.1.0
         * @param name must not be {@code null} or empty
         * @param fallback must not be {@code null}
         * @return never {@code null}, maybe empty
         */
        String get(String name, String fallback);

    }

    /**
     * Default implementation which uses {@link System#getenv(java.lang.String)}.
     */
    private static final class DefaultEnv implements Env {

        @Override
        public String get(final String name) {
            return get(name, "");
        }

        @Override
        public String get(final Names name) {
            return get(name.getPropertyName());
        }

        @Override
        public String get(final String name, final String fallback) {
            Validate.notEmpty(name, "name");
            Validate.notNull(fallback, "fallback");

            final String var = System.getenv(name);

            return var == null ? fallback : var;
        }

    }

    /**
     * Some common names provided by the JVM.
     *
     * FIXME Remove this. Has nothing to do with env. Are system properties.
     * @deprecated Will be remove in next version.
     */
    @Deprecated
    public static enum Names {

        /**
         * Character that separates components of a file path.
         * <p>
         * This is “/” on UNIX and “\” on Windows.
         * </p>
         */
        FILE_SEPARATOR("file.separator"),
        /**
         * Path used to find directories and JAR archives containing class files.
         * <p>
         * Elements of the class path are separated by a platform-specific character specified in the path.separator
         * property.
         * </p>
         */
        CLASS_PATH("java.class.path"),
        /**
         * Installation directory for Java Runtime Environment (JRE).
         */
        JAVA_HOME("java.home"),
        /**
         * JRE vendor name.
         */
        JAVA_VENDOR("java.vendor"),
        /**
         * JRE vendor URL.
         */
        JAVA_VENDOR_URL("java.vendor.url"),
        /**
         * JRE version number.
         */
        JAVA_VERSION("java.version"),
        /**
         * Sequence used by operating system to separate lines in text files.
         */
        LINE_SEPARATOR("line.separator"),
        /**
         * Operating system architecture.
         */
        OS_ARCH("os.arch"),
        /**
         * Operating system name.
         */
        OS_NAME("os.name"),
        /**
         * Operating system version.
         */
        OS_VERSION("os.version"),
        /**
         * Path separator character used in java.class.path.
         */
        OS_PATH_SEPARATOR("path.separator"),
        /**
         * User working directory.
         */
        USER_DIR("user.dir"),
        /**
         * User home directory.
         */
        USER_HOME("user.home"),
        /**
         * User account name.
         */
        USER_NAME("user.name");

        /**
         * The property name.
         */
        private final String propertyName;

        /**
         * Dedicated constructor.
         *
         * @param propertyName must not be {@code null} or empty.
         */
        private Names(final String propertyName) {
            this.propertyName = Validate.notEmpty(propertyName, "propertyName");
        }

        /**
         * Get the literal property name.
         *
         * @return never {@code null} or empty
         */
        public String getPropertyName() {
            return propertyName;
        }

        @Override
        public String toString() {
            return getPropertyName();
        }

    }
}
