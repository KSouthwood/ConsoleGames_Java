package com.github.ksouthwood.console_games.TextInput;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.Locale;
import java.util.Objects;
import java.util.Scanner;
import java.util.function.IntFunction;

public class ConsoleIOHandler {

    private final PrintStream output;
    private final Scanner input;

    public ConsoleIOHandler(InputStream input, PrintStream output) {
        Objects.requireNonNull(input, "Input stream cannot be null and should be System.in");
        Objects.requireNonNull(output, "Output stream cannot be null and should be System.out");
        this.input = new Scanner(input);
        this.output = output;
    }

    /**
     * Output a String to a PrintStream with a line break
     * <p>
     * Takes the supplied String and outputs it to the PrintStream, then terminates the line. (Usually with "\n".)
     *
     * @param msg String to be printed to the output
     */
    public void println(final String msg) {
        output.println(msg);
    }

    /**
     * Output a String to a PrintStream without a line ending
     * <p>
     * Takes the supplied String and outputs it to the PrintStream without terminating the line. The next output to the
     * PrintStream (either via print() or println()) will immediately follow this output.
     *
     * @param msg String to be printed to the output
     */
    public void print(final String msg) {
        output.print(msg);
        output.flush();
    }

    /**
     * Clear the console screen.
     * <p>
     * Clears the console in an OS dependent way.
     */
    public void clear() {
        try {
            var clearCommand = System.getProperty("os.name").contains("Windows")
                    ? new ProcessBuilder("cmd", "/c", "cls")
                    : new ProcessBuilder("clear");
            clearCommand.inheritIO().start().waitFor();
        } catch (IOException | InterruptedException noop) {
            // NOOP
        }
    }

    /**
     * Get and discard whatever is next on the input
     * <p>
     * Effectively discards whatever is next on the input since we don't save it in anyway.
     */
    public void getAnything() {
        input.nextLine();
    }

    /**
     * Read a line from the InputStream and trim leading/trailing spaces
     *
     * @return the next line on the input without leading and trailing spaces
     */
    public String getUserInput() {
        return input.nextLine().trim();
    }

    /**
     * Get a String array of the input line
     * <p>
     * Read a line from the InputStream, the split it by whitespace. Set the caseInsensitive flag to true to convert the
     * input to lower case, effectively ignoring the case of the input.
     *
     * @param caseInsensitive true if we want to ignore the case of the input
     * @return String array of the input split on whitespace
     */
    public String[] getUserInputAndSplit(final boolean caseInsensitive) {
        String input = getUserInput();
        if (caseInsensitive) { input = input.toLowerCase(Locale.ROOT); }
        return input.split("\\s+");
    }

    /**
     * Get an integer greater than 0
     *
     * @return an Integer greater than 0
     */
    public int getIntegerUntilAboveZero() {
        IntFunction<Integer> aboveZero = i -> i > 0 ? i : null;

        Integer value = null;
        while (value == null) {
            value = getInteger(aboveZero);
            if (value == null) {
                output.print("Error! Incorrect input. Try again: ");
            }
        }
        return value;
    }

    /**
     * Get an Integer in a validated range
     * <p>
     * Accepts an IntFunction to use for validating the Integer against. Returns null if the input is not able to be
     * parsed as an Integer.
     *
     * @param validation IntFunction to validate the desired range of Integer wanted
     * @return an Integer that passes the IntFunction
     */
    public Integer getInteger(final IntFunction<Integer> validation) {
        try {
            var readIn = this.input.nextLine();
            var parsedInt = Integer.parseInt(readIn);
            return validation.apply(parsedInt);
        } catch (NumberFormatException | IllegalStateException e) {
            return null;
        }
    }

    /**
     * Get a number in the specified range
     * <p>
     * Reads input from the user and validates it to be in the specified range. Prints an error message if the input is
     * a non-integer or not in the valid range.
     *
     * @param min the smallest number we will accept
     * @param max the largest number we will accept
     * @return a valid number in the specified range
     */
    public Integer getIntegerInRangeInclusive(final int min, final int max) {
        IntFunction<Integer> range = i -> (i >= min && i <= max) ? i : null;

        Integer value = null;
        while (value == null) {
            value = getInteger(range);
            if (value == null) {
                output.printf("Error! Number should be between %d and %d. Try again: ", min, max);
            }
        }

        return value;
    }
}
