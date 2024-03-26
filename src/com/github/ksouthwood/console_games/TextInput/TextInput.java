package com.github.ksouthwood.console_games.TextInput;

import java.util.Locale;
import java.util.Scanner;
import java.util.function.Function;

public class TextInput {
    private final Scanner scanner = new Scanner(System.in);

    /**
     * Prompt the user and get a number in a specified range
     * <p>
     * Using the supplied prompt string, prompt the user to enter a number from min to max inclusive. Checks if the
     * user entered digits only and then ensures the entered number is in the specified range. Print an error message
     * if neither of the conditions is met.
     *
     * @param prompt String to print to the console to let the user know what is expected
     * @param min the smallest number we will accept
     * @param max the largest number we will accept
     * @return a valid number in the specified range
     */
    public int getNumberInRange(final String prompt, final int min, final int max) {
        int choice = Integer.MIN_VALUE;
        Function<Integer, Boolean> validRange = (number -> number >= min && number <= max);
        String errorMsg = String.format("That is an invalid choice. Please choose a number in the range %d-%d.\n", min, max);

        do {
            System.out.println(prompt);
            String input = scanner.nextLine();
            if (input.matches("\\d+")) {
                choice = Integer.parseInt(input);
                if ((validRange.apply(choice))) {
                    continue;
                }
            }
            System.out.println(errorMsg);
        } while (!(validRange.apply(choice)));

        return choice;
    }

    /**
     * Prompt the user and read the entered text.
     * <p>
     * Using the supplied prompt string, read the text entered by the user and return it as a string array splitting
     * by spaces. Converts the text to lower case first if the flag is false.
     *
     * @param prompt String to print to the console to prompt the user
     * @param caseSensitive true if we want the text entered as is, false if the case doesn't matter
     * @return a String array from the entered text
     */
    public String[] readToStringArray(final String prompt, final boolean caseSensitive) {
        System.out.println(prompt);
        String input = scanner.nextLine();
        if (!caseSensitive) {
            input = input.toLowerCase(Locale.ROOT);
        }
        return input.split(" ");
    }
}
