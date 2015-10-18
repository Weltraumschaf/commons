package de.weltraumschaf.commons.testing.rules;

import java.lang.annotation.Annotation;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.sameInstance;
import static org.junit.Assert.assertThat;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;
import static org.mockito.Mockito.mock;

/**
 * Tests for {@link Repeater}.
 */
public final class RepeaterTest {

    @Rule
    public final ExpectedException thrown = ExpectedException.none();

    private final Repeater sut = new Repeater();

    @Test
    public void apply_throwsExcpetionIfDescriptionIsNull() {
        thrown.expect(NullPointerException.class);
        thrown.expectMessage("Parameter 'description' must not be null!");

        sut.apply(mock(Statement.class), null);
    }

    @Test
    public void apply_throwsExcpetionIfBaseStatementIsNull() {
        thrown.expect(NullPointerException.class);
        thrown.expectMessage("Parameter 'base' must not be null!");

        sut.apply(null, mock(Description.class));
    }

    @Test
    public void apply_withNoAnnotationsReturnsBaseStatement() {
        final Statement base = mock(Statement.class);

        final Statement repeeted = sut.apply(
            base,
            Description.createTestDescription(Repeater.class, "foo"));

        assertThat(repeeted, is(sameInstance(base)));
    }

    @Test
    public void hasRepeatAnnotation_withRunTimes() {
        assertThat(
            sut.hasRunTimesAnnotation(Description.createTestDescription(Repeater.class, "foo", new RunTimes() {

                @Override
                public int value() {
                    return 1;
                }

                @Override
                public Class<? extends Annotation> annotationType() {
                    return RunTimes.class;
                }
            })),
            is(true));
    }

    @Test
    public void hasRepeatAnnotation_withNoRunTimes() {
        assertThat(
            sut.hasRunTimesAnnotation(Description.createTestDescription(Repeater.class, "foo")),
            is(false));
    }

    @Test
    public void hasRepeatAnnotation_withRunMaxTimes() {
        assertThat(
            sut.hasRunMaxTimesAnnotation(Description.createTestDescription(Repeater.class, "foo", new RunMaxTimes() {

                @Override
                public int value() {
                    return RunMaxTimes.DEFAULT_MAX_TIMES;
                }

                @Override
                public Class<? extends Annotation> annotationType() {
                    return RunMaxTimes.class;
                }
            })),
            is(true));
    }

    @Test
    public void hasRepeatAnnotation_withNoRunMaxTimes() {
        assertThat(
            sut.hasRunMaxTimesAnnotation(Description.createTestDescription(Repeater.class, "foo")),
            is(false));
    }
}
