/*
 *  LICENSE
 *
 * "THE BEER-WARE LICENSE" (Revision 43):
 * "Sven Strittmatter" <weltraumschaf@googlemail.com> wrote this file.
 * As long as you retain this notice you can do whatever you want with
 * this stuff. If we meet some day, and you think this stuff is worth it,
 * you can buy me a non alcohol-free beer in return.
 *
 * Copyright (C) 2012 "Sven Strittmatter" <weltraumschaf@googlemail.com>
 */
package de.weltraumschaf.commons.concurrent;

import edu.umd.cs.mtc.MultithreadedTestCase;
import edu.umd.cs.mtc.TestFramework;
import org.junit.Test;

/**
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
public class ConcurrentStackTest extends MultithreadedTestCase {

    private Stack<Object> sut;

    @Override
    public void initialize() {
        sut = Concurrent.createStack();
    }

    @Override
    public void finish() {
        assertTrue(sut.isEmpty());
    }

    @Test public void testMTCInterruptedAcquire() throws Throwable {
        TestFramework.runOnce(new ConcurrentStackTest());
    }

}