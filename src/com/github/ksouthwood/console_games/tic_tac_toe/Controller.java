package com.github.ksouthwood.console_games.tic_tac_toe;

import com.github.ksouthwood.console_games.TextInput.ConsoleIOHandler;

import java.util.Set;


class Controller {
    enum Commands {
        PLAY ("play"),
        QUIT ("quit"),
        EXIT ("exit"),
        HELP ("help");

        private final String command;

        Commands(String command) {
            this.command = command;
        }

        String getCommand() {
            return command;
        }
    }

    private final static Set<String> PLAYER_TYPES = Set.of("human", "easy", "medium", "hard");
    private final ConsoleIOHandler console;
    private GameState gameState;

    Controller(ConsoleIOHandler console) {
        this.console = console;
    }

    void start() {
        gameState = GameState.MENU;
        while (gameState != GameState.EXIT) {
            switch (gameState) {
                case MENU -> getGameOptions();
                case SET_PLAYERS -> {
                    setPlayers();
                    gameLoop();
                }
                // TODO: Switch to log statement when logging added
                default -> System.err.println("Invalid state: " + gameState);
            }
        }
        console.println("Thanks for playing!");
    }

    private void gameLoop() {
        console.println("Nothing to do yet!");
    }

    private void setPlayers() {
        console.println("setPlayers() can't do anything yet.");
    }

    private void setComputerAILevel() {

    }

    private void gameOver() {

    }

    private void getGameOptions() {
        String errorMsg = "Sorry, that's invalid. Please try again or type 'help'.";
        String[] commandLine;
        while (gameState == GameState.MENU) {
            commandLine = console.getUserInputAndSplitLowercase();
            switch (commandLine.length) {
                case 1 -> {
                    boolean valid = validateOneTokenCommandLine(commandLine[0]);
                    if (!valid) {
                        console.println(errorMsg);
                    }
                }
                case 3 -> {
                    boolean valid = validateThreeTokenCommandLine(commandLine[0], commandLine[1], commandLine[2]);
                    if (!valid) {
                        console.println(errorMsg);
                    }
                }
                default -> console.println(errorMsg);
            }
        }
    }

    private boolean validateOneTokenCommandLine(String command) {
        boolean valid = false;

        if (command.equals(Commands.QUIT.getCommand()) ||
                command.equals(Commands.EXIT.getCommand())) {
            gameState = GameState.EXIT;
            valid = true;
        }

        if (command.equals(Commands.HELP.getCommand())) {
            printHelp();
            valid = true;
        }

        return valid;
    }

    private boolean validateThreeTokenCommandLine(String command, String playerOne, String playerTwo) {
        boolean valid = false;

        if (command.equals(Commands.PLAY.getCommand())) {
            if (PLAYER_TYPES.contains(playerOne) && PLAYER_TYPES.contains(playerTwo)) {
                gameState = GameState.SET_PLAYERS;
                valid = true;
            }
        }

        return valid;
    }

    private void printHelp() {
        console.println("""
                play [player1] [player2] - player1 and player2 are one of the following:
                    human - human player
                    easy - easy computer player
                    medium - medium computer player (slightly smarter)
                    hard - hard computer player (tough to beat)
                quit - stop playing Tic-Tac-Toe
                exit - stop playing Tic-Tac-Toe
                help - this message
                """);
    }
}
