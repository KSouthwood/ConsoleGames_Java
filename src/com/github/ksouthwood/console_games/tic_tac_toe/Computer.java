package com.github.ksouthwood.console_games.tic_tac_toe;

import com.github.ksouthwood.console_games.TextInput.ConsoleIOHandler;

abstract class Computer extends Player{
    Computer(ConsoleIOHandler console) {
        super(console);
    }
}

class EasyAI extends Computer {

    EasyAI(ConsoleIOHandler console) {
        super(console);
    }

    @Override
    int getCellToPlay() {
        return -1;
    }
}

class MediumAI extends Computer {
    MediumAI(ConsoleIOHandler console) {
        super(console);
    }

    @Override
    int getCellToPlay() {
        return -1;
    }
}

class HardAI extends Computer {
    HardAI(ConsoleIOHandler console) {
        super(console);
    }

    @Override
    int getCellToPlay() {
        return -1;
    }
}
