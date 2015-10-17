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
package de.weltraumschaf.commons.testing.rules;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotates a test method to be executed until it does not fail or maximum is
 * reached.
 * <p>
 * What is the use case of this stuff?
 * </p>
 *
 * <p>
 * Imagine a test which only fails some times because of a timing issue. E.g.
 * you have UI tests which are almost always fine and green, but... Some times
 * the UI test machine is under heavy load and on UI widget occurs a nanosecond
 * too late and the test fails. This leads to test erosion and everybody will
 * ignore the test failures because in the next run the test will be green. The
 * real defect is discovered days too late...
 * </p>
 *
 * <p>
 * For issues like that this rule gives the possibility to say: Hey, if that
 * test fail, then give it a next chance. Maybe it is green on the next run.
 * \this rule will execute a test at least once, but if it fails repeats the
 * execution until it passes or the maximum is reached (if a test fails five
 * times or so in sequence, then it is maybe a real defect).
 * </p>
 *
 * <p>
 * Use this annotation together with the {@link de.iteratec.stu.testtools.Repeater
 * repeater rule}.
 * </p>
 *
 * <p>
 * Example with default maximum value:
 * </p>
 *
 * <pre>
 * {@code
 *   &#064;Test
 *   &#064;RepeatUntilSuccess // Max. five times.
 *   public void someTestMethod() {
 *       // ...
 *   }
 * }</pre>
 *
 * <p>
 * Example with custom maximum value:
 * </p>
 *
 * <pre>
 * {@code
 *   &#064;RepeatUntilSuccess(3) // Max. three times.
 *   public void someOtherTestMethod() {
 *       // ...
 *   }
 * }</pre>
 *
 * @see Repeater
 * @since 2.1.0
 * @author Sven Strittmatter &lt;weltraumschaf@googlemail.com&gt;
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface RepeatUntilSuccess {

    /**
     * Default value for maximum executions.
     */
    int DEFAULT_MAX_EXECUTION = 5;

    /**
     * How many times a test should be executed at maximum if it fails.
     *
     * @return must be greater than 0, defaults to {@link #DEFAULT_MAX_EXECUTION}
     */
    int maxExecutions() default DEFAULT_MAX_EXECUTION;
}
