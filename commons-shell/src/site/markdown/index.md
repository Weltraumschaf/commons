# Commons Shell

This module  gives you  helper classes  to parse lines  from input  to determine
commands  and  their  arguments.  Imagine  you write  a  CLI  application  which
provides sub commands such as maven.  Maven provides various "sub commands" like
`mvn clean`  or `mvn install`  etc. In this  examples `clean` and  `install` are
sub commands.

Consider your  tool is  in a shell  script named `mytool`  and it  provides main
commands like `foo`, `bar`, and `baz`. Then you expect a CLI interface like:

    $> mytool foo
    $> mytool bar
    $> mytool baz

The API  also provides optional  sub commands.  Consider the main  command `foo`
provides the  sub commands  `bla` and  `blub`. Then you  expect a  CLI interface
like:

    $> mytool foo
    $> mytool foo bla
    $> mytool foo blub
    $> mytool bar
    $> mytool baz

The generic form of a parsed line is:

    $> tool main-command [sub-command] [arg1] .. [argN]
    
Better than thousand words is an example:

First you  need to define two  enums. One for the  main commads and one  for the
sub commands. At  least you should define  one sub command: The NONE  it will be
used for main commands without any sub command. Because enums are used it is not 
possible to provide a base class with the typical functionality.

    public enum MyMainCommand implements MainCommandType {
        FOO("foo"),
        BAR("bar"),
        BAZ("baz");
        
        private final String literal;
        
        private CommandMainType(final String name) {
            this.literal = name;
        }
        
        @Override
        public String toString() {
            return literal;
        }
    }

This is the minimal sub command enum, if any main command has no sub command:
    
    public enum MySubCommand implements SubCommandType {
            NONE("");
            
            private final String literal;
        
            private CommandSubType(final String literal) {
                this.literal = literal;
            }

            @Override
            public String toString() {
                return literal;
            }
    }
    
For our example we need to more enums:
    
    public enum MySubCommand implements SubCommandType {
        NONE(""),
        FOO_BLA("bla"),
        FOO_BLUB("blub");
        
        private final String literal;
        
        private CommandSubType(final String literal) {
            this.literal = literal;
        }

        @Override
        public String toString() {
            return literal;
        }
    }

Next we  need a literal string  to command enum  map. So the parser  knows which
literal token is what command:

    public class MyLiteralCommandMap extends LiteralCommandMap {
        
        public DhtLiteralCommandMap() {
            // Tell which is the default sub command, if no one is parsed
            super(CommandSubType.NONE); 
        }
        
        @Override
        protected Class<MyMainCommand> getMainCommandType() {
            return MyMainCommand.class;
        }
        
        @Override
        protected Class<MySubCommand> getSubCommandType() {
            return MySubCommand.class;
        }
        
    }

Now we can put everything together and parse the input:

    final Parser parser = Parsers.newParser(new MyLiteralCommandMap());
    final String inputLine = ...; // Read the input line.
    final ShellCommand cmd = parser.parse(inputLine);

Now we can determine which cummands were parsed:

    switch ((MyMainCommand)cmd.getMainCommand()) {
        case FOO:
            // ...
            break;
        case BAR:
            // ...
            break;
        case BAZ:
            // ...
            break;
    }
    
The class [ShellCommand][ShellCommand] also provides  the parsed sub command. If
no  sub  command  was  parsed,  then   the  default  (given  in  constructor  to
[LiteralCommandMap][LiteralCommandMap]) is returnd. It  also provides all parsed
arguments as typed tokens.

You can  also provide  a command  verifier, which  verifies the  givne commands.
E.g. it may check if a main command  has an invalid sub command givne or missing
or invalid arguments. In this example [Hamcrest][hamcrest] ist used:

    public class DhtCommandVerifier implements CommandVerifier {
        @Override
        public void verifyCommand(final ShellCommand cmd) throws SyntaxException {
            switch ((CommandMainType) cmd.getCommand()) {
                // No argument, no subcommand.
                case FOO:
                    assertNoArguments(cmd);
                    break;
                case BAR:
                case BAZ:
                    assertNoSubCommand(cmd);
                    assertNoArguments(cmd);
                    break;
                default:
                    // Nothing to do here.
            }
        }
        
        private void assertNoArguments(final ShellCommand cmd) throws SyntaxException {
            try {
                MatcherAssert.assertThat(cmd.getArguments(), is(empty()));
            } catch (final AssertionError err) {
                throw new SyntaxException(
                    String.format(
                        "Command '%s' does not support arguments!", 
                        cmd.getCommand()),
                    err
                );
            }
        }
        
        private void assertNoSubCommand(final ShellCommand cmd) throws SyntaxException {
            try {
                MatcherAssert.assertThat(
                    cmd.getSubCommand(), 
                    is(equalTo((SubCommandType) CommandSubType.NONE)));
            } catch (final AssertionError err) {
                throw new SyntaxException(
                    String.format(
                        "Command '%s' does not support subcommand '%s'!", 
                        cmd.getCommand(), 
                        cmd.getSubCommand()),
                    err
                );
            }
        }
    }
    
You inject this verifier to the parser:
    
    final Parser parser = Parsers.newParser(new MyCommandVerifier(), new MyLiteralCommandMap());

## Other Classes

TBD

### CommandType

TBD

### Scanners

TBD

### Parsers

TBD

### Characters

TBD

## Tokens

TBD

[ShellCommand]:         apidocs/de/weltraumschaf/commons/shell/ShellCommand.html
[LiteralCommandMap]:    apidocs/de/weltraumschaf/commons/shell/LiteralCommandMap.html
[hamcrest]:             http://hamcrest.org/