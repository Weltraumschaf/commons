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
package de.weltraumschaf.commons.system;

import de.weltraumschaf.commons.validate.Validate;

/**
 * Provides instances to access environment variables.
 *
 * @since 1.0.0
 * @author Sven Strittmatter &lt;weltraumschaf@googlemail.com&gt;
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
         * @param name must not be {@code null}
         * @return never {@code null}, maybe empty
         */
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

        /**
         * Get a environment variable.
         *
         * @since 2.0.0
         * @param name must not be {@code null} or empty
         * @param fallback must not be {@code null}
         * @return never {@code null}, maybe empty
         */
        String get(Names name, String fallback);

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
            return get(name.getName());
        }

        @Override
        public String get(final String name, final String fallback) {
            Validate.notEmpty(name, "name");
            Validate.notNull(fallback, "fallback");

            final String var = System.getenv(name);

            return var == null ? fallback : var;
        }

        @Override
        public String get(final Names name, final String fallback) {
            return get(name.getName(), fallback);
        }

    }

    /**
     * Some common environment variable names.
     * <p>
     * These environment variables need not be defined necessarily.
     * </p>
     *
     * @since 2.0.0
     */
    public enum Names {

        /**
         * Maven home directory such as {@code /Users/johndoe/.m2}.
         */
        M2("M2"),
        /**
         * Maven repository directory such as {@code /Users/johndoe/.m2/repository}.
         */
        M2_REPO("M2_REPO"),
        /**
         * Maven options.
         */
        MAVEN_OPTS("MAVEN_OPTS"),
        /**
         * Home directory of Java.
         */
        JAVA_HOME("JAVA_HOME"),
        /**
         * Systems CLI pager (e.g. more/less).
         */
        PAGER("PAGER"),
        /**
         * Current user.
         */
        USER("USER"),
        /**
         * Home directory of current user.
         */
        HOME("HOME"),
        /**
         * Path variable of current user.
         */
        PATH("PATH"),
        /**
         * Current working directory.
         */
        PWD("PWD"),
        /**
         * Language setting of current user.
         */
        LANGUAGE("LANGUAGE"),
        /**
         * Lang setting of current user.
         */
        LANG("LANG"),
        /**
         * Default shell of current user.
         */
        SHELL("SHELL"),
        /**
         * Default terminal of current user.
         */
        TERM("TERM"),
        /**
         * Temporary directory.
         */
        TMPDIR("TMPDIR");

        /**
         * The property name.
         */
        private final String envVarName;

        /**
         * Dedicated constructor.
         *
         * @param envVarName must not be {@code null} or empty.
         */
        private Names(final String envVarName) {
            this.envVarName = Validate.notEmpty(envVarName, "envVarName");
        }

        /**
         * Get the literal property name.
         *
         * @return never {@code null} or empty
         */
        public String getName() {
            return envVarName;
        }

        @Override
        public String toString() {
            return getName();
        }

    }
}
