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

import de.weltraumschaf.commons.system.DefaultExiter;
import de.weltraumschaf.commons.system.Exitable;
import de.weltraumschaf.commons.system.OperatingSystem;
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
 * <p>
 * Example:
 * </p>
 * <pre>{@code
 *  public final class MyFrame extends SwingFrame {
 *
 *      public MyFrame(final String title) {
 *          super(title);
 *          setDefaultCloseOperation(DISPOSE_ON_CLOSE); // optional
 *          setExitOnCloseWindow(true); // optional
 *      }
 *
 *      &#064;Override
 *      protected void initMenu() {
 *          final MenuBarBuilder builder = MenuBarBuilder.builder();
 *          // Build and set menu here.
 *          setJMenuBar(builder.create());
 *      }
 *
 *      &#064;Override
 *      protected void initToolBar() {
 *          final ToolBarBuilder builder = ToolBarBuilder.builder();
 *          // Build and set tool bar here.
 *          getContentPane().add(builder.create(), BorderLayout.NORTH);
 *      }
 *
 *      &#064;Override
 *      protected void initPanel() {
 *          // Initialize main panel here.
 *      }
 *  }
 * }</pre>
 *
 * @since 1.0.0
 * @author Sven Strittmatter &lt;weltraumschaf@googlemail.com&gt;
 * @version $Id: $Id
 */
public class SwingFrame extends JFrame {

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
     * Initializes the content pane layout with {@link java.awt.BorderLayout} and add {@link #panel} to the center.
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
     * Initializes the look and feel.
     *
     * Uses the system look and feel. If the guest OS is Mac OS x the menu bar is took off the frame.
     */
    protected void initLookAndFeel() {
        final OperatingSystem os = OperatingSystem.determine(OperatingSystem.OS_SYSTEM_PROPERTY);

        if (os == OperatingSystem.MACOSX) {
            // take the menu bar off the jframe
            System.setProperty("apple.laf.useScreenMenuBar", "true");
        }

        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (final ClassNotFoundException ex) {
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
     * This method add a default window listener which {@link #exiter exits} on
     * {@link java.awt.event.WindowAdapter#windowClosing(java.awt.event.WindowEvent)}. You may override this method for other
     * behaviour. This method is only invoked if {@link #setExitOnCloseWindow(boolean)} is set true.
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
     * Override this method with your custom code to add components to {@link #panel}.
     */
    protected void initPanel() {
        // Empty template method.
    }

}
