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

import org.junit.Test;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

/**
 * Test cases for {@link InvokableAdapter}.
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
public class InvokableAdapterTest {

    private final String[] args = new String[] {"foo", "bar", "baz"};
    private final InvokableAdapter app = new InvokableAdapter(args) {

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
        doThrow(new RuntimeException()).when(app).init();

        InvokableAdapter.main(app);
        verify(app, times(1)).setIoStreams(any(IOStreams.class));
        verify(app, times(1)).init();
        verify(app, never()).execute();
        verify(app, times(1)).exit(-1);
    }

    @Test public void mainWithExceptionInExecute() throws Exception {
        final Invokable app = mock(Invokable.class);
        doThrow(new RuntimeException()).when(app).execute();

        InvokableAdapter.main(app);
        verify(app, times(1)).setIoStreams(any(IOStreams.class));
        verify(app, times(1)).init();
        verify(app, times(1)).execute();
        verify(app, times(1)).exit(-1);
    }

    @Test public void getArgs() {
        assertArrayEquals(args, app.getArgs());
        assertNotSame(args, app.getArgs());
    }

    @Test public void getShutDownHook() {
        assertNotNull(app.getShutDownHooks());
    }

    @Test public void getAndSetIo() {
        assertNull(app.getIoStreams());
        final IOStreams io = IOStreams.newDefault();
        app.setIoStreams(io);
        assertSame(io, app.getIoStreams());
    }

}