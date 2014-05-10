/*
 * Copyright (C) 2007 The Guava Authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package de.weltraumschaf.commons.guava;

import de.weltraumschaf.commons.validate.Validate;
import java.util.Arrays;

/**
 * Helper functions that can operate on any {@code Object}.
 *
 * <p>
 * See the Guava User Guide on <a
 * href="http://code.google.com/p/guava-libraries/wiki/CommonObjectUtilitiesExplained">writing {@code Object} methods
 * with {@code Objects}</a>.
 *
 * @author Laurence Gonsalves
 * @since 2.0 (imported from Google Collections Library)
 */
public final class Objects {

    /**
     * Hidden for pure static factory.
     */
    private Objects() {
        super();
        throw new UnsupportedOperationException("Constructor must not be called by reflection!");
    }

    /**
     * Determines whether two possibly-null objects are equal. Returns:
     *
     * <ul>
     * <li>{@code true} if {@code a} and {@code b} are both null.
     * <li>{@code true} if {@code a} and {@code b} are both non-null and they are equal according to
     * {@link Object#equals(Object)}.
     * <li>{@code false} in all other situations.
     * </ul>
     *
     * <p>
     * This assumes that any non-null objects passed to this function conform to the {@code equals()} contract.
     *
     * @param a first object to compare
     * @param b second object to compare
     * @return {@code true} if a and be are equal, else {@code false}
     */
    public static boolean equal(final Object a, final Object b) {
        return a == b || a != null && a.equals(b);
    }

    /**
     * Generates a hash code for multiple values. The hash code is generated by calling
     * {@link Arrays#hashCode(Object[])}. Note that array arguments to this method, with the exception of a single
     * Object array, do not get any special handling; their hash codes are based on identity and not contents.
     *
     * <p>
     * This is useful for implementing {@link Object#hashCode()}. For example, in an object that has three properties,
     * {@code x}, {@code y}, and {@code z}, one could write:
     * <pre>   {@code
     *   public int hashCode() {
     *     return Objects.hashCode(getX(), getY(), getZ());
     *   }}</pre>
     *</p>
     *
     * <p>
     * <strong>Warning</strong>: When a single object is supplied, the returned hash code does not equal the hash code
     * of that object.
     * </p>
     *
     * @param objects objects to hash
     * @return the has code
     */
    public static int hashCode(final Object... objects) {
        return Arrays.hashCode(objects);
    }

    /**
     * Creates an instance of {@link ToStringHelper}.
     *
     * <p>
     * This is helpful for implementing {@link Object#toString()}. Specification by example:
     * <pre>   {@code
     *   // Returns "ClassName{}"
     *   Objects.toStringHelper(this)
     *       .toString();
     *
     *   // Returns "ClassName{x=1}"
     *   Objects.toStringHelper(this)
     *       .add("x", 1)
     *       .toString();
     *
     *   // Returns "MyObject{x=1}"
     *   Objects.toStringHelper("MyObject")
     *       .add("x", 1)
     *       .toString();
     *
     *   // Returns "ClassName{x=1, y=foo}"
     *   Objects.toStringHelper(this)
     *       .add("x", 1)
     *       .add("y", "foo")
     *       .toString();
     *
     *   // Returns "ClassName{x=1}"
     *   Objects.toStringHelper(this)
     *       .omitNullValues()
     *       .add("x", 1)
     *       .add("y", null)
     *       .toString();
     *   }}</pre>
     * </p>
     *
     * <p>
     * Note that in GWT, class names are often obfuscated.
     * </p>
     *
     * @since 2.0
     * @param self the object to generate the string for (typically {@code this}), used only for its class name
     * @return never {@code null}, always new instance
     */
    public static ToStringHelper toStringHelper(final Object self) {
        return new ToStringHelper(simpleName(self.getClass()));
    }

    /**
     * Creates an instance of {@link ToStringHelper} in the same manner as {@link Objects#toStringHelper(Object)}, but
     * using the name of {@code clazz} instead of using an instance's {@link Object#getClass()}.
     *
     * <p>
     * Note that in GWT, class names are often obfuscated.
     * </p>
     *
     * @since 7.0 (source-compatible since 2.0)
     * @param clazz the {@link Class} of the instance
     * @return never {@code null}, always new instance
     */
    public static ToStringHelper toStringHelper(final Class<?> clazz) {
        return new ToStringHelper(simpleName(clazz));
    }

    /**
     * Creates an instance of {@link ToStringHelper} in the same manner as {@link Objects#toStringHelper(Object)}, but
     * using {@code className} instead of using an instance's {@link Object#getClass()}.
     *
     * @since 7.0 (source-compatible since 2.0)
     * @param className the name of the instance type
     * @return never {@code null}, always new instance
     */
    public static ToStringHelper toStringHelper(final String className) {
        return new ToStringHelper(className);
    }

