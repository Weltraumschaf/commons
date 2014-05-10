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

package de.weltraumschaf.commons.experimental;

import de.weltraumschaf.commons.experimental.SoftCache.Finder;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import static org.mockito.Mockito.*;

/**
 * Tests for {@link SoftCache}.
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
public class SoftCacheTest {

    @Rule
    // CHECKSTYLE:OFF
    public final ExpectedException thrown = ExpectedException.none();
    // CHECKSTYLE:ON

    @SuppressWarnings("unchecked")
    private final Finder<String, String> finder = mock(Finder.class);
    private final SoftCache<String, String> sut = new SoftCache<String, String>();

    @Before
    public void injectFinder() {
        sut.finder(finder);
    }

    @Test(expected = NullPointerException.class)
    public void finder_nullThrowsExcpetion() {
        sut.finder(null);
    }

    @Test
    public void get_uncached_noFinder() {
        thrown.expect(NullPointerException.class);
        thrown.expectMessage("No value fond for key 'foo'!");

        new SoftCache<String, String>().get("foo");
    }

    @Test
    public void get_uncached() {
        when(finder.find("foo")).thenReturn("bar");

        assertThat(sut.get("foo"), is(equalTo("bar")));
        verify(finder).find("foo");
    }

    @Test
    public void get_cached() {
        sut.add("foo", "bar");

        assertThat(sut.get("foo"), is(equalTo("bar")));
        verify(finder, never()).find("foo");
    }

    @Test
    public void remove() {
        when(finder.find("foo")).thenReturn("bar");
        sut.add("foo", "bar");

        assertThat(sut.get("foo"), is(equalTo("bar")));
        verify(finder, never()).find("foo");

        sut.remove("foo");
        assertThat(sut.get("foo"), is(equalTo("bar")));
        verify(finder).find("foo");
    }

}
