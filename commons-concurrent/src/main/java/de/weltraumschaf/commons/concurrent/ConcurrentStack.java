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
import net.jcip.annotations.ThreadSafe;

/**
 * Stack implemented with compare-and-set algorithm from Brian Goetz.
 * <p>
 * Implemented with a linked list.
 * </p>
 *
 * <pre>
 * {@code
 * TOP     Node_0        Node_1        Node_2        Node_3     Node_4
 * null    next->        next->        next->        next->     null
 * }</pre>
 *
 * @since 1.0.0
 * @param <E> type of stack entries
 * @author Sven Strittmatter &lt;weltraumschaf@googlemail.com&gt;
 */
@ThreadSafe
final class ConcurrentStack<E> implements Stack<E> {

    /**
     * References the top of the stack.
     */
    private final AtomicReference<Entry<E>> top = new AtomicReference<>();

    @Override
    public boolean isEmpty() {
        return null == top.get();
    }

    @Override
    public E peek() {
        final Entry<E> currentTop = top.get();
        return null == currentTop
            ? null
            : currentTop.value;
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
                return currentTop.value;
            }
        }
    }

    @Override
    public void push(final E element) {
        while (true) {
            final Entry<E> currentTop = top.get();

            if (top.compareAndSet(currentTop, new Entry<>(element, currentTop))) {
                break;
            }
        }
    }

    @Override
    public int hashCode() {
        final Entry<E> currentTop = top.get();

        if (null == currentTop) {
            return 0;
        }

        return currentTop.hashCode();
    }

    @Override
    public boolean equals(final Object obj) {
        if (!(obj instanceof ConcurrentStack)) {
            return false;
        }

        final ConcurrentStack other = (ConcurrentStack) obj;
        return Objects.equal(top.get(), other.top.get());
    }

    @Override
    public String toString() {
        final StringBuilder buffer = new StringBuilder();
        buffer.append(getClass().getSimpleName()).append('[');

        Entry<E> element = top.get();
        boolean first = true;

        while (null != element) {
            if (!first) {
                buffer.append(", ");
            }

            buffer.append(element.value);
            element = element.next;
            first = false;
        }

        return buffer.append(']').toString();
    }

    /**
     * Linked list entry.
     * <p>
     * Not used outside the class.
     * </p>
     *
     * @param <T> type of entry object
     */
    private static final class Entry<T> {

        /**
         * Entry element.
         */
        private final T value;
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
            this.value = element;
            this.next = next;
        }

        @Override
        public int hashCode() {
            return Objects.hashCode(value, next);
        }

        @Override
        public boolean equals(Object obj) {
            if (!(obj instanceof Entry)) {
                return false;
            }

            final Entry other = (Entry) obj;
            return Objects.equal(value, other.value) && Objects.equal(next, other.next);
        }

    }
}
