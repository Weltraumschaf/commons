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
package de.weltraumschaf.commons.swing;

import de.weltraumschaf.commons.system.Exitable;
import java.awt.BorderLayout;
import java.awt.event.WindowEvent;
import static org.junit.Assert.*;
import org.junit.Test;
import static org.mockito.Mockito.*;

/**
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
public class SwingFrameTest {

    @Test
    public void constructObject() {
        final String title = "foobar";
        final SwingFrame sut = new SwingFrame(title);
        assertEquals(title, sut.getTitle());
        assertTrue(sut.getContentPane().getLayout() instanceof BorderLayout);
        assertSame(sut.getPanel(), sut.getContentPane().getComponent(0));
    }

    @Test
    public void initWithoutBindExitOnClose() {
        final SwingFrame sut = mock(SwingFrame.class, CALLS_REAL_METHODS);
        sut.init();

        verify(sut, times(1)).initLookAndFeel();
        verify(sut, never()).bindWindowClosing();
        verify(sut, times(1)).initMenu();
        verify(sut, times(1)).initToolBar();
        verify(sut, times(1)).initPanel();
    }

    @Test
    public void initWithBindExitOnClose() {
        final SwingFrame sut = mock(SwingFrame.class, CALLS_REAL_METHODS);
        sut.setExitOnCloseWindow(true);
        sut.init();

        verify(sut, times(1)).initLookAndFeel();
        verify(sut, times(1)).bindWindowClosing();
        verify(sut, times(1)).initMenu();
        verify(sut, times(1)).initToolBar();
        verify(sut, times(1)).initPanel();
    }

    @Test public void isMacOs() {
        if (System.getProperty("os.name").equals(SwingFrame.MAC_OSX)) {
            assertTrue(SwingFrame.isMacOs());
        } else {
            assertFalse(SwingFrame.isMacOs());
        }
    }

    @Test public void bindAndExitOnWindowClosing() {
        final SwingFrame sut = new SwingFrame("foobar");
        final Exitable exiter = mock(Exitable.class);

        sut.setExiter(exiter);
        sut.setExitOnCloseWindow(true);
        sut.init();
        sut.dispatchEvent(new WindowEvent(sut, WindowEvent.WINDOW_CLOSING));

        verify(exiter, times(1)).exit(0);
    }

    @Test public void notBindAndExitOnWindowClosing() {
        final SwingFrame sut = new SwingFrame("foobar");
        final Exitable exiter = mock(Exitable.class);

        sut.setExiter(exiter);
        sut.init();
        sut.dispatchEvent(new WindowEvent(sut, WindowEvent.WINDOW_CLOSING));

        verify(exiter, never()).exit(0);
    }

}
