/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright (c) 2012-2013 Oracle and/or its affiliates. All rights reserved.
 *
 * The contents of this file are subject to the terms of either the GNU
 * General Public License Version 2 only ("GPL") or the Common Development
 * and Distribution License("CDDL") (collectively, the "License").  You
 * may not use this file except in compliance with the License.  You can
 * obtain a copy of the License at
 * http://glassfish.java.net/public/CDDL+GPL_1_1.html
 * or packager/legal/LICENSE.txt.  See the License for the specific
 * language governing permissions and limitations under the License.
 *
 * When distributing the software, include this License Header Notice in each
 * file and include the License file at packager/legal/LICENSE.txt.
 *
 * GPL Classpath Exception:
 * Oracle designates this particular file as subject to the "Classpath"
 * exception as provided by Oracle in the GPL Version 2 section of the License
 * file that accompanied this code.
 *
 * Modifications:
 * If applicable, add the following below the License Header, with the fields
 * enclosed by brackets [] replaced by your own identifying information:
 * "Portions Copyright [year] [name of copyright owner]"
 *
 * Contributor(s):
 * If you wish your version of this file to be governed by only the CDDL or
 * only the GPL Version 2, indicate your decision by adding "[Contributor]
 * elects to include this software in this distribution under the [CDDL or GPL
 * Version 2] license."  If you don't indicate a single choice of license, a
 * recipient has the option to distribute your version of this file under
 * either the CDDL, the GPL Version 2 or to extend the choice of license to
 * its licensees as provided above.  However, if you add GPL Version 2 code
 * and therefore, elected the GPL Version 2 license, then the option applies
 * only if the new code is made subject to such option by the copyright
 * holder.
 */
package de.weltraumschaf.commons.uri;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Abstract skeleton implementation of a {@code  MultivaluedMap} that is backed by a [key, multi-value] store represented
 * as a {@code  Map Map&lt;K, List&lt;V&gt;&gt;}.
 *
 * @param <K> the type of keys maintained by this map.
 * @param <V> the type of mapped values.
 * @author Marek Potociar
 */
abstract class AbstractMultivaluedMap<K, V> implements MultivaluedMap<K, V> {

    /**
     * Backing store for the [key, multi-value] pairs.
     */
    protected final Map<K, List<V>> store;

    /**
     * Initialize the backing store in the abstract parent multivalued map implementation.
     *
     * @param store the backing {@code  Map} to be used as a [key, multi-value] store. Must not be {@code null}.
     * @throws NullPointerException in case the underlying {@code store} parameter is {@code null}.
     */
    AbstractMultivaluedMap(Map<K, List<V>> store) {
        if (store == null) {
            throw new NullPointerException("Underlying store must not be 'null'.");
        }
        this.store = store;
    }

    /**
     * {@inheritDoc}
     *
     * Set the value for the key to be a one item list consisting of the supplied value.
     * <p>
     * Any existing values will bereplaced.
     * </p>
     * <p>
     * NOTE: This implementation ignores {@code null} values; A supplied value of {@code null} is ignored and not added
     * to the purged value list. As a result of such operation, empty value list would be registered for the supplied
     * key. Overriding implementations may modify this behavior by redefining the {@code #addNull(java.util.List)}
     * method.
     * </p>
     */
    @Override
    public final void putSingle(K key, V value) {
        final List<V> values = getValues(key);

        values.clear();
        if (value != null) {
            values.add(value);
        } else {
            addNull(values);
        }
    }

    /**
     * Define the behavior for adding a {@code null} values to the value list.
     * <p>
     * Default implementation is a no-op, i.e. the {@code null} values are ignored. Overriding implementations may
     * modify this behavior by providing their own definitions of this method.
     * </p>
     *
     * @param values value list where the {@code null} value addition is being requested.
     */
    @SuppressWarnings("UnusedParameters")
    protected void addNull(List<V> values) {
        // do nothing in the default implementation; ignore the null value
    }

    /**
     * Define the behavior for adding a {@code null} values to the first position in the value list.
     * <p>
     * Default implementation is a no-op, i.e. the {@code null} values are ignored. Overriding implementations may
     * modify this behavior by providing their own definitions of this method.
     * </p>
     *
     * @param values value list where the {@code null} value addition is being requested.
     */
    @SuppressWarnings("UnusedParameters")
    protected void addFirstNull(List<V> values) {
        // do nothing in the default implementation; ignore the null value
    }

    /**
     * {@inheritDoc}
     *
     * Add a value to the current list of values for the supplied key.
     * <p>
     * NOTE: This implementation ignores {@code null} values; A supplied value of {@code null} is ignored and not added
     * to the value list. Overriding implementations may modify this behavior by redefining the
     * {@code #addNull(java.util.List)} method.
     * </p>
     */
    @Override
    public final void add(K key, V value) {
        final List<V> values = getValues(key);

        if (value != null) {
            values.add(value);
        } else {
            addNull(values);
        }
    }

