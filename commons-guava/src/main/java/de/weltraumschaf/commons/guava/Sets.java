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

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

/**
 * Minimal set of {@code com.google.common.collect.Sets}.
 *
 * @author Kevin Bourrillion
 * @author Jared Levy
 * @author Chris Povirk
 * @since 2.0 (imported from Google Collections Library)
 */
public final class Sets {

    /**
     * Capacity ratio.
     */
    private static final int THIRD = 3;

    /**
     * Hidden for pure static factory.
     */
    private Sets() {
        super();
        throw new UnsupportedOperationException("Constructor must not be called by reflection!");
    }

    /**
     * Returns a capacity that is sufficient to keep the map from being resized as long as it grows no larger than
     * expectedSize and the load factor is >= its default (0.75).
     *
     * @param expectedSize must not be negative
     * @return not negative
     */
    static int capacity(final int expectedSize) {
        if (expectedSize < THIRD) {
            if (!(expectedSize >= 0)) {
                throw new IllegalArgumentException("Parameter 'expectedSize' must not be negative!");
            }

            return expectedSize + 1;
        }

        if (expectedSize < 1 << (Integer.SIZE - 2)) {
            return expectedSize + expectedSize / THIRD;
        }

        return Integer.MAX_VALUE; // any large value
    }

    /**
     * Creates a new hash set.
     *
     * @param <E> type of elements
     * @return never {@code null}, always new instance
     */
    public static <E> Set<E> newHashSet() {
        return new HashSet<E>();
    }

    /**
     * Creates a <em>mutable</em> {@code HashSet} instance containing the given elements in unspecified order.
     *
     * @param <E> type of elements
     * @param elements the elements that the set should contain
     * @return a new {@code HashSet} containing those elements (minus duplicates)
     */
    @SuppressWarnings("unchecked")
    public static <E> Set<E> newHashSet(final E... elements) {
        final Set<E> set = newHashSetWithExpectedSize(elements.length);
        Collections.addAll(set, elements);
        return set;
    }

    /**
     * Creates a {@code HashSet} instance, with a high enough "initial capacity" that it <em>should</em> hold
     * {@code expectedSize} elements without growth.
     *
     * <p>
     * This behavior cannot be broadly guaranteed, but it is observed to
     * be true for OpenJDK 1.6. It also can't be guaranteed that the method isn't inadvertently <i>oversizing</i> the
     * returned set.
     * </p>
     *
     * @param <E> type of elements
     * @param expectedSize the number of elements you expect to add to the returned set
     * @return a new, empty {@code HashSet} with enough capacity to hold {@code
     *         expectedSize} elements without resizing
     * //CHECKSTYLE:OFF
     * @throws IllegalArgumentException if {@code expectedSize} is negative
     * //CHECKSTYLE:ON
     */
    public static <E> Set<E> newHashSetWithExpectedSize(final int expectedSize) {
        return new HashSet<E>(capacity(expectedSize));
    }

    /**
     * Creates a new tree set.
     *
     * @param <E> type of elements
     * @return never {@code null}, always new instance
     */
    public static <E> SortedSet<E> newTreeSet() {
        return new TreeSet<E>();
    }

}
