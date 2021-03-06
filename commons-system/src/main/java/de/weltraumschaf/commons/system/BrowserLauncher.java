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
import java.io.IOException;

/**
 * This class opens default browser for DemoLauncher class.
 * <p>
 * Default browser is detected by the operating system.
 * </p>
 * <p>
 * Copied from <a href="http://dev.vaadin.com/svn/releases/6.6.4/src/com/vaadin/launcher/">Vaadin browser launcher</a>.
 * </p>
 *
 * @since 1.0.0
 * @author Sven Strittmatter &lt;weltraumschaf@googlemail.com&gt;
 */
public final class BrowserLauncher {

    /**
     * Default executor.
     */
    private static final Executor DEFAULT_EXECUTOR = new DefaultExecutor(Runtime.getRuntime());

    /**
     * Determined operating system.
     */
    private final OperatingSystem os;
    /**
     * To executes external commands.
     */
    private final Executor executor;

    /**
     * Default constructor.
     * <p>
     * Try to determine operating system by itself via system property.
     * </p>
     */
    public BrowserLauncher() {
        this(OperatingSystem.determine(System.getProperty(OperatingSystem.OS_SYSTEM_PROPERTY, "")));
    }

    /**
     * Create launcher for particular operating system.
     * <p>
     * You can determine the OS e.g. this way:
     * </p>
     * <pre>{@code
     *  final BrowserLauncher launcher new BrowserLauncher(
     *      OperatingSystem.determine(System.getProperty(OperatingSystem.OS_SYSTEM_PROPERTY, "")));
     * }</pre>
     *
     * @param os detected operating system, must not be {@code null}
     */
    public BrowserLauncher(final OperatingSystem os) {
        this(os, DEFAULT_EXECUTOR);
    }

    /**
     * Dedicated constructor.
     *
     * @param os detected operating system, must not be {@code null}
     * @param runtime runtime for command execution, must not be {@code null}
     */
    public BrowserLauncher(final OperatingSystem os, final Executor runtime) {
        super();
        this.os = Validate.notNull(os, "os");
        this.executor = Validate.notNull(runtime, "runtime");
    }

    /**
     * Open browser on specified URL.
     * <p>
     * This method throws an {@link java.lang.IllegalArgumentException} if the OS (given by constructor) is not
     * supported.
     * </p>
     * <p>
     * This method throws an {@link java.lang.RuntimeException} if the browser can't be started.
     * </p>
     *
     * @param url URL to open, must not be {@code null} or empty
     */
    public void openBrowser(final String url) {
        Validate.notEmpty(url, "url");

        try {
            switch (os) {
                case LINUX:
                    openLinuxBrowser(url);
                    break;
                case MACOSX:
                    openMacBrowser(url);
                    break;
                case WINDOWS:
                    openWindowsBrowser(url);
                    break;
                default:
                    throw new IllegalArgumentException("Unsupported OS. Please open URL by hand: " + url);
            }
        } catch (final IOException ex) {
            throw new RuntimeException("Failed to open browser. Please go to " + url, ex);
        }
    }

    /**
     * Opens browser on GNU/Linux.
     *
     * @param url URL to open.
     * @return {@code true} if started
     * @throws IOException if no browser can be opened
     */
    private Process openLinuxBrowser(final String url) throws IOException {
        // Try x-www-browser, which is symlink to the default browser,
        // except if we found that it is Konqueror.
        if (!isKonquerorTheDefaultBrowser()) {
            try {
                return execCommand(CliCommands.GNU_X_WWW_BROWSER, url);
            } catch (final IOException ex) { // NOPMD
                // Try the next one.
            }
        }

        try {
            return execCommand(CliCommands.GNU_FIREFOX, url);
        } catch (final IOException ex) { // NOPMD
            // Try the next one.
        }

        try {
            return execCommand(CliCommands.GNU_MOZILLA, url);
        } catch (final IOException ex) { // NOPMD
            // Try the next one.
        }
        try {
            return execCommand(CliCommands.GNU_KONQUEROR, url);
        } catch (final IOException ex) { // NOPMD
            // Try the next one.
        }

        throw new IOException();
    }

