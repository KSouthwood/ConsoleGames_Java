package com.github.ksouthwood.console_games.TextInput;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.function.BiFunction;
import java.util.function.IntFunction;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.params.provider.Arguments.arguments;

public class ConsoleIOHandlerTest {

    private ConsoleIOHandler console;
    private ByteArrayOutputStream outputStream;
    private PrintStream printStream;

    @BeforeEach
    void beforeEach() {
        outputStream = new ByteArrayOutputStream();
        printStream = new PrintStream(outputStream);
    }

    ConsoleIOHandler getConsoleWithOutputOnly() {
        return new ConsoleIOHandler(System.in, printStream);
    }

    ConsoleIOHandler getConsoleWithInputAndOutput(String input) {
        ByteArrayInputStream inputStream = new ByteArrayInputStream(input.getBytes());
        return new ConsoleIOHandler(inputStream, printStream);
    }

    @ParameterizedTest
    @ValueSource(strings = {"Hello, World!", "I'm Batman.", "Everything is wonderful!"})
    void testPrintln(String toPrint) {
        console = getConsoleWithOutputOnly();
        console.println(toPrint);
        assertEquals(toPrint + "\n", outputStream.toString());
    }

    @ParameterizedTest
    @ValueSource(strings = {"Crazy?", "I was crazy once.", "Rats make me crazy."})
    void testPrint(String toPrint) {
        console = getConsoleWithOutputOnly();
        console.print(toPrint);
        assertEquals(toPrint, outputStream.toString());
    }

    @ParameterizedTest
    @ValueSource(strings = {"zero", "one thousand", "negative infinity", "true"})
    void testGetIntegerReturnsNull(String input) {
        console = getConsoleWithInputAndOutput(input);
        IntFunction<Integer> anyInteger = i -> i;
        Integer result = console.getInteger(anyInteger);
        assertNull(result);
    }

    @ParameterizedTest
    @CsvSource({
            "1, 1",
            "1000, 1000",
            "237, 237",
            "42, 42"
    })
    void testGetIntegerReturnsInteger(String input, Integer expected) {
        console = getConsoleWithInputAndOutput(input);
        IntFunction<Integer> anyInteger = i -> i;
        Integer result = console.getInteger(anyInteger);
        assertEquals(expected, result);
    }

    @ParameterizedTest
    @MethodSource("getIntegerUntilAboveZeroTestArguments")
    void testGetIntegerUntilAboveZero(String input, String output, int expected) {
        console = getConsoleWithInputAndOutput(input);
        int result = console.getIntegerUntilAboveZero();
        assertEquals(output, outputStream.toString());
        assertEquals(expected, result);
    }

    static Stream<Arguments> getIntegerUntilAboveZeroTestArguments() {
        String errorMessage = "Error! Incorrect input. Try again: ";
        return Stream.of(
                arguments("82\n", "", 82),
                arguments("-42\n42\n", errorMessage, 42),
                arguments("one\n1", errorMessage, 1),
                arguments("six\n-6\nzero\n0\n234", errorMessage.repeat(4), 234)
        );
    }

    @ParameterizedTest
    @MethodSource("testGetIntegerInRangeInclusiveArguments")
    void testGetIntegerInRangeInclusive(int min, int max, String input, String output, int expected) {
        console = getConsoleWithInputAndOutput(input);
        int result = console.getIntegerInRangeInclusive(min, max);
        assertEquals(output, outputStream.toString());
        assertEquals(expected, result);
    }

    static Stream<Arguments> testGetIntegerInRangeInclusiveArguments() {
        BiFunction<Integer, Integer, String> errorMessage = (min, max) -> String.format("Error! Number should be between %d and %d. Try again: ", min, max);

        return Stream.of(
                arguments(1, 10, "5", "", 5),
                arguments(1, 10, "0\n10", errorMessage.apply(1, 10), 10),
                arguments(-5, 5, "10\n-10\n6\n-6\n0\n", errorMessage.apply(-5, 5).repeat(4), 0),
                arguments(-5, 5, "10\n-10\n6\n-6\n-5\n", errorMessage.apply(-5, 5).repeat(4), -5),
                arguments(-5, 5, "10\n-10\n6\n-6\n5\n", errorMessage.apply(-5, 5).repeat(4), 5)
        );
    }

    @ParameterizedTest
    @MethodSource("testGetUserInputAndSplitArguments")
    void testGetUserInputAndSplit(String input, boolean caseInsensitive, String[] expected) {
        console = getConsoleWithInputAndOutput(input);
        String[] result = console.getUserInputAndSplit(caseInsensitive);
        assertArrayEquals(expected, result);
    }

    static Stream<Arguments> testGetUserInputAndSplitArguments() {
        return Stream.of(
                arguments("It was the best of times", false, new String[]{"It", "was", "the", "best", "of", "times"}),
                arguments("   Multiple\tTyPeS  oF \t SpACeS    ", true, new String[]{"multiple", "types", "of", "spaces"}),
                arguments("SomE InSaN3 KiNd oF TeXt!", false, new String[]{"SomE", "InSaN3", "KiNd", "oF", "TeXt!"})
        );
    }
}
