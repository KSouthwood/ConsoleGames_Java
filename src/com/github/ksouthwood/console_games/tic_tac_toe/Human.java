package com.github.ksouthwood.console_games.tic_tac_toe;

import com.github.ksouthwood.console_games.TextInput.ConsoleIOHandler;

import java.util.function.IntFunction;

class Human extends Player {
    Human(ConsoleIOHandler console) {
        super(console);
    }

    @Override
    int getCellToPlay() {
        String errorMsg = "Please enter two integers in range 1 - 3: ";

        console.print("Enter coordinates: ");
        int row = 0;
        int col = 0;

        while (!validRange(row, col)) {
            String[] input = console.getUserInputAndSplit(false);
            if (input.length != 2) {
                console.print(errorMsg);
                continue;
            }
            try {
                row = Integer.parseInt(input[0]);
                col = Integer.parseInt(input[1]);
            } catch (NumberFormatException | IllegalStateException exception) {
                console.print(errorMsg);
            }
        }
        return calcCell.apply(row, col);
    }

    private boolean validRange(int row, int col) {
        IntFunction<Boolean> range = x -> x >= 1 && x <= 3;

        return range.apply(row) && range.apply(col);
    }
}
