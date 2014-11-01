/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package de.weltraumschaf.commons.time;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * {@code StopWatch} provides a convenient API for timings.
 *
 * <p>
 * To start the watch, call {@link #start()}. At this point you can:
 * </p>
 * <ul>
 * <li>{@link #split()} the watch to get the time whilst the watch continues in the background. {@link #unsplit()} will
 * remove the effect of the split. At this point, these three options are available again.</li>
 * <li>{@link #suspend()} the watch to pause it. {@link #resume()} allows the watch to continue. Any time between the
 * suspend and resume will not be counted in the total. At this point, these three options are available again.</li>
 * <li>{@link #stop()} the watch to complete the timing session.</li>
 * </ul>
 *
 * <p>
 * It is intended that the output methods {@link #toString()} and {@link #getTime()} should only be called after stop,
 * split or suspend, however a suitable result will be returned at other points.
 * </p>
 *
 * <p>
 * NOTE: As from v2.1, the methods protect against inappropriate calls. Thus you cannot now call stop before start,
 * resume before suspend or unsplit before split.
 * </p>
 *
 * <ol>
 * <li>{@code split()}, {@code suspend()}, or {@code stop()} cannot be invoked twice</li>
 * <li>{@code unsplit()} may only be called if the watch has been {@code split()}</li>
 * <li>{@code resume()} may only be called if the watch has been {@code suspend()}</li>
 * <li>{@code start()} cannot be called twice without calling {@code reset()}</li>
 * </ol>
 *
 * <p>
 * This class is not thread-safe
 * </p>
 *
 * @since 1.0.0
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
public final class StopWatch {

    /**
     * Default format for string representation.
     */
    private static final String DATE_FORMAT = "HH:mm:ss.SSS";

    /**
     * Factor to convert from nanoseconds to milliseconds.
     */
    private static final long NANO_2_MILLIS = 1_000_000L;

    /**
     * The current running state of the StopWatch.
     */
    State runningState = State.UNSTARTED;

    /**
     * Whether the stopwatch has a split time recorded.
     */
    SplitState splitState = SplitState.UNSPLIT;

    /**
     * The start time.
     */
    private long startTime;

    /**
     * The start time in Millis - nanoTime is only for elapsed time so we need to also store the currentTimeMillis to
     * maintain the old getStartTime API.
     */
    private long startTimeMillis;

    /**
     * The stop time.
     */
    private long stopTime;

    /**
     * Start the stopwatch.
     *
     * <p>
     * This method starts a new timing session, clearing any previous values.
     * </p>
     *
     * <p>
     * Throws {@link IllegalStateException} if the StopWatch is already running or not started yet.
     * </p>
     */
    public void start() {
        if (this.runningState == State.STOPPED) {
            throw new IllegalStateException("Stopwatch must be reset before being restarted!");
        }

        if (this.runningState != State.UNSTARTED) {
            throw new IllegalStateException("Stopwatch already started!");
        }

        this.startTime = System.nanoTime();
        this.startTimeMillis = System.currentTimeMillis();
        this.runningState = State.RUNNING;
    }

    /**
     * Stop the stopwatch.
     *
     * <p>
     * This method ends a new timing session, allowing the time to be retrieved.
     * </p>
     *
     * <p>
     * Throws {@link IllegalStateException} if the StopWatch is not running.
     * </p>
     */
    public void stop() {
        if (this.runningState != State.RUNNING && this.runningState != State.SUSPENDED) {
            throw new IllegalStateException("Stopwatch is not running!");
        }

        if (this.runningState == State.RUNNING) {
            this.stopTime = System.nanoTime();
        }

        this.runningState = State.STOPPED;
    }

    /**
     * Resets the stopwatch. Stops it if need be.
     *
     * <p>
     * This method clears the internal values to allow the object to be reused.
     * </p>
     */
    public void reset() {
        this.runningState = State.UNSTARTED;
        this.splitState = SplitState.UNSPLIT;
    }

    /**
     * Split the time.
     *
     * <p>
     * This method sets the stop time of the watch to allow a time to be extracted. The start time is unaffected,
     * enabling {@link #unsplit()} to continue the timing from the original start point.
     * </p>
     *
     * <p>
     * Throws {@link IllegalStateException} if the StopWatch is not running.
     * </p>
     */
    public void split() {
        if (this.runningState != State.RUNNING) {
            throw new IllegalStateException("Stopwatch is not running!");
        }

        this.stopTime = System.nanoTime();
        this.splitState = SplitState.SPLIT;
    }

    /**
     * Remove a split.
     *
     * <p>
     * This method clears the stop time. The start time is unaffected, enabling timing from the original start point to
     * continue.
     * </p>
     *
     * <p>
     * Throws {@link IllegalStateException} if the StopWatch has not been split.
     * </p>
     */
    public void unsplit() {
        if (this.splitState != SplitState.SPLIT) {
            throw new IllegalStateException("Stopwatch has not been split!");
        }

        this.splitState = SplitState.UNSPLIT;
    }

    /**
     * Suspend the stopwatch for later resumption.
     *
     * <p>
     * This method suspends the watch until it is resumed. The watch will not include time between the suspend and
     * resume calls in the total time.
     * </p>
     *
     * <p>
     * Throws {@link IllegalStateException} if the StopWatch is not currently running.
     * </p>
     */
    public void suspend() {
        if (this.runningState != State.RUNNING) {
            throw new IllegalStateException("Stopwatch must be running to suspend!");
        }

        this.stopTime = System.nanoTime();
        this.runningState = State.SUSPENDED;
    }

    /**
     * Resume the stopwatch after a suspend.
     *
     * <p>
     * This method resumes the watch after it was suspended. The watch will not include time between the suspend and
     * resume calls in the total time.
     * </p>
     *
     * <p>
     * Throws {@link IllegalStateException} if the StopWatch has not been suspended.
     * </p>
     */
    public void resume() {
        if (this.runningState != State.SUSPENDED) {
            throw new IllegalStateException("Stopwatch must be suspended to resume!");
        }

        this.startTime += System.nanoTime() - this.stopTime;
        this.runningState = State.RUNNING;
    }

    /**
     * Get the time on the stopwatch.
     *
     * <p>
     * This is either the time between the start and the moment this method is called, or the amount of time between
     * start and stop.
     * </p>
     *
     * @return the time in milliseconds
     */
    public long getTime() {
        return getNanoTime() / NANO_2_MILLIS;
    }

    /**
     * Get the time on the stopwatch in nanoseconds.
     *
     * <p>
     * This is either the time between the start and the moment this method is called, or the amount of time between
     * start and stop.
     * </p>
     *
     * @return the time in nanoseconds
     */
    public long getNanoTime() {
        if (this.runningState == State.STOPPED || this.runningState == State.SUSPENDED) {
            return this.stopTime - this.startTime;
        } else if (this.runningState == State.UNSTARTED) {
            return 0;
        } else if (this.runningState == State.RUNNING) {
            return System.nanoTime() - this.startTime;
        }

        throw new RuntimeException("Illegal running state has occurred.");
    }

    /**
     * Get the split time on the stopwatch.
     *
     * <p>
     * This is the time between start and latest split.
     * </p>
     *
     * <p>
     * Throws {@link IllegalStateException} if the StopWatch has not yet been split.
     * </p>
     *
     * @return the split time in milliseconds
     */
    public long getSplitTime() {
        return getSplitNanoTime() / NANO_2_MILLIS;
    }

    /**
     * Get the split time on the stopwatch in nanoseconds.
     *
     * <p>
     * This is the time between start and latest split.
     * </p>
     *
     * <p>
     * Throws {@link IllegalStateException} if the StopWatch has not yet been split.
     * </p>
     *
     * @return the split time in nanoseconds
     */
    public long getSplitNanoTime() {
        if (this.splitState != SplitState.SPLIT) {
            throw new IllegalStateException("Stopwatch must be split to get the split time!");
        }

        return this.stopTime - this.startTime;
    }

    /**
     * Returns the time this stopwatch was started.
     *
     * <p>
     * Throws {@link IllegalStateException} if this StopWatch has not been started.
     * </p>
     *
     * @return the time this stopwatch was started
     */
    public long getStartTime() {
        if (this.runningState == State.UNSTARTED) {
            throw new IllegalStateException("Stopwatch has not been started!");
        }

        return this.startTimeMillis;
    }

    /**
     * Gets a summary of the time that the stopwatch recorded as a string.
     *
     * <p>
     * The format used is ISO8601-like, <i>hours</i>:<i>minutes</i>:<i>seconds</i>.<i>milliseconds</i>.
     * </p>
     *
     * @return the time as a String
     */
    @Override
    public String toString() {
        final Date date = new Date(getTime());
        final DateFormat formatter = new SimpleDateFormat(DATE_FORMAT);
        return formatter.format(date);
    }

    /**
     * Gets a summary of the split time that the stopwatch recorded as a string.
     *
     * <p>
     * The format used is ISO8601-like, <i>hours</i>:<i>minutes</i>:<i>seconds</i>.<i>milliseconds</i>.
     * </p>
     *
     * @return the split time as a String
     */
    public String toSplitString() {
        final Date date = new Date(getSplitTime());
        final DateFormat formatter = new SimpleDateFormat(DATE_FORMAT);
        return formatter.format(date);
    }

    /**
     * The method is used to find out if the StopWatch is started. A suspended StopWatch is also started watch.
     *
     * @return boolean If the StopWatch is started.
     */
    public boolean isStarted() {
        return runningState.isStarted();
    }

    /**
     * This method is used to find out whether the StopWatch is suspended.
     *
     * @return boolean If the StopWatch is suspended.
     */
    public boolean isSuspended() {
        return runningState.isSuspended();
    }

    /**
     * <p>
     * This method is used to find out whether the StopWatch is stopped. The stopwatch which's not yet started and
     * explicitly stopped stopwatch is considered as stopped.
     * </p>
     *
     * @return boolean If the StopWatch is stopped.
     */
    public boolean isStopped() {
        return runningState.isStopped();
    }

    /**
     * Enumeration type which indicates the split status of stopwatch.
     */
    enum SplitState {

        /**
         * Stopwatch is split.
         */
        SPLIT,
        /**
         * Stopwatch is unsplit.
         */
        UNSPLIT;
    }

    /**
     * Enumeration type which indicates the status of stopwatch.
     */
    enum State {

        /**
         * Stopwatch is not started.
         */
        UNSTARTED {
            @Override
            boolean isStarted() {
                return false;
            }

            @Override
            boolean isStopped() {
                return true;
            }

            @Override
            boolean isSuspended() {
                return false;
            }
        },
        /**
         * Stopwatch is running.
         */
        RUNNING {
            @Override
            boolean isStarted() {
                return true;
            }

            @Override
            boolean isStopped() {
                return false;
            }

            @Override
            boolean isSuspended() {
                return false;
            }
        },
        /**
         * Stopwatch is stopped.
         */
        STOPPED {
            @Override
            boolean isStarted() {
                return false;
            }

            @Override
            boolean isStopped() {
                return true;
            }

            @Override
            boolean isSuspended() {
                return false;
            }
        },
        /**
         * Stopwatch is suspended.
         */
        SUSPENDED {
            @Override
            boolean isStarted() {
                return true;
            }

            @Override
            boolean isStopped() {
                return false;
            }

            @Override
            boolean isSuspended() {
                return true;
            }
        };

        /**
         * The method is used to find out if the StopWatch is started.
         *
         * A suspended StopWatch is also started watch.
         *
         * @return boolean If the StopWatch is started.
         */
        abstract boolean isStarted();

        /**
         * This method is used to find out whether the StopWatch is stopped.
         *
         * The stopwatch which's not yet started and explicitly stopped stopwatch is considered as stopped.
         *
         * @return boolean If the StopWatch is stopped.
         */
        abstract boolean isStopped();

        /**
         * This method is used to find out whether the StopWatch is suspended.
         *
         * @return boolean If the StopWatch is suspended.
         */
        abstract boolean isSuspended();
    }
}
