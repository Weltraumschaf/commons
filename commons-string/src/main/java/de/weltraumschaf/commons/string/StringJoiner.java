/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package de.weltraumschaf.commons.string;

import java.util.Iterator;
import java.util.Objects;

/**
 * From {@literal org.apache.commons.lang3.StringJoiner}.
 *
 * @since 1.0.0
 * @author Sven Strittmatter &lt;weltraumschaf@googlemail.com&gt;
 */
public final class StringJoiner {

    /**
     * The empty String {@code ""}.
     */
    public static final String EMPTY = "";

    /**
     * Hidden because pure static final class.
     */
    private StringJoiner() {
        super();
        throw new UnsupportedOperationException("Must not be caled via reflection!");
    }

    /**
     * Joins the elements of the provided array into a single String containing the provided list of elements.
     *
     * <p>
     * No separator is added to the joined String. Null objects or empty strings within the array are represented by
     * empty strings.
     * </p>
     *
     * <pre>
     * StringJoiner.join(null)            = ""
     * StringJoiner.join([])              = ""
     * StringJoiner.join([null])          = ""
     * StringJoiner.join(["a", "b", "c"]) = "abc"
     * StringJoiner.join([null, "", "a"]) = "a"
     * </pre>
     *
     * @param <T> the specific type of values to join together
     * @param elements the values to join together, may be null
     * @return the joined String, never {@code null}
     */
    @SafeVarargs
    public static <T> String join(final T... elements) {
        return join(elements, EMPTY);
    }

    /**
     * Joins the elements of the provided array into a single String containing the provided list of elements.
     *
     * <p>
     * No delimiter is added before or after the list. Null objects or empty strings within the array are represented by
     * empty strings.
     * </p>
     *
     * <pre>
     * StringJoiner.join(null, *)               = ""
     * StringJoiner.join([], *)                 = ""
     * StringJoiner.join([null], *)             = ""
     * StringJoiner.join(["a", "b", "c"], ';')  = "a;b;c"
     * StringJoiner.join(["a", "b", "c"], null) = "abc"
     * StringJoiner.join([null, "", "a"], ';')  = ";a"
     * </pre>
     *
     * @param array the array of values to join together, may be null
     * @param separator the separator character to use
     * @return the joined String, never {@code null}
     */
    public static String join(final Object[] array, final char separator) {
        if (array == null || array.length == 0) {
            return EMPTY;
        }

        final StringBuilder buffer = new StringBuilder(array.length * 16);
        boolean first = true;

        for (final Object element : array) {
            if (element == null) {
                continue;
            }

            if (!first && separator > 0) {
                buffer.append(separator);
            }

            buffer.append(element);
            first = false;
        }

        return buffer.toString();
    }

    /**
     * Joins the elements of the provided array into a single String containing the provided list of elements.
     *
     * <p>
     * No delimiter is added before or after the list. Null objects or empty strings within the array are represented by
     * empty strings.
     * </p>
     *
     * <pre>
     * StringJoiner.join(null, *)         = ""
     * StringJoiner.join([], *)           = ""
     * StringJoiner.join([1, 2, 3], ';')  = "1;2;3"
     * StringJoiner.join([1, 2, 3], null) = "123"
     * </pre>
     *
     * @param array the array of values to join together, may be null
     * @param separator the separator character to use
     * @return the joined String, never {@code null}
     */
    public static String join(final long[] array, final char separator) {
        if (array == null || array.length == 0) {
            return EMPTY;
        }

        final StringBuilder buffer = new StringBuilder(array.length * 16);
        boolean first = true;

        for (final Object element : array) {
            if (element == null) {
                continue;
            }

            if (!first && separator > 0) {
                buffer.append(separator);
            }

            buffer.append(element);
            first = false;
        }

        return buffer.toString();
    }

    /**
     * Joins the elements of the provided array into a single String containing the provided list of elements.
     *
     * <p>
     * No delimiter is added before or after the list. Null objects or empty strings within the array are represented by
     * empty strings.
     * </p>
     *
     * <pre>
     * StringJoiner.join(null, *)         = ""
     * StringJoiner.join([], *)           = ""
     * StringJoiner.join([1, 2, 3], ';')  = "1;2;3"
     * StringJoiner.join([1, 2, 3], null) = "123"
     * </pre>
     *
     * @param array the array of values to join together, may be null
     * @param separator the separator character to use
     * @return the joined String, never {@code null}
     */
    public static String join(final int[] array, final char separator) {
        if (array == null || array.length == 0) {
            return EMPTY;
        }

        final StringBuilder buffer = new StringBuilder(array.length * 16);
        boolean first = true;

        for (final Object element : array) {
            if (element == null) {
                continue;
            }

            if (!first && separator > 0) {
                buffer.append(separator);
            }

            buffer.append(element);
            first = false;
        }

        return buffer.toString();
    }

