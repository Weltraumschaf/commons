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
public abstract class InvokableAdapter implements Invokable {

    private final String[] args;

    private IOStreams ioStreams;

    private final ShutDownHook shutDownHooks = new ShutDownHook();

    public InvokableAdapter(final String[] args) {
        this.args = args.clone();
    }

    public static void main(Invokable invokanle) {
        main(invokanle, IOStreams.newDefault());
    }

    public static void main(Invokable invokanle, final IOStreams ioStreams ) {
        invokanle.setIoStreams(ioStreams);

        try {
            invokanle.execute();
        } catch (Exception ex) {
            ioStreams.println(ex.getMessage());
            System.exit(-1);
        }

        System.exit(0);
    }

    @Override
    public void execute() throws Exception {
        Runtime.getRuntime().addShutdownHook(shutDownHooks);
    }

    protected String[] getArgs() {
        return args;
    }

    @Override
    public IOStreams getIoStreams() {
        return ioStreams;
    }

    @Override
    public void setIoStreams(IOStreams ioStreams) {
        this.ioStreams = ioStreams;
    }

    protected ShutDownHook getShutDownHooks() {
        return shutDownHooks;
    }

}
