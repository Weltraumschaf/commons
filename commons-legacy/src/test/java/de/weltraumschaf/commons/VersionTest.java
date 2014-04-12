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
package de.weltraumschaf.commons;

import java.io.IOException;
import static org.junit.Assert.*;
import org.junit.Test;

/**
 * Test cases for {@link Version}.
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
public class VersionTest {

    @Test public void loadAndGetVersion() throws IOException {
        final Version version = new Version("/de/weltraumschaf/commons/version.properties");
        version.load();
        assertEquals("1.0.0", version.getVersion());
        assertEquals("1.0.0", version.toString());
    }

}