    /**
     * <p>
     * Joins the elements of the provided array into a single String containing the provided list of elements.
     * </p>
     *
     * <p>
     * No delimiter is added before or after the list. Null objects or empty strings within the array are represented by
     * empty strings.
     * </p>
     *
     * <pre>
     * StringJoiner.join(null, *)         = null
     * StringJoiner.join([], *)           = ""
     * StringJoiner.join([1, 2, 3], ';')  = "1;2;3"
     * StringJoiner.join([1, 2, 3], null) = "123"
     * </pre>
     *
     * @param array the array of values to join together, may be null
     * @param separator the separator character to use
     * @return the joined String, never {@code null}
     */
    public static String join(final short[] array, final char separator) {
        if (array == null || array.length == 0) {
            return EMPTY;
        }

        final StringBuilder buffer = new StringBuilder(array.length * 16);
        boolean first = true;

        for (final Object element : array) {
            if (element == null) {
                continue;
            }

            if (!first && separator > 0) {
                buffer.append(separator);
            }

            buffer.append(element);
            first = false;
        }

        return buffer.toString();
    }

    /**
     * Joins the elements of the provided array into a single String containing the provided list of elements.
     *
     * <p>
     * No delimiter is added before or after the list. Null objects or empty strings within the array are represented by
     * empty strings.
     * </p>
     *
     * <pre>
     * StringJoiner.join(null, *)         = null
     * StringJoiner.join([], *)           = ""
     * StringJoiner.join([1, 2, 3], ';')  = "1;2;3"
     * StringJoiner.join([1, 2, 3], null) = "123"
     * </pre>
     *
     * @param array the array of values to join together, may be null
     * @param separator the separator character to use
     * @return the joined String, never {@code null}
     */
    public static String join(final byte[] array, final char separator) {
        if (array == null || array.length == 0) {
            return EMPTY;
        }

        final StringBuilder buffer = new StringBuilder(array.length * 16);
        boolean first = true;

        for (final Object element : array) {
            if (element == null) {
                continue;
            }

            if (!first && separator > 0) {
                buffer.append(separator);
            }

            buffer.append(element);
            first = false;
        }

        return buffer.toString();
    }

    /**
     * Joins the elements of the provided array into a single String containing the provided list of elements.
     *
     * <p>
     * No delimiter is added before or after the list. Null objects or empty strings within the array are represented by
     * empty strings.
     * </p>
     *
     * <pre>
     * StringJoiner.join(null, *)         = null
     * StringJoiner.join([], *)           = ""
     * StringJoiner.join([1, 2, 3], ';')  = "1;2;3"
     * StringJoiner.join([1, 2, 3], null) = "123"
     * </pre>
     *
     * @param array the array of values to join together, may be null
     * @param separator the separator character to use
     * @return the joined String, never {@code null}
     */
    public static String join(final char[] array, final char separator) {
        if (array == null || array.length == 0) {
            return EMPTY;
        }

        final StringBuilder buffer = new StringBuilder(array.length * 16);
        boolean first = true;

        for (final Object element : array) {
            if (element == null) {
                continue;
            }

            if (!first && separator > 0) {
                buffer.append(separator);
            }

            buffer.append(element);
            first = false;
        }

        return buffer.toString();
    }

    /**
     * Joins the elements of the provided array into a single String containing the provided list of elements.
     *
     * <p>
     * No delimiter is added before or after the list. Null objects or empty strings within the array are represented by
     * empty strings.
     * </p>
     *
     * <pre>
     * StringJoiner.join(null, *)         = null
     * StringJoiner.join([], *)           = ""
     * StringJoiner.join([1, 2, 3], ';')  = "1;2;3"
     * StringJoiner.join([1, 2, 3], null) = "123"
     * </pre>
     *
     * @param array the array of values to join together, may be null
     * @param separator the separator character to use
     * @return the joined String, never {@code null}
     */
    public static String join(final float[] array, final char separator) {
        if (array == null || array.length == 0) {
            return EMPTY;
        }

        final StringBuilder buffer = new StringBuilder(array.length * 16);
        boolean first = true;

        for (final Object element : array) {
            if (element == null) {
                continue;
            }

            if (!first && separator > 0) {
                buffer.append(separator);
            }

            buffer.append(element);
            first = false;
        }

        return buffer.toString();
    }

    /**
     * Joins the elements of the provided array into a single String containing the provided list of elements.
     *
     * <p>
     * No delimiter is added before or after the list. Null objects or empty strings within the array are represented by
     * empty strings.
     * </p>
     *
     * <pre>
     * StringJoiner.join(null, *)         = null
     * StringJoiner.join([], *)           = ""
     * StringJoiner.join([null], *)       = ""
     * StringJoiner.join([1, 2, 3], ';')  = "1;2;3"
     * StringJoiner.join([1, 2, 3], null) = "123"
     * </pre>
     *
     * @param array the array of values to join together, may be null
     * @param separator the separator character to use
     * @return the joined String, never {@code null}
     */
    public static String join(final double[] array, final char separator) {
        if (array == null || array.length == 0) {
            return EMPTY;
        }

        final StringBuilder buffer = new StringBuilder(array.length * 16);
        boolean first = true;

        for (final Object element : array) {
            if (element == null) {
                continue;
            }

            if (!first && separator > 0) {
                buffer.append(separator);
            }

            buffer.append(element);
            first = false;
        }

        return buffer.toString();
    }

