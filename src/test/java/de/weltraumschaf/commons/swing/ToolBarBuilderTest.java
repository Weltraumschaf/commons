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

    @Test @Ignore
    public void testSomeMethod() {
        final ActionListener listener = mock(ActionListener.class);
        JToolBar toolbar = ToolBarBuilder.builder()
            .button("/de/weltraumschaf/commons/swing/folder_16x16.gif")
                .toolTipText("Open an existing document.")
                .addListener(listener)
            .end()
            .button("/de/weltraumschaf/commons/swing/disk_16x16.gif")
                .toolTipText("Save current document.")
                .addListener(listener)
            .end()
            .button("/de/weltraumschaf/commons/swing/page_16x16.gif")
                .toolTipText("Create a new document.")
                .addListener(listener)
            .end()
            .create();
    }

}