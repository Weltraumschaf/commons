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

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

/**
 * Minimal set of {@code com.google.common.collect.Lists}.
 *
 * @author Kevin Bourrillion
 * @author Mike Bostock
 * @author Louis Wasserman
 * @since 2.0 (imported from Google Collections Library)
 */
public final class Lists {

    /**
     * Hidden for pure static factory.
     */
    private Lists() {
        super();
        throw new UnsupportedOperationException("Constructor must not be called by reflection!");
    }

    /**
     * Creates a <em>mutable</em>, empty {@link java.util.ArrayList} instance.
     *
     * @param <E> type of elements
     * @return a new, empty {@code ArrayList}
     */
    public static <E> List<E> newArrayList() {
        return new ArrayList<>();
    }

    /**
     * Used to avoid http://bugs.sun.com/view_bug.do?bug_id=6558557 problem.
     *
     * @param <T> type of elements
     * @param iterable iterable to cast
     * @return the iterable casted to collection
     */
    private static <T> Collection<T> cast(final Iterable<T> iterable) {
        return (Collection<T>) iterable;
    }

    /**
     * Creates a <em>mutable</em> {@link java.util.ArrayList} instance containing the given elements.
     *
     * @param <E> type of elements
     * @param elements the elements that the list should contain, in order
     * @return a new {@code ArrayList} containing those elements
     */
    public static <E> List<E> newArrayList(final Iterable<? extends E> elements) {
        if (elements == null) {
            throw new NullPointerException();
        }

        // Let ArrayList's sizing logic work, if possible
        if (elements instanceof Collection) {
            return new ArrayList<>(cast(elements));
        } else {
            return newArrayList(elements.iterator());
        }
    }

    /**
     * Creates a <em>mutable</em> {@link java.util.ArrayList} instance containing the given elements.
     *
     * @param <E> type of elements
     * @param elements the elements that the list should contain, in order
     * @return a new {@code ArrayList} containing those elements
     */
    public static <E> List<E> newArrayList(final Iterator<? extends E> elements) {
        final List<E> list = newArrayList();
        Iterators.addAll(list, elements);
        return list;
    }

    /**
     * Creates a <em>mutable</em> {@link java.util.ArrayList} instance containing the given elements.
     *
     * @param <E> type of elements
     * @param elements the elements that the list should contain, in order
     * @return a new {@code ArrayList} containing those elements
     */
    @SafeVarargs
    public static <E> List<E> newArrayList(final E ... elements) {
        final List<E> list = newArrayList();

        for (final E element : elements) {
            list.add(element);
        }

        return list;
    }
}