    /**
     * {@link Class#getSimpleName()} is not GWT compatible yet, so we provide our own implementation.
     *
     * @param clazz the {@link Class} of the instance
     * @return never {@code null}
     */
    private static String simpleName(final Class<?> clazz) {
        String name = clazz.getName();

        // the nth anonymous class has a class name ending in "Outer$n"
        // and local inner classes have names ending in "Outer.$1Inner"
        name = name.replaceAll("\\$[0-9]+", "\\$");

        // we want the name of the inner class all by its lonesome
        int start = name.lastIndexOf('$');

        // if this isn't an inner class, just find the start of the
        // top level class name.
        if (start == -1) {
            start = name.lastIndexOf('.');
        }

        return name.substring(start + 1);
    }

    /**
     * Support class for {@link Objects#toStringHelper}.
     *
     * @author Jason Lee
     * @since 2.0
     */
    public static final class ToStringHelper {

        /**
         * Default size for to string buffer.
         */
        private static final int DEFAULT_BUFFER_SIZE = 32;

        /**
         * Name of rendered class.
         */
        private final String className;
        /**
         * Holds the first member.
         */
        private final ValueHolder holderHead = new ValueHolder();
        /**
         * Holds the last member.
         */
        private ValueHolder holderTail = holderHead;
        /**
         * Whether {@code null} values should be omitted or not.
         */
        private boolean omitNullValues;

        /**
         * Use {@link Objects#toStringHelper(Object)} to create an instance.
         *
         * @param className must not be {@code null}
         */
        private ToStringHelper(final String className) {
            this.className = Validate.notNull(className, "className");
        }

        /**
         * Configures the {@link ToStringHelper} so {@link #toString()} will ignore properties with null value.
         *
         * The order of calling this method, relative to the {@code add()}/{@code addValue()} methods,
         * is not significant.
         *
         * @since 12.0
         * @return self for method chaining
         */
        public ToStringHelper omitNullValues() {
            omitNullValues = true;
            return this;
        }

        /**
         * Adds a name/value pair to the formatted output in {@code name=value} format.
         *
         * If {@code value} is {@code null}, the string {@code "null"} is used, unless {@link #omitNullValues()} is
         * called, in which case this name/value pair will not be added.
         *
         * @param name name of property
         * @param value value of property
         * @return self for method chaining
         */
        public ToStringHelper add(final String name, final Object value) {
            return addHolder(name, value);
        }

        /**
         * Adds a name/value pair to the formatted output in {@code name=value} format.
         *
         * @since 11.0 (source-compatible since 2.0)
         * @param name name of property
         * @param value value of property
         * @return self for method chaining
         */
        public ToStringHelper add(final String name, final boolean value) {
            return addHolder(name, String.valueOf(value));
        }

        /**
         * Adds a name/value pair to the formatted output in {@code name=value} format.
         *
         * @since 11.0 (source-compatible since 2.0)
         * @param name name of property
         * @param value value of property
         * @return self for method chaining
         */
        public ToStringHelper add(final String name, final char value) {
            return addHolder(name, String.valueOf(value));
        }

        /**
         * Adds a name/value pair to the formatted output in {@code name=value} format.
         *
         * @since 11.0 (source-compatible since 2.0)
         * @param name name of property
         * @param value value of property
         * @return self for method chaining
         */
        public ToStringHelper add(final String name, final double value) {
            return addHolder(name, String.valueOf(value));
        }

        /**
         * Adds a name/value pair to the formatted output in {@code name=value} format.
         *
         * @since 11.0 (source-compatible since 2.0)
         * @param name name of property
         * @param value value of property
         * @return self for method chaining
         */
        public ToStringHelper add(final String name, final float value) {
            return addHolder(name, String.valueOf(value));
        }

        /**
         * Adds a name/value pair to the formatted output in {@code name=value} format.
         *
         * @since 11.0 (source-compatible since 2.0)
         * @param name name of property
         * @param value value of property
         * @return self for method chaining
         */
        public ToStringHelper add(final String name, final int value) {
            return addHolder(name, String.valueOf(value));
        }

        /**
         * Adds a name/value pair to the formatted output in {@code name=value} format.
         *
         * @since 11.0 (source-compatible since 2.0)
         * @param name name of property
         * @param value value of property
         * @return self for method chaining
         */
        public ToStringHelper add(final String name, final long value) {
            return addHolder(name, String.valueOf(value));
        }

        /**
         * Adds an unnamed value to the formatted output.
         *
         * <p>
         * It is strongly encouraged to use {@link #add(String, Object)} instead and give value a readable name.
         * </p>
         *
         * @param value value of property
         * @return self for method chaining
         */
        public ToStringHelper addValue(final Object value) {
            return addHolder(value);
        }

