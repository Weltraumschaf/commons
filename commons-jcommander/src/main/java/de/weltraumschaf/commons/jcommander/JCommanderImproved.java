/*
 *  LICENSE
 *
 * "THE BEER-WARE LICENSE" (Revision 43):
 * "Sven Strittmatter" &lt;weltraumschaf@googlemail.com&gt; wrote this file.
 * As long as you retain this notice you can do whatever you want with
 * this stuff. If we meet some day, and you think this stuff is worth it,
 * you can buy me a non alcohol-free beer in return.
 *
 * Copyright (C) 2012 "Sven Strittmatter" &lt;weltraumschaf@googlemail.com&gt;
 */
package de.weltraumschaf.commons.jcommander;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.ParameterDescription;
import de.weltraumschaf.commons.validate.Validate;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * This helper class removes boiler plate if you use {@link JCommander}.
 * <p>
 * Example:
 * </p>
 * <pre>
 * {@code
 * public static void main(final String[] args) {
 *     // Create an instance for your custom Options type.
 *     // This option type is a typical JCommander annotated beanclass.
 *     // Note that this type must provide a zero argument constructor.
 *     // If not the constructor of JCommanderImproved throws an exception.
 *     final JCommanderImproved<Options> cliArgs = new JCommanderImproved<Options>("mytool", Options.class);
 *
 *     // Parse and gather the options.
 *     final Options opts = cliArgs.gatherOptions(args);
 *
 *     // Print the helpmessage:
 *     System.out.print(
 *         cliArgs.helpMessage(
 *             "[-v|--version] [-h|--help]",
 *             "This is mytool.",
 *             "$> mytool -v")
 *     );
 * }
 * }
 * </pre>
 *
 * @since 1.0.1
 * @param <O> type of options bean
 * @author Sven Strittmatter &lt;weltraumschaf@googlemail.com&gt;
 */
public final class JCommanderImproved<O> {

    /**
     * Default width of left column (in characters) for generated help string.
     */
    private static final int LEFT_COL_WIDTH = 22;
    /**
     * Available width (in characters) for generated help string.
     */
    private static final int AVAILABLE_WIDTH = 80;
    /**
     * System dependent newline character.
     */
    private static final String DEFAULT_NEW_LINE = String.format("%n");
    /**
     * Parses the command line options.
     */
    private JCommander optionsParser = new JCommander();
    /**
     * Class for type of options model.
     */
    private final Class<O> optionsType;
    /**
     * The name of the tool for what this class gather options.
     */
    private final String programName;

    /**
     * Dedicated constructor.
     * <p>
     * Throws an {@link IllegalArgumentException} if the options type can't be instantiated.
     * </p>
     *
     * @param programName must not be {@code null} or empty
     * @param optionsType must not be {@code null}
     */
    public JCommanderImproved(final String programName, final Class<O> optionsType) {
        super();
        this.programName = Validate.notEmpty(programName, "programName");
        this.optionsParser.setProgramName(programName);
        this.optionsType = Validate.notNull(optionsType, "optionsType");
        createOptions(optionsType); // Check if instantiatable.
    }

    /**
     * Parses the given arguments and return a ready set up options bean.
     *
     * @param args must not be {@code null}
     * @return never {@code null} always new instance
     */
    public O gatherOptions(final String[] args) {
        final O opts = createOptions(optionsType);

        reset();
        optionsParser.addObject(opts);
        optionsParser.parse(Validate.notNull(args, "args"));

        return opts;
    }

