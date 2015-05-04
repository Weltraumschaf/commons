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

import java.awt.event.ActionListener;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import static org.junit.Assert.*;
import org.junit.Test;
import static org.mockito.Mockito.*;

/**
 * Test cases for Menu bar builder.
 *
 * @author Sven Strittmatter &lt;weltraumschaf@googlemail.com&gt;
 */
public class MenuBarBuilderTest {

    @Test
    public void buildMenuBar() {
        final ActionListener listener1 = mock(ActionListener.class);
        final ActionListener listener2 = mock(ActionListener.class);

        final JMenuBar menubar = MenuBarBuilder.builder()
            .menu("File")
                .item("Open")
                    .addListener(listener1)
                .end()
                .separator()
                .item("Save")
                    .addListener(listener2)
                .end()
            .end()
            .menu("Edit")
                .item("foo")
                .end()
            .end()
            .menu("View")
                .item("bar")
                .end()
            .end()
            .menu("Window")
                .item("baz")
                .end()
            .end()
            .create();

        assertEquals(4, menubar.getComponentCount());

        final JMenu file = menubar.getMenu(0);
        assertEquals("File", file.getActionCommand());
        assertEquals(3, file.getItemCount());
        final JMenuItem open = file.getItem(0);
        assertEquals("Open", open.getActionCommand());
        assertSame(listener1, open.getActionListeners()[0]);
        assertNull(file.getItem(1));
        final JMenuItem save = file.getItem(2);
        assertEquals("Save", save.getActionCommand());
        assertSame(listener2, save.getActionListeners()[0]);

        final JMenu edit = menubar.getMenu(1);
        assertEquals("Edit", edit.getActionCommand());
        assertEquals(1, edit.getItemCount());
        final JMenuItem foo = edit.getItem(0);
        assertEquals("foo", foo.getActionCommand());

        final JMenu view = menubar.getMenu(2);
        assertEquals("View", view.getActionCommand());
        assertEquals(1, view.getItemCount());
        final JMenuItem bar = view.getItem(0);
        assertEquals("bar", bar.getActionCommand());

        final JMenu window = menubar.getMenu(3);
        assertEquals("Window", window.getActionCommand());
        assertEquals(1, window.getItemCount());
        final JMenuItem baz = window.getItem(0);
        assertEquals("baz", baz.getActionCommand());
    }

}
