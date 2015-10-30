package de.weltraumschaf.commons.testing.rules;

import java.util.List;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import org.junit.ClassRule;
import org.junit.Test;
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

/**
 * Tests that {@link Repeater} can't be used as class rule.
 */
public final class Repeater_AsClassRuleTest {

    @Test
    public void runsPojoWithRepeaterAsClassRule() {
        final Result result = JUnitCore.runClasses(Foo.class);

        assertThat(result.getFailureCount(), is(1));

        final List<Failure> failures = result.getFailures();
        assertThat(failures, hasSize(1));

        final Failure failure = failures.get(0);
        assertThat(failure.getException() instanceof RuntimeException, is(true));
        assertThat(
            failure.getMessage(),
            is("The rule de.weltraumschaf.commons.testing.rules.Repeater must not be used as class rule! You can only use it with @Rule."));
    }

    public static final class Foo {
        @ClassRule
        public static final Repeater repeater = new Repeater();

        @Test
        public void foo() {}
    }
}
