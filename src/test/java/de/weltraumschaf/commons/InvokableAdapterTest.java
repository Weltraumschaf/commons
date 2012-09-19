/*
 * LICENSE
 *
 * "THE BEER-WARE LICENSE" (Revision 42):
 * "Sven Strittmatter" <weltraumschaf@googlemail.com> wrote this file.
 * As long as you retain this notice you can do whatever you want with
 * this stuff. If we meet some day, and you think this stuff is worth it,
 * you can buy me a beer in return.
 *
 */

package de.weltraumschaf.commons;

import java.io.InputStream;
import java.io.PrintStream;
import static org.junit.Assert.*;
import org.junit.Ignore;
import org.junit.Test;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

/**
 * Test cases for {@link InvokableAdapter}.
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
public class InvokableAdapterTest {

    private final String[] args = new String[] {"foo", "bar", "baz"};

    private final InvokableAdapter sut = new InvokableAdapter(args) {

        @Override
        public void execute() throws Exception {
            throw new UnsupportedOperationException("Not supported yet.");
        }

    };

    @Test public void mainWithCustomDefaultIo() throws Exception {
        final IOStreams io = IOStreams.newDefault();
        final Invokable app = mock(Invokable.class);

        InvokableAdapter.main(app, io);
        verify(app, times(1)).setIoStreams(io);
        verify(app, times(1)).init();
        verify(app, times(1)).execute();
        verify(app, times(1)).exit(0);
    }

    @Test public void mainWithDefaultIo() throws Exception {
        final Invokable app = mock(Invokable.class);

        InvokableAdapter.main(app);
        verify(app, times(1)).setIoStreams(any(IOStreams.class));
        verify(app, times(1)).init();
        verify(app, times(1)).execute();
        verify(app, times(1)).exit(0);
    }

    @Test public void mainWithExceptionInInit() throws Exception {
        final Invokable app = mock(Invokable.class);
        final String msg = "Error message";
        doThrow(new RuntimeException(msg)).when(app).init();
        final IOStreams io = new IOStreams(mock(InputStream.class),
                                           mock(PrintStream.class),
                                           mock(PrintStream.class));

        InvokableAdapter.main(app, io);
        verify(app, times(1)).setIoStreams(io);
        verify(app, times(1)).init();
        verify(io.getStderr(), times(1)).println(msg);
        verify(app, never()).execute();
        verify(app, times(1)).exit(-1);
    }

    @Test public void mainWithExceptionInExecute() throws Exception {
        final Invokable app = mock(Invokable.class);
        final String msg = "Error message";
        doThrow(new RuntimeException(msg)).when(app).execute();
        final IOStreams io = new IOStreams(mock(InputStream.class),
                                           mock(PrintStream.class),
                                           mock(PrintStream.class));

        InvokableAdapter.main(app, io);
        verify(app, times(1)).setIoStreams(io);
        verify(app, times(1)).init();
        verify(app, times(1)).execute();
        verify(io.getStderr(), times(1)).println(msg);
        verify(app, times(1)).exit(-1);
    }

    @Test public void getArgs() {
        assertArrayEquals(args, sut.getArgs());
        assertNotSame(args, sut.getArgs());
    }

    @Test public void getShutDownHook() {
        assertNotNull(sut.getShutDownHooks());
    }

    @Test public void getAndSetIo() {
        assertNull(sut.getIoStreams());
        final IOStreams io = IOStreams.newDefault();
        sut.setIoStreams(io);
        assertSame(io, sut.getIoStreams());
    }

}