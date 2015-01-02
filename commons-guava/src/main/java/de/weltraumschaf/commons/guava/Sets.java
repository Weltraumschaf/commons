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

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.util.AbstractSet;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
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
     * This behavior cannot be broadly guaranteed, but it is observed to be true for OpenJDK 1.6. It also can't be
     * guaranteed that the method isn't inadvertently <i>oversizing</i> the returned set.
     * </p>
     *
     * @param <E> type of elements
     * @param expectedSize the number of elements you expect to add to the returned set
     * @return a new, empty {@code HashSet} with enough capacity to hold {@code
     *         expectedSize} elements without resizing //CHECKSTYLE:OFF
     * @throws IllegalArgumentException if {@code expectedSize} is negative //CHECKSTYLE:ON
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

    /**
     * Creates an empty {@code Set} that uses identity to determine equality. It compares object references, instead of
     * calling {@code equals}, to determine whether a provided object matches an element in the set. For example,
     * {@code contains} returns {@code false} when passed an object that equals a set member, but isn't the same
     * instance. This behavior is similar to the way {@code IdentityHashMap} handles key lookups.
     *
     * @since 8.0
     */
    public static <E> Set<E> newIdentityHashSet() {
        return Sets.newSetFromMap(Maps.<E, Boolean>newIdentityHashMap());
    }

    /**
     * Returns a set backed by the specified map. The resulting set displays the same ordering, concurrency, and
     * performance characteristics as the backing map. In essence, this factory method provides a {@link Set}
     * implementation corresponding to any {@link Map} implementation. There is no need to use this method on a
     * {@link Map} implementation that already has a corresponding {@link Set} implementation (such as
     * {@link java.util.HashMap} or {@link java.util.TreeMap}).
     *
     * <p>
     * Each method invocation on the set returned by this method results in exactly one method invocation on the backing
     * map or its {@code keySet} view, with one exception. The {@code addAll} method is implemented as a sequence of
     * {@code put} invocations on the backing map.
     *
     * <p>
     * The specified map must be empty at the time this method is invoked, and should not be accessed directly after
     * this method returns. These conditions are ensured if the map is created empty, passed directly to this method,
     * and no reference to the map is retained, as illustrated in the following code fragment:
     * <pre>  {@code
     *
     *   Set<Object> identityHashSet = Sets.newSetFromMap(
     *       new IdentityHashMap<Object, Boolean>());}</pre>
     *
     * This method has the same behavior as the JDK 6 method {@code Collections.newSetFromMap()}. The returned set is
     * serializable if the backing map is.
     *
     * @param map the backing map
     * @return the set backed by the map
     * @throws IllegalArgumentException if {@code map} is not empty
     */
    public static <E> Set<E> newSetFromMap(Map<E, Boolean> map) {
        return new SetFromMap<E>(map);
    }

    /**
     * Ensures the truth of an expression involving one or more parameters to the calling method.
     *
     * @param expression a boolean expression
     * @param errorMessage the exception message to use if the check fails; will be converted to a string using
     * {@link String#valueOf(Object)}
     * @throws IllegalArgumentException if {@code expression} is false
     */
    public static void checkArgument(
            boolean expression, Object errorMessage) {
        if (!expression) {
            throw new IllegalArgumentException(String.valueOf(errorMessage));
        }
    }

    private static class SetFromMap<E> extends AbstractSet<E> implements Set<E>, Serializable {

        private final Map<E, Boolean> m; // The backing map
        private transient Set<E> s; // Its keySet

        SetFromMap(Map<E, Boolean> map) {
            checkArgument(map.isEmpty(), "Map is non-empty");
            m = map;
            s = map.keySet();
        }

        @Override
        public void clear() {
            m.clear();
        }

        @Override
        public int size() {
            return m.size();
        }

        @Override
        public boolean isEmpty() {
            return m.isEmpty();
        }

        @Override
        public boolean contains(Object o) {
            return m.containsKey(o);
        }

        @Override
        public boolean remove(Object o) {
            return m.remove(o) != null;
        }

        @Override
        public boolean add(E e) {
            return m.put(e, Boolean.TRUE) == null;
        }

        @Override
        public Iterator<E> iterator() {
            return s.iterator();
        }

        @Override
        public Object[] toArray() {
            return s.toArray();
        }

        @Override
        public <T> T[] toArray(T[] a) {
            return s.toArray(a);
        }

        @Override
        public String toString() {
            return s.toString();
        }

        @Override
        public int hashCode() {
            return s.hashCode();
        }

        @Override
        public boolean equals(Object object) {
            return this == object || this.s.equals(object);
        }

        @Override
        public boolean containsAll(Collection<?> c) {
            return s.containsAll(c);
        }

        @Override
        public boolean removeAll(Collection<?> c) {
            return s.removeAll(c);
        }

        @Override
        public boolean retainAll(Collection<?> c) {
            return s.retainAll(c);
        }

        // addAll is the only inherited implementation
        private static final long serialVersionUID = 0;

        private void readObject(ObjectInputStream stream)
                throws IOException, ClassNotFoundException {
            stream.defaultReadObject();
            s = m.keySet();
        }
    }
}
