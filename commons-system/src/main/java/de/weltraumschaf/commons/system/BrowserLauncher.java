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
package de.weltraumschaf.commons.system;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import edu.umd.cs.findbugs.annotations.SuppressWarnings;

/**
 * This class opens default browser for DemoLauncher class.
 *
 * Default browser is detected by the operating system.
 *
 * Copied from http://dev.vaadin.com/svn/releases/6.6.4/src/com/vaadin/launcher/
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
public final class BrowserLauncher {

    /**
     * Windows command to open an URL.
     */
    private static final String WINDOWS_CMD = "cmd /c start";

    /**
     * Mac OS command to open an URL.
     */
    private static final String MAC_OS_CMD = "open";

    /**
     * Default Linux command to open an URL.
     */
    private static final String GNU_X_WWW_BROWSER_CMD = "x-www-browser";

    /**
     * Firefox Linux command to open an URL.
     */
    private static final String GNU_FIREFOX_CMD = "firefox";

    /**
     * Mozilla Linux command to open an URL.
     */
    private static final String GNU_MOZILLA_CMD = "mozilla";
    /**
     * Konqueror Linux command to open an URL.
     */
    private static final String GNU_KONQUEROR_CMD = "konqueror";

    /**
     * Determined operating system.
     */
    private final OperatingSystem os;
    /**
     * To executes external commands.
     */
    private final Runtime runtime;

    /**
     * Default constructor.
     */
    public BrowserLauncher() {
        this(OperatingSystem.determine(System.getProperty("os.name", "")), Runtime.getRuntime());
    }

    /**
     * Dedicated constructor.
     *
     * @param os detected operating system
     * @param runtime runtime for command execution
     */
    public BrowserLauncher(final OperatingSystem os, final Runtime runtime) {
        super();
        this.os = os;
        this.runtime = runtime;
    }

    /**
     * Opens browser on GNU/Linux.
     *
     * @param url URL to open.
     * @return <code>true</code> if started
     */
    private boolean openLinuxBrowser(final String url) {
        // See if the default browser is Konqueror by resolving the symlink.
        boolean isDefaultKonqueror = false;

        try {
            // Find out the location of the x-www-browser link from path.
            final Process process = execCommand("which", GNU_X_WWW_BROWSER_CMD);
            final BufferedInputStream ins = new BufferedInputStream(process.getInputStream());
            @SuppressWarnings("DM_DEFAULT_ENCODING")
            final BufferedReader bufreader = new BufferedReader(new InputStreamReader(ins));
            final String defaultLinkPath = bufreader.readLine();
            ins.close();
            bufreader.close();

            // The path is null if the link did not exist.
            if (defaultLinkPath != null) {
                // See if the default browser is Konqueror.
                final File file = new File(defaultLinkPath);
                final String canonical = file.getCanonicalPath();

                if (canonical.indexOf(GNU_KONQUEROR_CMD) != -1) {
                    isDefaultKonqueror = true;
                }
            }
        } catch (final IOException ex) { // NOPMD
            // The symlink was probably not found, so this is ok.
        }

        // Try x-www-browser, which is symlink to the default browser,
        // except if we found that it is Konqueror.
        if (! isDefaultKonqueror) {
            try {
                execCommand(GNU_X_WWW_BROWSER_CMD, url);
                return true;
            } catch (final IOException ex) { // NOPMD
                // Try the next one.
            }
        }

        // Try firefox
        try {
            execCommand(GNU_FIREFOX_CMD, url);
            return true;
        } catch (final IOException ex) { // NOPMD
            // Try the next one.
        }

        // Try mozilla
        try {
            execCommand(GNU_MOZILLA_CMD, url);
            return true;
        } catch (final IOException ex) { // NOPMD
            // Try the next one.
        }

        // Try konqueror
        try {
            execCommand(GNU_KONQUEROR_CMD, url);
            return true;
        } catch (final IOException ex) { // NOPMD
            // Try the next one.
        }

        return false;
    }

    /**
     * Opens browser on Mac OS X.
     *
     * @param url URL to open.
     * @return <code>true</code> if started
     */
    private boolean openMacBrowser(final String url) {
        try {
            execCommand(MAC_OS_CMD, url);
        } catch (final IOException e) {
            return false;
        }

        return true;
    }

    /**
     * Opens browser on Microsoft Windows.
     *
     * @param url URL to open.
     * @return <code>true</code> if started
     */
    private boolean openWindowsBrowser(final String url) {
        try {
            execCommand(WINDOWS_CMD, url);
        } catch (final IOException e) {
            return false;
        }

        return true;
    }

    /**
     * Open browser on specified URL.
     *
     * @param url URL to open.
     */
    public void openBrowser(final String url) {
        boolean started = false;

        switch (os) {
            case LINUX:
                started = openLinuxBrowser(url);
                break;
            case MACOSX:
                started = openMacBrowser(url);
                break;
            case WINDOWS:
                started = openWindowsBrowser(url);
                break;
            default:
                throw new RuntimeException("Unsupported OS. Please go to " + url);
        }

        if (!started) {
            throw new RuntimeException("Failed to open browser. Please go to " + url);
        }
    }

    /**
     * Executes external command to open url.
     *
     * @param cmd external command
     * @param argument argument string
     * @return A new {@link java.io.Process} object for managing the subprocess
     * @throws IOException if an I/O error occurs
     */
    private Process execCommand(final String cmd, final String argument) throws IOException {
        return runtime.exec(String.format("%s %s", cmd, argument));
    }
}
