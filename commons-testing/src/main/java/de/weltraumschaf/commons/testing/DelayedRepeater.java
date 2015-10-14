/*
 *  LICENSE
 *
 * "THE BEER-WARE LICENSE" (Revision 43):
 * "Sven Strittmatter" &lt;weltraumschaf@googlemail.com&gt; wrote this file.
 * As long as you retain this notice you can do whatever you want with
 * this stuff. If we meet some day, and you think this stuff is worth it,
 * you can buy me a non alcohol-free beer in return.
 *
 * Copyright (C) 2012 "Sven Strittmatter" &lt;weltraumschaf@googlemail.com&gt;
 */
package de.weltraumschaf.commons.testing;

import de.weltraumschaf.commons.validate.Validate;
import java.util.concurrent.Callable;

/**
 * This helper is to test asynchronous behavior which does not provide call backs.
 * <p>
 * Imagine you have a job scheduler you want to test. You add some tasks to this scheduler and after a time period you
 * want to check if all jobs are done:
 * </p>
 *
 * <pre>
 * {@code
 * final List&lt;Job&gt; jobs = new ArrayList&lt;Job&gt;();
 * final int jobCount = 50;
 *
 * for (int i = 0; i &lt; jobCount; ++i) {
 *   jobs.add(new Job(&quot;Testjob&quot; + i));
 * }
 *
 * final Scheduler sut = new Scheduler();
 * sut.execute(jobs);
 * Thread.sleep(500);
 *
 * for (final Job job : jobs) {
 *   assertThat(job.isDone, is(true));
 * }
 * }</pre>
 *
 * <p>
 * The problem with this approach is, that most time on your power machine 500 ms is enough to do the jobs. But
 * sometimes it is not! So this approach will lead to a flaky test which will fail unexpectedly. See
 * <a href="http://martinfowler.com/articles/nonDeterminism.html">
 * Martin Fowlers Blog</a> for more information about non deterministic tests.
 * </p>
 * <p>
 * This helper class gives you the opportunity to retry tests. This is done by first waiting a time period in
 * combination with ignoring {@link AssertionError
 * test failures} until the maximum repetition limit is reached. So the above example will looks like:
 * </p>
 *
 * <pre>
 * {@code
 *  final List&lt;Job&gt; jobs = new ArrayList&lt;Job&gt;();
 *  final int jobCount = 50;
 *
 *  for (int i = 0; i &lt; jobCount; ++i) {
 *      jobs.add(new Job(&quot;Testjob&quot; + i));
 *  }
 *
 *  final Scheduler sut = new Scheduler();
 *  sut.execute(jobs);
 *
 *  DelayedRepeater.create(500, 3).execute(new Runnable(){
 *      public void run() {
 *          for (final Job job : jobs) {
 *              assertThat(job.isDone, is(true));
 *          }
 *      }
 *  });
 * }</pre>
 *
 * <p>
 * Or you can use {@link Callable}:
 * </p>
 *
 * * <pre>
 * {@code
 *  final List&lt;Job&gt; jobs = new ArrayList&lt;Job&gt;();
 *  final int jobCount = 50;
 *
 *  for (int i = 0; i &lt; jobCount; ++i) {
 *      jobs.add(new Job(&quot;Testjob&quot; + i));
 *  }
 *
 *  final Scheduler sut = new Scheduler();
 *  sut.execute(jobs);
 *  DelayedRepeater.create(500, 3).execute(new Callable<Void>(){
 *      public Void run() {
 *          for (final Job job : jobs) {
 *              assertThat(job.isDone, is(true));
 *          }
 *
 *          return null;
 *      }
 *  });
 * }</pre>
 *
 * <p>
 * The above example first waits for 500 ms before it invokes the runnable with the assertions. Also it ignores all
 * {@link AssertionError test failures} if some occurs. Then it waits again 500 ms and ignores failures. This happens
 * for maximum three times. After that it rethrows the {@link AssertionError test failures} and let your test fail. On
 * the other hand, if the assertions pass the loop is returned early. So in best case the example will run in 500 ms and
 * in worst case 1500 ms (plus other execution time).
 * </p>
 * <p>
 * The great benefit of this approach is that the test will run fast (it loops only until all assertions pass). But it
 * also dynamically retries on a slow machine until a maximum amount of time.
 * </p>
 *
 * @since 2.1.0
 * @author Sven Strittmatter &lt;weltraumschaf@googlemail.com&gt;
 */
