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

import javax.swing.JMenuBar;

/**
 * This builder offers a internal DSL for creating menu bars.
 *
 * Example:
 * <code>
 * JMenuBar menubar = builder()
 *            .menu("File")
 *                .item("Open")
 *                    .addListener(new Listener())
 *                .end()
 *                .separator()
 *                .item("Save")
 *                    .addListener(new Listener())
 *                .end()
 *            .end()
 *            .menu("Edit")
 *                .item("foo")
 *                .end()
 *            .end()
 *            .menu("View")
 *                .item("bar")
 *                .end()
 *            .end()
 *            .menu("Window")
 *                .item("baz")
 *                .end()
 *            .end()
 *        .create();
 * </code>
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
public final class MenuBarBuilder {

    /**
     * The built menu bar.
     */
    private final JMenuBar menuBar = new JMenuBar();

    /**
     * Use {@link #builder()} instead.
     */
    private MenuBarBuilder() {
        super();
    }

    /**
     * Creates a new builder instance.
     *
     * @return Always a new instance.
     */
    public static MenuBarBuilder builder() {
        return new MenuBarBuilder();
    }

    /**
     * Returns the built menu bar.
     *
     * @return Returns always the same instance per builder.
     */
    public JMenuBar create() {
        return menuBar;
    }

    /**
     * Creates a menu entry in menu bar.
     *
     * @param name Name of the menu.
     * @return A menu builder.
     */
    public MenuBuilder menu(final String name) {
        final MenuBuilder builder = new MenuBuilder(name, this);
        menuBar.add(builder.getMenu());
        return builder;
    }

}
