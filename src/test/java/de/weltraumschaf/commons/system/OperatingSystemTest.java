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

import static org.junit.Assert.*;
import org.junit.Test;

/**
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
public class OperatingSystemTest {

    @Test
    public void determine() {
        assertEquals(OperatingSystem.WINDOWS, OperatingSystem.determine("windows"));
        assertEquals(OperatingSystem.LINUX, OperatingSystem.determine("linux"));
        assertEquals(OperatingSystem.MACOSX, OperatingSystem.determine("mac os x"));
        assertEquals(OperatingSystem.UNKNOWN, OperatingSystem.determine("foobar"));
    }

    @Test
    public void getName() {
        assertEquals("win", OperatingSystem.WINDOWS.getName());
        assertEquals("linux", OperatingSystem.LINUX.getName());
        assertEquals("mac os x", OperatingSystem.MACOSX.getName());
        assertEquals("", OperatingSystem.UNKNOWN.getName());
    }

}