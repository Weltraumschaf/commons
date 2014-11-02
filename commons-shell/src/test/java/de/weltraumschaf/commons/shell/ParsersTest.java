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
package de.weltraumschaf.commons.shell;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import org.junit.Test;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;
import org.junit.Rule;
import org.junit.rules.ExpectedException;
import static org.mockito.Mockito.mock;

/**
 * Tests for {@link Parsers}.
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
public class ParsersTest {

    @Rule
    //CHECKSTYLE:OFF
    public final ExpectedException thrown = ExpectedException.none();
    //CHECKSTYLE:ON

    @Test
    public void invokeConstructorByReflectionThrowsException() throws Exception {
        assertThat(Parsers.class.getDeclaredConstructors().length, is(1));

        final Constructor<Parsers> ctor = Parsers.class.getDeclaredConstructor();
        ctor.setAccessible(true);

        thrown.expect(either(instanceOf(UnsupportedOperationException.class))
                .or(instanceOf(InvocationTargetException.class)));
        ctor.newInstance();
    }

    @Test
    public void newParser_withCommandMap_neverNull() {
        assertThat(Parsers.newParser(mock(LiteralCommandMap.class)), is(not(nullValue())));
        assertThat(Parsers.newParser(mock(LiteralCommandMap.class)), is(not(nullValue())));
        assertThat(Parsers.newParser(mock(LiteralCommandMap.class)), is(not(nullValue())));
    }

    @Test
    public void newParser_withCommandMap_alwaysNewInstance() {
        final Parser one = Parsers.newParser(mock(LiteralCommandMap.class));
        final Parser two = Parsers.newParser(mock(LiteralCommandMap.class));
        final Parser three = Parsers.newParser(mock(LiteralCommandMap.class));

        assertThat(one, is(not(sameInstance(two))));
        assertThat(one, is(not(sameInstance(three))));
        assertThat(two, is(not(sameInstance(three))));
    }

    @Test
    public void newParser_withScannerAndCommandMap_neverNull() {
        assertThat(Parsers.newParser(
            mock(Scanner.class),
            mock(LiteralCommandMap.class)),
                is(not(nullValue())));
        assertThat(Parsers.newParser(
            mock(Scanner.class),
            mock(LiteralCommandMap.class)),
                is(not(nullValue())));
        assertThat(Parsers.newParser(
            mock(Scanner.class),
            mock(LiteralCommandMap.class)),
                is(not(nullValue())));
    }

    @Test
    public void newParser_withScannerAndCommandMap_alwaysNewInstance() {
        final Parser one = Parsers.newParser(
            mock(Scanner.class),
            mock(LiteralCommandMap.class));
        final Parser two = Parsers.newParser(
            mock(Scanner.class),
            mock(LiteralCommandMap.class));
        final Parser three = Parsers.newParser(
            mock(Scanner.class),
            mock(LiteralCommandMap.class));

        assertThat(one, is(not(sameInstance(two))));
        assertThat(one, is(not(sameInstance(three))));
        assertThat(two, is(not(sameInstance(three))));
    }

    @Test
    public void newParser_withVerifierAndCommandMap_neverNull() {
        assertThat(Parsers.newParser(
            mock(CommandVerifier.class),
            mock(LiteralCommandMap.class)),
                is(not(nullValue())));
        assertThat(Parsers.newParser(
            mock(CommandVerifier.class),
            mock(LiteralCommandMap.class)),
                is(not(nullValue())));
        assertThat(Parsers.newParser(
            mock(CommandVerifier.class),
            mock(LiteralCommandMap.class)),
                is(not(nullValue())));
    }

    @Test
    public void newParser_withVerifierAndCommandMap_alwaysNewInstance() {
        final Parser one = Parsers.newParser(
            mock(CommandVerifier.class),
            mock(LiteralCommandMap.class));
        final Parser two = Parsers.newParser(
            mock(CommandVerifier.class),
            mock(LiteralCommandMap.class));
        final Parser three = Parsers.newParser(
            mock(CommandVerifier.class),
            mock(LiteralCommandMap.class));

        assertThat(one, is(not(sameInstance(two))));
        assertThat(one, is(not(sameInstance(three))));
        assertThat(two, is(not(sameInstance(three))));
    }

    @Test
    public void newParser_withScanneVerifierAndCommandMap_neverNull() {
        assertThat(Parsers.newParser(
            mock(Scanner.class),
            mock(CommandVerifier.class),
            mock(LiteralCommandMap.class)),
                is(not(nullValue())));
        assertThat(Parsers.newParser(
            mock(Scanner.class),
            mock(CommandVerifier.class),
            mock(LiteralCommandMap.class)),
                is(not(nullValue())));
        assertThat(Parsers.newParser(
            mock(Scanner.class),
            mock(CommandVerifier.class),
            mock(LiteralCommandMap.class)),
                is(not(nullValue())));
    }

    @Test
    public void newParser_withScanneVerifierAndCommandMap_alwaysNewInstance() {
        final Parser one = Parsers.newParser(
            mock(Scanner.class),
            mock(CommandVerifier.class),
            mock(LiteralCommandMap.class));
        final Parser two = Parsers.newParser(
            mock(Scanner.class),
            mock(CommandVerifier.class),
            mock(LiteralCommandMap.class));
        final Parser three = Parsers.newParser(
            mock(Scanner.class),
            mock(CommandVerifier.class),
            mock(LiteralCommandMap.class));

        assertThat(one, is(not(sameInstance(two))));
        assertThat(one, is(not(sameInstance(three))));
        assertThat(two, is(not(sameInstance(three))));
    }
}
