package de.weltraumschaf.commons.testing.rules;

import de.weltraumschaf.commons.validate.Validate;

/**
 * Object used by integration tests fro {@link Repeater}.
 */
final class Subject {

    /**
     * How many times {@link #run()} was called.
     */
    private int runnedTimes;
    /**
     * How many {@link #run()  runs} should throw an exception.
     */
    private int failForTimes;
    /**
     * Should {@link #run()} should throw exceptions.
     */
    private boolean shouldFail;

    /**
     * Do something.
     * <p>
     * Each invocation increments {@link #getRunnedTimes()}.
     * </p>
     * <p>
     * Depending on {@link #setShouldFail(boolean)} and {@link #failForTimes(int)}
     * this method throws {@link RuntimeException}.
     * </p>
     */
    public void run() {
        runnedTimes++;

        if (shouldFail) {
            if (0 == failForTimes) {
                // Always fail.
                throw fail();
            } else if (runnedTimes <= failForTimes) {
                // Only fail for failForTimes.
                throw fail();
            }
        }
    }

    /**
     * Create an error.
     *
     * @return never {@code null}, always new instance
     */
    private RuntimeException fail() {
        return new RuntimeException("Fail at run: " + runnedTimes);
    }

    /**
     * Whether {@link #run()} should throw error.
     *
     * @param shouldFail {@code true} for errors, else {@code false}
     */
    public void setShouldFail(final boolean shouldFail) {
        this.shouldFail = shouldFail;
    }

    /**
     * How many times {@link #run()} was invoked.
     *
     * @return not negative
     */
    public int getRunnedTimes() {
        return runnedTimes;
    }

    /**
     * If you do not want that {@link #run()} throws always an error you can set how many times it should.
     * <p>
     * Implicitly sey {@link #setShouldFail(boolean)} to {@code true}.
     * </p>
     *
     * @param times must be greater than 0
     */
    public void failForTimes(int times) {
        Validate.greaterThan(times, 0);
        setShouldFail(true);
        failForTimes = times;
    }

}
