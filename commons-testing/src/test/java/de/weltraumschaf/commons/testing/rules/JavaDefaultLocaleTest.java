package de.weltraumschaf.commons.testing.rules;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import java.util.Locale;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * Tests for {@link JavaDefaultLocale}.
 */
public class JavaDefaultLocaleTest {

    private Locale backup;

    @Before
    public void backupLocale() {
        backup = Locale.getDefault();
    }

    @After
    public void restoreLocale() {
        Locale.setDefault(backup);
    }

    @Test
    public void setBeforeAndResetAfter() throws Throwable {
        Locale.setDefault(Locale.ITALIAN);
        final JavaDefaultLocale subjectUnderTest = new JavaDefaultLocale(Locale.JAPAN);

        subjectUnderTest.before();
        assertThat(Locale.getDefault(), is(equalTo(Locale.JAPAN)));

        subjectUnderTest.after();
        assertThat(Locale.getDefault(), is(equalTo(Locale.ITALIAN)));
    }

}
