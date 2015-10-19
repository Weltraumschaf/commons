# Commons Testing

This module contains utilities and helpers for unit testing.

## General Helpers

### CapturingPrintStream

`CapturingPrintStream` is a  class for capturing print stream which  may be used
to capture the output printed to `System.out` or `System.err`.

```
final CapturingPrintStream out = new CapturingPrintStream();
System.setOut(out);
System.out.print("hello, world");
final String output = out.getCapturedOutput();
```

Same    way    you    can    redirect    the    error    output    by    setting
`System.setErr(PrintStream)`. Usually  this is helpful  if you have  legacy code
which uses  `System.out` or `System.err`  directly without any  abstraction. You
can capture that output and inspect it.

So essentially `CapturingPrintStream` is a convenient shorthand for:

```
final ByteArrayOutputStream out = new ByteArrayOutputStream();
System.setOut(new PrintStream(out));
final String content = out.toString();
```

### DelayedRepeater

`DelayedRepeater` is a  utility class to wait repeatedly for  something until it
happens. The use  case are subject under test which  does something asynchronous
over the  network or in other  threads, but does  not provide a callback  API to
hook  in.   Usually  you   fire  your   acting  call  and   then  loop   with  a
`Thread#sleep(int)` until you  can assert. But to  do this all over  the time by
hand is  tedious and  error prone.  Also you  have untested  logic in  your test
code.  `DelayedRepeater` abstracts  this "sleeping  loop"  away for  you and  is
tested.

The basic  idea is: You make  your arrangement and  acting of your test  code as
usual, but the assertion as a callback of the `DelayedRepeater`.

Example with `Runnable` as callback:

```
public class TestSomething {
    
    @Test
    public void testSomeThing() {
        final SomeClass subjectUnderTest = new SomeClass();
        
        subjectUnderTest.doSomethingLongAsync();
        
        DelayedRepeater.create(500, 3).execute(new Runnable() {
            public void run() {
                assertThat(
                    subjectUnderTest.getResult(),
                    is("foobar"));
            }
        });
    }
    
}
```

Example with `Callable` as callback:

```
public class TestSomething {
    @Test
    public void testSomeThing() {
        final SomeClass subjectUnderTest = new SomeClass();
        
        subjectUnderTest.doSomethingLongAsync();
        
        DelayedRepeater.create(500, 3).execute(new Callable<Void>() {
            public Void call() throws Exception {
                assertThat(
                    subjectUnderTest.getResult(),
                    is("foobar"));
                return null;
            }
        });
    }
}
```

## JUnit Rules

This module provides custom rules for [JUnit][junit].
For more information about JUnit rules see [the documentation][junit-rules].

### CapturedOutput Rule

This  rule  utilizes the  `CapturingPrintStream`  to  redirect `System.out`  and
`System.err` before each test method  invocation and restores them afterwards to
the origins. The rule also provides  methods to set expectation matchers for the
captured string. 

```
public class TestSomething {

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
```

### JavaDefaultLocale Rule

Sometime  you have  legacy  code which  relies internally  on  the java  default
locale and you  can't inject or change this  from your test code. This  is a big
pain  if you  have  machines with  different locales  (e.g.  de_DE as  developer
machine  and en_US  as CI  machine). A  common approach  is to  set the  default
locale before the  tests to a fix one.  But you have to remember to  set it back
to not influence other code which may rely on that locale:


```
public class TestSomething {

    private Locale backup;
    
    @Before
    public void setLocale() {
        backup = Locale.getDefault();
        Locale.setDefault(Locale.ENGLISH);
    }
    
    @After
    public void restoreLocale9) {
        Locale.setDefault(backup);    
    }

    @Test
    public void testSomeThing() {
        ...
    }
}
```

But this approach  is tedious and error  prone. It is easy  to forget resetting.
Also it is code  duplication and violates DRY if you do this  in all your tests.
The `JavaDefaultLocale` is for doing this tedious stuff for you:

```
public class TestSomething {

    @Rule 
    public final JavaDefaultLocale localeRule = new JavaDefaultLocale(Locale.ENGLISH);
    
    @Test
    public void testSomeThing() {
        ...
    }
}
```

### Repeater Rule

The repeater rule executes annotated tests multiple times.

#### RunTimes Annotation

Sometimes you  have legacy code with  weird timing issues or  other side effects
and the  code only does  sometimes something  wrong. Imagine: The  customer says
every one hundred  X or so the bug  happens. But when you runt  your tests every
thing is  fine. Then you  start hitting  the run test  button over and  over and
BANG!, the  bug occurs. But the  next time it is  gone. Then you start  to write
loops in  your test code to  provoke the bug. But  this is not good  practice to
add logic into your tests. (Because where are tests for this logic?)

The repeat rule  is a tested helper to  do that. You add that  rule and annotate
the tests you wanted repeatedly executed with an annotation:

```
public class TestSomething {

    @Rule 
    public final Repeater repeater = new Repeater();
    
    @Test
    @RunTimes(100)
    public void testSomeThing() {
        ...
    }
}
```

#### RunMaxTimes Annotation

Common pain  in the ass  are UI tests.  You have tests  which runs fine  on your
machine and  they run  fine on the  CI almost always,  but sometimes  they fail.
Then you  investigate, but they  rune fine  on your machine  and on the  CI. But
sometimes... The problem is timing: You have  to define timeouts in your UI test
framework.  That's almost  always enough.  But  in rare  situation (heavy  load,
solar wind,  what ever) the  timeout is not enough  and the tests  fail randomly
without a reason.  This erodes the whole test suite  because everybody starts to
ignore the red CI.

A simple solution is: If a single UI  test fails, then start it again and see if
fails again.  Unless everything is fine.  If it continues failing  then there is
maybe a real bug.  But you don't want to do this manually  with the whole suite.
For  this  use  case  is  the  `RunMaxtimes`  annotation.  In  contrast  to  the
`RunTimes` annotation it  does not run the  test the given value  times, it only
runs the  test multiple times  if it  fails until it  succeeds or the  max times
value is reached. If one of the runs  succeeded the test is marked green. If all
runs failed the test is marked red.

Example:

```
public class TestSomething {

    @Rule 
    public final Repeater repeater = new Repeater();
    
    @Test
    @RunMaxTimes(3)
    public void testSomeThing() {
        ...
    }
}
```

In the example above  the test is executed one time. If  it didn't failed that's
it. Unless the  test is repeated and  executed second time. If  it didn't failed
that's it. Unless the  test is repeated again and executed  third and last time.
If it failed again, then the test is  marked red. So the test is always executed
multiple times until it succeeded or the given number of tries is exhausted.

## Custom Hamcrest Matchers

This module provides custom matchers for [Hamcrest][hamcrest].

### ApplicationExceptionCodeMatcher

This  matcher  helps you  to  match  your  own  exit code  implementations  (see
application module).

Example:

```
enum ExitCodeImpl implements ExitCode {
    FOO(0), BAR(1), BAZ(2);

    private final int code;

    private ExitCodeImpl(final int code) {
        this.code = code;
    }

    @Override
    public int getCode() {
        return code;
    }
}

public class MyTest {

    @Test
    public void throwsFooException() {
        final ApplicationException ex =
            new ApplicationException(ExitCodeImpl.FOO, "foo");

        assertThat(ex, hasExitCode(ExitCodeImpl.FOO));
    }
}
```

### HasMessage

TODO

### IntegerIsCloseTo

TODO

### LongIsCloseTo

TODO

[junit]:        http://junit.org/
[junit-rules]:  https://github.com/junit-team/junit/wiki/Rules
[hamcrest]:     http://hamcrest.org/JavaHamcrest/
