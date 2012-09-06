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

package de.weltraumschaf.commons;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Holds {@link Runnable callbacks} to execute them in any order.
 *
 * Example:
 * <code>
 * ShutDownHook hook = new ShutDownHook();
 * hook.register(new Runnable() {
 *
 *      &#064;Override
 *      public void  run() {
 *          // Your shutdown code.
 *      }
 *
 * });
 * </code>
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
public class ShutDownHook extends Thread {

    /**
     * Set of callbacks.
     */
    private final Set<Runnable> callbacks = new HashSet<Runnable>();

    /**
     * Iterates over all {@link #callbacks} and run them.
     */
    @Override
    public void run() {
        final Iterator<Runnable> iterator = callbacks.iterator();

        while (iterator.hasNext()) {
            final Runnable callback = iterator.next();
            try {
                callback.run();
            } catch (Exception ex) {
                Logger.getLogger(ShutDownHook.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    /**
     * Registers a callback.
     *
     * @param callback Runnable object.
     * @return Return itself for method chaining.
     */
    public ShutDownHook register(final Runnable callback) {
        callbacks.add(callback);
        return this;
    }

}
