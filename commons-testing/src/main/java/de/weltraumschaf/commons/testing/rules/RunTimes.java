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
 * Annotates how may times a test method must be executed.
 * <p>
 * What is the use case of this stuff?
 * </p>
 *
 * <p>
 * Some times you have legacy code which depend on some wired timing or other
 * non-deterministic resources. Imagine a bug, where the customer tells you:
 * "Oh, it's not a big deal. It only occurs some times. Maybe 1 or two per one
 * hundred...". How do you reproduce that. If there is no other chance, then you
 * must execute that edge case 100 times and check if it fails at least once. Or
 * execute it 1000 times and check.
 * </p>
 *
 * <p>
 * For this use case is this annotation: To execute a test multiple time and see
 * how often it failed. The whole test will be green if all runs passed.
 * </p>
 *
 * <p>
 * Use this annotation together with the {@link de.weltraumschaf.commons.testing.rules.Repeater
 * repeater rule}.
 * </p>
 *
 * <p>
 * Example:
 * </p>
 *
 * <pre>
 * {@code
 *   &#064;Test
 *   &#064;RunTimes(10) // Runs this test ten times.
 *   public void someTestMethod() {
 *       // ...
 *   }
 * }</pre>
 *
 * <p>
 * Based on
 * <a href="http://www.codeaffine.com/2013/04/10/running-junit-tests-repeatedly-without-loops/">
 * Blog post by Frank Appel</a>.
 * </p>
 *
 * @see Repeater
 * @since 2.1.0
 * @author Sven Strittmatter &lt;weltraumschaf@googlemail.com&gt;
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface RunTimes {

    /**
     * How many times a test must be executed.
     * <p>
     *  Must return an int greater than 0.
     * </p>
     *
     * @return greater 0
     */
    int value();
}
