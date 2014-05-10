/*
 * LICENSE
 *
 * "THE BEER-WARE LICENSE" (Revision 43):
 * "Sven Strittmatter" <weltraumschaf(at)googlemail(dot)com> wrote this file.
 * As long as you retain this notice you can do whatever you want with
 * this stuff. If we meet some day, and you think this stuff is worth it,
 * you can buy me a non alcohol-free beer in return.
 *
 * Copyright (C) 2012 "Sven Strittmatter" <weltraumschaf(at)googlemail(dot)com>
 */

package de.weltraumschaf.commons.concurrent;

import de.weltraumschaf.commons.guava.Objects;
import java.util.concurrent.atomic.AtomicReference;

/**
 * Stack implemented with compare-and-set algorithm from Brian Goetz.
 *
 * Implemented with a linked list.
 *
 * @since 1.0.0
 * @param <E> type of stack entries
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
class ConcurrentStack<E> implements Stack<E> {

    /**
     * References the top of the stack.
     */
    private final AtomicReference<Entry<E>> top = new AtomicReference<Entry<E>>();

    @Override
    public boolean isEmpty() {
        return null == top.get();
    }

    @Override
    public E peek() {
        final Entry<E> currentTop = top.get();
        return null == currentTop
            ? null
            : currentTop.element;
    }

    @Override
    public E pop() {
        Entry<E> currentTop;

        while (true) {
            currentTop = top.get();

            if (null == currentTop) {
                return null;
            }

            if (top.compareAndSet(currentTop, currentTop.next)) {
                return currentTop.element;
            }
        }
    }

    @Override
    public void push(final E element) {
        while (true) {
            final Entry<E> currentTop = top.get();

            if (top.compareAndSet(currentTop, new Entry<E>(element, currentTop))) {
                break;
            }
        }
    }

    @Override
    public int hashCode() {
        throw new UnsupportedOperationException("Not implemented yet!");
    }

    @Override
    public boolean equals(Object obj) {
        throw  new UnsupportedOperationException("Not implemented yet!");
    }

    @Override
    public String toString() {
        throw new UnsupportedOperationException("Not implemented yet!");
    }

    /**
     * Linked list entry.
     *
     * Not used outside the class.
     *
     * @param <T> type of entry object
     */
    private static final class Entry<T> {
        /**
         * Entry element.
         */
        private final T element;
        /**
         * Link to next entry, maybe {@code null}.
         */
        private final Entry<T> next;

        /**
         * Dedicated constructor.
         *
         * @param element entry element
         * @param next next element
         */
        Entry(final T element, final Entry<T> next) {
            super();
            this.element = element;
            this.next = next;
        }

        @Override
        public int hashCode() {
            return Objects.hashCode(element, next);
        }

        @Override
        public boolean equals(Object obj) {
            if (!(obj instanceof Entry)) {
                return false;
            }

            final Entry other = (Entry) obj;
            return Objects.equal(element, other.element) && Objects.equal(next, other.next);
        }

        @Override
        public String toString() {
            final String nextHashcode = (null == next)
                    ? "null"
                    : Integer.toHexString(next.hashCode());
            return String.format("%s (%s -> %s)", element, Integer.toHexString(hashCode()), nextHashcode);
        }

    }
}
