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

import java.net.URL;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JToolBar;

/**
 * This builder offers a internal DSL for creating tool bars.
 *
 * Example:
 * <code>
 * JToolBar toolbar = ToolBarBuilder.builder()
 *            .button("/de/weltraumschaf/swing/folder_16x16.gif")
 *                .toolTipText("Open an existing document.")
 *                .addListener(new Listener())
 *            .end()
 *            .button("/de/weltraumschaf/swing/disk_16x16.gif")
 *                .toolTipText("Save current document.")
 *                .addListener(new Listener())
 *            .end()
 *            .button("/de/weltraumschaf/swing/page_16x16.gif")
 *                .toolTipText("Create a new document.")
 *                .addListener(new Listener())
 *            .end()
 *            .create();
 * </code>
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
public final class ToolBarBuilder {

    /**
     * The built tool bar.
     */
    final JToolBar toolbar = new JToolBar();

    /**
     * Use {@link #builder()} instead.
     */
    private ToolBarBuilder() {
        super();
    }

    /**
     * Creates a new builder instance.
     *
     * @return Always a new instance.
     */
    public static ToolBarBuilder builder() {
        return new ToolBarBuilder();
    }

    /**
     * Creates a button in the tool bar.
     *
     * @param iconResource Path to the button's icon resource.
     * @return A button builder.
     */
    public ToolBarButtonBuilder button(final URL iconResource) {
        return button(new ImageIcon(iconResource));
    }

    /**
     * Creates a button in the tool bar.
     *
     * @param icon Icon of the button.
     * @return A button builder.
     */
    public ToolBarButtonBuilder button(final Icon icon) {
        final ToolBarButtonBuilder builder = new ToolBarButtonBuilder(icon, this);
        toolbar.add(builder.getButton());
        return builder;
    }

    /**
     * Returns the built tool bar.
     *
     * @return Returns always the same instance per builder.
     */
    public JToolBar create() {
        return toolbar;
    }

}
