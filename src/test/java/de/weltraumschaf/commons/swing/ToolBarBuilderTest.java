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

import java.awt.event.ActionListener;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JToolBar;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Ignore;
import static org.mockito.Mockito.*;

/**
 * Test cases for tool bar builder.
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
public class ToolBarBuilderTest {

    @Test
    public void testSomeMethod() {
        final ActionListener listener1 = mock(ActionListener.class);
        final ActionListener listener2 = mock(ActionListener.class);
        final ActionListener listener3 = mock(ActionListener.class);
        final String tooltip1 = "Open an existing document.";
        final String tooltip2 = "Save current document.";
        final String tooltip3 = "Create a new document.";
        final String path = "/de/weltraumschaf/commons/swing";
        final Icon icon1 = new ImageIcon(getClass().getResource(path + "/folder_16x16.gif"));
        final Icon icon2 = new ImageIcon(getClass().getResource(path + "/disk_16x16.gif"));
        final Icon icon3 = new ImageIcon(getClass().getResource(path + "/page_16x16.gif"));

        JToolBar toolbar = ToolBarBuilder.builder()
            .button(icon1)
                .toolTipText(tooltip1)
                .addListener(listener1)
            .end()
            .button(icon2)
                .toolTipText(tooltip2)
                .addListener(listener2)
            .end()
            .button(icon3)
                .toolTipText(tooltip3)
                .addListener(listener3)
            .end()
            .create();

        assertEquals(3, toolbar.getComponentCount());

        final JButton folder = (JButton) toolbar.getComponentAtIndex(0);
        assertSame(listener1, folder.getActionListeners()[0]);
        assertEquals(tooltip1, folder.getToolTipText());
        assertSame(icon1, folder.getIcon());

        final JButton disk = (JButton) toolbar.getComponentAtIndex(1);
        assertSame(listener2, disk.getActionListeners()[0]);
        assertEquals(tooltip2, disk.getToolTipText());
        assertSame(icon2, disk.getIcon());

        final JButton page = (JButton) toolbar.getComponentAtIndex(2);
        assertSame(listener3, page.getActionListeners()[0]);
        assertEquals(tooltip3, page.getToolTipText());
        assertSame(icon3, page.getIcon());
    }

}