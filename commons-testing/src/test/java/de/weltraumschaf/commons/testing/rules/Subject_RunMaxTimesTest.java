package de.weltraumschaf.commons.testing.rules;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.Rule;
import org.junit.rules.ExpectedException;

/**
 * Integration tests for {@link Repeater} with {@link RunMaxTimes} annotation.
 */
public class Subject_RunMaxTimesTest {

    @Rule
    public final Repeater repeater = new Repeater();
    @Rule
    public final ExpectedException thrown = ExpectedException.none();
    private final Subject sut = new Subject();
    private int counter;

    @Test
    @RunMaxTimes(10)
    public void runMaxTemTimes() {
        sut.run();

        assertThat(sut.getRunnedTimes(), is(1));
    }

    @Test
    @RunMaxTimes(10)
    public void runMaxTemTimes_filThreeTimes() {
        sut.failForTimes(3);

        try {
            sut.run();
        } catch (final AssertionError ex) {
            // Ignore it.
        }

        assertThat(sut.getRunnedTimes(), is(4));
    }

    @Test
    @RunMaxTimes(10)
    public void runMaxTemTimes_alwaysFail() {
        counter++;
        thrown.expect(AssertionError.class);
        thrown.expectMessage("Fail at run: " + counter);
        sut.setShouldFail(true);

        sut.run();

        assertThat(sut.getRunnedTimes(), is(10));
    }

}
