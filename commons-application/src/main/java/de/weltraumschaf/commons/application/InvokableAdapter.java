/*
 * LICENSE
 *
 * "THE BEER-WARE LICENSE" (Revision 43):
 * "Sven Strittmatter" <weltraumschaf(at)googlemail(dot)com> wrote this file.
 * As long as you retain this notice you can do whatever you want with
 * this stuff. If we meet some day, and you think this stuff is worth it,
 * you can buy me a non alcohol-free beer in return.
 *
 * Copyright (C) 2012 "Sven Strittmatter" <weltraumschaf(at)googlemail(dot)com>
 */
package de.weltraumschaf.commons.application;

import de.weltraumschaf.commons.system.DefaultExiter;
import de.weltraumschaf.commons.system.ExitCode;
import de.weltraumschaf.commons.system.Exitable;
import java.io.UnsupportedEncodingException;
import java.util.concurrent.Callable;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Abstract base implementation of an command line invokable.
 *
 * <p>
 * Extend this class in your client code.
 * </p>
 *
 * <p>
 * Example:
 * </p>
 * <pre>{@code
 *  public final class MyApp extends InvokableAdapter {
 *
 *      public MyApp(final String[] args) {
 *          super(args);
 *      }
 *
 *      public static void main(final String[] args) {
 *          InvokableAdapter.main(new MyApp(args));
 *      }
 *
 *      &#064;Override
 *      public void execute() throws Exception {
 *          registerShutdownHook(new Runnable() {
 *
 *              &#064;Override
 *              public void run() {
 *                  // Your shutdown code goes here.
 *              }
 *          });
 *
 *          // Your application code here.
 *      }
 *  }
 * }</pre>
 *
 * @since 1.0.0
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
public abstract class InvokableAdapter implements Invokable {

    /**
     * Copy of the command line arguments.
     */
    private final String[] args;

    /**
     * Container for shutdown {@link Runnable callbacks}.
     */
    private final ShutDownHook shutDownHook;

    /**
     * I/O streams.
     */
    private IO ioStreams;

    /**
     * Abstraction for {@link System#exit(int)}.
     */
    private Exitable exiter = new DefaultExiter();

    /**
     * Runtime for registering callbacks.
     */
    private final Runtime runtime;

    /**
     * Copies the command line arguments.
     *
     * @param args Command line arguments.
     */
    public InvokableAdapter(final String[] args) {
        this(args, Runtime.getRuntime(), new ShutDownHook());
    }

    /**
     * Dedicated constructor.
     *
     * @param args Command line arguments.
     * @param runtime Runtime for registering callbacks.
     * @param shutDownHook Object to hold shutdown hooks.
     */
    public InvokableAdapter(final String[] args, final Runtime runtime, final ShutDownHook shutDownHook) {
        this.args = args.clone();
        this.runtime = runtime;
        this.shutDownHook = shutDownHook;
    }

    /**
     * Invokes {@link #main(de.weltraumschaf.commons.application.Invokable, de.weltraumschaf.commons.application.IO)
     * invokable} with default I/O.
     *
     * @param invokable Implementation to invoke.
     */
    public static void main(final Invokable invokable) {
        try {
            main(invokable, IOStreams.newDefault());
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(InvokableAdapter.class.getName()).log(Level.SEVERE, null, ex);
            invokable.exit(-1);
        }
    }

    /**
     * Inject the I/O streams to the invokable and then calls {@link Invokable#init()} and then
     * {@link Invokable#execute()}.
     *
     * This method handles ell thrown {@link Exception} and calls {@link System#exit(int)}.
     *
     * @param invokable Implementation to invoke.
     * @param ioStreams I/O streams.
     */
    public static void main(final Invokable invokable, final IO ioStreams) {
        main(invokable, ioStreams, false);
    }

    /**
     * Inject the I/O streams to the invokable and then calls {@link Invokable#init()} and then
     * {@link Invokable#execute()}.
     *
     * This method handles ell thrown {@link Exception} and calls {@link System#exit(int)}, and prints stack trace if
     * <code>debug</code> is <tt>true</tt>.
     *
     * @param invokable implementation to invoke
     * @param ioStreams I/O streams
     * @param debug print stack trace if true
     */
    public static void main(final Invokable invokable, final IO ioStreams, final boolean debug) {
        invokable.setIoStreams(ioStreams);

        try {
            invokable.init();
            invokable.execute();
        } catch (final Exception ex) {
            ioStreams.errorln("FATAL: " + ex.getMessage());

            if (debug) {
                ioStreams.printStackTrace(ex);
            }

            if (ex instanceof ApplicationException) { // NOPMD Don't want to duplicate code for ERR out.
                invokable.exit(((ApplicationException) ex).getExitCode());
            } else {
                invokable.exit(-1);
            }
        }

        invokable.exit(0);
    }

    /**
     * Adds shutdown hook to runtime.
     */
    @Override
    public final void init() {
        runtime.addShutdownHook(shutDownHook);
    }

    /**
     * Get the command line arguments.
     *
     * @return Returns copy of the arguments array.
     */
    public final String[] getArgs() {
        return args.clone();
    }

    @Override
    public final IO getIoStreams() {
        return ioStreams;
    }

    @Override
    public final void setIoStreams(final IO ioStreams) {
        this.ioStreams = ioStreams;
    }

    /**
     * Register a {@link Runnable callback} as shut down hook.
     *
     * @param callback must not be {@code null}
     */
    public final void registerShutdownHook(final Runnable callback) {
        shutDownHook.register(callback);
    }

    /**
     * Register a {@link Callable callback} as shut down hook.
     *
     * @param callback must not be {@code null}
     */
    public final void registerShutdownHook(final Callable<Void> callback) {
        shutDownHook.register(callback);
    }

    @Override
    public final void exit(int status) {
        exiter.exit(status);
    }

    @Override
    public final void exit(ExitCode status) {
        exiter.exit(status);
    }

    @Override
    public final void setExiter(final Exitable exiter) {
        this.exiter = exiter;
    }

}
