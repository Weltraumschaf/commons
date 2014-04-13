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

import java.util.Collection;
import java.util.Iterator;

/**
 * Minimal set of {@code com.google.common.collect.Iterators}.
 *
 * @author Kevin Bourrillion
 * @author Jared Levy
 * @since 2.0 (imported from Google Collections Library)
 */
final class Iterators {

    /**
     * Hidden for pure static factory.
     */
    private Iterators() {
        super();
        throw new UnsupportedOperationException("Constructor must not be called by reflection!");
    }

    /**
     * Adds all elements in {@code iterator} to {@code collection}. The iterator will be left exhausted: its
     * {@code hasNext()} method will return {@code false}.
     *
     * @param <T> type of elements
     * @param addTo must not be {@code null}
     * @param iterator must not be {@code null}
     * @return whether the collection was modified or not
     */
    public static <T> boolean addAll(final Collection<T> addTo, final Iterator<? extends T> iterator) {
        Objects.checkNotNull(addTo);
        Objects.checkNotNull(iterator);
        boolean wasModified = false;

        while (iterator.hasNext()) {
            wasModified |= addTo.add(iterator.next());
        }

        return wasModified;
    }

}
