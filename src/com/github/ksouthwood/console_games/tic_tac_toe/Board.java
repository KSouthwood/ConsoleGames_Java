package com.github.ksouthwood.console_games.tic_tac_toe;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.IntStream;

class Board {
    enum Cells {
        X('X', 1),
        O('O', -1),
        E(' ', 0);

        private final char mark;
        private final int weight;

        Cells(char mark, int weight) {
            this.mark = mark;
            this.weight = weight;
        }

        char getMark() {
            return mark;
        }

        int getWeight() {
            return weight;
        }
    }

    private final Cells[] board;
    private final Set<Integer> emptyCells;
    private final int[][] lines = {
            {0, 1, 2}, {3, 4, 5}, {6, 7, 8},
            {0, 3, 6}, {1, 4, 7}, {2, 5, 8},
            {0, 4, 8}, {2, 4, 6}
    };

    Board() {
        board = new Cells[9];
        emptyCells = new HashSet<>();
        initBoard();
    }

    void initBoard() {
        Arrays.fill(board, Cells.E);
        IntStream.rangeClosed(0, 8).forEach(emptyCells::add);
    }

    boolean markCell(final int player, final int cell) {
        if (emptyCells.contains(cell)) {
            board[cell] = player == 0 ? Cells.X : Cells.O;
            emptyCells.remove(cell);
            return true;
        }
        return false;
    }

    GameState checkForWin(final int cell) {
        GameState state = switch (cell) {
            case 0 -> checkLines(new int[]{0, 3, 6});
            case 1 -> checkLines(new int[]{0, 4});
            case 2 -> checkLines(new int[]{0, 5, 7});
            case 3 -> checkLines(new int[]{1, 3,});
            case 4 -> checkLines(new int[]{1, 4, 6, 7});
            case 5 -> checkLines(new int[]{1, 5});
            case 6 -> checkLines(new int[]{2, 3, 7});
            case 7 -> checkLines(new int[]{2, 4});
            case 8 -> checkLines(new int[]{2, 5, 6});
            default -> throw new IllegalArgumentException(String.valueOf(cell)); // TODO: switch to log file
        };

        return emptyCells.isEmpty() ? GameState.DRAW : state;
    }

    private GameState checkLines(final int[] linesToCheck) {
        int weight;
        for (int lineToCheck : linesToCheck) {
            weight = 0;
            for (int cellToCheck : lines[lineToCheck]) {
                weight += board[cellToCheck].getWeight();
            }
            switch (weight) {
                case 3 -> {
                    return GameState.X_WON; // X has 3 in a row
                }
                case -3 -> {
                    return GameState.O_WON; // O has 3 in a row
                }
            }
        }

        return GameState.IN_PLAY; // In play
    }

    private void checkRows() {

    }

    private void checkColumns() {

    }

    String drawBoard() {
        String upperLeft = "+";
        String upperRight = "+";
        String lowerLeft = "+";
        String lowerRight = "+";
        String horizontalEdge = "-";
        String verticalEdge = "|";
        String rowFormat = "%1$s %2$s %3$s %4$s %1$s";
        String topBorder = (upperLeft + horizontalEdge.repeat(7) + upperRight);
        String botBorder = (lowerLeft + horizontalEdge.repeat(7) + lowerRight);
        String row1 = rowFormat.formatted(verticalEdge, board[0].getMark(), board[1].getMark(), board[2].getMark());
        String row2 = rowFormat.formatted(verticalEdge, board[3].getMark(), board[4].getMark(), board[5].getMark());
        String row3 = rowFormat.formatted(verticalEdge, board[6].getMark(), board[7].getMark(), board[8].getMark());
        return topBorder + "\n" +
                row1 + "\n" +
                row2 + "\n" +
                row3 + "\n" +
                botBorder;
    }
}
