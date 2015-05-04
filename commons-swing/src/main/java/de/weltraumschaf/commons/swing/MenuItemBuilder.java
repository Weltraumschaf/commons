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

import de.weltraumschaf.commons.system.OperatingSystem;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import javax.swing.JMenuItem;
import javax.swing.KeyStroke;

/**
 * Builder to create a menu item.
 * <p>
 * You should not use this class directly. Instead use {@link MenuBarBuilder}.
 * </p>
 *
 * @since 1.0.0
 * @author Sven Strittmatter &lt;weltraumschaf@googlemail.com&gt;
 */
public final class MenuItemBuilder {
    /**
     * Used for OS specific differences.
     */
    private static final OperatingSystem OS = OperatingSystem.determine(System.getProperty("os.name", ""));

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
     * Add an accelerator key.
     *
     * For Mac OS with mask {@link KeyEvent#META_DOWN_MASK} for all other systems
     * {@link KeyEvent#CTRL_DOWN_MASK}.
     *
     * @param accelerator Accelerator key character.
     * @return Returns the builder itself.
     */
    public MenuItemBuilder setAccelerator(final char accelerator) {
        if (OperatingSystem.MACOSX == OS) {
            menuItem.setAccelerator(KeyStroke.getKeyStroke(accelerator, KeyEvent.META_DOWN_MASK));
        } else {
            menuItem.setAccelerator(KeyStroke.getKeyStroke(accelerator, KeyEvent.CTRL_DOWN_MASK));
        }

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
