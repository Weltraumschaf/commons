/*
 *  LICENSE
 *
 * "THE BEER-WARE LICENSE" (Revision 43):
 * "Sven Strittmatter" &lt;weltraumschaf@googlemail.com&gt; wrote this file.
 * As long as you retain this notice you can do whatever you want with
 * this stuff. If we meet some day, and you think this stuff is worth it,
 * you can buy me a non alcohol-free beer in return.
 *
 * Copyright (C) 2012 "Sven Strittmatter" &lt;weltraumschaf@googlemail.com&gt;
 */
package de.weltraumschaf.commons.experimental;

import de.weltraumschaf.commons.validate.Validate;
import java.lang.ref.SoftReference;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * A simple in memory cache based on {@link SoftReference soft reference}.
 *
 * @param <K> type of cache key
 * @param <V> type of cached value
 * @author Sven Strittmatter &lt;weltraumschaf@googlemail.com&gt;
 */
@Experimental
public class SoftCache<K, V> {

    /**
     * Holds the cached data.
     */
    private final Map<K, SoftReference<V>> data = new ConcurrentHashMap<>();
    /**
     * Try to finds values if a key not present yet in cache.
     */
    private Finder<K, V> finder = new NullFinder<>();

    /**
     * Get a value from the cache.
     * <p>
     * If key not present yet, then the finder is asked. If no finder set a default finder is used which does not find
     * anything.
     * </p>
     *
     * @param key must not be {@code null}
     * @return may be {@code null}
     */
    public V get(final K key) {
        if (null == key) {
            throw new NullPointerException("Parameter 'key' must not be null!");
        }

        final SoftReference<V> reference = data.get(key);

        if (null == reference || null == reference.get()) {
            final V value = finder.find(key);

            if (null == value) {
                throw new NullPointerException(String.format("No value fond for key '%s'!", key));
            }
            add(key, value);
            return value;
        }

        return reference.get();
    }

    /**
     * Add a value to the cache.
     *
     * @param key must not be {@code null}
     * @param value must not be {@code null}
     */
    public void add(final K key, final V value) {
        Validate.notNull(key, "key");
        Validate.notNull(value, "value");
        final SoftReference<V> reference = data.put(key, new SoftReference<>(value));

        if (null != reference) {
            reference.clear();
        }
    }

    /**
     * Removes value from cache.
     *
     * @param key must not be {@code null}
     */
    public void remove(final K key) {
        if (null == key) {
            throw new NullPointerException("Parameter 'key' must not be null!");
        }

        final SoftReference<V> reference = data.remove(key);

        if (null != reference) {
            reference.clear();
        }
    }

    /**
     * Set the finder for this cache.
     *
     * @param f must not be {@code null}
     */
    public void finder(final Finder<K, V> f) {
        finder = Validate.notNull(f, "f");
    }

    /**
     * Implementations of this interface may be used to provide values not in the cache yet.
     *
     * @param <K> type of cache key
     * @param <V> type of cached value
     */
    public interface Finder<K, V> {

        /**
         * Try to find a value for the key not already in the cache.
         *
         * @param key must not be {@code null}
         * @return may be {@code null}
         */
        V find(K key);
    }

    /**
     * Default implementation which does not find anything.
     *
     * @param <K> type of cache key
     * @param <V> type of cached value
     */
    private static final class NullFinder<K, V> implements Finder<K, V> {

        @Override
        public V find(final K key) {
            return null;
        }

    }
}
