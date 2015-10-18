package de.weltraumschaf.commons.testing.rules;

import static org.hamcrest.Matchers.closeTo;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.sameInstance;
import static org.hamcrest.Matchers.startsWith;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.List;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;

import de.weltraumschaf.commons.testing.rules.Repeater.RepeatStatement;
import de.weltraumschaf.commons.validate.Validate;

/**
 * Tests for {@link Repeater} with {@link RunTimes} annotation.
 */
public class Repeater_WithRunTimesTest {

  private static final double ALLOWED_DEVIATION = 0.01d;

  private static final String NL = String.format("%n");

  @Rule
  public final ExpectedException thrown = ExpectedException.none();

  private final Repeater sut = new Repeater();

  private static Description createDescriptionWithoutAnnotations() {
    return Description.createTestDescription(Repeater_WithRunTimesTest.class, "foo");
  }

  private static Description createDescriptionWithRepeatAnnotation(final int ruleValue) {
    return Description.createTestDescription(Repeater_WithRunTimesTest.class, "foo", new RuleStub(ruleValue));
  }

  @Test
  public void apply_throwsExcpetionIfDescriptionIsNull() {
    thrown.expect(NullPointerException.class);
    thrown.expectMessage("Parameter 'description' must not be null!");

    sut.apply(new StatementStub(), null);
  }

  @Test
  public void apply_throwsExcpetionIfBaseStatementIsNull() {
    thrown.expect(NullPointerException.class);
    thrown.expectMessage("Parameter 'base' must not be null!");

    sut.apply(null, createDescriptionWithRepeatAnnotation(1));
  }

  @Test
  public void apply_withNoAnnotationsReturnsBaseStatement() {
    final StatementStub base = new StatementStub();
    final Statement repeeted = sut.apply(base, createDescriptionWithoutAnnotations());

    assertThat(repeeted, is(sameInstance((Statement)base)));
  }

  @Test
  public void apply_throwsExcpetionIfRuleAnnotationWithLessThanOne() {
    thrown.expect(IllegalArgumentException.class);
    thrown.expectMessage("Parameter 'times' must be greater than 0!");

    sut.apply(new StatementStub(), createDescriptionWithRepeatAnnotation(0));
  }

  @Test
  public void apply_executesBaseStatementOneTime() throws Throwable {
    final StatementStub base = new StatementStub();
    final Statement repeeted = sut.apply(base, createDescriptionWithRepeatAnnotation(1));

    assertThat(repeeted, is(instanceOf(RepeatStatement.class)));
    repeeted.evaluate();
    assertThat(base.getEvaluatedCount(), is(1));
  }

  @Test
  public void apply_executesBaseStatementThreeTimes() throws Throwable {
    final StatementStub base = new StatementStub();
    final Statement repeated = sut.apply(base, createDescriptionWithRepeatAnnotation(3));

    assertThat(repeated, is(instanceOf(RepeatStatement.class)));
    repeated.evaluate();
    assertThat(base.getEvaluatedCount(), is(3));
  }

  @Test
  public void apply_executesBaseStatementTwentyTime() throws Throwable {
    final StatementStub base = new StatementStub();
    final Statement repeated = sut.apply(base, createDescriptionWithRepeatAnnotation(20));

    assertThat(repeated, is(instanceOf(RepeatStatement.class)));
    repeated.evaluate();
    assertThat(base.getEvaluatedCount(), is(20));
  }

  @Test
  public void apply_doesEvaluateAllRunsAlthoughRunFails() throws Throwable {
    final StatementStub base = new StatementStub(2);

    try {
      sut.apply(base, createDescriptionWithRepeatAnnotation(20)).evaluate();
      fail("Expected exception not thrown!");
    } catch (final AssertionError e) {
      assertThat(e.getMessage(), startsWith("There were 10 (50.00 %) errors in 20 runs:"));
    }

    assertThat(base.getEvaluatedCount(), is(20));
  }

  @Test
  public void calculateFailedRepetitionPercentage_throwsExceptionIfTimesIsZero() {
    thrown.expect(IllegalArgumentException.class);

    RepeatStatement.calculateFailedRepetitionPercentage(0, 5);
  }

