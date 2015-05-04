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

import de.weltraumschaf.commons.system.SystemProperties.Names;
import static org.hamcrest.Matchers.either;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import org.junit.Test;
import org.junit.Rule;
import org.junit.rules.ExpectedException;

/**
 * Tests for {@link SystemProperties}.
 *
 * @author Sven Strittmatter &lt;weltraumschaf@googlemail.com&gt;
 */
public class SystemPropertiesTest {

    @Rule
    // CHECKSTYLE:OFF
    public final ExpectedException thrown = ExpectedException.none();
    // CHECKSTYLE:ON

    private final SystemProperties.Properties sut = SystemProperties.defaultProperties();

    @Test
    public void get_throwsExceptionIfNameIsNull() {
        thrown.expect(NullPointerException.class);
        thrown.expectMessage("name");

        sut.get((Names) null);
    }

    @Test
    public void get_throwsExceptionIfNameStringIsNull() {
        thrown.expect(NullPointerException.class);
        thrown.expectMessage("name");

        sut.get((String) null);
    }

    @Test
    public void get_throwsExceptionIfNameStringIsEmpty() {
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("name");

        sut.get("");
    }

    @Test
    public void get_throwsExceptionIfFallBackIsNullForNames() {
        thrown.expect(NullPointerException.class);
        thrown.expectMessage("fallback");

        sut.get(Names.OS_ARCH, null);
    }

    @Test
    public void get_throwsExceptionIfFallBackIsNullForStringName() {
        thrown.expect(NullPointerException.class);
        thrown.expectMessage("fallback");

        sut.get("os.arch", null);
    }

    @Test
    public void get_Names_returnDefault() {
        assertThat(sut.get(Names.UNKNOWN, "snafu"), is("snafu"));
    }

    @Test
    public void get_String_returnDefault() {
        assertThat(sut.get("foobar", "snafu"), is("snafu"));
    }

    @Test
    public void get_Names() {
        assertThat(sut.get(Names.FILE_SEPARATOR), either(is("/")).or(is("\\")));
    }

    @Test
    public void get_String() {
        assertThat(sut.get("file.separator"), either(is("/")).or(is("\\")));
    }

}
