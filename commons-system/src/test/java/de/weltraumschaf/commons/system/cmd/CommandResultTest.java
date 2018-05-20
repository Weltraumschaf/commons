package de.weltraumschaf.commons.system.cmd;

import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.Test;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

/**
 * Tests for {@link CommandResult}.
 *
 * @author Sven Strittmatter &lt;weltraumschaf@googlemail.com&gt;
 */
public class CommandResultTest {

    @Test
    public void equalsAndHashCode() {
        EqualsVerifier.forClass(CommandResult.class).verify();
    }

    @Test(expected = NullPointerException.class)
    @SuppressWarnings("ResultOfObjectAllocationIgnored")
    public void constructor_stdoutMustNotBeNull() {
        new CommandResult(0, null, "");
    }

    @Test(expected = NullPointerException.class)
    @SuppressWarnings("ResultOfObjectAllocationIgnored")
    public void constructor_stderrMustNotBeNull() {
        new CommandResult(0, "", null);
    }

    @Test
    public void isSuccessful() {
        assertThat(new CommandResult(0, "", "").isSuccessful(), is(true));
        assertThat(new CommandResult(-1, "", "").isSuccessful(), is(false));
        assertThat(new CommandResult(1, "", "").isSuccessful(), is(false));
    }

    @Test
    public void isFailed() {
        assertThat(new CommandResult(0, "", "").isFailed(), is(false));
        assertThat(new CommandResult(-1, "", "").isFailed(), is(true));
        assertThat(new CommandResult(1, "", "").isFailed(), is(true));
    }
}
