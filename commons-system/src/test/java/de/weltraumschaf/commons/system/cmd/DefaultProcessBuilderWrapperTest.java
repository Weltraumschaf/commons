package de.weltraumschaf.commons.system.cmd;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import java.io.IOException;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

/**
 * Tests for {@link DefaultProcessBuilderWrapper}.
 *
 * @author Sven Strittmatter &lt;weltraumschaf@googlemail.com&gt;
 */
public final class DefaultProcessBuilderWrapperTest {
    @Rule
    public final TemporaryFolder tmp = new TemporaryFolder();
    private final DefaultProcessBuilderWrapper sut = new DefaultProcessBuilderWrapper();

    @Test(expected = NullPointerException.class)
    public void start_commandArgumentMustNotBeNull() throws IOException {
        sut.start((String) null);
    }

    @Test(expected = NullPointerException.class)
    public void start_commandArgumentsMustNotContainNull() throws IOException {
        sut.start("foo", null, "bar");
    }

    @Test(expected = IllegalArgumentException.class)
    public void start_commandArgumentMustNotBeEmpty() throws IOException {
        sut.start();
    }

    @Test
    public void start() throws IOException, InterruptedException {
        final Process process = sut.start("ls", "-la", tmp.getRoot().getAbsolutePath());

        assertThat(process.waitFor(), is(0));
    }
}
