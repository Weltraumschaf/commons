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
     * Hidden for pure static factory.
     */
    private Maps() {
        super();
        throw new UnsupportedOperationException("Constructor must not be called by reflection!");
    }

    /**
     * Creates a <i>mutable</i>, empty {@code HashMap} instance.
     *
     * @param <K> type of key
     * @param <V> type of value
     * @return a new, empty {@code HashMap}
     */
    public static <K, V> Map<K, V> newHashMap() {
        return new HashMap<K, V>();
    }

    /**
     * Creates a <i>mutable</i> {@code HashMap} instance with the same mappings as the specified map.
     *
     * @param <K> type of key
     * @param <V> type of value
     * @param map the mappings to be placed in the new map
     * @return a new {@code HashMap} initialized with the mappings from {@code
     *         map}
     */
    public static <K, V> Map<K, V> newHashMap(final Map<? extends K, ? extends V> map) {
        return new HashMap<K, V>(map);
    }

}
