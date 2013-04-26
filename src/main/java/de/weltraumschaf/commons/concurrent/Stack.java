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
 * Interface for a stack (LIFO).
 *
 * @param <E> type of stack entries
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
public interface Stack<E> {

    /**
     * Check if stack is empty.
     *
     * @return {@code true} if empty; else {@code false}
     */
    boolean isEmpty();

    /**
     * Returns the top element of stack w/o remove it.
     *
     * @return top element, {@code null} if stack is empty
     */
    E peek();

    /**
     * Returns the top element of stack and removes it.
     *
     * @return top element, {@code null} if stack is empty
     */
    E pop();

    /**
     * Push entry on top of the stack.
     *
     * @param element pushed entry
     */
    void push(E element);

}