  @Test
  public void calculateFailedRepetitionPercentage_throwsExceptionIfErrorsIsNegative() {
    thrown.expect(IllegalArgumentException.class);

    RepeatStatement.calculateFailedRepetitionPercentage(1, -1);
  }

  @Test
  public void calculateFailedRepetitionPercentage() {
    assertThat(RepeatStatement.calculateFailedRepetitionPercentage(5, 0), is(closeTo(0.0d, ALLOWED_DEVIATION)));
    assertThat(RepeatStatement.calculateFailedRepetitionPercentage(100, 10), is(closeTo(10.0d, ALLOWED_DEVIATION)));
    assertThat(RepeatStatement.calculateFailedRepetitionPercentage(100, 50), is(closeTo(50.0d, ALLOWED_DEVIATION)));
    assertThat(RepeatStatement.calculateFailedRepetitionPercentage(20, 10), is(closeTo(50.0d, ALLOWED_DEVIATION)));
    assertThat(RepeatStatement.calculateFailedRepetitionPercentage(70, 11), is(closeTo(15.71d, ALLOWED_DEVIATION)));
  }

  @Test
  public void formatErrors_trhowsExceptionIfErrorsIsNull() {
    thrown.expect(NullPointerException.class);

    RepeatStatement.formatErrors(null, 1);
  }

  @Test
  public void formatErrors() {
    final List<Throwable> errors = new ArrayList<>();
    assertThat(RepeatStatement.formatErrors(errors, 1), is(equalTo("There were 0 (0.00 %) errors in 1 run")));
    assertThat(RepeatStatement.formatErrors(errors, 2), is(equalTo("There were 0 (0.00 %) errors in 2 runs")));
    Throwable t = new Throwable("snafu"); // NOPMD Need this type as fixture.
    t.setStackTrace(new StackTraceElement[] {new StackTraceElement("foo", "bar", "baz", 42)});
    errors.add(t);
    assertThat(RepeatStatement.formatErrors(errors, 1), is(equalTo("There were 1 (100.00 %) errors in 1 run:" + NL
                                                                   + "1. java.lang.Throwable: snafu" + NL
                                                                   + "\tat foo.bar(baz:42)" + NL)));
    t = new Throwable("fubar"); // NOPMD Need this type as fixture.
    t.setStackTrace(new StackTraceElement[] {new StackTraceElement("foo2", "bar2", "baz2", 23)});
    errors.add(t);
    assertThat(RepeatStatement.formatErrors(errors, 10), is(equalTo("There were 2 (20.00 %) errors in 10 runs:" + NL
                                                                    + "1. java.lang.Throwable: snafu" + NL
                                                                    + "\tat foo.bar(baz:42)" + NL + NL
                                                                    + "2. java.lang.Throwable: fubar" + NL
                                                                    + "\tat foo2.bar2(baz2:23)" + NL)));
  }

  /**
   * Stubs the annotation which will be given by JVM runtime.
   */
  @SuppressWarnings("all")
  // Suppress warning about implementing Repeat annotation
  private static class RuleStub implements Annotation, RunTimes {

    private final int value;

    RuleStub(final int value) {
      this.value = value;
    }

    @Override
    public int value() {
      return value;
    }

    @Override
    public Class<? extends Annotation> annotationType() {
      return RunTimes.class;
    }

  }

  /**
   * Stubs the base statement which will be the executed test statement in real tests.
   */
  private static class StatementStub extends Statement {

    /**
     * How many evaluations should throw an assertion error.
     */
    private final int failFactor;

    /**
     * Count how many times was evaluated.
     */
    private int count;

    /**
     * Initializes {@link #failFactor} with one.
     */
    StatementStub() {
      this(1);
    }

    /**
     * Dedicated constructor.
     *
     * @param failFactor must be greater than 0
     */
    StatementStub(final int failFactor) {
      super();
      Validate.isTrue(failFactor > 0, "Parameter failFactor must be greater than 0!");
      this.failFactor = failFactor;
    }

    @Override
    public void evaluate() throws Throwable {
      ++count;

      if (count % failFactor != 0) {
        throw new AssertionError();
      }
    }

    /**
     * Returns how many times {@link #evaluate()} was invoked.
     *
     * @return not negative
     */
    public int getEvaluatedCount() {
      return count;
    }
  }

}
