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
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Ignore;
import static org.mockito.Mockito.*;

/**
 * Test cases for Menu bar builder.
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
public class MenuBarBuilderTest {

    @Test @Ignore
    public void buildMenuBar() {
        final ActionListener listener1 = mock(ActionListener.class);
        final ActionListener listener2 = mock(ActionListener.class);

        JMenuBar menubar = MenuBarBuilder.builder()
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

        final JMenu file = (JMenu) menubar.getComponent(0);
        assertEquals("File", file.getName());
        assertEquals(2, file.getComponentCount());
        final JMenuItem open = (JMenuItem) file.getComponent(0);
        assertEquals("Open", open.getName());
        assertSame(listener1, open.getActionListeners()[0]);
        final JMenuItem save = (JMenuItem) file.getComponent(1);
        assertEquals("Save", open.getName());
        assertSame(listener2, open.getActionListeners()[0]);

        final JMenu edit = (JMenu) menubar.getComponent(0);
        assertEquals("Edit", edit.getName());
        assertEquals(1, edit.getComponentCount());
        final JMenuItem foo = (JMenuItem) file.getComponent(0);
        assertEquals("foo", foo.getName());

        final JMenu view = (JMenu) menubar.getComponent(0);
        assertEquals("View", view.getName());
        assertEquals(1, view.getComponentCount());
        final JMenuItem bar = (JMenuItem) file.getComponent(0);
        assertEquals("bar", bar.getName());

        final JMenu window = (JMenu) menubar.getComponent(0);
        assertEquals("Window", window.getName());
        assertEquals(1, window.getComponentCount());
        final JMenuItem baz = (JMenuItem) file.getComponent(0);
        assertEquals("baz", baz.getName());
    }

}