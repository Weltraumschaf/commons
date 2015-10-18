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
package de.weltraumschaf.commons.validate;

/**
 * Provides methods to validate inputs.
 *
 * @since 1.0.0
 * @author Sven Strittmatter &lt;weltraumschaf@googlemail.com&gt;
 */
public final class Validate {

    /**
     * Hidden for pure static class.
     */
    private Validate() {
        super();
        throw new UnsupportedOperationException("Must not be caled via reflection!");
    }

    /**
     * Validates that a given reference is not {@code null}.
     *
     * <p>
     * This example validates that {@code input} is not null. If it is {@code null} a
     * {@link java.lang.NullPointerException} will be thrown with the {@code name} as hint in the exception message.:
     * </p>
     * <pre>{@code
     * void someMethod(final Object input) {
     *     final Object validInput = Validate.notNull(input, "input");
     *     // ...
     * }
     * }</pre>
     *
     * <p>
     * The second parameter {@code name} may be omitted. Then a {@link java.lang.NullPointerException} without any
     * message will be thrown. For this purpose you can call the convenience method {@link #notNull(java.lang.Object)}.
     * </p>
     *
     * @param <T> type of reference
     * @param reference validated reference
     * @param name name of validated reference, may be {@code null}
     * @return the reference, if it is valid
     */
    public static <T> T notNull(final T reference, final String name) {
        if (null == reference) {
            if (null == name) {
                throw new NullPointerException();
            } else {
                throw new NullPointerException(String.format("Parameter '%s' must not be null!", name));
            }
        }

        return reference;
    }

    /**
     * Convenience message for {@link #notNull(java.lang.Object, java.lang.String)} with {@code null} as second
     * parameter.
     *
     * @param <T> type of reference
     * @param reference validated reference
     * @return the reference, if it is valid
     */
    public static <T> T notNull(final T reference) {
        return notNull(reference, null);
    }

    /**
     * Validates that a given string reference is not {@code null} or empty.
     *
     * <p>
     * This example validates that {@code input} is not null. If it is {@code null} a
     * {@link java.lang.NullPointerException} will be thrown with the {@code name} as hint in the exception message.
     * Also it validates that {@code input} is not empty by calling {@link java.lang.String#isEmpty()}. If the string is
     * empty an {@link java.lang.IllegalArgumentException} will be thrown:
     * </p>
     * <pre>{@code
     * void someMethod(final String input) {
     *     final Strign validInput = Validate.notEmpty(input, "input");
     *     // ...
     * }
     * }</pre>
     *
     * <p>
     * The second parameter {@code name} may be omitted. Then a {@link java.lang.NullPointerException} or
     * {@link java.lang.IllegalArgumentException} without any message will be thrown. For this purpose you can call the
     * convenience method {@link #notEmpty(java.lang.String)}.
     * </p>
     *
     * @param reference validated reference
     * @param name name of validated reference, may be {@code null}
     * @return the reference, if it is valid
     */
    public static String notEmpty(final String reference, final String name) {
        notNull(reference, name);

        if (reference.isEmpty()) {
            if (null == name) {
                throw new IllegalArgumentException("Parameter must not be empty!");
            } else {
                throw new IllegalArgumentException(String.format("Parameter '%s' must not be empty!", name));
            }
        }

        return reference;
    }

    /**
     * Convenience message for {@link #notEmpty(java.lang.String, java.lang.String)} with {@code null} as second
     * parameter.
     *
     * @param reference validated reference
     * @return the reference, if it is valid
     */
    public static String notEmpty(final String reference) {
        return notEmpty(reference, null);
    }

    /**
     * Tests that a given integer reference value is greater than the given lower bound.
     * <p>
     * The method throws an {@link java.lang.IllegalArgumentException} if the given reference is less than or equal the
     * given lower bound value.
     * </p>
     *
     * @param reference validated reference
     * @param lowerBound bound to test against
     * @param name name of validated reference, may be {@code null}
     * @return the reference, if it is valid
     */
    public static int greaterThan(final int reference, final int lowerBound, final String name) {
        if (reference <= lowerBound) {
            if (null == name) {
                throw new NullPointerException(String.format(
                    "Reference is not greater than lower bound: %d > %d!",
                    reference,
                    lowerBound));
            } else {
                throw new IllegalArgumentException(
                    String.format("Parameter '%s' must be greater than %d (was %d)!", name, lowerBound, reference));
            }
        }

        return reference;
    }

    /**
     * Convenience method for {@link #greaterThan(int, int, java.lang.String)} with {@code null} as name.
     *
     * @param reference validated reference
     * @param lowerBound bound to test against
     * @return the reference, if it is valid
     */
    public static int greaterThan(final int reference, final int lowerBound) {
        return greaterThan(reference, lowerBound, null);
    }

