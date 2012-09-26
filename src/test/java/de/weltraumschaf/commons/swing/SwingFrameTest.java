/*
 * LICENSE
 *
 * "THE BEER-WARE LICENSE" (Revision 42):
 * "Sven Strittmatter" <weltraumschaf@googlemail.com> wrote this file.
 * As long as you retain this notice you can do whatever you want with
 * this stuff. If we meet some day, and you think this stuff is worth it,
 * you can buy me a beer in return.
 *
 */

package de.weltraumschaf.commons.swing;

import static org.junit.Assert.*;
import org.junit.Test;
import static org.mockito.Mockito.*;

/**
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
public class SwingFrameTest {

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

}
