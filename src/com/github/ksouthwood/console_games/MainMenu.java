package com.github.ksouthwood.console_games;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.function.Function;

public class MainMenu {
    private static final Scanner scanner = new Scanner(System.in);

    private static final ArrayList<String> gameList = new ArrayList<>(List.of(
            "Tic-Tac-Toe",
            "Connect Four",
            "Battleship",
            "Indigo (Card game)"
    ));

    MainMenu() {

    }

    void mainMenu() {
        printWelcomeMessage();
        chooseGame();
    }

    private void printWelcomeMessage() {
        System.out.println("""
                Welcome to Console Games!
                    
                Please choose a game from our list to play. Enjoy!
                """);
    }

    private void chooseGame() {
        printGameList();

        int choice = getGameChoice();

        System.out.println("Preparing to play " + gameList.get(choice - 1) + "...");
    }

    private static int getGameChoice() {
        int choice = 0;
        int numOfGames = gameList.size();
        Function<Integer, Boolean> validRange = (num -> num > 0 && num <= numOfGames);
        String errorMsg = "That is an invalid choice. Please choose a number in the range 1-%d.\n\n";

        do {
            System.out.println("Please enter the number for the game you wish to play: ");
            String input = scanner.nextLine();
            if (input.matches("\\d")) {
                choice = Integer.parseInt(input);
                if (validRange.apply(choice)) {
                    break;
                }
                System.out.printf(errorMsg, numOfGames);
                continue;
            }
            System.out.printf(errorMsg, numOfGames);
        } while (!(validRange.apply(choice)));
        return choice;
    }

    private static void printGameList() {
        for (int index = 0; index < gameList.size(); index++) {
            System.out.printf("%d. %s\n", index + 1, gameList.get(index));
        }
        System.out.println();
    }

}