    /**
     * {@inheritDoc}
     *
     * Add multiple values to the current list of values for the supplied key.
     * <p>
     * If the supplied array of new values is empty, method returns immediately. Method throws a
     * {@code NullPointerException} if the supplied array of values is {@code null}.
     * </p>
     * <p>
     * NOTE: This implementation ignores {@code null} values; Any of the supplied values of {@code null} is ignored and
     * not added to the value list. Overriding implementations may modify this behavior by redefining the
     * {@code #addNull(java.util.List)} method.
     * </p>
     */
    @Override
    @SafeVarargs
    public final void addAll(K key, V... newValues) {
        if (newValues == null) {
            throw new NullPointerException("Supplied array of values must not be null.");
        }
        if (newValues.length == 0) {
            return;
        }

        final List<V> values = getValues(key);

        for (V value : newValues) {
            if (value != null) {
                values.add(value);
            } else {
                addNull(values);
            }
        }
    }

    /**
     * {@inheritDoc}
     *
     * Add all the values from the supplied value list to the current list of values for the supplied key.
     * <p>
     * If the supplied value list is empty, method returns immediately. Method throws a {@code NullPointerException} if
     * the supplied array of values is {@code null}.
     * </p>
     * <p>
     * NOTE: This implementation ignores {@code null} values; Any {@code null} value in the supplied value list is
     * ignored and not added to the value list. Overriding implementations may modify this behavior by redefining the
     * {@code #addNull(java.util.List)} method.
     * </p>
     */
    @Override
    public final void addAll(K key, List<V> valueList) {
        if (valueList == null) {
            throw new NullPointerException("Supplied list of values must not be null.");
        }
        if (valueList.isEmpty()) {
            return;
        }

        final List<V> values = getValues(key);

        for (V value : valueList) {
            if (value != null) {
                values.add(value);
            } else {
                addNull(values);
            }
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final V getFirst(K key) {
        final List<V> values = store.get(key);
        if (values != null && values.size() > 0) {
            return values.get(0);
        } else {
            return null;
        }
    }

    /**
     * {@inheritDoc}
     *
     * Add a value to the first position in the current list of values for the supplied key.
     * <p>
     * NOTE: This implementation ignores {@code null} values; A supplied value of {@code null} is ignored and not added
     * to the purged value list. Overriding implementations may modify this behavior by redefining the
     * {@code #addFirstNull(java.util.List)} method.
     * </p>
     */
    @Override
    public final void addFirst(K key, V value) {
        final List<V> values = getValues(key);

        if (value != null) {
            values.add(0, value);
        } else {
            addFirstNull(values);
        }
    }

    /**
     * Return a non-null list of values for a given key. The returned list may be empty.
     * <p>
     * If there is no entry for the key in the map, a new empty {@code  java.util.List} instance is created, registered
     * within the map to hold the values of the key and returned from the method.
     * </p>
     *
     * @param key the key.
     * @return value list registered with the key. The method is guaranteed to never return {@code null}.
     */
    protected final List<V> getValues(K key) {
        List<V> l = store.get(key);
        if (l == null) {
            l = new LinkedList<V>();
            store.put(key, l);
        }
        return l;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return store.toString();
    }

    /**
     * {@inheritDoc}
     *
     * {@inheritDoc }
     * <p>
     * This implementation delegates the method call to to the the underlying [key, multi-value] store.
     * </p>
     */
    @Override
    public int hashCode() {
        return store.hashCode();
    }

    /**
     * {@inheritDoc}
     *
     * {@inheritDoc }
     * <p>
     * This implementation delegates the method call to to the the underlying [key, multi-value] store.
     * </p>
     */
    @Override
    @SuppressWarnings("EqualsWhichDoesntCheckParameterClass")
    public boolean equals(Object o) {
        return store.equals(o);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Collection<List<V>> values() {
        return store.values();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int size() {
        return store.size();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<V> remove(Object key) {
        return store.remove(key);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void putAll(Map<? extends K, ? extends List<V>> m) {
        store.putAll(m);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<V> put(K key, List<V> value) {
        return store.put(key, value);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Set<K> keySet() {
        return store.keySet();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isEmpty() {
        return store.isEmpty();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<V> get(Object key) {
        return store.get(key);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Set<Map.Entry<K, List<V>>> entrySet() {
        return store.entrySet();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean containsValue(Object value) {
        return store.containsValue(value);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean containsKey(Object key) {
        return store.containsKey(key);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void clear() {
        store.clear();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equalsIgnoreValueOrder(MultivaluedMap<K, V> omap) {
        if (this == omap) {
            return true;
        }

        if (!keySet().equals(omap.keySet())) {
            return false;
        }

        for (final Map.Entry<K, List<V>> e : entrySet()) {
            final List<V> olist = omap.get(e.getKey());

            if (e.getValue().size() != olist.size()) {
                return false;
            }

            for (final V v : e.getValue()) {
                if (!olist.contains(v)) {
                    return false;
                }
            }
        }

        return true;
    }
}
