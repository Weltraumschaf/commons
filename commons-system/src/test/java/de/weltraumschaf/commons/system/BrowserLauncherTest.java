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

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import org.junit.Test;

/**
 * Tests for {@link BrowserLauncher}.
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
public class BrowserLauncherTest {

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

}
