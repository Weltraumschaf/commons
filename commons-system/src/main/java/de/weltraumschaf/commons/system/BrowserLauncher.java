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

import de.weltraumschaf.commons.validate.Validate;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import edu.umd.cs.findbugs.annotations.SuppressWarnings;

/**
 * This class opens default browser for DemoLauncher class.
 * <p>
 * Default browser is detected by the operating system.
 * </p>
 * <p>
 * Copied from <a href="http://dev.vaadin.com/svn/releases/6.6.4/src/com/vaadin/launcher/">Vaadin browser launcher</a>.
 * </p>
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
     * @param os detected operating system, must not be {@code null}
     * @param runtime runtime for command execution, must not be {@code null}
     */
    public BrowserLauncher(final OperatingSystem os, final Runtime runtime) {
        super();
        this.os = Validate.notNull(os, "os");
        this.runtime = Validate.notNull(runtime, "runtime");
    }

    /**
     * Open browser on specified URL.
     * <p>
     * This method throws an {@link IllegalArgumentException} if the OS (given by constructor) is not supported.
     * </p>
     * <p>
     * This method throws an {@link RuntimeException} if the browser can't be started.
     * </p>
     *
     * @param url URL to open, must not be {@code null} or empty
     */
    public void openBrowser(final String url) {
        Validate.notEmpty(url, "url");
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
                throw new IllegalArgumentException("Unsupported OS. Please go to " + url);
        }

        if (!started) {
            throw new RuntimeException("Failed to open browser. Please go to " + url);
        }
    }

    /**
     * Opens browser on GNU/Linux.
     *
     * @param url URL to open.
     * @return {@code true} if started
     */
    private boolean openLinuxBrowser(final String url) {
        // See if the default browser is Konqueror by resolving the symlink.
        boolean isDefaultKonqueror = false;

        try {
            // Find out the location of the x-www-browser link from path.
            final Process process = execCommand(CliCommands.UNIX_WHICH, GNU_X_WWW_BROWSER_CMD);
            final BufferedInputStream ins = new BufferedInputStream(process.getInputStream());
            @SuppressWarnings(
                    value = "DM_DEFAULT_ENCODING",
                    justification = "We readfrom process IO, so hopefully this uses the platform encoding.")
            final BufferedReader bufreader = new BufferedReader(new InputStreamReader(ins));
            final String defaultLinkPath = bufreader.readLine();
            ins.close();
            bufreader.close();

            // The path is null if the link did not exist.
            if (defaultLinkPath != null) {
                // See if the default browser is Konqueror.
                final File file = new File(defaultLinkPath);
                final String canonical = file.getCanonicalPath();

                if (canonical.contains(GNU_KONQUEROR_CMD)) {
                    isDefaultKonqueror = true;
                }
            }
        } catch (final IOException ex) { // NOPMD
            // The symlink was probably not found, so this is ok.
        }

        // Try x-www-browser, which is symlink to the default browser,
        // except if we found that it is Konqueror.
        if (!isDefaultKonqueror) {
            try {
                execCommand(CliCommands.GNU_X_WWW_BROWSER, url);
                return true;
            } catch (final IOException ex) { // NOPMD
                // Try the next one.
            }
        }

        // Try firefox
        try {
            execCommand(CliCommands.GNU_FIREFOX, url);
            return true;
        } catch (final IOException ex) { // NOPMD
            // Try the next one.
        }

        // Try mozilla
        try {
            execCommand(CliCommands.GNU_MOZILLA, url);
            return true;
        } catch (final IOException ex) { // NOPMD
            // Try the next one.
        }

        // Try konqueror
        try {
            execCommand(CliCommands.GNU_KONQUEROR, url);
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
     * @return {@code true} if started
     */
    private boolean openMacBrowser(final String url) {
        try {
            execCommand(CliCommands.MAC_OS, url);
        } catch (final IOException e) {
            return false;
        }

        return true;
    }

    /**
     * Opens browser on Microsoft Windows.
     *
     * @param url URL to open.
     * @return {@code true} if started
     */
    private boolean openWindowsBrowser(final String url) {
        try {
            execCommand(CliCommands.WINDOWS, url);
        } catch (final IOException e) {
            return false;
        }

        return true;
    }

    /**
     * Executes external command to open URL.
     *
     * @param cmd external command, must not be {@code null} or empty
     * @param argument argument string, must not be {@code null} or empty
     * @return A new {@link java.io.Process} object for managing the subprocess
     * @throws IOException if an I/O error occurs
     */
    private Process execCommand(final CliCommands cmd, final String argument) throws IOException {
        return runtime.exec(cmd.getCommand(argument));
    }

    /**
     * Abstracts process execution.
     */
    interface Executor {

        /**
         * Executes given string command as new process.
         *
         * @param command must not be {@code null} or empty
         * @return never {@code null}
         * @throws IOException if command can't be read/executed
         */
        Process exec(String command) throws IOException;
    }

    /**
     * Default implementation which delegates to {@link Runtime}.
     */
    static final class DefaultExecutor implements Executor {

        /**
         * To executes external commands.
         */
        private final Runtime runtime;

        /**
         * Dedicated constructor.
         *
         * @param runtime must not be {@code null}
         */
        public DefaultExecutor(final Runtime runtime) {
            super();
            this.runtime = Validate.notNull(runtime);
        }

        @Override
        public Process exec(final String command) throws IOException {
            return runtime.exec(command);
        }

    }

    /**
     * Collects browser open commands for the CLI of various operating systems and browsers.
     */
    static enum CliCommands {

        /**
         * Windows command to open an URL.
         */
        WINDOWS("cmd /c start %s"),
        /**
         * Mac OS command to open an URL.
         */
        MAC_OS("open %s"),
        /**
         * Default Linux command to open an URL.
         */
        GNU_X_WWW_BROWSER("x-www-browser %s"),
        /**
         * Firefox Linux command to open an URL.
         */
        GNU_FIREFOX("firefox %s"),
        /**
         * Mozilla Linux command to open an URL.
         */
        GNU_MOZILLA("mozilla %s"),
        /**
         * Konqueror Linux command to open an URL.
         */
        GNU_KONQUEROR("konqueror %s"),
        /**
         * Command to find a binary on UNIX systems.
         */
        UNIX_WHICH("which %s");

        /**
         * Holds the command format string.
         */
        private final String command;

        /**
         * Dedicated constructor.
         *
         * @param command must not be {@code null} or empty
         */
        CliCommands(final String command) {
            this.command = Validate.notEmpty(command);
        }

        /**
         * Returns the command appended with the argumet string with a space before.
         *
         * @param arguments must not be {@code null} or empty
         * @return never {@code null} or empty
         */
        String getCommand(final String arguments) {
            return String.format(command, Validate.notEmpty(arguments));
        }

    }
}
