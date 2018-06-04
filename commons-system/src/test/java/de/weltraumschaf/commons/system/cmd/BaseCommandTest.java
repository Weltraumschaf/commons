package de.weltraumschaf.commons.system.cmd;

import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Collections;

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
        new BaseCommand(null) ;
    }

    @Test(expected = IllegalArgumentException.class)
    public void constructor_commandMustNotBeEmpty() {
        new BaseCommand("") ;
    }

    @Test(expected = NullPointerException.class)
    public void constructor_argumentsMustNotBeNull() {
        new BaseCommand("bar", null) ;
    }

    @Test(expected = NullPointerException.class)
    public void constructor_builderMustNotBeNull() {
        new BaseCommand("bar", Collections.emptyList(), null) ;
    }

    @Test
    public void execute_ok() throws IOException, InterruptedException {
        final Process process = mock(Process.class);
        when(process.getInputStream())
            .thenReturn(new ByteArrayInputStream("std out ...".getBytes(StandardCharsets.UTF_8)));
        when(process.getErrorStream())
            .thenReturn(new ByteArrayInputStream("".getBytes(StandardCharsets.UTF_8)));
        when(process.waitFor()).thenReturn(0);
        final ProcessBuilderWrapper builder = mock(ProcessBuilderWrapper.class);
        when(builder.start(Arrays.asList("bar", "-baz", "snafu"))).thenReturn(process);

        final BaseCommand sut = new BaseCommand("bar", Arrays.asList("-baz","snafu"), builder);

        assertThat(
            sut.execute(),
            is(CommandResult.ok("std out ...")));
    }

    @Test
    public void execute_error() throws IOException, InterruptedException {
        final Process process = mock(Process.class);
        when(process.getInputStream())
            .thenReturn(new ByteArrayInputStream("std out ...".getBytes(StandardCharsets.UTF_8)));
        when(process.getErrorStream())
            .thenReturn(new ByteArrayInputStream("std err\nsnafu ...".getBytes(StandardCharsets.UTF_8)));
        when(process.waitFor()).thenReturn(42);
        final ProcessBuilderWrapper builder = mock(ProcessBuilderWrapper.class);
        when(builder.start(Arrays.asList("bar", "-baz","snafu"))).thenReturn(process);

        final BaseCommand sut = new BaseCommand("bar", Arrays.asList("-baz","snafu"), builder);

        assertThat(
            sut.execute(),
            is(CommandResult.error(42, "std err\nsnafu ...")));
    }
}
