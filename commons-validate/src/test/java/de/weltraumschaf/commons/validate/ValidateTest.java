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
import org.hamcrest.Matchers;
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
}
