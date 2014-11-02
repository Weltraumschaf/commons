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
 * Queue implemented with compare-and-set algorithm from Brian Goetz.
 * <p>
 * Implemented with a linked list.
 * </p>
 *
 * <pre>
 * HEAD    Node_0        Node_1        Node_2        Node_3     TAIL
 * null    next->        next->        next->        next->     null
 * </pre>
 *
 * @since 1.0.0
 * @param <E> type of queue entries
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
@ThreadSafe
final class ConcurrentQueue<E> implements Queue<E> {

    /**
     * References the head of the linked list.
     */
    private final AtomicReference<Entry<E>> head = new AtomicReference<Entry<E>>();
    /**
     * References the tail of the linked list.
     */
    private final AtomicReference<Entry<E>> tail = new AtomicReference<Entry<E>>();

    @Override
    public boolean isEmpty() {
        return null == head.get() && null == tail.get();
    }

    @Override
    public void add(E element) {
        final Entry<E> node = new Entry<E>(element);

        while (true) {
            if (null == head.get()) {
                // First element.
                if (head.compareAndSet(null, node)) {
                    break;
                }
            } else {
                final Entry<E> currentTail = tail.get();

                if (null == currentTail) {
                    final Entry<E> currentHead = head.get();
                    currentHead.next = node;

                    if (tail.compareAndSet(null, node) && head.compareAndSet(currentHead, currentHead)) {
                        break;
                    }
                } else {
                    currentTail.next = node;

                    if (tail.compareAndSet(currentTail, node)) {
                        break;
                    }
                }

            }
        }
    }

    @Override
    public E get() {
        while (true) {
            final Entry<E> currentHead = head.get();

            if (null == currentHead) {
                return null;
            }

            if (null == currentHead.next) {
                if (head.compareAndSet(currentHead, currentHead.next) && tail.compareAndSet(tail.get(), null)) {
                    return currentHead.value;
                }
            } else {
                if (head.compareAndSet(currentHead, currentHead.next)) {
                    return currentHead.value;
                }
            }
        }

    }

    @Override
    public int hashCode() {
        final Entry<E> currentTop = head.get();

        if (null == currentTop) {
            return 0;
        }

        return currentTop.hashCode();
    }

    @Override
    public boolean equals(final Object obj) {
        if (!(obj instanceof ConcurrentQueue)) {
            return false;
        }

        final ConcurrentQueue other = (ConcurrentQueue) obj;
        return Objects.equal(head.get(), other.head.get()) && Objects.equal(tail.get(), other.tail.get());
    }

    @Override
    public String toString() {
        final StringBuilder buffer = new StringBuilder();
        buffer.append(getClass().getSimpleName()).append('[');

        Entry<E> element = head.get();
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
     *
     * Not used outside the class.
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
        private Entry<T> next;

        /**
         * Dedicated constructor.
         *
         * @param element entry element
         */
        Entry(final T element) {
            super();
            this.value = element;
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
