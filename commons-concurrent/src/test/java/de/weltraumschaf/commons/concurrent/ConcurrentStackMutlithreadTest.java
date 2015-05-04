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

import edu.umd.cs.mtc.MultithreadedTestCase;
import edu.umd.cs.mtc.TestFramework;
import org.junit.Test;
import static org.junit.Assert.assertThat;
import static org.hamcrest.CoreMatchers.*;

/**
 * Multithreaded tests for {@link ConcurrentStack}.
 *
 * @author Sven Strittmatter &lt;weltraumschaf@googlemail.com&gt;
 */
public class ConcurrentStackMutlithreadTest extends MultithreadedTestCase {

    private Stack<Integer> sut = Concurrent.newStack(); // Prevent NPE for normal tests.

    // Concurrent tests.

    @Override
    public void initialize() {
        sut = Concurrent.newStack();
    }

    public void thread1() {
        assertThat(sut.isEmpty(), is(true));
        sut.push(1);
        sut.push(2);
        assertThat(sut.isEmpty(), is(false));
        waitForTick(2);
        assertThat(sut.isEmpty(), is(true));
    }

    public void thread2() {
        waitForTick(1);
        assertThat(sut.isEmpty(), is(false));
        assertThat(sut.pop(), is(2));
        assertThat(sut.pop(), is(1));
        assertThat(sut.isEmpty(), is(true));
        waitForTick(3);
    }

    @Override
    public void finish() {
        assertTrue(sut.isEmpty());
    }

    @Test public void concurrentPushAndPop() throws Throwable {
        TestFramework.runOnce(new ConcurrentStackMutlithreadTest());
    }

    // Non concurrent tests.

    @Test public void isEmpty() {
        assertThat(sut.isEmpty(), is(true));
        sut.push(1);
        assertThat(sut.isEmpty(), is(false));
        sut.pop();
        assertThat(sut.isEmpty(), is(true));
        sut.push(1);
        sut.push(1);
        sut.push(1);
        assertThat(sut.isEmpty(), is(false));
        sut.pop();
        assertThat(sut.isEmpty(), is(false));
        sut.pop();
        assertThat(sut.isEmpty(), is(false));
        sut.pop();
        assertThat(sut.isEmpty(), is(true));
    }

    @Test public void pushAndPop() {
        assertThat(sut.pop(), is(nullValue()));

        sut.push(1);
        assertThat(sut.pop(), is(1));
        assertThat(sut.pop(), is(nullValue()));

        sut.push(1);
        sut.push(2);
        sut.push(3);
        assertThat(sut.pop(), is(3));
        assertThat(sut.pop(), is(2));
        assertThat(sut.pop(), is(1));
        assertThat(sut.pop(), is(nullValue()));
    }

}