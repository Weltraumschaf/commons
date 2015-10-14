package de.weltraumschaf.commons.testing.rules;

import de.weltraumschaf.commons.validate.Validate;
import java.text.SimpleDateFormat;
import java.util.Locale;

import org.junit.rules.ExternalResource;

/**
 * This rule sets the {@link Locale#setDefault(Locale) default locale} to a
 * desired locale and resets it after the tests to the value before.
 * <p>
 * This is useful e.g. for tests which relies on {@link SimpleDateFormat}
 * because this piece of crap uses the systems locale in a non accessible static
 * way. Which leads to tests which works on OS with one local but not on
 * machines with an other locale. Praise the SUN engineers, not!
 * </p>
 * <p>
 * Example:
 * </p>
 *
 * <pre>
 * {@code
 * public class TestSomething {
 *
 *   &#064;Rule
 *   public final JavaDefaultLocale localeRule = new JavaDefaultLocale(Locale.ENGLISH);
 *
 *   &#064;Test
 *   public void someTestMethod() {}
 * }
 * }</pre>
 *
 * @since 1.0.0
 */
public final class JavaDefaultLocale extends ExternalResource {

    /**
     * The locale wanted as default for Java's locale.
     */
    private final Locale wanted;

    /**
     * The origin locale.
     */
    private Locale backup;

    /**
     * Dedicated constructor.
     *
     * @param wanted must not be {@code null}
     */
    public JavaDefaultLocale(Locale wanted) {
        super();
        this.wanted = Validate.notNull(wanted, "wanted");
    }

    @Override
    protected void before() throws Throwable {
        backup = Locale.getDefault();
        Locale.setDefault(wanted);
    }

    @Override
    protected void after() {
        Locale.setDefault(backup);
    }

}