    /**
     * Tests that a given integer reference value is greater than or equal the given lower bound.
     * <p>
     * The method throws an {@link java.lang.IllegalArgumentException} if the given reference is less than the given
     * lower bound value.
     * </p>
     *
     * @param reference validated reference
     * @param lowerBound bound to test against
     * @param name name of validated reference, may be {@code null}
     * @return the reference, if it is valid
     */
    public static int greaterThanOrEqual(final int reference, final int lowerBound, final String name) {
        if (reference < lowerBound) {
            if (null == name) {
                throw new NullPointerException(String.format(
                    "Reference is not less than lower bound: %d < %d!",
                    reference,
                    lowerBound));
            } else {
                throw new IllegalArgumentException(
                    String.format(
                        "Parameter '%s' must be greater or equal than %d (was %d)!",
                        name,
                        lowerBound,
                        reference));
            }
        }

        return reference;
    }

    /**
     * Convenience method for {@link #greaterThanOrEqual(int, int, java.lang.String)} with {@code null} as name.
     *
     * @param reference validated reference
     * @param lowerBound bound to test against
     * @return the reference, if it is valid
     */
    public static int greaterThanOrEqual(final int reference, final int lowerBound) {
        return greaterThanOrEqual(reference, lowerBound, null);
    }

    /**
     * Tests that a given integer reference value is greater than the given lower bound.
     * <p>
     * The method throws an {@link java.lang.IllegalArgumentException} if the given reference is less than or equal the
     * given lower bound value.
     * </p>
     *
     * @param reference validated reference
     * @param lowerBound bound to test against
     * @param name name of validated reference, may be {@code null}
     * @return the reference, if it is valid
     */
    public static long greaterThan(final long reference, final long lowerBound, final String name) {
        if (reference <= lowerBound) {
            if (null == name) {
                throw new NullPointerException(String.format(
                    "Reference is not less equal than lower bound: %d <= %d!",
                    reference,
                    lowerBound));
            } else {
                throw new IllegalArgumentException(
                    String.format(
                        "Parameter '%s' must be greater than %d (was %d)!",
                        name,
                        lowerBound,
                        reference));
            }
        }

        return reference;
    }

    /**
     * Convenience method for {@link #greaterThan(long, long, java.lang.String)} with {@code null} as name.
     *
     * @param reference validated reference
     * @param lowerBound bound to test against
     * @return the reference, if it is valid
     */
    public static long greaterThan(final long reference, final long lowerBound) {
        return greaterThan(reference, lowerBound, null);
    }

    /**
     * Tests that a given integer reference value is greater than or equal the given lower bound.
     * <p>
     * The method throws an {@link java.lang.IllegalArgumentException} if the given reference is less than the given
     * lower bound value.
     * </p>
     *
     * @param reference validated reference
     * @param lowerBound bound to test against
     * @param name name of validated reference, may be {@code null}
     * @return the reference, if it is valid
     */
    public static long greaterThanOrEqual(final long reference, final long lowerBound, final String name) {
        if (reference < lowerBound) {
            if (null == name) {
                throw new NullPointerException(String.format(
                    "Reference is not less than lower bound: %d < %d!",
                    reference,
                    lowerBound));
            } else {
                throw new IllegalArgumentException(
                    String.format(
                        "Parameter '%s' must be greater than or equal %d (was %d)!",
                        name,
                        lowerBound,
                        reference));
            }
        }

        return reference;
    }

    /**
     * Convenience method for {@link #greaterThanOrEqual(long, long, java.lang.String)} with {@code null} as name.
     *
     * @param reference validated reference
     * @param lowerBound bound to test against
     * @return the reference, if it is valid
     */
    public static long greaterThanOrEqual(final long reference, final long lowerBound) {
        return greaterThanOrEqual(reference, lowerBound, null);
    }

    /**
     * Tests that a given expression is true.
     * <p>
     * Throws an {@link java.lang.IllegalArgumentException} if the expression is {@code false}.
     * </p>
     *
     * @param expression tested expression
     * @param message must not be {@code null} or empty
     */
    public static void isTrue(final boolean expression, final String message) {
        Validate.notEmpty(message, "Parameter 'message' must not be null or empty!");

        if (!expression) {
            throw new IllegalArgumentException(message);
        }
    }

    /**
     * Ensures that {@code index} specifies a valid <i>element</i> in an array, list or string of size {@code size}. An
     * element index may range from zero, inclusive, to {@code size}, exclusive.
     *
     * TODO: Review this method.
     *
     * @since 2.0
     * @param index a user-supplied index identifying an element of an array, list or string
     * @param size the size of that array, list or string
     * @return the value of {@code index}
     * @throws java.lang.IndexOutOfBoundsException if {@code index} is negative or is not less than {@code size}
     * @throws java.lang.IllegalArgumentException if {@code size} is negative
     */
    public static int checkElementIndex(int index, int size) {
        return checkElementIndex(index, size, "index");
    }

