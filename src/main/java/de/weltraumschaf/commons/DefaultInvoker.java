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

/**
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
public class DefaultInvoker implements Invokable {

    private final String[] args;

    private final IOStreams ioStreams;

    private final ShutDownHook shutDownHooks = new ShutDownHook();

    public DefaultInvoker(final String[] args, final IOStreams ioStreams) {
        this.args = args.clone();
        this.ioStreams = ioStreams;
    }

    public static void main(final String[] args) {
        final IOStreams ioStreams = IOStreams.newDefault();

        try {
            final Invokable application = new DefaultInvoker(args, ioStreams);
            application.execute();
        } catch (Exception ex) {
            ioStreams.getStderr().println(ex.getMessage());
            System.exit(-1);
        }
    }

    @Override
    public void execute() throws Exception {
        Runtime.getRuntime().addShutdownHook(shutDownHooks);
    }

    protected String[] getArgs() {
        return args;
    }

    protected IOStreams getIoStreams() {
        return ioStreams;
    }

    protected ShutDownHook getShutDownHooks() {
        return shutDownHooks;
    }

}
