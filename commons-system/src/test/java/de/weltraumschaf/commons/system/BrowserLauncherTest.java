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

import java.io.IOException;
import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertThat;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import static org.mockito.Mockito.*;

/**
 * Tests for {@link BrowserLauncher}.
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
public class BrowserLauncherTest {

    @Rule
    // CHECKSTYLE:OFF
    public final ExpectedException thrown = ExpectedException.none();
    // CHECKSTYLE:ON

    @Test
    public void CliCommands_getCommand_windows() {
        assertThat(
                BrowserLauncher.CliCommands.WINDOWS.getCommand(),
                is(equalTo("cmd /c start")));
    }

    @Test
    public void CliCommands_getCommand_windowsWithArguments() {
        assertThat(
                BrowserLauncher.CliCommands.WINDOWS.getCommand("foobar"),
                is(equalTo("cmd /c start foobar")));
    }

    @Test
    public void CliCommands_getCommand_macOsX() {
        assertThat(
                BrowserLauncher.CliCommands.MAC_OS.getCommand(),
                is(equalTo("open")));
    }

    @Test
    public void CliCommands_getCommand_macOsXwithArguments() {
        assertThat(
                BrowserLauncher.CliCommands.MAC_OS.getCommand("foobar"),
                is(equalTo("open foobar")));
    }

    @Test
    public void CliCommands_getCommand_GNU_X_wwwBrowser() {
        assertThat(
                BrowserLauncher.CliCommands.GNU_X_WWW_BROWSER.getCommand(),
                is(equalTo("x-www-browser")));
    }

    @Test
    public void CliCommands_getCommand_GNU_X_wwwBrowserWithArgumnes() {
        assertThat(
                BrowserLauncher.CliCommands.GNU_X_WWW_BROWSER.getCommand("foobar"),
                is(equalTo("x-www-browser foobar")));
    }

    @Test
    public void CliCommands_getCommand_GNUR_firefox() {
        assertThat(
                BrowserLauncher.CliCommands.GNU_FIREFOX.getCommand(),
                is(equalTo("firefox")));
    }

    @Test
    public void CliCommands_getCommand_GNUR_firefoxWithArgumnes() {
        assertThat(
                BrowserLauncher.CliCommands.GNU_FIREFOX.getCommand("foobar"),
                is(equalTo("firefox foobar")));
    }

    @Test
    public void CliCommands_getCommand_GNU_mozilla() {
        assertThat(
                BrowserLauncher.CliCommands.GNU_MOZILLA.getCommand(),
                is(equalTo("mozilla")));
    }

    @Test
    public void CliCommands_getCommand_GNU_mozillaWithArgumnes() {
        assertThat(
                BrowserLauncher.CliCommands.GNU_MOZILLA.getCommand("foobar"),
                is(equalTo("mozilla foobar")));
    }

    @Test
    public void CliCommands_getCommand_GNU_konqueror() {
        assertThat(
                BrowserLauncher.CliCommands.GNU_KONQUEROR.getCommand(),
                is(equalTo("konqueror")));
    }

    @Test
    public void CliCommands_getCommand_GNU_konquerorWithArgumnes() {
        assertThat(
                BrowserLauncher.CliCommands.GNU_KONQUEROR.getCommand("foobar"),
                is(equalTo("konqueror foobar")));
    }

    @Test
    public void CliCommands_getCommand_UNIX_which() {
        assertThat(
                BrowserLauncher.CliCommands.UNIX_WHICH.getCommand(),
                is(equalTo("which")));
    }

    @Test
    public void CliCommands_getCommand_UNIX_whichWithArgumnes() {
        assertThat(
                BrowserLauncher.CliCommands.UNIX_WHICH.getCommand("foobar"),
                is(equalTo("which foobar")));
    }

    @Test(expected = NullPointerException.class)
    public void DefaultExecutor_exec_throwsExceptionIfRuntimeIsNull() {
        new BrowserLauncher.DefaultExecutor(null);
    }

    @Test
    public void DefaultExecutor_exec_throwsExceptionIfRuntimeThrowsOne() throws IOException {
        final IOException exception = new IOException();
        final Runtime runtime = mock(Runtime.class);
        final BrowserLauncher.DefaultExecutor sut = new BrowserLauncher.DefaultExecutor(runtime);
        final String command = "command foobar";

        when(runtime.exec(command)).thenThrow(exception);
        thrown.expect(is(sameInstance(exception)));

        sut.exec(command);
    }

    @Test
    public void DefaultExecutor_exec() throws IOException {
        final Runtime runtime = mock(Runtime.class);
        final BrowserLauncher.DefaultExecutor sut = new BrowserLauncher.DefaultExecutor(runtime);
        final String command = "command foobar";

        sut.exec(command);

        verify(runtime).exec(command);
    }

    @Test
    public void openUrl_windows() throws IOException {
        final BrowserLauncher.Executor executor = mock(BrowserLauncher.Executor.class);
        final BrowserLauncher sut = new BrowserLauncher(OperatingSystem.WINDOWS, executor);

        sut.openBrowser("http://www.weltramschaf.de");

        verify(executor).exec("cmd /c start http://www.weltramschaf.de");
    }

    @Test
    public void openUrl_macOsX() throws IOException {
        final BrowserLauncher.Executor executor = mock(BrowserLauncher.Executor.class);
        final BrowserLauncher sut = new BrowserLauncher(OperatingSystem.MACOSX, executor);

        sut.openBrowser("http://www.weltramschaf.de");

        verify(executor).exec("open http://www.weltramschaf.de");
    }

    @Test
    public void openUrl_linux_xWwwBrowser() throws IOException {
        final BrowserLauncher.Executor executor = mock(BrowserLauncher.Executor.class);
        final BrowserLauncher sut = new BrowserLauncher(OperatingSystem.LINUX, executor);

        sut.openBrowser("http://www.weltramschaf.de");

        verify(executor).exec("x-www-browser http://www.weltramschaf.de");
    }

    @Test
    public void openUrl_linux_firefox() throws IOException {
        final BrowserLauncher.Executor executor = mock(BrowserLauncher.Executor.class);
        final BrowserLauncher sut = new BrowserLauncher(OperatingSystem.LINUX, executor);

        when(executor.exec("x-www-browser http://www.weltramschaf.de")).thenThrow(new IOException());
        sut.openBrowser("http://www.weltramschaf.de");

        verify(executor).exec("firefox http://www.weltramschaf.de");
    }

    @Test
    public void openUrl_linux_mozilla() throws IOException {
        final BrowserLauncher.Executor executor = mock(BrowserLauncher.Executor.class);
        final BrowserLauncher sut = new BrowserLauncher(OperatingSystem.LINUX, executor);

        when(executor.exec("x-www-browser http://www.weltramschaf.de")).thenThrow(new IOException());
        when(executor.exec("firefox http://www.weltramschaf.de")).thenThrow(new IOException());
        sut.openBrowser("http://www.weltramschaf.de");

        verify(executor).exec("mozilla http://www.weltramschaf.de");
    }

    @Test
    public void openUrl_linux_konqueror() throws IOException {
        final BrowserLauncher.Executor executor = mock(BrowserLauncher.Executor.class);
        final BrowserLauncher sut = new BrowserLauncher(OperatingSystem.LINUX, executor);

        when(executor.exec("x-www-browser http://www.weltramschaf.de")).thenThrow(new IOException());
        when(executor.exec("firefox http://www.weltramschaf.de")).thenThrow(new IOException());
        when(executor.exec("mozilla http://www.weltramschaf.de")).thenThrow(new IOException());
        sut.openBrowser("http://www.weltramschaf.de");

        verify(executor).exec("konqueror http://www.weltramschaf.de");
    }

    @Test(expected = RuntimeException.class)
    public void openUrl_linux_noBrowser() throws IOException {
        final BrowserLauncher.Executor executor = mock(BrowserLauncher.Executor.class);
        final BrowserLauncher sut = new BrowserLauncher(OperatingSystem.LINUX, executor);

        when(executor.exec("x-www-browser http://www.weltramschaf.de")).thenThrow(new IOException());
        when(executor.exec("firefox http://www.weltramschaf.de")).thenThrow(new IOException());
        when(executor.exec("mozilla http://www.weltramschaf.de")).thenThrow(new IOException());
        when(executor.exec("konqueror http://www.weltramschaf.de")).thenThrow(new IOException());
        sut.openBrowser("http://www.weltramschaf.de");
    }
}
