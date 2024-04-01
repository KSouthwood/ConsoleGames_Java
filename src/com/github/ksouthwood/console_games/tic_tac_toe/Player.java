package com.github.ksouthwood.console_games.tic_tac_toe;

import com.github.ksouthwood.console_games.TextInput.ConsoleIOHandler;

import java.util.function.BiFunction;

abstract class Player {
    protected final ConsoleIOHandler console;
    protected final BiFunction<Integer, Integer, Integer> calcCell = (row, col) -> (row - 1) * 3 + (col - 1);

    Player(ConsoleIOHandler console) {
        this.console = console;
    }

    abstract int getCellToPlay();
}
