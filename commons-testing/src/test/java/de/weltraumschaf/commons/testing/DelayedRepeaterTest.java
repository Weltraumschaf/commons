package de.weltraumschaf.commons.testing;

import java.util.concurrent.Callable;
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
        thrown.expect(IllegalArgumentException.class);
        DelayedRepeater.create(-1, 3);
    }

    @Test
    @SuppressWarnings("ResultOfObjectAllocationIgnored")
    public void throwExceptionIfConstructWithMaxRetriesLessThanOne() {
        thrown.expect(IllegalArgumentException.class);
        DelayedRepeater.create(500, 0);
    }

    @Test(expected = NullPointerException.class)
    public void execute_withNullRunnableThrowsException() throws InterruptedException {
        DelayedRepeater.create(500, 1).execute((Runnable) null);
    }

    @Test(expected = NullPointerException.class)
    public void execute_withNullCallableThrowsException() throws InterruptedException {
        DelayedRepeater.create(500, 1).execute((Callable) null);
    }

    @Test
    public void shouldFailReturnsFalseByDefault() {
        assertThat(DelayedRepeater.create(500, 3).shouldFail(), is(false));
    }

    @Test
    public void onlyExecuteOnceIfNoAssertionOrInterruptedException_withRunnable() throws InterruptedException {
        final RunnableStub task = new RunnableStub();
        final DelayedRepeater sut = DelayedRepeater.create(10, 3);

        sut.execute(task);

        assertThat(sut.shouldFail(), is(false));
        assertThat(task.getExecutedCount(), is(1));
    }

    @Test
    public void executeThreeTimesIfAssertionErrrorIsThrownAndMaxRetryIsThree_withRunnable() throws InterruptedException {
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

    @Test
    public void onlyExecuteOnceIfNoAssertionOrInterruptedException_withCallable() throws InterruptedException {
        final CallableStub task = new CallableStub();
        final DelayedRepeater sut = DelayedRepeater.create(10, 3);

        sut.execute(task);

        assertThat(sut.shouldFail(), is(false));
        assertThat(task.getExecutedCount(), is(1));
    }

    @Test
    public void executeThreeTimesIfAssertionErrrorIsThrownAndMaxRetryIsThree_withCallable() throws InterruptedException {
        final CallableStub task = new CallableStub();
        task.throwAssertionError();
        final DelayedRepeater sut = DelayedRepeater.create(10, 3);

        try {
            sut.execute(task);
        } catch (final AssertionError ex) { /* Ignore because we provoke it. */

        }

        assertThat(sut.shouldFail(), is(true));
        assertThat(task.getExecutedCount(), is(3));
    }

    private static abstract class BaseStub {

        private int executedCount;

        private boolean throwAssertionError;

        final void throwAssertionError() {
            throwAssertionError = true;
        }

        final int getExecutedCount() {
            return this.executedCount;
        }

        protected final void execute() {
            ++executedCount;
            throwErrorIfWanted();
        }

        private void throwErrorIfWanted() {
            if (throwAssertionError) {
                throw new AssertionError();
            }
        }

    }

    private static final class RunnableStub extends BaseStub implements Runnable {

        @Override
        public void run() {
            execute();
        }

    }

    private static final class CallableStub extends BaseStub implements Callable<Void> {

        @Override
        public Void call() throws Exception {
            execute();
            return null;
        }

    }
}
