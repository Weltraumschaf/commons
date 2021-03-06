# Commons Application

## Invokable Interface and Classes

If     you    write     command     line     applications    (something     with
`main(java.langString[]))`  then you  tend to  always build  up the  same boiler
plate     code.      This     module      provides     a      abstarct     class
[InvokableAdapter][InvokableAdapter]  for  that  boiler  plate  code.  It  is  a
defautl implementation  of [Invokable][Invokable]. Subclass the  adapter and put
your application  code in `Invokable#run()`  and implement a basci  main method.
See the [Javadoc][InvokableAdapter] for example code.

The   [Invokable][Invokable]    also   provides    an   abstraction    for   IO:
[IOStreams][IOStreams] with  an interface [IO][IO].  This makes it easy  to mock
out IO for testing.

It also  provides a mechanism  to add [shutdown  hooks][ShutDownHook]: Runnables
executed if the JVM is stoped by <kbd>ctrl + c</kbd>.

Example:

    public final class MyApp extends InvokableAdapter {
        
        public MyApp(final String[] args) {
            super(args);
        }
        
        public static void main(final String[] args) {
            InvokableAdapter.main(new MyApp(args));
        }
        
        @Override
        public void execute() throws Exception {
            registerShutdownHook(new Runnable() {
                
                @Override
                public void run() {
                    // Your shutdown code goes here.
                }
            });
            
            // Your application code here.
        }
    }

[ApplicationException][ApplicationException]  are  used  to  signal  application
abbort   on  abnormal   internal  errors   during  execution.   The  exit   code
implementation of the  `system` module is responsible  on the `System#exit(int)`
behaviour. Typically you  want your application exit with an  proper exit value.
You can  pass that value to  the thrown exception.  On the other hand  you don't
want to exit  the JVM realy in  testing environment. For that purose  there is a
null  exiter  implementation available.  See  [system  module][system] for  more
information.

## Version Class

[Version] provides the ability to get  the Maven POM version in your application
code. See [Javadoc][Version] for example code.

[Invokable]:            apidocs/de/weltraumschaf/commons/application/Invokable.html
[InvokableAdapter]:     apidocs/de/weltraumschaf/commons/application/InvokableAdapter.html
[IO]:                   apidocs/de/weltraumschaf/commons/application/IO.html
[IOStreams]:            apidocs/de/weltraumschaf/commons/application/IOStreams.html
[ShutDownHook]:         apidocs/de/weltraumschaf/commons/application/ShutDownHook.html
[ApplicationException]: apidocs/de/weltraumschaf/commons/application/ApplicationException.html
[Version]:              apidocs/de/weltraumschaf/commons/application/Version.html
[maven]:                https://maven.apache.org/
[system]:               ../system/