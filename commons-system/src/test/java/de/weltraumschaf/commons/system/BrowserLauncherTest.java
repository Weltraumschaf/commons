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
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.sameInstance;
import static org.junit.Assert.assertThat;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

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
    public void BrowserCommands_getCommand_windows() {
        assertThat(
                BrowserLauncher.BrowserCommands.WINDOWS.getCommand("foobar"),
                is(equalTo("cmd /c start foobar")));
    }

    @Test
    public void BrowserCommands_getCommand_macOsX() {
        assertThat(
                BrowserLauncher.BrowserCommands.MAC_OS.getCommand("foobar"),
                is(equalTo("open foobar")));
    }

    @Test
    public void BrowserCommands_getCommand_GNU_X_wwwBrowser() {
        assertThat(
                BrowserLauncher.BrowserCommands.GNU_X_WWW_BROWSER.getCommand("foobar"),
                is(equalTo("x-www-browser foobar")));
    }

    @Test
    public void BrowserCommands_getCommand_GNUR_firefox() {
        assertThat(
                BrowserLauncher.BrowserCommands.GNU_FIREFOX.getCommand("foobar"),
                is(equalTo("firefox foobar")));
    }

    @Test
    public void BrowserCommands_getCommand_GNU_mozilla() {
        assertThat(
                BrowserLauncher.BrowserCommands.GNU_MOZILLA.getCommand("foobar"),
                is(equalTo("mozilla foobar")));
    }

    @Test
    public void BrowserCommands_getCommand_GNU_konqueror() {
        assertThat(
                BrowserLauncher.BrowserCommands.GNU_KONQUEROR.getCommand("foobar"),
                is(equalTo("konqueror foobar")));
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
}