public final class DelayedRepeater {

    /**
     * Milliseconds to wait before invoking {@link Runnable#run()}.
     */
    private final int waitMillies;

    /**
     * Maximum amount of retries before failing.
     */
    private final int maxRetries;

    /**
     * Current amount of tried loops.
     * <p>
     * Initialized and updated by {@link #execute(Runnable)}.
     */
    private int currentRetries;

    /**
     * Dedicated constructor.
     * <p>
     * Use {@link #create(int, int)} to get a new instance.
     * </p>
     *
     * @param waitMillies milliseconds to wait before invoking the runnable, must be greater than 0
     * @param maxRetries maxim number of tries, must be greater than 0
     */
    private DelayedRepeater(final int waitMillies, final int maxRetries) {
        super();
        Validate.isTrue(
            waitMillies > 0,
            "Parameter 'waitMillies' must not be less than 1!");
        Validate.isTrue(
            maxRetries > 0,
            "Parameter 'maxRetries' must not be less than 1!");
        this.waitMillies = waitMillies;
        this.maxRetries = maxRetries;
    }

    /**
     * Indicates that the whole test should fail because the maximum retries were exhausted.
     *
     * @return {@code true} if tests should fail, else {@code false}
     */
    boolean shouldFail() {
        return this.currentRetries == this.maxRetries;
    }

    /**
     * Takes a {@link Runnable runnable} with some assertions to execute multiple times.
     *
     * @param retriedAssertions must not be {@code null}
     * @throws InterruptedException if the sleep thread is notified
     */
    public void execute(final Runnable retriedAssertions) throws InterruptedException {
        Validate.notNull(retriedAssertions, "retriedAssertions");
        execute(new RunnableInvoker(retriedAssertions));
    }

    /**
     * Takes a {@link Callable callable} with some assertions to execute multiple times.
     *
     * @param retriedAssertions must not be {@code null}
     * @throws InterruptedException if the sleep thread is notified
     */
    public void execute(final Callable<Void> retriedAssertions) throws InterruptedException {
        Validate.notNull(retriedAssertions, "retriedAssertions");
        execute(new CallableInvoker(retriedAssertions));
    }

    /**
     * Executes the wrapped assertions until success or it should fail.
     *
     * @param wrapped must not be {@code null}
     * @throws InterruptedException if the sleep thread is notified
     */
    @SuppressWarnings("SleepWhileInLoop")
    private void execute(final Invoker wrapped) throws InterruptedException {
        this.currentRetries = 1;

        while (true) {
            try {
                Thread.sleep(this.waitMillies);
                wrapped.execute();
                break;
            } catch (final AssertionError ex) {
                if (shouldFail()) {
                    throw ex;
                }
            }

            ++this.currentRetries;
        }
    }

    /**
     * Factory method to create a new instance.
     *
     * @param waitMillies milliseconds to wait before invoking the runnable, must be greater than 0
     * @param maxRetries maxim number of tries, must be greater than 0
     * @return never {@code null}, always new instance
     */
    public static DelayedRepeater create(final int waitMillies, final int maxRetries) {
        return new DelayedRepeater(waitMillies, maxRetries);
    }

    /**
     * Interface to wrap to invocation of various targets.
     */
    private interface Invoker {

        /**
         * Executes the target.
         */
        void execute();
    }

    /**
     * Implementation to execute {@link Runnable}.
     */
    private static final class RunnableInvoker implements Invoker {

        /**
         * The invoked target.
         */
        private final Runnable target;

        /**
         * Dedicated constructor.
         *
         * @param target must not be {@code null}
         */
        private RunnableInvoker(final Runnable target) {
            super();
            this.target = Validate.notNull(target, "target");
        }

        @Override
        public void execute() {
            target.run();
        }
    }

    /**
     * Implementation to execute {@link Callable}.
     */
    private static final class CallableInvoker implements Invoker {

        /**
         * The invoked target.
         */
        private final Callable<Void> target;

        /**
         * Dedicated constructor.
         *
         * @param target must not be {@code null}
         */
        private CallableInvoker(final Callable<Void> target) {
            super();
            this.target = Validate.notNull(target, "target");
        }

        @Override
        public void execute() {
            try {
                target.call();
            } catch (final Exception ex) {
                throw new RuntimeException(ex.getMessage(), ex);
            }
        }
    }
}
