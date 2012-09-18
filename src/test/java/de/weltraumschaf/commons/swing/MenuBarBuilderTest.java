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
import javax.swing.JMenuBar;
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
        final ActionListener listener = mock(ActionListener.class);

        JMenuBar menubar = MenuBarBuilder.builder()
            .menu("File")
                .item("Open")
                    .addListener(listener)
                .end()
                .separator()
                .item("Save")
                    .addListener(listener)
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
    }

}