    /**
     * Generates help message for main command.
     * <p>
     * The given parameters enhance the produced help messages:
     * </p>
     * <ul>
     * <li>{@code usage}: A usage string like e.g. {@literal -a --foo [-h|--help]}</li>
     * <li>{@code descriptions}: A more or less longer description of the tool.</li>
     * <li>{@code example}: Examples how to use the tool.</li>
     * </ul>
     * <p>
     * If one of the three parameters is empty the whole part is omitted in the output.
     * </p>
     *
     * @param usage must not be {@code null}
     * @param descriptions must not be {@code null}
     * @param example must not be {@code null}
     * @return never {@code null} or empty
     */
    public String helpMessage(final String usage, final String descriptions, final String example) {
        Validate.notNull(usage, "usage");
        Validate.notNull(descriptions, "descriptions");
        Validate.notNull(example, "example");

        reset();
        final String indent = "  ";
        final StringBuilder help = new StringBuilder();

        help.append("Usage: ").append(programName);

        if (!usage.trim().isEmpty()) {
            help.append(' ').append(usage.trim());
        }

        help.append(DEFAULT_NEW_LINE).append(DEFAULT_NEW_LINE);

        if (!descriptions.trim().isEmpty()) {
            help.append(descriptions.trim()).append(DEFAULT_NEW_LINE);
        }

        if (optionsParser.getParameters().isEmpty()) {
            // This adds the filed escriptions first time.
            gatherOptions(new String[0]);
        }

        final List<ParameterDescription> parameters = optionsParser.getParameters();
        // Sort them, so the order is same on different VMs.
        Collections.sort(parameters, new Comparator<ParameterDescription>() {

          @Override
          public int compare(final ParameterDescription o1, final ParameterDescription o2) {
            return o1.getLongestName().compareTo(o2.getLongestName());
          }
        });

        if (!parameters.isEmpty()) {
            help.append(DEFAULT_NEW_LINE)
                    .append("Options")
                    .append(DEFAULT_NEW_LINE)
                    .append(DEFAULT_NEW_LINE);

            final int leftColumnWidth = LEFT_COL_WIDTH;
            final int rightColumnwidth = AVAILABLE_WIDTH - leftColumnWidth;

            for (final ParameterDescription parameter : parameters) {
                help.append(rightPad(indent + parameter.getNames(), leftColumnWidth))
                        .append(lineBreak(parameter.getDescription(), rightColumnwidth, leftColumnWidth))
                        .append(DEFAULT_NEW_LINE);
            }
        }

        help.append(DEFAULT_NEW_LINE);

        if (!example.trim().isEmpty()) {
            help.append("Example").append(DEFAULT_NEW_LINE)
                    .append(DEFAULT_NEW_LINE)
                    .append(indent).append(example.trim()).append(DEFAULT_NEW_LINE);
        }

        help.append(DEFAULT_NEW_LINE);
        return help.toString();
    }

    /**
     * Pads given string with spaces on the right side to given length.
     *
     * @param in if {@code null} only spaces will be returned
     * @param length must not be negative
     * @return never {@code null}
     */
    static String rightPad(final String in, final int length) {
        Validate.greaterThanOrEqual(length, 0, "length");

        if (null == in) {
            return spaces(length);
        }

        if (length - in.length() == 0) {
            return in;
        }

        return in + spaces(length - in.length());
    }

    /**
     * Generates a string of spaces with given length.
     *
     * @param length empty string will be returned if < 1
     * @return never {@code null}
     */
    static String spaces(final int length) {
        if (length < 1) {
            return "";
        }

        final StringBuilder buffer = new StringBuilder(" ");

        for (int i = 1; i < length; ++i) {
            buffer.append(' ');
        }

        return buffer.toString();
    }

    /**
     * Breaks the given string to fit given length and pads on the left side before line breaks.
     *
     * @param in empty string will be returned if {@code null}
     * @param length must not be negative
     * @param leftPadLength must not be negative
     * @return never {@code null}
     */
    static String lineBreak(final String in, final int length, final int leftPadLength) {
        if (null == in) {
            return "";
        }

        Validate.greaterThanOrEqual(length, 0, "length");
        Validate.greaterThanOrEqual(leftPadLength, 0, "leftPadLength");

        if (in.length() <= length) {
            return in;
        }

        final StringBuilder sb = new StringBuilder(in);
        int i = 0;

        while ((i = sb.indexOf(" ", i + length)) != -1) {
            sb.replace(i, i + 1, DEFAULT_NEW_LINE);
        }

        return sb.toString()
                .replace(
                        DEFAULT_NEW_LINE,
                        DEFAULT_NEW_LINE + spaces(leftPadLength));
    }

    private void reset() {
        optionsParser = new JCommander();
    }

    /**
     * Creates an option bean.
     * <p>
     * Throws an {@link IllegalArgumentException} if the options type can't be instantiated.
     * </p>
     *
     * @param <O> type of options bean
     * @param optionsType must not be {@code null}
     * @return never {@code null}, always new instance
     */
    private static <O> O createOptions(final Class<O> optionsType) {
        try {
            return optionsType.newInstance();
        } catch (final InstantiationException | IllegalAccessException ex) {
            throw new IllegalArgumentException(ex.getMessage(), ex);
        }
    }
}
