package com.github.ksouthwood.console_games.TextInput;

import java.util.Scanner;
import java.util.function.Function;

public class TextInput {
    private final Scanner scanner = new Scanner(System.in);

    public int getNumberInRange(String prompt, int min, int max) {
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
}
