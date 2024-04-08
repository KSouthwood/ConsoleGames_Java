package com.github.ksouthwood.console_games.tic_tac_toe;

import com.github.ksouthwood.console_games.TextInput.ConsoleIOHandler;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.stream.Stream;

public class TicTacToeTest {
    private Controller controller;
    private ConsoleIOHandler console;
    private ByteArrayInputStream inputStream;
    private ByteArrayOutputStream outputStream;
    private PrintStream printStream;

    Controller getControllerForTest(String input) {
        inputStream = new ByteArrayInputStream(input.getBytes());
        outputStream = new ByteArrayOutputStream();
        printStream = new PrintStream(outputStream);
        console = new ConsoleIOHandler(inputStream, printStream);
        return new Controller(console);
    }

    @DisplayName("Incorrect command lines at beginning of game.")
    @ParameterizedTest
    @MethodSource()
    void testIncorrectCommandParsing(String command) {
        controller = getControllerForTest(command);
        controller.start();
        assertEquals("""
                        Command: Sorry, that's invalid. Please try again or type 'help'.
                        Command: Thanks for playing!
                        """,
                outputStream.toString());
    }

    static Stream<String> testIncorrectCommandParsing() {
        return Stream.of(
                "play human computer\nexit\n",
                "play easy\nexit\n",
                "play easy med hard\nexit\n",
                "human\nexit\n",
                "quit easy hard\nexit\n"
        );
    }

    @ParameterizedTest
    @MethodSource()
    void testXWins(String commands) {
        controller = getControllerForTest(commands);
        controller.start();
        String[] lines = outputStream.toString().split("\n");
        assertTrue(lines[lines.length - 2].contains("X has won!"));
    }

    static Stream<String> testXWins() {
        return Stream.of(
                """
                        play human human
                        1 1
                        2 1
                        1 2
                        2 2
                        1 3
                        exit
                        """,
                """
                        play human human
                        2 1
                        1 1
                        2 2
                        3 2
                        2 3
                        exit
                        """,
                """
                        play human human
                        3 1
                        1 1
                        3 2
                        2 2
                        3 3
                        exit
                        """,
                """
                        play human human
                        1 1
                        1 2
                        2 1
                        2 2
                        3 1
                        exit
                        """,
                """
                        play human human
                        1 2
                        1 3
                        2 2
                        1 1
                        3 2
                        exit
                        """,
                """
                        play human human
                        1 3
                        1 2
                        2 3
                        2 2
                        3 3
                        exit
                        """,
                """
                        play human human
                        1 1
                        1 2
                        2 2
                        3 2
                        3 3
                        exit
                        """,
                """
                        play human human
                        1 3
                        1 2
                        2 2
                        3 2
                        3 1
                        exit
                        """
        );
    }

    @ParameterizedTest
    @MethodSource()
    void testOWins(String commands) {
        controller = getControllerForTest(commands);
        controller.start();
        String[] lines = outputStream.toString().split("\n");
        assertTrue(lines[lines.length - 2].contains("O has won!"));
    }

    static Stream<String> testOWins() {
        return Stream.of(
                """
                        play human human
                        2 2
                        1 1
                        3 1
                        1 2
                        2 1
                        1 3
                        exit
                        """,
                """
                        play human human
                        1 1
                        2 1
                        3 1
                        2 2
                        1 3
                        2 3
                        exit
                        """,
                """
                        play human human
                        1 1
                        3 1
                        2 2
                        3 2
                        2 1
                        3 3
                        exit
                        """,
                """
                        play human human
                        2 2
                        1 1
                        1 3
                        2 1
                        3 3
                        3 1
                        exit
                        """,
                """
                        play human human
                        1 1
                        1 2
                        1 3
                        2 2
                        2 1
                        3 2
                        exit
                        """,
                """
                        play human human
                        1 1
                        1 3
                        2 2
                        2 3
                        3 1
                        3 3
                        exit
                        """,
                """
                        play human human
                        2 1
                        1 1
                        1 3
                        2 2
                        3 1
                        3 3
                        exit
                        """,
                """
                        play human human
                        1 1
                        1 3
                        3 3
                        2 2
                        2 3
                        3 1
                        exit
                        """
        );
    }

    @ParameterizedTest
    @MethodSource()
    void testDraws(String commands) {
        controller = getControllerForTest(commands);
        controller.start();
        String[] lines = outputStream.toString().split("\n");
        assertTrue(lines[lines.length - 2].contains("Game is a draw!"));
    }

    static Stream<String> testDraws() {
        return Stream.of(
                """
                        play human human
                        2 2
                        1 1
                        1 3
                        3 1
                        2 1
                        2 3
                        1 2
                        3 2
                        3 3
                        exit
                        """,
                """
                        play human human
                        1 1
                        1 2
                        2 1
                        2 2
                        3 2
                        3 1
                        1 3
                        2 3
                        3 3
                        exit
                        """,
                """
                        play human human
                        3 3
                        1 1
                        2 2
                        3 2
                        3 1
                        1 3
                        1 2
                        2 1
                        2 3
                        exit
                        """
        );
    }
}
