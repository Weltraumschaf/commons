/*
 * LICENSE
 *
 * "THE BEER-WARE LICENSE" (Revision 43):
 * "Sven Strittmatter" <weltraumschaf(at)googlemail(dot)com> wrote this file.
 * As long as you retain this notice you can do whatever you want with
 * this stuff. If we meet some day, and you think this stuff is worth it,
 * you can buy me a non alcohol-free beer in return.
 *
 * Copyright (C) 2012 "Sven Strittmatter" <weltraumschaf(at)googlemail(dot)com>
 */
/**
 * Sub package for a simple interactive shell.
 * <p>
 * Contains:
 * </p>
 *
 * <ul>
 *  <li>input scanner/parser</li>
 *  <li>helper classes for scanning</li>
 *  <li>shell command and token abstraction</li>
 * </ul>
 *
 * <h3>Example</h3>
 *
 * <p>First define main and sub command types.</p>
 *
 * // CHECKSTYLE:OFF
 * <pre>{@code public enum MyMainType implements MainCommandType {
 *
 *     HELP("help"),
 *     FOO("foo"),
 *     EXIT("exit");
 *
 *     // Literal command string used in shell.
 *     private final String literal;
 *
 *     private NeuronMainType(final String name) {
 *         this.literal = name;
 *     }
 *
 *     {@literal @}Override
 *     public String toString() {
 *         return literal;
 *     }
 * }
 *
 * public enum MySubType implements SubCommandType {
 *
 *     NONE(""), // Default for main commands w/o sub commands.
 *     BAR("bar"),
 *     BAZ("baz");
 *
 *     // Literal sub command string used in shell.
 *     private final String literal;
 *
 *     private NeuronSubType(final String name) {
 *         this.literal = name;
 *     }
 *
 *     {@literal @}Override
 *     public String toString() {
 *         return literal;
 *     }
 * }
 * }</pre>
 * // CHECKSTYLE:ON
 *
 * <p>And then define a literal to command map:</p>
 *
 * // CHECKSTYLE:OFF
 * <pre>{@code public class MyLiteralCommandMap extends LiteralCommandMap {
 *
 *     public MyLiteralCommandMap() {
 *         super(MySubType.NONE); // Default for main commands w/o sub commands.
 *     }
 *
 *     {@literal @}Override
 *     protected void initCommandMap(Map<String, MainCommandType> map) {
 *         for (final MyMainType t : MyMainType.values()) {
 *             map.put(t.toString(), t);
 *         }
 *     }
 *
 *     {@literal @}Override
 *     protected void initSubCommandMap(Map<String, SubCommandType> map) {
 *         for (final MySubType t : MySubType.values()) {
 *             if (t.toString().isEmpty()) {
 *                 continue; // Ignore to do not recognize empty strings as sub command in parser.
 *             }
 *             map.put(t.toString(), t);
 *         }
 *     }
 * }
 * }</pre>
 * // CHECKSTYLE:ON
 *
 * <p>Now you can implement a simple REPL:</p>
 *
 * // CHECKSTYLE:OFF
 * <pre>{@code Parser parser = Parsers.newParser(new MyLiteralCommandMap());
 * BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
 *
 * while (true) {
 *     System.out.println("prompt> ");
 *     final String inputLine = input.readLine();
 *
 *     try {
 *         final ShellCommand cmd = parser.parse(inputLine);
 *         // Do something with the shell command...
 *
 *         if (shellCmd.getCommand() == NeuronMainType.EXIT) {
 *             break;
 *         }
 *     } catch (SyntaxException ex) {
 *         System.out.println("Error: " + ex.getMessage());
 *     }
 * }
 *
 * input.close();
 * }</pre>
 * // CHECKSTYLE:ON
 *
 * <p>
 * If you want to verify the parsed commands, e.g. check if a command has a proper subcommand or arguments
 * you must implement {@link de.weltraumschaf.commons.shell.CommandVerifier} and pass it to the
 * {@link de.weltraumschaf.commons.shell.Parsers parser factory}.
 * </p>
 * <p>
 * This package is a part of the open-source <a href="https://github.com/Weltraumschaf/commons">Commons</a> lib.
 * </p>
 */
package de.weltraumschaf.commons.shell;
