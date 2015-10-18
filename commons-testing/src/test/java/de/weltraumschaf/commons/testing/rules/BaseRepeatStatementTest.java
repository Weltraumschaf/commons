package de.weltraumschaf.commons.testing.rules;

import de.weltraumschaf.commons.testing.rules.Repeater.BaseRepeatStatement;
import de.weltraumschaf.commons.testing.rules.Repeater.RepeatStatement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import static org.hamcrest.Matchers.closeTo;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runners.model.Statement;
import static org.mockito.Mockito.mock;

/**
 * Tests for {@link BaseRepeatStatement}.
 */
public final class BaseRepeatStatementTest {

    private static final double ALLOWED_DEVIATION = 0.01d;

    private static final String NL = String.format("%n");

    @Rule
    public final ExpectedException thrown = ExpectedException.none();

    @Test
    @SuppressWarnings("ResultOfObjectAllocationIgnored")
    public void constructWithTimesLessThanOneThrowsException() {
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("Parameter 'times' must be greater than 0!");

        new BaseRepeatStatementStub(0, mock(Statement.class));
    }

    @Test
    @SuppressWarnings("ResultOfObjectAllocationIgnored")
    public void constructWithNullAsBaseThrowsException() {
        thrown.expect(NullPointerException.class);
        thrown.expectMessage("Parameter 'base' must not be null!");

        new BaseRepeatStatementStub(1, null);
    }

    @Test
    public void assertNoErrors_withEmptyListDoesNotThrowException() {
        new BaseRepeatStatementStub(1, mock(Statement.class)).assertNoErrors(Collections.<Throwable>emptyList());
    }

    @Test
    public void assertNoErrors_withNonEmptyListDoesThrowException() {
        thrown.expect(AssertionError.class);
        thrown.expectMessage(
            "There were 1 (100.00 %) errors in 1 run:" + NL
            + "1. java.lang.Throwable: snafu" + NL
            + "\tat foo.bar(baz:42)" + NL);

        final List<Throwable> errors = new ArrayList<>();
        Throwable t = new Throwable("snafu"); // NOPMD Need this type as fixture.
        t.setStackTrace(new StackTraceElement[]{new StackTraceElement("foo", "bar", "baz", 42)});
        errors.add(t);

        new BaseRepeatStatementStub(1, mock(Statement.class)).assertNoErrors(errors);
    }

    @Test
    public void calculateFailedRepetitionPercentage_throwsExceptionIfTimesIsZero() {
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("Parameter times must not be less than 1!");

        BaseRepeatStatement.calculateFailedRepetitionPercentage(0, 5);
    }

    @Test
    public void calculateFailedRepetitionPercentage_throwsExceptionIfErrorsIsNegative() {
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("Parameter errors must not be less than 0!");

        BaseRepeatStatement.calculateFailedRepetitionPercentage(1, -1);
    }

    @Test
    public void calculateFailedRepetitionPercentage() {
        assertThat(
            RepeatStatement.calculateFailedRepetitionPercentage(5, 0),
            is(closeTo(0.0d, ALLOWED_DEVIATION)));
        assertThat(
            RepeatStatement.calculateFailedRepetitionPercentage(100, 10),
            is(closeTo(10.0d, ALLOWED_DEVIATION)));
        assertThat(
            RepeatStatement.calculateFailedRepetitionPercentage(100, 50),
            is(closeTo(50.0d, ALLOWED_DEVIATION)));
        assertThat(
            RepeatStatement.calculateFailedRepetitionPercentage(20, 10),
            is(closeTo(50.0d, ALLOWED_DEVIATION)));
        assertThat(
            RepeatStatement.calculateFailedRepetitionPercentage(70, 11),
            is(closeTo(15.71d, ALLOWED_DEVIATION)));
    }

    @Test
    public void formatErrors_trhowsExceptionIfErrorsIsNull() {
        thrown.expect(NullPointerException.class);
        thrown.expectMessage("Parameter 'errors' must not be null!");

        BaseRepeatStatement.formatErrors(null, 1);
    }

    @Test
    public void formatErrors_noErrorsInOneRun() {
        assertThat(
            BaseRepeatStatement.formatErrors(new ArrayList<Throwable>(), 1),
            is(equalTo("There were 0 (0.00 %) errors in 1 run")));
    }

    @Test
    public void formatErrors_noErrorsInTwoRuns() {
        assertThat(
            BaseRepeatStatement.formatErrors(new ArrayList<Throwable>(), 2),
            is(equalTo("There were 0 (0.00 %) errors in 2 runs")));
    }

    @Test
    public void formatErrors_oneErrorInOneRun() {
        final List<Throwable> errors = new ArrayList<>();
        Throwable t = new Throwable("snafu"); // NOPMD Need this type as fixture.
        t.setStackTrace(new StackTraceElement[]{new StackTraceElement("foo", "bar", "baz", 42)});
        errors.add(t);

        assertThat(
            RepeatStatement.formatErrors(errors, 1),
            is(equalTo("There were 1 (100.00 %) errors in 1 run:" + NL
                    + "1. java.lang.Throwable: snafu" + NL
                    + "\tat foo.bar(baz:42)" + NL)));
    }

    @Test
    public void formatErrors_twoErrorsInTenRuns() {
        final List<Throwable> errors = new ArrayList<>();
        Throwable t = new Throwable("snafu"); // NOPMD Need this type as fixture.
        t.setStackTrace(new StackTraceElement[]{new StackTraceElement("foo", "bar", "baz", 42)});
        errors.add(t);
        t = new Throwable("fubar"); // NOPMD Need this type as fixture.
        t.setStackTrace(new StackTraceElement[]{new StackTraceElement("foo2", "bar2", "baz2", 23)});
        errors.add(t);

        assertThat(
            BaseRepeatStatement.formatErrors(errors, 10),
            is(equalTo("There were 2 (20.00 %) errors in 10 runs:" + NL
                    + "1. java.lang.Throwable: snafu" + NL
                    + "\tat foo.bar(baz:42)" + NL + NL
                    + "2. java.lang.Throwable: fubar" + NL
                    + "\tat foo2.bar2(baz2:23)" + NL)));
    }

    private static final class BaseRepeatStatementStub extends BaseRepeatStatement {

        BaseRepeatStatementStub(int times, Statement base) {
            super(times, base);
        }

        @Override
        public void evaluate() throws Throwable {
            throw new UnsupportedOperationException("Not supported yet.");
        }

    }
}
