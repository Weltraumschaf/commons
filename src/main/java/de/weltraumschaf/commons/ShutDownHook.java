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
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
public class ShutDownHook extends Thread {

    private final Set<Runnable> shutDownCallbacks = new HashSet<Runnable>();

    @Override
    public void run() {
        final Iterator<Runnable> iterator = shutDownCallbacks.iterator();

        while (iterator.hasNext()) {
            final Runnable callback = iterator.next();
            try {
                callback.run();
            } catch (Exception ex) {
                Logger.getLogger(ShutDownHook.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public ShutDownHook registerShutdownCallback(final Runnable callback) {
        shutDownCallbacks.add(callback);
        return this;
    }

}