    /**
     * See if the default browser is Konqueror by resolving the symlink.
     *
     * FIXME: Fix Konqueror browser detection.
     *
     * @return {@code true} if Konqueror is default browser, else {@code false}
     */
    private boolean isKonquerorTheDefaultBrowser() {
//        try {
//            // Find out the location of the x-www-browser link from path.
//            final Process process = execCommand(CliCommands.UNIX_WHICH, CliCommands.GNU_X_WWW_BROWSER.getCommand());
//            final BufferedInputStream ins = new BufferedInputStream(process.getInputStream());
//            @SuppressWarnings(
//                    value = "DM_DEFAULT_ENCODING",
//                    justification = "We readfrom process IO, so hopefully this uses the platform encoding.")
//            final BufferedReader bufreader = new BufferedReader(new InputStreamReader(ins));
//            final String defaultLinkPath = bufreader.readLine();
//            bufreader.close();
//            ins.close();
//
//            // The path is null if the link did not exist.
//            if (defaultLinkPath != null) {
//                // See if the default browser is Konqueror.
//                final File file = new File(defaultLinkPath);
//                final String canonical = file.getCanonicalPath();
//
//                if (canonical.contains(CliCommands.GNU_KONQUEROR.getCommand())) {
//                    return true;
//                }
//            }
//        } catch (final IOException ex) { // NOPMD
//            // The symlink was probably not found, so this is ok.
//        }

        return false;
    }

    /**
     * Opens browser on Mac OS X.
     *
     * @param url URL to open.
     * @return {@code true} if started
     * @throws IOException if browser can not be opened
     */
    private Process openMacBrowser(final String url) throws IOException {
        return execCommand(CliCommands.MAC_OS, url);
    }

    /**
     * Opens browser on Microsoft Windows.
     *
     * @param url URL to open.
     * @return {@code true} if started
     * @throws IOException if browser can not be opened
     */
    private Process openWindowsBrowser(final String url) throws IOException {
        return execCommand(CliCommands.WINDOWS, url);
    }

    /**
     * Executes external command to open URL.
     *
     * @param cmd external command, must not be {@code null} or empty
     * @param argument argument string, must not be {@code null} or empty
     * @return A new {@link java.lang.Process} object for managing the subprocess
     * @throws IOException if an I/O error occurs
     */
    private Process execCommand(final CliCommands cmd, final String argument) throws IOException {
        return executor.exec(cmd.getCommand(argument));
    }

    /**
     * Abstracts process execution.
     */
    public interface Executor {

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
        WINDOWS("cmd /c start"),
        /**
         * Mac OS command to open an URL.
         */
        MAC_OS("open"),
        /**
         * Default Linux command to open an URL.
         */
        GNU_X_WWW_BROWSER("x-www-browser"),
        /**
         * Firefox Linux command to open an URL.
         */
        GNU_FIREFOX("firefox"),
        /**
         * Mozilla Linux command to open an URL.
         */
        GNU_MOZILLA("mozilla"),
        /**
         * Konqueror Linux command to open an URL.
         */
        GNU_KONQUEROR("konqueror"),
        /**
         * Command to find a binary on UNIX systems.
         */
        UNIX_WHICH("which");

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
         * Returns the bare command.
         *
         * @return never {@code null} or empty
         */
        String getCommand() {
            return command;
        }

        /**
         * Returns the command appended with the argument string with a space before.
         *
         * @param arguments must not be {@code null} or empty
         * @return never {@code null} or empty
         */
        String getCommand(final String arguments) {
            return String.format("%s %s", command, Validate.notEmpty(arguments));
        }

    }
}
