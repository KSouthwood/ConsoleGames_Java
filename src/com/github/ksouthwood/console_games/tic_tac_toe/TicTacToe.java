package com.github.ksouthwood.console_games.tic_tac_toe;

public class TicTacToe {
    public void start() {
        welcomeMessage();
        new Controller().start();
        System.out.println("Returning now...");
    }

    private void welcomeMessage() {
        System.out.println("""
                Welcome to Tic-Tac-Toe!!
                
                """);
    }
}
