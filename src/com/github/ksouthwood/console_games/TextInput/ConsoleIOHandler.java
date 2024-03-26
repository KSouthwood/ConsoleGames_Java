package com.github.ksouthwood.console_games.TextInput;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
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

    public void println(String msg) {
        output.println(msg);
    }

    public void print(String msg) {
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

    public void getAnything() {
        input.nextLine();
    }

    public String getUserInput() {
        return input.nextLine().trim();
    }

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

    public Integer getInteger(IntFunction<Integer> validation) {
        try {
            var readIn = this.input.nextLine();
            var parsedInt = Integer.parseInt(readIn);
            return validation.apply(parsedInt);
        } catch (NumberFormatException | IllegalStateException e) {
            return null;
        }
    }
}
