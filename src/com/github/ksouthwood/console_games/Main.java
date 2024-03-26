package com.github.ksouthwood.console_games;

import com.github.ksouthwood.console_games.TextInput.ConsoleIOHandler;

public class Main {
    public static void main(String[] args) {
        if (args.length == 0) {
            ConsoleIOHandler console = new ConsoleIOHandler(System.in, System.out);
            new MainMenu(console).mainMenu();
        } else {
            parseArgs();
        }
    }



    private static void parseArgs() {
        // TODO: 1/14/23 not yet implemented
    }
}
