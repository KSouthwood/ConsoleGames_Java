package com.github.ksouthwood.console_games;

import com.github.ksouthwood.console_games.battleship.Battleship;
import com.github.ksouthwood.console_games.connect_four.ConnectFour;
import com.github.ksouthwood.console_games.indigo.Indigo;
import com.github.ksouthwood.console_games.tic_tac_toe.TicTacToe;
import com.github.ksouthwood.console_games.TextInput.TextInput;

import java.util.ArrayList;
import java.util.List;

public class MainMenu {

    private final TextInput textInput = new TextInput();

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

        int choice = textInput.getNumberInRange(
                "Please enter the number for the game you wish to play: ",
                1,
                gameList.size()
        );

        System.out.println("Preparing to play " + gameList.get(choice - 1) + "...");
        switch (choice) {
            case 1 -> new TicTacToe().start();
            case 2 -> new ConnectFour().start();
            case 3 -> new Battleship().start();
            case 4 -> new Indigo().start();
        }
    }

    private static void printGameList() {
        for (int index = 0; index < gameList.size(); index++) {
            System.out.printf("%d. %s\n", index + 1, gameList.get(index));
        }
        System.out.println();
    }

}
