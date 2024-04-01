package com.github.ksouthwood.console_games.tic_tac_toe;

import com.github.ksouthwood.console_games.TextInput.ConsoleIOHandler;

import java.util.HashSet;
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

    enum PlayerTypes {
        HUMAN ("human"),
        EASY ("easy"),
        MEDIUM ("medium"),
        HARD("hard");

        private final String playerType;
        private final static Set<String> playerTypeSet = new HashSet<>();

        PlayerTypes(final String playerType) {
            this.playerType = playerType;
        }

        static {
            for (PlayerTypes value : values()) {
                playerTypeSet.add(value.playerType);
            }
        }

        static PlayerTypes getPlayerType(String type) {
            for (PlayerTypes value : values()) {
                if (value.playerType.equals(type)) {
                    return value;
                }
            }
            return null;
        }

        static boolean contains(final String type) {
            return playerTypeSet.contains(type);
        }
    }

    private final ConsoleIOHandler console;
    private GameState gameState;
    private Board board;
    private final Player[] players = new Player[2];

    Controller(ConsoleIOHandler console) {
        this.console = console;
    }

    void start() {
        gameState = GameState.MENU;
        board = new Board();

        String[] commandLine = new String[0];

        while (gameState != GameState.EXIT) {
            switch (gameState) {
                case MENU -> commandLine = getGameOptions();
                case SET_PLAYERS -> {
                    setPlayers(new PlayerTypes[] {PlayerTypes.getPlayerType(commandLine[1]), PlayerTypes.getPlayerType(commandLine[2])});
                    gameLoop();
                }
                // TODO: Switch to log statement when logging added
                default -> System.err.println("Invalid state: " + gameState);
            }
        }
        console.println("Thanks for playing!");
    }

    private void gameLoop() {
        int player = -1;
        board.initBoard();
        while (gameState == GameState.IN_PLAY) {
            console.clear();
            console.println(board.drawBoard());
            player = (player + 1) % 2;
            int cell = -1;
            boolean validMove = false;
            while (!validMove) {
                cell = players[player].getCellToPlay();
                validMove = board.markCell(player, cell);
                if (!validMove) { console.println("That cell is occupied. Please choose another: "); }
            }
            gameState = board.checkForWin(cell);
            switch (gameState) {
                case X_WON -> console.println("X has won!");
                case O_WON -> console.println("O has won!");
                case DRAW -> console.println("Game is a draw!");
            }
        }
        gameState = GameState.MENU;

    }

    private void setPlayers(PlayerTypes[] playerTypes) {
        for (int index = 0; index < players.length; index++) {
            players[index] = switch (playerTypes[index]) {
                case HUMAN -> new Human(console);
                case EASY -> new EasyAI(console);
                case MEDIUM -> new MediumAI(console);
                case HARD -> new HardAI(console);
            };
        }
        gameState = GameState.IN_PLAY;
    }

    private void setComputerAILevel() {

    }

    private void gameOver() {

    }

    private String[] getGameOptions() {
        String errorMsg = "Sorry, that's invalid. Please try again or type 'help'.";
        String[] commandLine = new String[0];
        while (gameState == GameState.MENU) {
            console.print("Command: ");
            commandLine = console.getUserInputAndSplit(true);
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
        return commandLine;
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
            if (PlayerTypes.contains(playerOne) && PlayerTypes.contains(playerTwo)) {
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
