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
    public static <E> ArrayList<E> newArrayList() {
        return new ArrayList<E>();
    }

    /**
     * Creates a <em>mutable</em> {@link java.util.ArrayList} instance containing the given elements.
     *
     * @param <E> type of elements
     * @param elements the elements that the list should contain, in order
     * @return a new {@code ArrayList} containing those elements
     */
    public static <E> ArrayList<E> newArrayList(final Iterable<? extends E> elements) {
        if (elements == null) {
            throw new NullPointerException();
        }

        // Let ArrayList's sizing logic work, if possible
        return (elements instanceof Collection)
                ? new ArrayList<E>(Collections2.cast(elements))
                : newArrayList(elements.iterator());
    }

    /**
     * Creates a <em>mutable</em> {@link java.util.ArrayList} instance containing the given elements.
     *
     * @param <E> type of elements
     * @param elements the elements that the list should contain, in order
     * @return a new {@code ArrayList} containing those elements
     */
    public static <E> ArrayList<E> newArrayList(final Iterator<? extends E> elements) {
        final ArrayList<E> list = newArrayList();
        Iterators.addAll(list, elements);
        return list;
    }

}
