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
import javax.swing.JMenuItem;

/**
 * Builder to create a menu item.
 *
 * You should not use this class directly. Instead use {@link MenuBarBuilder}.
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
public final class MenuItemBuilder {

    /**
     * The built item.
     */
    private final JMenuItem menuItem;

    /**
     * The menu builder which creates the menu to which item will belong.
     */
    private final MenuBuilder parent;

    /**
     * Package private constructor.
     *
     * This sub builder is constructed by the {@link MenuBuilder}.
     *
     * @param name Name of the item.
     * @param parent Constructing menu builder.
     */
    MenuItemBuilder(final String name, final MenuBuilder parent) {
        super();
        this.menuItem = new JMenuItem(name);
        this.parent   = parent;
    }

    /**
     * Get the built item.
     *
     * @return Always return the same instance per builder.
     */
    JMenuItem getMenuItem() {
        return menuItem;
    }

    /**
     * Add a listener to the item.
     *
     * @param listener Action listener to handle events.
     * @return Returns the builder itself.
     */
    public MenuItemBuilder addListener(final ActionListener listener) {
        menuItem.addActionListener(listener);
        return this;
    }

    /**
     * Call if ready with building the menu item.
     *
     * @return The parent menu builder.
     */
    public MenuBuilder end() {
        return parent;
    }
}
