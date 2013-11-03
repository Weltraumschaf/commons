package de.weltraumschaf.commons.guava;

import java.util.HashMap;

public final class Maps {

    private Maps() {
        super();
    }

    /**
     * Returns a capacity that is sufficient to keep the map from being resized as long as it grows no larger than
     * expectedSize and the load factor is >= its default (0.75).
     */
    static int capacity(int expectedSize) {
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

}
