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

/**
 * Provides static methods for working with {@code Collection} instances.
 *
 * @author Chris Povirk
 * @author Mike Bostock
 * @author Jared Levy
 * @since 2.0 (imported from Google Collections Library)
 */
final class Collections2 {

    /**
     * Hidden for pure static factory.
     */
    private Collections2() {
        super();
        throw new UnsupportedOperationException("Constructor must not be called by reflection!");
    }

    /**
     * Used to avoid http://bugs.sun.com/view_bug.do?bug_id=6558557
     */
    static <T> Collection<T> cast(final Iterable<T> iterable) {
        return (Collection<T>) iterable;
    }

}
