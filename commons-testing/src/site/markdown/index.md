# Commons Testing

This module contains utilities and helpers for unit testing.

## Capturing Print Stream

`CapturingPrintStream` is a  class for capturing print stream which  may be used
to capture the output printed to `System.out` or `System.err`.

    final CapturingPrintStream out = new CapturingPrintStream();
    System.setOut(out);
    System.out.print("hello, world");
    final String output = out.getCapturedOutput();

Same    way    you    can    redirect    the    error    output    by    setting
`System.setErr(PrintStream)`. Usualy  this is  helpful if  you have  legacy code
which uses  `System.out` or `System.err`  directly without any  abstraction. You
can capture that output and inspect it.

So essentially `CapturingPrintStream` is a convenient shorthand for:

    ByteArrayOutputStream out = new ByteArrayOutputStream();
    System.setOut(new PrintStream(out));    
    String content = out.toString();

## JUnit Rules

### @Rule CapturedOutput

This  rule  utilizes the  `CapturingPrintStream`  to  redirect `System.out`  and
`System.err` before each test method  invocation and restores them afterwards to
the origins. The rule also provides  methods to set expectation matchers for the
captured string.

    public class OutputTest {

        @Rule
        public final CapturedOutput output = new CapturedOutput();

        @Test
        public void captureOut() {
            output.expectOut("foobar");
            output.expectOut(not("snafu"));
            
            System.out.print("foobar");
        }
        
        @Test
        public void captureErr() {
            output.expectErr("foobar");
            output.expectErr(not("snafu"));
            
            System.err.print("foobar");
        }
    }
