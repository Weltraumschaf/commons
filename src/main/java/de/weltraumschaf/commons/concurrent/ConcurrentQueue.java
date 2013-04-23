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

import java.util.concurrent.atomic.AtomicReference;

/**
 * Queue implemented with compare-and-set algorithm from Brian Goetz.
 *
 * <pre>
 * remove()                                                     add()
 * HEAD    Node_0        Node_1        Node_2        Node_3     TAIL
 * null <-prev next-> <-prev next-> <-prev next-> <-prev next-> null
 * </pre>
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
class ConcurrentQueue<E> implements Queue<E> {

    private final AtomicReference<Node<E>> head = new AtomicReference<Node<E>>();
    private final AtomicReference<Node<E>> tail = new AtomicReference<Node<E>>();

    @Override
    public boolean empty() {
        return null == head && null == tail;
    }

    @Override
    public void add(E element) {
        final Node<E> node = new Node<E>(element);

        while (true) {
            final Node<E> currentTail = tail.get();
            node.prev = currentTail;

            if (tail.compareAndSet(currentTail, node)) {
                break;
            }
        }
    }

    @Override
    public E remove() {
        return null;
    }

    private static class Node<E> {
        private final E element;
        private Node<E> prev;
        private Node<E> next;

        private Node(final E element) {
            this.element = element;
        }

    }
}
