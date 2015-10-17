package de.weltraumschaf.commons.testing;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

/**
 * Tests for {@link DelayedRepeater}.
 */
public class DelayedRepeaterTest {

    @Rule
    //CHECKSTYLE:OFF
    public final ExpectedException thrown = ExpectedException.none();
  //CHECKSTYLE:ON

    @Test
    @SuppressWarnings("ResultOfObjectAllocationIgnored")
    public void throwExceptionIfConstructWithWaitMilliesLessThanZero() {
        this.thrown.expect(IllegalArgumentException.class);
        DelayedRepeater.create(-1, 3);
    }

    @Test
    @SuppressWarnings("ResultOfObjectAllocationIgnored")
    public void throwExceptionIfConstructWithMaxRetriesLessThanOne() {
        this.thrown.expect(IllegalArgumentException.class);
        DelayedRepeater.create(500, 0);
    }

    @Test
    public void shouldFailReturnsFalseByDefault() {
        assertThat(DelayedRepeater.create(500, 3).shouldFail(), is(false));
    }

    @Test
    public void onlyExecuteOnceIfNoAssertionOrInterruptedException() throws InterruptedException {
        final RunnableStub task = new RunnableStub();
        final DelayedRepeater sut = DelayedRepeater.create(10, 3);
        sut.execute(task);
        assertThat(sut.shouldFail(), is(false));
        assertThat(task.getExecutedCount(), is(1));
    }

    @Test
    public void executeThreeTimesIfAssertionErrrorIsThrownAndMaxRetryIsThree() throws InterruptedException {
        final RunnableStub task = new RunnableStub();
        task.throwAssertionError();
        final DelayedRepeater sut = DelayedRepeater.create(10, 3);

        try {
            sut.execute(task);
        } catch (final AssertionError ex) { /* Ignore because we provoke it. */

        }

        assertThat(sut.shouldFail(), is(true));
        assertThat(task.getExecutedCount(), is(3));
    }

    private static final class RunnableStub implements Runnable {

        private int executedCount;

        private boolean throwAssertionError;

        @Override
        public void run() {
            ++this.executedCount;

            if (this.throwAssertionError) {
                throw new AssertionError();
            }
        }

        public int getExecutedCount() {
            return this.executedCount;
        }

        public void throwAssertionError() {
            this.throwAssertionError = true;
        }

    }
}
