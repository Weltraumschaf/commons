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

/**
 * Factory to create implementations.
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
public final class Concurrent {

    /**
     * Hidden for utility class.
     */
    private Concurrent() {
        super();
    }

    /**
     * Create a stack.
     *
     * @param <E> type of stack entries
     * @return new instance
     */
    public static <E> Stack<E> createStack() {
        return new ConcurrentStack<E>();
    }

    /**
     * Create a queue.
     *
     * @param <E> type of queue entries
     * @return new instance
     */
    public static <E> Queue<E> createQueue() {
        return new ConcurrentQueue<E>();
    }

}