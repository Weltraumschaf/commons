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

import java.util.HashMap;
import java.util.Map;

/**
 * Minimal set of {@code com.google.common.collect.Maps}.
 *
 * @author Kevin Bourrillion
 * @author Mike Bostock
 * @author Isaac Shum
 * @author Louis Wasserman
 * @since 2.0 (imported from Google Collections Library)
 */
public final class Maps {

    /**
     * Hidden for pure static facotry.
     */
    private Maps() {
        super();
    }

    /**
     * Returns a capacity that is sufficient to keep the map from being resized as long as it grows no larger than
     * expectedSize and the load factor is >= its default (0.75).
     */
    static int capacity(final int expectedSize) {
        if (expectedSize < 3) {
            if (!(expectedSize >= 0)) {
                throw new IllegalArgumentException();
            }
            return expectedSize + 1;
        }

        if (expectedSize < 1 << (Integer.SIZE - 2)) {
            return expectedSize + expectedSize / 3;
        }

        return Integer.MAX_VALUE; // any large value
    }

    /**
     * Creates a <i>mutable</i>, empty {@code HashMap} instance.
     *
     * <p>
     * <b>Note:</b> if mutability is not required, use {@link
     * ImmutableMap#of()} instead.
     *
     * <p>
     * <b>Note:</b> if {@code K} is an {@code enum} type, use {@link
     * #newEnumMap} instead.
     *
     * @return a new, empty {@code HashMap}
     */
    public static <K, V> HashMap<K, V> newHashMap() {
        return new HashMap<K, V>();
    }

    /**
     * Creates a <i>mutable</i> {@code HashMap} instance with the same mappings as the specified map.
     *
     * <p>
     * <b>Note:</b> if mutability is not required, use {@link
     * ImmutableMap#copyOf(Map)} instead.
     *
     * <p>
     * <b>Note:</b> if {@code K} is an {@link Enum} type, use {@link
     * #newEnumMap} instead.
     *
     * @param map the mappings to be placed in the new map
     * @return a new {@code HashMap} initialized with the mappings from {@code
     *         map}
     */
    public static <K, V> HashMap<K, V> newHashMap(final Map<? extends K, ? extends V> map) {
        return new HashMap<K, V>(map);
    }

}
