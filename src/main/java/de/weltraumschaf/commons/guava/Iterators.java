package de.weltraumschaf.commons.guava;

import java.util.Collection;
import java.util.Iterator;

public final class Iterators {

    private Iterators() {
        super();
    }


    /**
     * Adds all elements in {@code iterator} to {@code collection}. The iterator will be left exhausted: its
     * {@code hasNext()} method will return {@code false}.
     *
     * @return {@code true} if {@code collection} was modified as a result of this operation
     */
    public static <T> boolean addAll(
            Collection<T> addTo, Iterator<? extends T> iterator) {
        checkNotNull(addTo);
        checkNotNull(iterator);
        boolean wasModified = false;
        while (iterator.hasNext()) {
            wasModified |= addTo.add(iterator.next());
        }
        return wasModified;
    }

    /**
     * Ensures that an object reference passed as a parameter to the calling method is not null.
     *
     * @param reference an object reference
     * @return the non-null reference that was validated
     * @throws NullPointerException if {@code reference} is null
     */
    public static <T> T checkNotNull(T reference) {
        if (reference == null) {
            throw new NullPointerException();
        }
        return reference;
    }
}
