# Commons Shell

This package  gives you helper  classes to parse  lines from input  to determine
commands. Imagine you  write a CLI application which provides  sub commands such
as  maven.  Maven  provides  various  "subcommands" like  `mvn  clean`  or  `mvn
install` or such. In this examples `clean` and `install` are sub commands.

This  package give  you  some classes  to parse  input  efficently to  determine
commands  and sub  commands.  Consider your  tool  is in  a  shell script  named
`mytool` and it provides commands like  `foo`, `bar`, and `baz`. Then you expect
a CLI interface like:

    $> mytool foo
    $> mytool bar
    $> mytool baz

The API also provides optional  subcommands. Consider the command `foo` provides
the subcommands `bla` and `blub`. Then you expect a CLI interface like:

    $> mytool foo
    $> mytool foo bla
    $> mytool foo blub
    $> mytool bar
    $> mytool baz

The generic form of a parsed line is:

    $> tool command [subcommand] [arg1] .. [argN]

## ShellCommand Foundation

TBD

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

[jline]:    https://jline.github.io/jline2/
