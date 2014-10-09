/*
 *  LICENSE
 *
 * "THE BEER-WARE LICENSE" (Revision 43):
 * "Sven Strittmatter" <weltraumschaf@googlemail.com> wrote this file.
 * As long as you retain this notice you can do whatever you want with
 * this stuff. If we meet some day, and you think this stuff is worth it,
 * you can buy me a non alcohol-free beer in return.
 *
 * Copyright (C) 2012 "Sven Strittmatter" <weltraumschaf@googlemail.com>
 */

package de.weltraumschaf.commons.system;

import org.junit.Test;
import static org.junit.Assert.assertThat;
import static org.hamcrest.Matchers.*;

/**
 * Tests for {@link Environments}.
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
public class EnvironmentsTest {

    private final Environments.Env sut = Environments.defaultEnv();

    @Test
    public void getByString() {
        assertThat(sut.get("SHELL"), is(equalTo("/bin/bash")));
        assertThat(sut.get("_commons__unknown__"), is(equalTo("")));
    }

    @Test
    public void getByString_withFallback() {
        assertThat(sut.get("SHELL", "default"), is(equalTo("/bin/bash")));
        assertThat(sut.get("_commons__unknown__", "default"), is(equalTo("default")));
    }

    @Test(expected = NullPointerException.class)
    public void getByString_fallbackMustNotBeNull() {
        sut.get("SHELL", null);
    }
}