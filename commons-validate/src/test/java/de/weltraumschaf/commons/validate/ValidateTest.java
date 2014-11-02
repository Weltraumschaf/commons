/*
 *  LICENSE
 *
 * "THE BEER-WARE LICENSE" (Revision 43):
 * "Sven Strittmatter" <weltraumschaf@googlemail.com> wrote this file.
 * As long as you retain this notice you can do whatever you want with
 * this stuff. If we meet some day, and you think this stuff is worth it,
 * you can buy me a non alcohol-free beer in return.
 *
 * Copyright (C) 2012 "Sven Strittmatter" <weltraumschaf@googlemail.com>
 */
package de.weltraumschaf.commons.validate;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import static org.hamcrest.CoreMatchers.sameInstance;
import static org.hamcrest.Matchers.either;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import org.junit.Test;
import org.junit.Rule;
import org.junit.rules.ExpectedException;

/**
 * Tests for {@link Validate}.
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
public class ValidateTest {

    @Rule
    //CHECKSTYLE:OFF
    public final ExpectedException thrown = ExpectedException.none();
    //CHECKSTYLE:ON

    @Test
    public void invokeConstructorByReflectionThrowsException() throws Exception {
        assertThat(Validate.class.getDeclaredConstructors().length, is(1));

        final Constructor<Validate> ctor = Validate.class.getDeclaredConstructor();
        ctor.setAccessible(true);

        thrown.expect(either(instanceOf(UnsupportedOperationException.class))
                .or(instanceOf(InvocationTargetException.class)));
        ctor.newInstance();
    }

    @Test
    public void notNull_nullReferenceThrowsException_nullName() {
        thrown.expect(NullPointerException.class);
        Validate.notNull(null, null);
    }

    @Test
    public void notNull_nullReferenceThrowsException_notNullName() {
        thrown.expect(NullPointerException.class);
        thrown.expectMessage("Parameter 'foo' must not be null!");
        Validate.notNull(null, "foo");
    }

    @Test
    public void notNull_nullReferenceThrowsException() {
        thrown.expect(NullPointerException.class);
        Validate.notNull(null);
    }

    @Test
    public void notNull_notNullReferenceReturnsIt() {
        final Object o = new Object();
        assertThat(Validate.notNull(o), is(sameInstance(o)));
        assertThat(Validate.notNull(o, null), is(sameInstance(o)));
    }

    @Test
    public void notEmpty_nullReferenceThrowsException_nullName() {
        thrown.expect(NullPointerException.class);
        Validate.notEmpty(null, null);
    }

    @Test
    public void notEmpty_emptyReferenceThrowsException_nullName() {
        thrown.expect(IllegalArgumentException.class);
        Validate.notEmpty("", null);
    }

    @Test
    public void notEmpty_nullReferenceThrowsException_notNullName() {
        thrown.expect(NullPointerException.class);
        thrown.expectMessage("Parameter 'foo' must not be null!");
        Validate.notEmpty(null, "foo");
    }

    @Test
    public void notEmpty_emptyReferenceThrowsException_notNullName() {
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("Parameter 'foo' must not be empty!");
        Validate.notEmpty("", "foo");
    }

    @Test
    public void notEmpty_nullReferenceThrowsException() {
        thrown.expect(NullPointerException.class);
        Validate.notEmpty(null);
    }

    @Test
    public void notEmpty_emptyReferenceThrowsException() {
        thrown.expect(IllegalArgumentException.class);
        Validate.notEmpty("");
    }

    @Test
    public void notEmpty_notEmptyReferenceReturnsIt() {
        final String str = "foobar";
        assertThat(Validate.notEmpty(str), is(sameInstance(str)));
        assertThat(Validate.notEmpty(str, null), is(sameInstance(str)));
    }

    @Test
    public void greaterThan_int_greaterThanReturnsReference() {
        assertThat(Validate.greaterThan(5, 4, ""), is(5));
    }

    @Test
    public void greaterThan_int_sameValuesThrowsException() {
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("Parameter 'foo' must be greater than 5 (was 5)!");
        Validate.greaterThan(5, 5, "foo");
    }

    @Test
    public void greaterThan_int_referenceLessThrowsException() {
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("Parameter 'foo' must be greater than 5 (was 4)!");
        Validate.greaterThan(4, 5, "foo");
    }

    @Test
    public void greaterThanOrEqual_int_greaterThanReturnsReference() {
        assertThat(Validate.greaterThanOrEqual(5, 4, ""), is(5));
    }

    @Test
    public void greaterThanOrEqual_int_sameValuesThrowsException() {
        assertThat(Validate.greaterThanOrEqual(5, 5, ""), is(5));
    }

    @Test
    public void greaterThanOrEqual_int_referenceLessThrowsException() {
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("Parameter 'foo' must be greater or equal than 5 (was 4)!");
        Validate.greaterThanOrEqual(4, 5, "foo");
    }

    @Test
    public void greaterThan_long_greaterThanReturnsReference() {
        assertThat(Validate.greaterThan(5L, 4L, ""), is(5L));
    }

    @Test
    public void greaterThan_long_sameValuesThrowsException() {
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("Parameter 'foo' must be greater than 5 (was 5)!");
        Validate.greaterThan(5L, 5L, "foo");
    }

    @Test
    public void greaterThan_long_referenceLessThrowsException() {
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("Parameter 'foo' must be greater than 5 (was 4)!");
        Validate.greaterThan(4L, 5L, "foo");
    }

    @Test
    public void greaterThanOrEqual_long_greaterThanReturnsReference() {
        assertThat(Validate.greaterThanOrEqual(5L, 4L, ""), is(5L));
    }

    @Test
    public void greaterThanOrEqual_long_sameValuesThrowsException() {
        assertThat(Validate.greaterThanOrEqual(5L, 5L, ""), is(5L));
    }

    @Test
    public void greaterThanOrEqual_long_referenceLessThrowsException() {
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("Parameter 'foo' must be greater than or equal 5 (was 4)!");
        Validate.greaterThanOrEqual(4L, 5L, "foo");
    }

    @Test
    public void isTrue_falseThrowsExcpetion() {
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("message");
        Validate.isTrue(false, "message");
    }

    @Test
    public void isTrue_trueDoesNotThrowsExcpetion() {
        Validate.isTrue(true, "message");
    }
}