    /**
     * Joins the elements of the provided array into a single String containing the provided list of elements.
     *
     * <p>
     * No delimiter is added before or after the list. A {@code null} separator is the same as an empty String ("").
     * Null objects or empty strings within the array are represented by empty strings.</p>
     *
     * <pre>
     * StringJoiner.join(null, *)                = null
     * StringJoiner.join([], *)                  = ""
     * StringJoiner.join([null], *)              = ""
     * StringJoiner.join(["a", "b", "c"], "--")  = "a--b--c"
     * StringJoiner.join(["a", "b", "c"], null)  = "abc"
     * StringJoiner.join(["a", "b", "c"], "")    = "abc"
     * StringJoiner.join([null, "", "a"], ',')   = ",,a"
     * </pre>
     *
     * @param array the array of values to join together, may be null
     * @param separator the separator character to use, null treated as ""
     * @return the joined String, never {@code null}
     */
    public static String join(final Object[] array, final String separator) {
        if (array == null || array.length == 0) {
            return EMPTY;
        }

        final StringBuilder buffer = new StringBuilder(array.length * 16);
        boolean first = true;

        for (final Object element : array) {
            if (element == null) {
                continue;
            }

            if (!first && separator != null) {
                buffer.append(separator);
            }

            buffer.append(element);
            first = false;
        }

        return buffer.toString();
    }

    /**
     * Joins the elements of the provided {@code Iterator} into a single String containing the provided elements.
     *
     * <p>
     * No delimiter is added before or after the list. Null objects or empty strings within the iteration are
     * represented by empty strings.
     * </p>
     *
     * <p>
     * See the examples here: {@link #join(Object[],char)}.
     * </p>
     *
     * @param iterator the {@code Iterator} of values to join together, may be null
     * @param separator the separator character to use
     * @return the joined String, never {@code null}
     */
    public static String join(final Iterator<?> iterator, final char separator) {
        return join(iterator, separator > 0 ? String.valueOf(separator) : EMPTY);
    }

    /**
     * Joins the elements of the provided {@code Iterator} into a single String containing the provided elements.
     *
     * <p>
     * No delimiter is added before or after the list. A {@code null} separator is the same as an empty String ("").
     * </p>
     *
     * <p>
     * See the examples here: {@link #join(Object[],String)}.
     * </p>
     *
     * @param iterator the {@code Iterator} of values to join together, may be null
     * @param separator the separator character to use, null treated as ""
     * @return the joined String, never {@code null}
     */
    public static String join(final Iterator<?> iterator, final String separator) {
        // handle null, zero and one elements before building a buffer
        if (iterator == null) {
            return EMPTY;
        }

        if (!iterator.hasNext()) {
            return EMPTY;
        }

        final Object first = iterator.next();

        if (!iterator.hasNext()) {
            return Objects.toString(first);
        }

        // two or more elements
        final StringBuilder buf = new StringBuilder(256); // Java default is 16, probably too small

        if (first != null) {
            buf.append(first);
        }

        while (iterator.hasNext()) {
            if (separator != null) {
                buf.append(separator);
            }

            final Object obj = iterator.next();

            if (obj != null) {
                buf.append(obj);
            }
        }

        return buf.toString();
    }

    /**
     * Joins the elements of the provided {@code Iterable} into a single String containing the provided elements.
     *
     * <p>
     * No delimiter is added before or after the list. Null objects or empty strings within the iteration are
     * represented by empty strings.
     * </p>
     *
     * <p>
     * See the examples here: {@link #join(Object[],char)}.
     * </p>
     *
     * @param iterable the {@code Iterable} providing the values to join together, may be null
     * @param separator the separator character to use
     * @return the joined String, never {@code null}
     */
    public static String join(final Iterable<?> iterable, final char separator) {
        if (iterable == null) {
            return EMPTY;
        }

        return join(iterable.iterator(), separator);
    }

    /**
     * Joins the elements of the provided {@code Iterable} into a single String containing the provided elements.
     *
     * <p>
     * No delimiter is added before or after the list. A {@code null} separator is the same as an empty String ("").
     * </p>
     *
     * <p>
     * See the examples here: {@link #join(Object[],String)}.
     * </p>
     *
     * @param iterable the {@code Iterable} providing the values to join together, may be null
     * @param separator the separator character to use, null treated as ""
     * @return the joined String, join {@code null}
     */
    public static String join(final Iterable<?> iterable, final String separator) {
        if (iterable == null) {
            return EMPTY;
        }

        return join(iterable.iterator(), separator);
    }
}
