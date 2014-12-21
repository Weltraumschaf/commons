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

import com.beust.jcommander.Parameter;
import java.util.Objects;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import org.junit.Ignore;
import org.junit.Test;

/**
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
public class JCommanderImproved_ReuseTest {

    final Options sut = new Options();

    @Test
    public void defaults() {
        assertThat(sut.configurationFile, is(""));
        assertThat(sut.location, is(""));
        assertThat(sut.help, is(false));
        assertThat(sut.version, is(false));
    }

    @Test
    public void gatherOptions_empty() {
        final Options options = Options.gatherOptions(new String[0]);

        assertThat(options.configurationFile, is(""));
        assertThat(options.location, is(""));
        assertThat(options.help, is(false));
        assertThat(options.version, is(false));
    }

    @Test
    public void gatherOptions_allAsShort() {
        final Options options = Options.gatherOptions(new String[] {
            "-v", "-h", "-l", "location", "-c", "config"
        });

        assertThat(options.configurationFile, is("config"));
        assertThat(options.location, is("location"));
        assertThat(options.help, is(true));
        assertThat(options.version, is(true));
    }

    @Test
    public void gatherOptions_allAsLong() {
        final Options options = Options.gatherOptions(new String[] {
            "--version", "--help", "--location", "location", "--config", "config"
        });

        assertThat(options.configurationFile, is("config"));
        assertThat(options.location, is("location"));
        assertThat(options.help, is(true));
        assertThat(options.version, is(true));
    }

    @Test
    public void helpMessage() {
        assertThat(
                Options.helpMessage(),
                is(
                        "Usage: NAME USAGE\n"
                        + "\n"
                        + "DESCRIPTION\n"
                        + "\n"
                        + "Options\n"
                        + "\n"
                        + "  -l, --location      Location of the blog installation.\n"
                        + "  -v, --version       Show the version.\n"
                        + "  -h, --help          Show this help.\n"
                        + "  -c, --config        Config file to use.\n"
                        + "\n"
                        + "Example\n"
                        + "\n"
                        + "  EXAMPLE\n"
                        + "\n"
                ));
    }

    public static final class Options {

        private static final String USAGE = "USAGE";
        private static final String DESCRIPTION = "DESCRIPTION";
        private static final String EXAMPLE = "EXAMPLE";
        private static final String NAME = "NAME";

        /**
         * Command line options parser.
         */
        private static final JCommanderImproved<Options> PROVIDER
                = new JCommanderImproved<>(NAME, Options.class);

        @Parameter(
                names = {"-h", "--help"},
                description = "Show this help.")
        private boolean help;
        @Parameter(
                names = {"-v", "--version"},
                description = "Show the version.")
        private boolean version;
        /**
         * Where is the blog installed.
         */
        @Parameter(
                names = {"-l", "--location"},
                //            required = true,
                description = "Location of the blog installation.")
        private String location = "";
        /**
         * Configuration file argument.
         */
        @Parameter(
                names = {"-c", "--config"},
                //            required = true,
                description = "Config file to use.")
        private String configurationFile = "";

        public static Options gatherOptions(final String[] args) {
            return PROVIDER.gatherOptions(args);
        }

        public static String helpMessage() {
            return PROVIDER.helpMessage(USAGE, DESCRIPTION, EXAMPLE);
        }

        @Override
        public int hashCode() {
            return Objects.hash(configurationFile, help, location, version);
        }

        @Override
        public boolean equals(final Object obj) {
            if (!(obj instanceof Options)) {
                return false;
            }

            final Options other = (Options) obj;
            return Objects.equals(configurationFile, other.configurationFile)
                    && Objects.equals(help, other.help)
                    && Objects.equals(location, other.location)
                    && Objects.equals(version, other.version);
        }

    }
}
