package de.weltraumschaf.commons.testing.rules;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import org.junit.Test;
import org.junit.Rule;
import org.junit.rules.ExpectedException;

/**
 * Integration tests for {@link Repeater} with {@link RunTimes} annotation.
 */
public class Subject_RunTimesTest {

    @Rule
    public final Repeater repeater = new Repeater();
    @Rule
    public final ExpectedException thrown = ExpectedException.none();
    private final Subject sut = new Subject();
    private int counter;

    @Test
    @RunTimes(10)
    public void runTemTimes() {
        sut.run();

        assertThat(sut.getRunnedTimes(), is(++counter));
    }

    @Test
    @RunTimes(10)
    public void runTemTimes_alwaysFail() {
        counter++;
        thrown.expect(AssertionError.class);
        thrown.expectMessage("Fail at run: " + counter);

        sut.setShouldFail(true);
        sut.run();

        assertThat(sut.getRunnedTimes(), is(counter));
    }
}
