package com.github.ksouthwood.console_games;

import com.github.ksouthwood.console_games.TextInput.ConsoleIOHandler;
import com.github.ksouthwood.console_games.battleship.Battleship;
import com.github.ksouthwood.console_games.connect_four.ConnectFour;
import com.github.ksouthwood.console_games.indigo.Indigo;
import com.github.ksouthwood.console_games.tic_tac_toe.TicTacToe;

import java.util.ArrayList;
import java.util.List;

public class MainMenu {
    private final ConsoleIOHandler console;

    private final ArrayList<String> gameList = new ArrayList<>(List.of(
            "Tic-Tac-Toe",
            "Connect Four",
            "Battleship",
            "Indigo (Card game)"
    ));

    MainMenu() {
        this.console = new ConsoleIOHandler(System.in, System.out);
    }

    MainMenu(ConsoleIOHandler console) {
        this.console = console;
    }

    void mainMenu() {
        printWelcomeMessage();
        chooseGame();
    }

    private void printWelcomeMessage() {
        console.println("""
                Welcome to Console Games!
                    
                Please choose a game from our list to play. Enjoy!
                """);
    }

    private void chooseGame() {
        printGameList();

        console.print("Please enter the number for the game you wish to play: ");
        int choice = console.getIntegerInRangeInclusive(1, gameList.size());

        console.println("Preparing to play " + gameList.get(choice - 1) + "...\n");
        switch (choice) {
            case 1 -> new TicTacToe(console).start();
            case 2 -> new ConnectFour().start();
            case 3 -> new Battleship().start();
            case 4 -> new Indigo().start();
        }
    }

    private void printGameList() {
        StringBuilder games = new StringBuilder();
        for (int index = 0; index < gameList.size(); index++) {
            games.append(String.format("%d. %s\n", index + 1, gameList.get(index)));
        }

        console.println(games.toString());
    }

}
