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
package de.weltraumschaf.commons.time;

import de.weltraumschaf.commons.testing.hamcrest.CommonsTestingMatchers;
import de.weltraumschaf.commons.time.StopWatch.SplitState;
import de.weltraumschaf.commons.time.StopWatch.State;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

/**
 * Tests for {@link StopWatch}.
 *
 * @author Sven Strittmatter &lt;weltraumschaf@googlemail.com&gt;
 */
public class StopWatchTest {

    @Rule
    public final ExpectedException thrown = ExpectedException.none();
    private static final long DELTA = 20L;
    private final StopWatch sut = new StopWatch();

    private void assertTime(final long actual, final long expected) {
        assertThat(actual, is(CommonsTestingMatchers.closeTo(expected, DELTA)));
    }

    private void waitOneSecond() throws InterruptedException {
        Thread.sleep(1_000L);
    }

    @Test
    public void defaults() {
        assertThat(sut.isStarted(), is(false));
        assertThat(sut.isStopped(), is(true));
        assertThat(sut.isSuspended(), is(false));
        assertThat(sut.getNanoTime(), is(0L));
        assertThat(sut.getTime(), is(0L));
        assertThat(sut.toString(), is("01:00:00.000"));
    }

    @Test
    public void toSplitString_inUnsplitState() {
        thrown.expect(IllegalStateException.class);
        thrown.expectMessage("Stopwatch must be split to get the split time!");

        sut.toSplitString();
    }

    @Test
    public void getSplitNanoTime_inUnsplitState() {
        thrown.expect(IllegalStateException.class);
        thrown.expectMessage("Stopwatch must be split to get the split time!");

        sut.getSplitNanoTime();
    }

    @Test
    public void getSplitTime_inUnsplitState() {
        thrown.expect(IllegalStateException.class);
        thrown.expectMessage("Stopwatch must be split to get the split time!");

        sut.getSplitTime();
    }

    @Test
    public void getStartTime_inUnstartedState() {
        thrown.expect(IllegalStateException.class);
        thrown.expectMessage("Stopwatch has not been started!");

        sut.getStartTime();
    }

    @Test
    public void start_inStoppedState() {
        thrown.expect(IllegalStateException.class);
        thrown.expectMessage("Stopwatch must be reset before being restarted!");

        sut.runningState = State.STOPPED;
        sut.start();
    }

    @Test
    public void start_inRunningState() {
        thrown.expect(IllegalStateException.class);
        thrown.expectMessage("Stopwatch already started!");

        sut.runningState = State.RUNNING;
        sut.start();
    }

    @Test
    public void start_inSuspendedState() {
        thrown.expect(IllegalStateException.class);
        thrown.expectMessage("Stopwatch already started!");

        sut.runningState = State.SUSPENDED;
        sut.start();
    }

    @Test
    public void start() {
        sut.start();

        assertThat(sut.isStarted(), is(true));
        assertThat(sut.isStopped(), is(false));
        assertThat(sut.isSuspended(), is(false));
        assertThat(sut.splitState, is(SplitState.UNSPLIT));
    }

    @Test
    public void stop_inStopedState() {
        thrown.expect(IllegalStateException.class);
        thrown.expectMessage("Stopwatch is not running!");

        sut.runningState = State.STOPPED;
        sut.stop();
    }

    @Test
    public void stop_inUnstartedState() {
        thrown.expect(IllegalStateException.class);
        thrown.expectMessage("Stopwatch is not running!");

        sut.runningState = State.UNSTARTED;
        sut.stop();
    }

    @Test
    public void stop() {
        sut.start();
        sut.stop();

        assertThat(sut.isStarted(), is(false));
        assertThat(sut.isStopped(), is(true));
        assertThat(sut.isSuspended(), is(false));
        assertThat(sut.splitState, is(SplitState.UNSPLIT));
    }

    @Test
    public void reset() {
        sut.runningState = State.RUNNING;
        sut.splitState = SplitState.SPLIT;

        sut.reset();

        assertThat(sut.runningState, is(State.UNSTARTED));
        assertThat(sut.splitState, is(SplitState.UNSPLIT));
    }

