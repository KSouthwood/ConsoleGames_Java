package com.github.ksouthwood.console_games.tic_tac_toe;

import com.github.ksouthwood.console_games.TextInput.ConsoleIOHandler;

public class TicTacToe {
    private final ConsoleIOHandler console;

    public TicTacToe(ConsoleIOHandler console) {
        this.console = console;
    }

    public void start() {
        console.clear();
        welcomeMessage();
        new Controller(console).start();
        System.out.println("Returning now...");
    }

    private void welcomeMessage() {
        System.out.println("""
                Welcome to Tic-Tac-Toe!!
                
                The classic game of three in a row. Get three X's or O's in a row (horizontal,
                vertical, or diagonal) in order to win the game. You can play against a friend,
                the computer, or even have the computer play itself!
                
                To begin a game, type "play" followed by two of the following: "human", "easy",
                "medium" or "hard". When you're finished, type "exit" or "quit". If you forget
                what to do, type "help".
                """);
    }
}
