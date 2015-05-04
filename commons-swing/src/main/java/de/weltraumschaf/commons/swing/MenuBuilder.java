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

import javax.swing.JMenu;

/**
 * Builder to create a menu.
 * <p>
 * You should not use this class directly. Instead use {@link MenuBarBuilder}.
 * </p>
 *
 * @since 1.0.0
 * @author Sven Strittmatter &lt;weltraumschaf@googlemail.com&gt;
 */
public final class MenuBuilder {

    /**
     * The built menu.
     */
    private final JMenu menu;

    /**
     * The menu bar builder which creates the menu bar to which menu will belong.
     */
    private final MenuBarBuilder parent;

    /**
     * Package private constructor.
     *
     * This sub builder is constructed by the {@link MenuBarBuilder}.
     *
     * @param name Name of the menu.
     * @param parent Constructing menu bar builder.
     */
    MenuBuilder(final String name, final MenuBarBuilder parent) {
        super();
        this.menu   = new JMenu(name);
        this.parent = parent;
    }

    /**
     * Get the built menu.
     *
     * @return Always return the same instance per builder.
     */
    JMenu getMenu() {
        return menu;
    }

    /**
     * Creates a menu item in the menu.
     *
     * @param name Name of the item.
     * @return A menu item builder.
     */
    public MenuItemBuilder item(final String name) {
        final MenuItemBuilder builder = new MenuItemBuilder(name, this);
        menu.add(builder.getMenuItem());
        return builder;
    }

    /**
     * Add a separator to the menu.
     *
     * @return Returns the menu builder itself.
     */
    public MenuBuilder separator() {
        menu.addSeparator();
        return this;
    }

    /**
     * Call if ready with building the menu.
     *
     * @return The parent menu bar builder.
     */
    public MenuBarBuilder end() {
        return parent;
    }

}