    @Test
    public void split_inStoppedState() {
        thrown.expect(IllegalStateException.class);
        thrown.expectMessage("Stopwatch is not running!");

        sut.runningState = State.STOPPED;

        sut.split();
    }

    @Test
    public void split_inSuspendedState() {
        thrown.expect(IllegalStateException.class);
        thrown.expectMessage("Stopwatch is not running!");

        sut.runningState = State.SUSPENDED;

        sut.split();
    }

    @Test
    public void split_inUnstartedState() {
        thrown.expect(IllegalStateException.class);
        thrown.expectMessage("Stopwatch is not running!");

        sut.runningState = State.UNSTARTED;

        sut.split();
    }

    @Test
    public void split() {
        sut.start();
        sut.split();

        assertThat(sut.isStarted(), is(true));
        assertThat(sut.isStopped(), is(false));
        assertThat(sut.isSuspended(), is(false));
        assertThat(sut.splitState, is(SplitState.SPLIT));
    }

    @Test
    public void unsplit_inUnsplitState() {
        thrown.expect(IllegalStateException.class);
        thrown.expectMessage("Stopwatch has not been split!");

        sut.splitState = SplitState.UNSPLIT;

        sut.unsplit();
    }

    @Test
    public void unsplit() {
        sut.start();
        sut.split();

        sut.unsplit();

        assertThat(sut.splitState, is(SplitState.UNSPLIT));
    }

    @Test
    public void suspend_inStopped() {
        thrown.expect(IllegalStateException.class);
        thrown.expectMessage("Stopwatch must be running to suspend!");

        sut.runningState = State.STOPPED;

        sut.suspend();
    }

    @Test
    public void suspend_inSuspended() {
        thrown.expect(IllegalStateException.class);
        thrown.expectMessage("Stopwatch must be running to suspend!");

        sut.runningState = State.SUSPENDED;

        sut.suspend();
    }

    @Test
    public void suspend_inUnstarted() {
        thrown.expect(IllegalStateException.class);
        thrown.expectMessage("Stopwatch must be running to suspend!");

        sut.runningState = State.UNSTARTED;

        sut.suspend();
    }

    @Test
    public void suspend() {
        sut.runningState = State.RUNNING;

        sut.suspend();

        assertThat(sut.isStarted(), is(true));
        assertThat(sut.isSuspended(), is(true));
        assertThat(sut.isStopped(), is(false));
    }

    @Test
    public void resume_isInRunningState() {
        thrown.expect(IllegalStateException.class);
        thrown.expectMessage("Stopwatch must be suspended to resume!");

        sut.runningState = State.RUNNING;

        sut.resume();
    }

    @Test
    public void resume_isInStoppedState() {
        thrown.expect(IllegalStateException.class);
        thrown.expectMessage("Stopwatch must be suspended to resume!");

        sut.runningState = State.STOPPED;

        sut.resume();
    }

    @Test
    public void resume_isInUnstartedState() {
        thrown.expect(IllegalStateException.class);
        thrown.expectMessage("Stopwatch must be suspended to resume!");

        sut.runningState = State.UNSTARTED;

        sut.resume();
    }

    @Test
    public void resume() {
        sut.start();
        sut.suspend();

        sut.resume();

        assertThat(sut.runningState, is(State.RUNNING));
    }

    @Test
    public void startStopAndGetTime() throws InterruptedException {
        sut.start();
        waitOneSecond();
        sut.stop();

        assertTime(sut.getTime(), 1_000L);
    }

    @Test
    public void startSuspendresumeStopAndGetTime() throws InterruptedException {
        sut.start();
        waitOneSecond();
        sut.suspend();

        assertTime(sut.getTime(), 1_000L);

        waitOneSecond();
        sut.resume();
        waitOneSecond();
        sut.stop();

        assertTime(sut.getTime(), 2_000L);
    }

    @Test
    public void startSplitStopAndGetTime() throws InterruptedException {
        sut.start();
        waitOneSecond();
        sut.split();

        assertTime(sut.getTime(), 1_000L);

        waitOneSecond();
        sut.unsplit();
        waitOneSecond();
        sut.stop();

        assertTime(sut.getTime(), 3_000L);
    }
}