        /**
         * Adds an unnamed value to the formatted output.
         *
         * <p>
         * It is strongly encouraged to use {@link #add(String, boolean)} instead and give value a readable name.
         * </p>
         *
         * @since 11.0 (source-compatible since 2.0)
         * @param value value of property
         * @return self for method chaining
         */
        public ToStringHelper addValue(final boolean value) {
            return addHolder(String.valueOf(value));
        }

        /**
         * Adds an unnamed value to the formatted output.
         *
         * <p>
         * It is strongly encouraged to use {@link #add(String, char)} instead and give value a readable name.
         * </p>
         *
         * @since 11.0 (source-compatible since 2.0)
         * @param value value of property
         * @return self for method chaining
         */
        public ToStringHelper addValue(final char value) {
            return addHolder(String.valueOf(value));
        }

        /**
         * Adds an unnamed value to the formatted output.
         *
         * <p>
         * It is strongly encouraged to use {@link #add(String, double)} instead and give value a readable name.
         * </p>
         *
         * @since 11.0 (source-compatible since 2.0)
         * @param value value of property
         * @return self for method chaining
         */
        public ToStringHelper addValue(final double value) {
            return addHolder(String.valueOf(value));
        }

        /**
         * Adds an unnamed value to the formatted output.
         *
         * <p>
         * It is strongly encouraged to use {@link #add(String, float)} instead and give value a readable name.
         * </p>
         *
         * @since 11.0 (source-compatible since 2.0)
         * @param value value of property
         * @return self for method chaining
         */
        public ToStringHelper addValue(final float value) {
            return addHolder(String.valueOf(value));
        }

        /**
         * Adds an unnamed value to the formatted output.
         *
         * <p>
         * It is strongly encouraged to use {@link #add(String, int)} instead and give value a readable name.
         * </p>
         *
         * @since 11.0 (source-compatible since 2.0)
         * @param value value of property
         * @return self for method chaining
         */
        public ToStringHelper addValue(final int value) {
            return addHolder(String.valueOf(value));
        }

        /**
         * Adds an unnamed value to the formatted output.
         *
         * <p>
         * It is strongly encouraged to use {@link #add(String, long)} instead and give value a readable name.
         * </p>
         *
         * @since 11.0 (source-compatible since 2.0)
         * @param value value of property
         * @return self for method chaining
         */
        public ToStringHelper addValue(final long value) {
            return addHolder(String.valueOf(value));
        }

        /**
         * Returns a string in the format specified by {@link Objects#toStringHelper(Object)}.
         *
         * <p>
         * After calling this method, you can keep adding more properties to later call toString() again and get a more
         * complete representation of the same object; but properties cannot be removed, so this only allows limited
         * reuse of the helper instance. The helper allows duplication of properties (multiple name/value pairs with the
         * same name can be added).
         * </p>
         *
         * @return never {@code null}
         */
        @Override
        public String toString() {
            // create a copy to keep it consistent in case value changes
            final boolean omitNullValuesSnapshot = omitNullValues;
            String nextSeparator = "";
            final StringBuilder builder = new StringBuilder(DEFAULT_BUFFER_SIZE).append(className)
                    .append('{');

            for (ValueHolder valueHolder = holderHead.next; valueHolder != null;
                    valueHolder = valueHolder.next) {
                if (!omitNullValuesSnapshot || valueHolder.value != null) {
                    builder.append(nextSeparator);
                    nextSeparator = ", ";

                    if (valueHolder.name != null) {
                        builder.append(valueHolder.name).append('=');
                    }

                    builder.append(valueHolder.value);
                }
            }

            return builder.append('}').toString();
        }

        /**
         * Links a new {@link ValueHolder} to the tail of the values linked list and returns it.
         *
         * @return never {@code null}, always new instance
         */
        private ValueHolder addHolder() {
            final ValueHolder valueHolder = new ValueHolder();
            holderTail.next = valueHolder;
            holderTail = valueHolder;
            return valueHolder;
        }

        /**
         * Adds an unnamed value to the formatted output.
         *
         * @param value value of property
         * @return self for method chaining
         */
        private ToStringHelper addHolder(final Object value) {
            final ValueHolder valueHolder = addHolder();
            valueHolder.value = value;
            return this;
        }

        /**
         * Adds an named value to the formatted output.
         *
         * @param name name of property
         * @param value value of property
         * @return self for method chaining
         */
        private ToStringHelper addHolder(final String name, final Object value) {
            final ValueHolder valueHolder = addHolder();
            valueHolder.value = value;
            valueHolder.name = Validate.notNull(name);
            return this;
        }

        /**
         * Value class to hold properties.
         */
        private static final class ValueHolder {
            /**
             * Name of property.
             */
            String name;
            /**
             * Value of property.
             */
            Object value;
            /**
             * Next property.
             */
            ValueHolder next;
        }
    }
}
