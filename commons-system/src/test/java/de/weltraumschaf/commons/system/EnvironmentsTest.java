/*
 *  LICENSE
 *
 * "THE BEER-WARE LICENSE" (Revision 43):
 * "Sven Strittmatter" &lt;weltraumschaf@googlemail.com&gt; wrote this file.
 * As long as you retain this notice you can do whatever you want with
 * this stuff. If we meet some day, and you think this stuff is worth it,
 * you can buy me a non alcohol-free beer in return.
 *
 * Copyright (C) 2012 "Sven Strittmatter" &lt;weltraumschaf@googlemail.com&gt;
 */
package de.weltraumschaf.commons.system;

import de.weltraumschaf.commons.system.Environments.Names;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import org.junit.Test;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;
import org.junit.Rule;
import org.junit.rules.ExpectedException;

/**
 * Tests for {@link Environments}.
 *
 * @author Sven Strittmatter &lt;weltraumschaf@googlemail.com&gt;
 */
public class EnvironmentsTest {

    @Rule
    //CHECKSTYLE:OFF
    public final ExpectedException thrown = ExpectedException.none();
    //CHECKSTYLE:ON

    private final Environments.Env sut = Environments.defaultEnv();

    @Test
    public void invokeConstructorByReflectionThrowsException() throws Exception {
        assertThat(Environments.class.getDeclaredConstructors().length, is(1));

        final Constructor<Environments> ctor = Environments.class.getDeclaredConstructor();
        ctor.setAccessible(true);

        thrown.expect(either(instanceOf(UnsupportedOperationException.class))
                .or(instanceOf(InvocationTargetException.class)));
        ctor.newInstance();
    }

    @Test
    public void getByString() {
        assertThat(
            sut.get("SHELL"),
            is(anyOf(equalTo("/bin/bash"), equalTo("/usr/local/bin/bash"), equalTo("/bin/bash"))));
        assertThat(sut.get("_commons__unknown__"), is(equalTo("")));
    }

    @Test
    public void getByString_withFallback() {
        assertThat(
            sut.get("SHELL", "default"),
            is(anyOf(equalTo("/bin/bash"), equalTo("/usr/local/bin/bash"), equalTo("/bin/bash"))));
        assertThat(sut.get("_commons__unknown__", "default"), is(equalTo("default")));
    }

    @Test
    public void getByName() {
        assertThat(
            sut.get(Names.SHELL),
            is(anyOf(equalTo("/bin/bash"), equalTo("/usr/local/bin/bash"), equalTo("/bin/bash"))));
    }

    @Test
    public void getByName_withFallback() {
        assertThat(
            sut.get(Names.SHELL, "default"),
            is(anyOf(equalTo("/bin/bash"), equalTo("/usr/local/bin/bash"), equalTo("/bin/bash"))));
    }

    @Test(expected = NullPointerException.class)
    public void getByString_fallbackMustNotBeNull() {
        sut.get("SHELL", null);
    }

    @Test
    public void allNamesAreThemSelves() {
        for (final Names name : Names.values()) {
            assertThat(name.name(), is(name.getName()));
        }
    }
}
