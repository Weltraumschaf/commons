package de.weltraumschaf.commons.testing.rules;

import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.sameInstance;
import static org.hamcrest.Matchers.startsWith;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

import java.lang.annotation.Annotation;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;

import de.weltraumschaf.commons.testing.rules.Repeater.RepeatUntilSuccessStatement;
import de.weltraumschaf.commons.validate.Validate;

/**
 * Tests for {@link Repeater} with {@link RepeatUntilSuccess}.
 */
public class Repeater_RepeatUntilSuccess {

  @Rule
  public final ExpectedException thrown = ExpectedException.none();

  private final Repeater sut = new Repeater();

  private static Description createDescriptionWithoutAnnotations() {
    return Description.createTestDescription(Repeater_RepeatUntilSuccess.class, "foo");
  }

  private static Description createDescriptionWithRepeatUntilSuccessAnnotation(final int ruleValue) {
    return Description.createTestDescription(Repeater_RepeatUntilSuccess.class, "foo", new RuleStub(ruleValue));
  }

  @Test
  public void apply_throwsExcpetionIfDescriptionIsNull() {
    thrown.expect(NullPointerException.class);
    thrown.expectMessage("Parameter description must not be null!");

    sut.apply(new StatementStub(), null);
  }

  @Test
  public void apply_throwsExcpetionIfBaseStatementIsNull() {
    thrown.expect(NullPointerException.class);
    thrown.expectMessage("Parameter statement must not be null!");

    sut.apply(null, createDescriptionWithRepeatUntilSuccessAnnotation(1));
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
    thrown.expectMessage("Parameter times must be greater than 0!");
    sut.apply(new StatementStub(), createDescriptionWithRepeatUntilSuccessAnnotation(0));
  }

  @Test
  public void apply_executesBaseStatementOneTimeIfItDoesNotFail() throws Throwable {
    final StatementStub base = new StatementStub(0);
    final Statement repeeted = sut.apply(base, createDescriptionWithRepeatUntilSuccessAnnotation(5));
    assertThat(repeeted, is(instanceOf(RepeatUntilSuccessStatement.class)));
    repeeted.evaluate();
    assertThat(base.getEvaluatedCount(), is(1));
  }

  @Test
  public void apply_executesBaseStatementThreeTimesIfItDoesNotFailOnThirdTime() throws Throwable {
    final StatementStub base = new StatementStub(2);
    final Statement repeated = sut.apply(base, createDescriptionWithRepeatUntilSuccessAnnotation(5));

    assertThat(repeated, is(instanceOf(RepeatUntilSuccessStatement.class)));
    repeated.evaluate();
    assertThat(base.getEvaluatedCount(), is(3));
  }

  @Test
  public void apply_executesBaseStatementFiveTimesAndFailsIfStatementFails() throws Throwable {
    final StatementStub base = new StatementStub(5);
    final Statement repeated = sut.apply(base, createDescriptionWithRepeatUntilSuccessAnnotation(5));

    assertThat(repeated, is(instanceOf(RepeatUntilSuccessStatement.class)));
    try {
      repeated.evaluate();
      fail("Expected exception not thrown!");
    } catch (final AssertionError e) {
      assertThat(e.getMessage(), startsWith("There were 5 (100.00 %) errors in 5 runs:"));
    }
    assertThat(base.getEvaluatedCount(), is(5));
  }

  /**
   * Stubs the annotation which will be given by JVM runtime.
   */
  @SuppressWarnings("all")
  // Suppress warning about implementing Repeat annotation
  private static class RuleStub implements Annotation, RepeatUntilSuccess {

    private final int value;

    RuleStub(final int value) {
      this.value = value;
    }

    @Override
    public int maxExecutions() {
      return value;
    }

    @Override
    public Class<? extends Annotation> annotationType() {
      return RepeatUntilSuccess.class;
    }
  }

  /**
   * Stubs the base statement which will be the executed test statement in real tests.
   */
  private static class StatementStub extends Statement {

    /**
     * How many evaluations should throw an assertion error.
     */
    private final int failTimes;

    /**
     * Count how many times was evaluated.
     */
    private int count;

    /**
     * Initializes {@link #failTimes} with one.
     */
    StatementStub() {
      this(1);
    }

    /**
     * Dedicated constructor.
     *
     * @param failTimes must be greater or equal 0
     */
    StatementStub(final int failTimes) {
      super();
      Validate.isTrue(failTimes >= 0, "Parameter failTimes must be greater than 0!");
      this.failTimes = failTimes;
    }

    @Override
    public void evaluate() throws Throwable {
      ++count;

      if (count <= failTimes) {
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
