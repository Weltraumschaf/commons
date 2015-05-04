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
import javax.swing.Icon;
import javax.swing.JButton;

/**
 * Builder to create a tool bar button.
 * <p>
 * You should not use this class directly. Instead use {@link ToolBarBuilder}.
 * </p>
 *
 * @since 1.0.0
 * @author Sven Strittmatter &lt;weltraumschaf@googlemail.com&gt;
 */
public final class ToolBarButtonBuilder {

    /**
     * The built button.
     */
    private final JButton button;

    /**
     * The tool bar builder which creates the tool bar to which button will belong.
     */
    private final ToolBarBuilder parent;

    /**
     * Package private constructor.
     *
     * This sub builder is constructed by the {@link ToolBarBuilder}.
     *
     * @param image Image of the button.
     * @param parent Constructing tool bar builder.
     */
    ToolBarButtonBuilder(final Icon image, final ToolBarBuilder parent) {
        this.button = new JButton(image);
        this.parent = parent;
    }

    /**
     * Get the built button.
     *
     * @return Always return the same instance per builder.
     */
    JButton getButton() {
        return button;
    }

    /**
     * Set tool tip text of the button.
     *
     * @param text Tool tip text.
     * @return The builder itself.
     */
    public ToolBarButtonBuilder toolTipText(final String text) {
        button.setToolTipText(text);
        return this;
    }

    /**
     * Add a listener to the button.
     *
     * @param listener Action listener to handle events.
     * @return Returns the builder itself.
     */
    public ToolBarButtonBuilder addListener(final ActionListener listener) {
        button.addActionListener(listener);
        return this;
    }

    /**
     * Call if ready with building the button.
     *
     * @return The parent tool bar builder.
     */
    public ToolBarBuilder end() {
        return parent;
    }

}
