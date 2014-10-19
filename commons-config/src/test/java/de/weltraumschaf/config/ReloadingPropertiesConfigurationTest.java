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
package de.weltraumschaf.config;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Properties;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import org.junit.Test;
import org.junit.Before;
import org.junit.Rule;
import org.junit.rules.ExpectedException;
import org.junit.rules.TemporaryFolder;

/**
 * Tests for {@link ReloadingPropertiesConfiguration}.
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
public class ReloadingPropertiesConfigurationTest {

    @Rule
    // CHECKSTYLE:OFF
    public final TemporaryFolder tmp = new TemporaryFolder();
    // CHECKSTYLE:ON
    @Rule
    // CHECKSTYLE:OFF
    public final ExpectedException thrown = ExpectedException.none();
    // CHECKSTYLE:ON

    private final Properties properties = new Properties();
    private File configFile;
    private ReloadingPropertiesConfiguration sut;

    private void save(final File file) throws IOException {
        try (final OutputStream outputStream = new FileOutputStream(file)) {
            properties.store(outputStream, null);
            outputStream.flush();
        }
    }

    @Before
    public void createPropertiesAndSut() throws IOException {
        properties.clear();
        properties.setProperty("foo", "bar");
        properties.setProperty("baz", "false");
        properties.setProperty("snafu", "42");

        configFile = tmp.newFile();
        save(configFile);
        sut = new TestStub(configFile);
    }

    @Test
    public void getProperty_propertyNameNull() {
        thrown.expect(NullPointerException.class);
        thrown.expectMessage("propertyName");

        sut.getProperty(null, "");
    }

    @Test
    public void getProperty_propertyNameEmpty() {
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("propertyName");

        sut.getProperty("", "");
    }

    @Test
    public void getProperty_defaultValueNull() {
        thrown.expect(NullPointerException.class);
        thrown.expectMessage("defaultValue");

        sut.getProperty("foo", null);
    }

    @Test
    public void getProperty() {
        assertThat(sut.getProperty("foo", ""), is("bar"));
        assertThat(sut.getProperty("baz", ""), is("false"));
        assertThat(sut.getProperty("snafu", ""), is("42"));
    }

    @Test
    public void getProperty_defaultValue() {
        assertThat(sut.getProperty("foo", "deffoo"), is("bar"));
        assertThat(sut.getProperty("baz", "defbaz"), is("false"));
        assertThat(sut.getProperty("snafu", "defsnafu"), is("42"));
        assertThat(sut.getProperty("notexisting", "def"), is("def"));
    }

    @Test
    public void getRequiredProperty() {
        assertThat(sut.getRequiredProperty("foo"), is("bar"));
    }

    @Test
    public void getRequiredProperty_notExisting() {
        thrown.expect(RuntimeException.class);
        thrown.expectMessage("'notexisting'");

        sut.getRequiredProperty("notexisting");
    }

    @Test
    public void getInteger() {
        assertThat(sut.getInteger("snafu", 23), is(42));
        assertThat(sut.getInteger("notexisting", 23), is(23));
    }

    @Test
    public void getFlag() {
        assertThat(sut.getFlag("baz", true), is(false));
        assertThat(sut.getFlag("notexisting", true), is(true));
    }

    @Test
    public void reloadConfigurationAfterChange() throws IOException, InterruptedException {
        assertThat(sut.getProperty("foo", "deffoo"), is("bar"));
        assertThat(sut.getProperty("baz", "defbaz"), is("false"));
        assertThat(sut.getProperty("snafu", "defsnafu"), is("42"));
        assertThat(sut.getProperty("notexisting", "def"), is("def"));

        properties.clear();
        properties.setProperty("foo", "bar1");
        properties.setProperty("baz", "true");
        properties.setProperty("snafu", "23");
        properties.setProperty("notexisting", "exists");
        save(configFile);
        Thread.sleep(1_000L);

        assertThat(sut.getProperty("foo", "deffoo"), is("bar1"));
        assertThat(sut.getProperty("baz", "defbaz"), is("true"));
        assertThat(sut.getProperty("snafu", "defsnafu"), is("23"));
        assertThat(sut.getProperty("notexisting", "def"), is("exists"));
    }

    private static final class TestStub extends ReloadingPropertiesConfiguration {

        public TestStub(final File file) {
            super(file);
        }

    }
}
