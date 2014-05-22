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

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.sameInstance;
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
    public final ExpectedException thrown = ExpectedException.none();

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
    public void greaterThan_greaterThanReturnsReference() {
        assertThat(Validate.greaterThan(5, 4, ""), is(5));
    }

    @Test
    public void greaterThan_sameValuesThrowsException() {
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("Parameter 'foo' muts be greater than 5 (was 5)!");
        Validate.greaterThan(5, 5, "foo");
    }

    @Test
    public void greaterThan_referenceLessThrowsException() {
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("Parameter 'foo' muts be greater than 5 (was 4)!");
        Validate.greaterThan(4, 5, "foo");
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
