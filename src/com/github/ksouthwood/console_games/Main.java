package com.github.ksouthwood.console_games;

public class Main {
    public static void main(String[] args) {
        if (args.length == 0) {
            new MainMenu().mainMenu();
        } else {
            parseArgs();
        }
    }



    private static void parseArgs() {
        // TODO: 1/14/23 not yet implemented
    }
}
