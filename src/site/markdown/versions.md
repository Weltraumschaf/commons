# Versions

## Version 0.1.0

- <code>CapturingOutputStream</code>: Can be used to capture output print
  to <code>System.out</code> or <code>System.err</code>.
- <code>IOStreams</code>: Immutable object to aggregate I/O streams.

## Version 0.1.1

- Fix POM and add signatures.

## Version 0.1.2

- Add more documentation.
- Add <code>toString()</code>, <code>hashCode()</code>, <code>equals()</code>
  to <code>CapturingOutputStream()</code>.
- Add example code and documentation to <code>IOStreams</code>.

## Version 0.1.3

- Messing around with releasing to Sonatype Nexus in combination with GitHub.

## Version 0.1.4

- First successful promoted release.

## Version 0.2.0

- <code>Version</code>: Reads the current version from a property file.
- <code>Invokable</code>: Application object invokable by command line interface.

## Version 0.2.1

- <code>Exitable</code>: Abstraction for <code>java.lang.System#exit()</code>.

## Version 0.2.2

- <code>SwingFrame</code>: Common base functionality for a Swing main window.
- <code>MenuBarBuilder</code>: Simple internal DSL to create menu bars.
- <code>ToolBarBuilder</code>: Simple internal DSL to create tool bars.

## Version 0.2.3

- Refactorings for better design, clean code, and extensability.

## Version 0.2.4

- Refactoring and test improvements.
- Fix the license and automatically add it to source.

## Version 0.2.5

- <code>IO</code>: Print stack trace of Throwable instead of Exception.
- <code>OperatingSystem</code>: Helper to determine the OS the JVM runs on.
- <code>BrowserLauncher</code>: HElper to open an URL in systems default browser.

## Version 0.2.6

- <code>ShutDownHook</code>: Is now public, because it is exported API by InvokableAdapter.
- <code>InvokableAdapter</code>: Add debug option to print stack trace on errors during execution.

## Version 0.3.0

- Introduce shell package: Provides basics for an interactive shell w/ input command parsing.

## Version 0.3.1

- Allow literals starting with '-', '\\', and '/' in shell command scanner.

## Version 0.4.0

- Add ApplicationException and catch it in InvokableAdapter t ouse exit code.
- Introduce own packages for character/token stuff useful for lexing/parsing.
- Introduce new token types: FLOAT, BOOLEAN.
- Renamed token type NUMBER -> INTEGER.
- Introduce separate methods to determine single/double quote characters.
- Introduce method for recognizing operator characters.

## Version 1.0.0

- Add concurrent package.
- Introduce modules.
- Move application stuff under new package name <code>aplication</code>.
- Add capturing output rule for [JUnit][junit].
- Add validate module.
- Remove <code>CapturingOutputStream</code> and add <code>CapturingPrintStream</code>.

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

[junit]:    http://www.junit.org/
