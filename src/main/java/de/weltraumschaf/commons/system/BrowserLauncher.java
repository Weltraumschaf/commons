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

    private final OperatingSystem os;
    private final Runtime runtime;

    public BrowserLauncher() {
        this(OperatingSystem.determine(System.getProperty("os.name", "")), Runtime.getRuntime());
    }

    public BrowserLauncher(final OperatingSystem os, final Runtime runtime) {
        super();
        this.os = os;
        this.runtime = runtime;
    }

    private boolean openLinuxBrowser(final String url) {
        boolean started = false;
        // See if the default browser is Konqueror by resolving the symlink.
        boolean isDefaultKonqueror = false;

        try {
            // Find out the location of the x-www-browser link from path.
            final Process process = runtime.exec("which x-www-browser");
            final BufferedInputStream ins = new BufferedInputStream(process.getInputStream());
            final BufferedReader bufreader = new BufferedReader(new InputStreamReader(ins));
            final String defaultLinkPath = bufreader.readLine();
            ins.close();

            // The path is null if the link did not exist.
            if (defaultLinkPath != null) {
                // See if the default browser is Konqueror.
                final File file = new File(defaultLinkPath);
                final String canonical = file.getCanonicalPath();

                if (canonical.indexOf("konqueror") != -1) {
                    isDefaultKonqueror = true;
                }
            }
        } catch (IOException e1) {
            // The symlink was probably not found, so this is ok.
        }

        // Try x-www-browser, which is symlink to the default browser,
        // except if we found that it is Konqueror.
        if (!started && !isDefaultKonqueror) {
            try {
                runtime.exec("x-www-browser " + url);
                started = true;
            } catch (final IOException e) {
            }
        }

        // Try firefox
        if (!started) {
            try {
                runtime.exec("firefox " + url);
                started = true;
            } catch (final IOException e) {
            }
        }

        // Try mozilla
        if (!started) {
            try {
                runtime.exec("mozilla " + url);
                started = true;
            } catch (final IOException e) {
            }
        }

        // Try konqueror
        if (!started) {
            try {
                runtime.exec("konqueror " + url);
                started = true;
            } catch (final IOException e) {
            }
        }
        return started;
    }

    private boolean openMacBrowser(final String url) {
        boolean started = false;

        // Try open
        if (!started) {
            try {
                runtime.exec("open " + url);
                started = true;
            } catch (final IOException e) {
            }
        }

        return started;
    }

    private boolean openWindowsBrowser(final String url) {
        boolean started = false;

        if (!started) {
            try {
                runtime.exec("cmd /c start " + url);
                started = true;
            } catch (final IOException e) {
            }
        }

        return started;
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
        }

        if (!started) {
            throw new RuntimeException("Failed to open browser. Please go to " + url);
        }
    }

}
