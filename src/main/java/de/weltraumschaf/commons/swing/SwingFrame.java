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

import de.weltraumschaf.commons.system.DefaultExiter;
import de.weltraumschaf.commons.system.Exitable;
import java.awt.BorderLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

/**
 * Extension of {@link javax.swing.JFrame} with {@link java.awt.BorderLayout} for easier use.
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
public class SwingFrame extends JFrame {

    /**
     * System property for Mac OS X.
     */
    static final String MAC_OSX = "Mac OS X";

    /**
     * Logging facility.
     */
    private static final Logger LOGGER = Logger.getLogger(SwingFrame.class.getName());

    /**
     * Id for serialization.
     */
    private static final long serialVersionUID = 1L;

    /**
     * Centered panel for the main UI in a bordered layout.
     */
    protected final JPanel panel = new JPanel();

    /**
     * Whether to exit the whole application on closing window.
     */
    private boolean exitOnCloseWindow;

    /**
     * Abstraction for {@link System#exit(int)}.
     */
    private transient Exitable exiter = new DefaultExiter();

    /**
     * Initializes the content pane layout with {@link java.awt.BorderLayout} and add
     * {@link #panel} to the center.
     *
     * @param title the title for the frame
     */
    public SwingFrame(final String title) {
        super(title);
        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(panel, BorderLayout.CENTER);
    }

    /**
     * Call this method to initializes the frame before displaying it.
     */
    public final void init() {
        initLookAndFeel();

        if (exitOnCloseWindow) {
            bindWindowClosing();
        }

        initMenu();
        initToolBar();
        initPanel();
    }

    /**
     * Whether to exit application on closing the window.
     *
     * By default {@link #exitOnCloseWindow} is false.
     *
     * @param exitOnCloseWindow True or false.
     */
    public final void setExitOnCloseWindow(boolean exitOnCloseWindow) {
        this.exitOnCloseWindow = exitOnCloseWindow;
    }

    /**
     * Set an exiter to handle exit calls.
     *
     * @param exiter Object which handles exit calls.
     */
    public final void setExiter(final Exitable exiter) {
        this.exiter = exiter;
    }

    /**
     * Get the main panel of the frame to add components.
     *
     * @return Returns reference of {@link #panel}.
     */
    public final JPanel getPanel() {
        return panel;
    }

    /**
     * Returns true if the host OS is Mac OS X.
     *
     * @return true or false.
     */
    public static boolean isMacOs() {
        return System.getProperty("os.name").equals(MAC_OSX);
    }

    /**
     * Initializes the look and feel.
     *
     * Uses the system look and feel. If the guest OS is Mac OS x the menu
     * bar is took off the frame.
     */
    protected void initLookAndFeel() {
        if (isMacOs()) {
            // take the menu bar off the jframe
            System.setProperty("apple.laf.useScreenMenuBar", "true");
        }

        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException ex) {
            LOGGER.log(Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            LOGGER.log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            LOGGER.log(Level.SEVERE, null, ex);
        } catch (UnsupportedLookAndFeelException ex) {
            LOGGER.log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Binds on the window closing event and calls the exiter to exit application.
     *
     * This method add a default window listener which {@link #exiter exits}
     * on {@link WindowAdapter#windowClosing(java.awt.event.WindowEvent)}.
     * You may override this method for other behaviour. This method is only
     * invoked if {@link #setExitOnCloseWindow(boolean)} is set true.
     */
    protected void bindWindowClosing() {
        addWindowListener(new WindowAdapter() {

            @Override
            public void windowClosing(WindowEvent e) {
                exiter.exit(0);
            }

        });
    }

    /**
     * Template method for menu initialization.
     *
     * Override this method with your custom code, if you need a menu.
     */
    protected void initMenu() {
        // Empty template method.
    }

    /**
     * Template method for tool bar initialization.
     *
     * Override this method with your custom code, if you need a tool bar.
     */
    protected void initToolBar() {
        // Empty template method.
    }

    /**
     * Template method for main UI initialization.
     *
     * Override this method with your custom code to add components
     * to {@link #panel}.
     */
    protected void initPanel() {
        // Empty template method.
    }

}
