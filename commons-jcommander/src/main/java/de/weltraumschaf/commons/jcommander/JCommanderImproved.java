/*
 *  LICENSE
 *
 * "THE BEER-WARE LICENSE" (Revision 43):
 * "Sven Strittmatter" <weltraumschaf@googlemail.com> wrote this file.
 * As long as you retain this notice you can do whatever you want with
 * this stuff. If we meet some day, and you think this stuff is worth it,
 * you can buy me a non alcohol-free beer in return.
 *
 * Copyright (C) 2012 "Sven Strittmatter" <weltraumschaf@googlemail.com>
 */
package de.weltraumschaf.commons.jcommander;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.ParameterDescription;
import de.weltraumschaf.commons.validate.Validate;
import java.util.List;

/**
 *
 * @parma O type of options model
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
public final class JCommanderImproved<O> {

    private static final String DEFAULT_NEW_LINE = String.format("%n");

    /**
     * Parses the command line options.
     */
    private final JCommander optionsParser = new JCommander();
    /**
     * Class for type of options model.
     */
    private final Class<O> optionsType;
    private final String programName;

    /**
     * Dedicated constructor.
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

    private static <O> O createOptions(final Class<O> optionsType) {
        try {
            return optionsType.newInstance();
        } catch (final InstantiationException | IllegalAccessException ex) {
            throw new IllegalArgumentException(ex.getMessage(), ex);
        }
    }

    public O gatherOptions(final String[] args) {
        final O opts = createOptions(optionsType);

        optionsParser.addObject(opts);
        optionsParser.parse(args);

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

        final List<ParameterDescription> parameters = optionsParser.getParameters();

        if (!parameters.isEmpty()) {
            help.append(DEFAULT_NEW_LINE)
                    .append("Options")
                    .append(DEFAULT_NEW_LINE)
                    .append(DEFAULT_NEW_LINE);

            final int leftColumnWidth = 22;
            final int rightColumnwidth = 80 - leftColumnWidth;

            for (final ParameterDescription parameter : parameters) {
                help.append(pad(indent + parameter.getNames(), leftColumnWidth))
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

    static String pad(final String in, final int length) {
        if (null == in) {
            return spaces(length);
        }

        return in + spaces(length - in.length());
    }

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

    static String lineBreak(final String in, final int length, final int leftPadLength) {
        if (null == in) {
            return "";
        }

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
}
