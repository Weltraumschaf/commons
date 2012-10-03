/*
 * LICENSE
 *
 * "THE BEER-WARE LICENSE" (Revision 43):
 * "Sven Strittmatter" <weltraumschaf(at)googlemail(dot)com> wrote this file.
 * As long as you retain this notice you can do whatever you want with
 * this stuff. If we meet some day, and you think this stuff is worth it,
 * you can buy me a non alcohol free beer in return.
 *
 * Copyright (C) 2012 "Sven Strittmatter" <weltraumschaf(at)googlemail(dot)com>
 */
package de.weltraumschaf.commons.swing;

import java.awt.event.ActionListener;
import java.net.URL;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JToolBar;
import static org.junit.Assert.*;
import org.junit.Test;
import static org.mockito.Mockito.*;

/**
 * Test cases for tool bar builder.
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
public class ToolBarBuilderTest {

    @Test
    public void buildToolBar() {
        final ActionListener listener1 = mock(ActionListener.class);
        final ActionListener listener2 = mock(ActionListener.class);
        final ActionListener listener3 = mock(ActionListener.class);
        final String tooltip1 = "Open an existing document.";
        final String tooltip2 = "Save current document.";
        final String tooltip3 = "Create a new document.";
        final String path = "/de/weltraumschaf/commons/swing";
        final URL icon1 = getClass().getResource(path + "/folder_16x16.gif");
        final Icon icon2 = new ImageIcon(getClass().getResource(path + "/disk_16x16.gif"));
        final Icon icon3 = new ImageIcon(getClass().getResource(path + "/page_16x16.gif"));

        final JToolBar toolbar = ToolBarBuilder.builder()
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

        final JButton folder = (JButton) toolbar.getComponent(0);
        assertSame(listener1, folder.getActionListeners()[0]);
        assertEquals(tooltip1, folder.getToolTipText());
        assertNotNull(folder.getIcon());

        final JButton disk = (JButton) toolbar.getComponent(1);
        assertSame(listener2, disk.getActionListeners()[0]);
        assertEquals(tooltip2, disk.getToolTipText());
        assertSame(icon2, disk.getIcon());

        final JButton page = (JButton) toolbar.getComponent(2);
        assertSame(listener3, page.getActionListeners()[0]);
        assertEquals(tooltip3, page.getToolTipText());
        assertSame(icon3, page.getIcon());
    }

}
