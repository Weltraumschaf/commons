package de.weltraumschaf.commons.system.cmd;

import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Tests for {@link BaseCommand}.
 *
 * @author Sven Strittmatter &lt;weltraumschaf@googlemail.com&gt;
 */
public class BaseCommandTest {

    @Test(expected = NullPointerException.class)
    public void constructor_commandMustNotBeNull() {
        new BaseCommand(null, "") ;
    }

    @Test(expected = IllegalArgumentException.class)
    public void constructor_commandMustNotBeEmpty() {
        new BaseCommand("", "") ;
    }

    @Test(expected = NullPointerException.class)
    public void constructor_argumentsMustNotBeNull() {
        new BaseCommand("bar", null) ;
    }

    @Test(expected = NullPointerException.class)
    public void constructor_builderMustNotBeNull() {
        new BaseCommand("bar", "", null) ;
    }

    @Test
    public void execute() throws IOException, InterruptedException {
        final Process process = mock(Process.class);
        when(process.getInputStream())
            .thenReturn(new ByteArrayInputStream("std out ...".getBytes(StandardCharsets.UTF_8)));
        when(process.getErrorStream())
            .thenReturn(new ByteArrayInputStream("std err\nsnafu ...".getBytes(StandardCharsets.UTF_8)));
        when(process.waitFor()).thenReturn(42);
        final ProcessBuilderWrapper builder = mock(ProcessBuilderWrapper.class);
        when(builder.start("bar", "-baz snafu")).thenReturn(process);

        final BaseCommand sut = new BaseCommand("bar", "-baz snafu", builder);

        assertThat(
            sut.execute(),
            is(new CommandResult(42, "std out ...", "std err\nsnafu ...")));
    }
}