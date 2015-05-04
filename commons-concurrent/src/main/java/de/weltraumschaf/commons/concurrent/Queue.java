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

import net.jcip.annotations.ThreadSafe;

/**
 * Interface for queues (FIFO).
 *
 * @since 1.0.0
 * @param <E> type of stack entries
 * @author Sven Strittmatter &lt;weltraumschaf@googlemail.com&gt;
 * @version $Id: $Id
 */
@ThreadSafe
public interface Queue<E> {

    /**
     * Check if queue is empty.
     *
     * @return {@code true} if empty; else {@code false}
     */
    boolean isEmpty();

    /**
     * Add element to the tail of queue.
     *
     * @param element added entry
     */
    void add(E element);

    /**
     * Get element from head of queue.
     *
     * @return entry first element, {@code null} if queue is empty
     */
    E get();

}