    /**
     * Ensures that {@code index} specifies a valid <i>element</i> in an array, list or string of size {@code size}. An
     * element index may range from zero, inclusive, to {@code size}, exclusive.
     *
     * TODO: Review this method.
     *
     * @since 2.0
     * @param index a user-supplied index identifying an element of an array, list or string
     * @param size the size of that array, list or string
     * @param desc the text to use to describe this index in an error message
     * @return the value of {@code index}
     * @throws java.lang.IndexOutOfBoundsException if {@code index} is negative or is not less than {@code size}
     * @throws java.lang.IllegalArgumentException if {@code size} is negative
     */
    public static int checkElementIndex(int index, int size, String desc) {
        // Carefully optimized for execution by hotspot (explanatory comment above)
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException(badElementIndex(index, size, desc));
        }
        return index;
    }

    /**
     * TODO: Review this method.
     *
     * @since 2.0
     * @param index TODO Write comment
     * @param size TODO Write comment
     * @param desc TODO Write comment
     * @return TODO Write comment
     */
    private static String badElementIndex(int index, int size, String desc) {
        if (index < 0) {
            return format("%s (%s) must not be negative", desc, index);
        } else if (size < 0) {
            throw new IllegalArgumentException("negative size: " + size);
        } else { // index >= size
            return format("%s (%s) must be less than size (%s)", desc, index, size);
        }
    }

    /**
     * Substitutes each {@code %s} in {@code template} with an argument. These are matched by position - the first
     * {@code %s} gets {@code args[0]}, etc. If there are more arguments than placeholders, the unmatched arguments will
     * be appended to the end of the formatted message in square braces.
     *
     * TODO: Review this method.
     *
     * @param template a non-null string containing 0 or more {@code %s} placeholders.
     * @param args the arguments to be substituted into the message template. Arguments are converted to strings using
     * {@link String#valueOf(Object)}. Arguments can be null.
     */
    static String format(String template,
        Object... args) {
        template = String.valueOf(template); // null -> "null"

        // start substituting the arguments into the '%s' placeholders
        final StringBuilder builder = new StringBuilder(
            template.length() + 16 * args.length);
        int templateStart = 0;
        int i = 0;
        while (i < args.length) {
            final int placeholderStart = template.indexOf("%s", templateStart);
            if (placeholderStart == -1) {
                break;
            }
            builder.append(template.substring(templateStart, placeholderStart));
            builder.append(args[i++]);
            templateStart = placeholderStart + 2;
        }
        builder.append(template.substring(templateStart));

        // if we run out of placeholders, append the extra args in square braces
        if (i < args.length) {
            builder.append(" [");
            builder.append(args[i++]);
            while (i < args.length) {
                builder.append(", ");
                builder.append(args[i++]);
            }
            builder.append(']');
        }

        return builder.toString();
    }

    /**
     * Ensures that {@code start} and {@code end} specify a valid <i>positions</i>
     * in an array, list or string of size {@code size}, and are in order. A position index may range from zero to
     * {@code size}, inclusive.
     *
     * TODO: Review this method.
     *
     * @since 2.0
     * @param start a user-supplied index identifying a starting position in an array, list or string
     * @param end a user-supplied index identifying a ending position in an array, list or string
     * @param size the size of that array, list or string
     * @throws java.lang.IndexOutOfBoundsException if either index is negative or is greater than {@code size}, or if
     * {@code end} is less than {@code start}
     * @throws java.lang.IllegalArgumentException if {@code size} is negative
     */
    public static void checkPositionIndexes(int start, int end, int size) {
        // Carefully optimized for execution by hotspot (explanatory comment above)
        if (start < 0 || end < start || end > size) {
            throw new IndexOutOfBoundsException(badPositionIndexes(start, end, size));
        }
    }

    /**
     * TODO Write comment
     *
     * @since 2.0
     * @param start TODO Write comment
     * @param end TODO Write comment
     * @param size TODO Write comment
     * @return TODO Write comment
     */
    private static String badPositionIndexes(int start, int end, int size) {
        if (start < 0 || start > size) {
            return badPositionIndex(start, size, "start index");
        }
        if (end < 0 || end > size) {
            return badPositionIndex(end, size, "end index");
        }
        // end < start
        return format("end index (%s) must not be less than start index (%s)",
            end, start);
    }

    /**
     * TODO Write comment
     *
     * @since 2.0
     * @param index TODO Write comment
     * @param size TODO Write comment
     * @param desc TODO Write comment
     * @return TODO Write comment
     */
    private static String badPositionIndex(int index, int size, String desc) {
        if (index < 0) {
            return format("%s (%s) must not be negative", desc, index);
        } else if (size < 0) {
            throw new IllegalArgumentException("negative size: " + size);
        } else { // index > size
            return format("%s (%s) must not be greater than size (%s)",
                desc, index, size);
        }
    }
}
