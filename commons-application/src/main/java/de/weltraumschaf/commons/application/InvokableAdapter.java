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
import de.weltraumschaf.commons.validate.Validate;
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
 * @author Sven Strittmatter &lt;weltraumschaf@googlemail.com&gt;
 */
public abstract class InvokableAdapter implements Invokable {

    /**
     * Use this field to enable the printing of stack traces.
     * <p>
     * By default this value is {@code false}. If you want stack traces from exceptions bubbled up until the main
     * function, then set this field to {@code true}.
     * </p>
     */
    public boolean debug; // NOSONAR Must be public to be set from static environment.

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
        super();
        this.args = args.clone();
        this.runtime = runtime;
        this.shutDownHook = shutDownHook;
    }

    /**
     * Invokes {@link #main(de.weltraumschaf.commons.application.Invokable, de.weltraumschaf.commons.application.IO)
     * invokable} with default I/O.
     *
     * @param invokable must not be {@code null}
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
     * Inject the I/O streams to the invokable and then calls
     * {@link de.weltraumschaf.commons.application.Invokable#init()} and then
     * {@link de.weltraumschaf.commons.application.Invokable#execute()}.
     * <p>
     * This method handles ell thrown {@link java.lang.Exception} and calls {@link java.lang.System#exit(int)}.
     * </p>
     *
     * @param invokable must not be {@code null}
     * @param ioStreams must not be {@code null}
     */
    public static void main(final Invokable invokable, final IO ioStreams) {
        Validate.notNull(invokable, "invokable");
        invokable.setIoStreams(ioStreams);

        try {
            invokable.init();
            invokable.execute();
        } catch (final Exception ex) {
            ioStreams.errorln("FATAL: " + ex.getMessage());

            if (invokable.isDebugEnabled()) {
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
     * Inject the I/O streams to the invokable and then calls
     * {@link de.weltraumschaf.commons.application.Invokable#init()} and then
     * {@link de.weltraumschaf.commons.application.Invokable#execute()}.
     *
     * <p>
     * This method handles ell thrown {@link java.lang.Exception} and calls {@link java.lang.System#exit(int)}, and
     * prints stack trace if {@code debug} is {@code true}.
     * </p>
     *
     * @param invokable implementation to invoke
     * @param ioStreams I/O streams
     * @param debug print stack trace if {@code true}
     * @deprecated use
     * {@link #main(de.weltraumschaf.commons.application.Invokable, de.weltraumschaf.commons.application.IO)} instead
     */
    @Deprecated
    public static void main(final Invokable invokable, final IO ioStreams, final boolean debug) {
        ((InvokableAdapter) invokable).debug = debug;
        main(invokable, ioStreams);
    }

    @Override
    public final boolean isDebugEnabled() {
        return debug;
    }

    /**
     * Adds shutdown hook to runtime.
     */
    @Override
    public final void init() {
        runtime.addShutdownHook(shutDownHook);

        if (debug) {
            getIoStreams().println("[Commons] Debug is on.");
        }
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
        this.ioStreams = Validate.notNull(ioStreams, "ioStreams");
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
        this.exiter = Validate.notNull(exiter, "exiter");
    }

}
