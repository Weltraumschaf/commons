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
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;
import org.junit.Test;

/**
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
public class ConcurrentQueueTest extends MultithreadedTestCase {

    private Queue<Integer> sut = Concurrent.createQueue(); // Prevent NPE for normal tests.

    // Concurrent tests.

    @Override
    public void initialize() {
        sut = Concurrent.createQueue();
    }

    public void thread1() {
        assertThat(sut.isEmpty(), is(true));
        sut.add(1);
        sut.add(2);
        assertThat(sut.isEmpty(), is(false));
        waitForTick(2);
        assertThat(sut.isEmpty(), is(true));
    }

    public void thread2() {
        waitForTick(1);
        assertThat(sut.isEmpty(), is(false));
        assertThat(sut.get(), is(1));
        assertThat(sut.get(), is(2));
        assertThat(sut.isEmpty(), is(true));
        waitForTick(3);
    }

    @Override
    public void finish() {
        assertTrue(sut.isEmpty());
    }

    @Test public void concurrentAddAndGet() throws Throwable {
        TestFramework.runOnce(new ConcurrentQueueTest());
    }

    // Non concurrent tests.

    @Test public void isEmpty() {
        assertThat(sut.isEmpty(), is(true));
        sut.add(1);
        assertThat(sut.isEmpty(), is(false));
        sut.get();
        assertThat(sut.isEmpty(), is(true));

        sut.add(1);
        sut.add(2);
        sut.add(3);
        assertThat(sut.isEmpty(), is(false));
        sut.get();
        assertThat(sut.isEmpty(), is(false));
        sut.get();
        assertThat(sut.isEmpty(), is(false));
        sut.get();
        assertThat(sut.isEmpty(), is(true));
    }

    @Test public void addAndGet() {
        assertThat(sut.get(), is(nullValue()));

        sut.add(1);
        assertThat(sut.get(), is(1));
        assertThat(sut.get(), is(nullValue()));

        sut.add(1);
        sut.add(2);
        sut.add(3);
        assertThat(sut.get(), is(1));
        assertThat(sut.get(), is(2));
        assertThat(sut.get(), is(3));
        assertThat(sut.get(), is(nullValue()));
    }

}