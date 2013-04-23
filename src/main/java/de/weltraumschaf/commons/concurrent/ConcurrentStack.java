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
 * Stack implemented with compare-and-set algorithm from Brian Goetz.
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
class ConcurrentStack<E> implements Stack<E> {

    private final AtomicReference<Node<E>> top = new AtomicReference<Node<E>>();

    @Override
    public boolean isEmpty() {
        return null == top.get();
    }

    @Override
    public E peek() {
        final Node<E> currentTop = top.get();
        return null == currentTop
            ? null
            : currentTop.element;
    }

    @Override
    public E pop() {
        Node<E> currentTop;

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
            final Node<E> currentTop = top.get();

            if (top.compareAndSet(currentTop, new Node<E>(element, currentTop))) {
                break;
            }
        }
    }

    private static class Node<E> {
        private final E element;
        private final Node<E> next;

        private Node(final E element, final Node<E> next) {
            this.element = element;
            this.next = next;
        }

    }
}
