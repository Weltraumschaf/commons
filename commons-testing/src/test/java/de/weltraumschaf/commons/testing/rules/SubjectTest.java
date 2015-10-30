package de.weltraumschaf.commons.testing.rules;

import static org.hamcrest.Matchers.is;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Rule;
import org.junit.rules.ExpectedException;

/**
 * Tests for {@link Subject}.
 */
public class SubjectTest {

    @Rule
    public final ExpectedException thrown = ExpectedException.none();
    private final Subject sut = new Subject();

    @Test
    public void getRunnedTimes() {
        assertThat(sut.getRunnedTimes(), is(0));

        sut.run();

        assertThat(sut.getRunnedTimes(), is(1));

        sut.run();
        sut.run();

        assertThat(sut.getRunnedTimes(), is(3));
    }

    @Test
    public void setShouldFail() {
        thrown.expect(RuntimeException.class);
        thrown.expectMessage("Fail at run: 1");

        sut.setShouldFail(true);

        sut.run();
    }

    @Test
    public void failForTimes() {
        sut.failForTimes(3);

        try {
            sut.run();
            fail("expected excpetion not thrown!");
        } catch (final RuntimeException ex) {
            assertThat(sut.getRunnedTimes(), is(1));
        }

        try {
            sut.run();
            fail("expected excpetion not thrown!");
        } catch (final RuntimeException ex) {
            assertThat(sut.getRunnedTimes(), is(2));
        }

        try {
            sut.run();
            fail("expected excpetion not thrown!");
        } catch (final RuntimeException ex) {
            assertThat(sut.getRunnedTimes(), is(3));
        }

        sut.run();
        assertThat(sut.getRunnedTimes(), is(4));
    }

}
