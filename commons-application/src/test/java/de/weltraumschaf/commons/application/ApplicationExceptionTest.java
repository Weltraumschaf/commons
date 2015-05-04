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

package de.weltraumschaf.commons.application;

import de.weltraumschaf.commons.system.ExitCode;
import org.junit.Test;
import static org.junit.Assert.assertThat;
import static org.hamcrest.Matchers.*;

/**
 * Tests for {@link ApplicationException}.
 *
 * @author Sven Strittmatter &lt;weltraumschaf@googlemail.com&gt;
 */
public class ApplicationExceptionTest {

    private final ApplicationException sut = new ApplicationException(ExitcodeStub.FOOBAR, "foobar");

    @Test
    public void getExitCode() {
        assertThat(sut.getExitCode(), is((ExitCode)ExitcodeStub.FOOBAR));
    }

    @Test
    public void getMessage() {
        assertThat(sut.getMessage(), is("foobar"));
    }

    private static enum ExitcodeStub implements ExitCode {

        FOOBAR;

        @Override
        public int getCode() {
            return 42;
        }

    }
}
