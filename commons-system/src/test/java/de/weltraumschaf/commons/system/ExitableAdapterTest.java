/*
 *  LICENSE
 *
 * "THE BEER-WARE LICENSE" (Revision 43):
 * "Sven Strittmatter" &lt;weltraumschaf@googlemail.com&gt; wrote this file.
 * As long as you retain this notice you can do whatever you want with
 * this stuff. If we meet some day, and you think this stuff is worth it,
 * you can buy me a non alcohol-free beer in return.
 *
 * Copyright (C) 2012 "Sven Strittmatter" &lt;weltraumschaf@googlemail.com&gt;
 */

package de.weltraumschaf.commons.system;

import org.junit.Test;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Tests for {@link ExitableAdapter}.
 *
 * @author Sven Strittmatter &lt;weltraumschaf@googlemail.com&gt;
 */
public class ExitableAdapterTest {

    private final Exitable sut = spy(new ExitableAdapterStub());

    @Test
    public void exit() {
        final ExitCode status = mock(ExitCode.class);
        when(status.getCode()).thenReturn(42);

        sut.exit(status);

        verify(sut).exit(42);
    }

    private static class ExitableAdapterStub extends ExitableAdapter {

        @Override
        public void exit(int status) {

        }

    }
}
