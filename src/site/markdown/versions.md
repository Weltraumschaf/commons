# Versions

## Version 0.1.0

- `CapturingOutputStream`: Can be used to capture output printed to `System.out`
  or `System.err`.
- `IOStreams`: Immutable object to aggregate I/O streams.

## Version 0.1.1

- Fix POM and add signatures.

## Version 0.1.2

- Add more documentation.
- Add `toString()`, `hashCode()`, `equals()` to `CapturingOutputStream()`.
- Add example code and documentation to `IOStreams`.

## Version 0.1.3

- Messing around with releasing to Sonatype Nexus in combination with GitHub.

## Version 0.1.4

- First successful promoted release.

## Version 0.2.0

- `Version`: Reads the current version from a property file.
- `Invokable`: Application object invokable by command line interface.

## Version 0.2.1

- `Exitable`: Abstraction for `java.lang.System#exit()`.

## Version 0.2.2

- `SwingFrame`: Common base functionality for a Swing main window.
- `MenuBarBuilder`: Simple internal DSL to create Swing menu bars.
- `ToolBarBuilder`: Simple internal DSL to create Swing tool bars.

## Version 0.2.3

- Refactorings for better design, clean code, and extensability.

## Version 0.2.4

- Refactoring and test improvements.
- Fix the license and automatically add it to source.

## Version 0.2.5

- `IO`: Print stack trace of Throwable instead of Exception.
- `OperatingSystem`: Helper to determine the OS the JVM runs on.
- `BrowserLauncher`: Helper to open an URI in systems default browser.

## Version 0.2.6

- `ShutDownHook`: Is now public because it is exported API by InvokableAdapter.
- `InvokableAdapter`: Add debug option to print stack trace on errors during
  execution.

## Version 0.3.0

- Introduce shell package: Provides basics for an interactive shell with input
  command parsing.

## Version 0.3.1

- Allow literals starting with '-', '\\', and '/' in shell command scanner.

## Version 0.4.0

- Add `ApplicationException` and catch it in `InvokableAdapter` to use exit code.
- Introduce own packages for character/token stuff useful for lexing/parsing.
- Introduce new token types: `FLOAT`, `BOOLEAN`.
- Renamed token type `NUMBER` -> `INTEGER`.
- Introduce separate methods to determine single/double quote characters.
- Introduce method for recognizing operator characters.

## Version 1.0.0

- Add concurrent package.
- Introduce modules.
- Move application stuff under new package name `application`.
- Add capturing output rule for [JUnit][junit].
- Add validate module.
- Remove `CapturingOutputStream` and add `CapturingPrintStream`.

## Version 1.0.1

- Add null aware trim.
- Add some common environment names.
- Add JCommander extension.
- Add method to create sorted set.

## Version 1.1.0

- Config Module
    - Introduces autorealoading properties based configuration.
- System Module
    - Add falback value to environment.
    - Add helper to read system properties.
- Testing Module
    - Matcher (`ApplicationExceptionCodeMatcher#hasExitCode()`) to expect
      an `ApplicationException` with particular `ExitCode`.

## Version 1.1.1

- Fix the maven project site.
- Add missing documentation.

## Version 1.1.2

- Fix the maven project site.

## Version 1.1.3

- Fixes in documentation.
- Fix Sonar warnings.

## Version 1.1.4

[junit]:    http://www.junit.org/
