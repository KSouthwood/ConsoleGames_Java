package com.github.ksouthwood.console_games;

import com.github.ksouthwood.console_games.TextInput.ConsoleIOHandler;
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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.params.provider.Arguments.arguments;

public class ConsoleIOHandlerTest {

    private ConsoleIOHandler console;
    private ByteArrayOutputStream outputStream;

    @BeforeEach
    void beforeEach() {
        outputStream = new ByteArrayOutputStream();
    }

    @ParameterizedTest
    @ValueSource(strings = {"Hello, World!", "I'm Batman.", "Everything is wonderful!"})
    void testPrintln(String toPrint) {
        console = new ConsoleIOHandler(System.in, new PrintStream(outputStream));
        console.println(toPrint);
        assertEquals(toPrint + "\n", outputStream.toString());
    }

    @ParameterizedTest
    @ValueSource(strings = {"Crazy?", "I was crazy once.", "Rats make me crazy."})
    void testPrint(String toPrint) {
        console = new ConsoleIOHandler(System.in, new PrintStream(outputStream));
        console.print(toPrint);
        assertEquals(toPrint, outputStream.toString());
    }

    @ParameterizedTest
    @ValueSource(strings = {"zero", "one thousand", "negative infinity", "true"})
    void testGetIntegerReturnsNull(String input) {
        console = new ConsoleIOHandler(new ByteArrayInputStream(input.getBytes()), new PrintStream(outputStream));
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
        console = new ConsoleIOHandler(new ByteArrayInputStream(input.getBytes()), new PrintStream(outputStream));
        IntFunction<Integer> anyInteger = i -> i;
        Integer result = console.getInteger(anyInteger);
        assertEquals(expected, result);
    }

    @ParameterizedTest
    @MethodSource("getIntegerUntilAboveZeroTestArguments")
    void testGetIntegerUntilAboveZero(String input, String output, int expected) {
        console = new ConsoleIOHandler(new ByteArrayInputStream(input.getBytes()), new PrintStream(outputStream));
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
        console = new ConsoleIOHandler(new ByteArrayInputStream(input.getBytes()), new PrintStream(outputStream));
        int result = console.getIntegerInRangeInclusive(min, max);
        assertEquals(output, outputStream.toString());
        assertEquals(expected, result);
    }

    public static Stream<Arguments> testGetIntegerInRangeInclusiveArguments() {
        BiFunction<Integer, Integer, String> errorMessage = (min, max) -> String.format("Error! Number should be between %d and %d. Try again: ", min, max);

        return Stream.of(
                arguments(1, 10, "5", "", 5),
                arguments(1, 10, "0\n10", errorMessage.apply(1, 10), 10),
                arguments(-5, 5, "10\n-10\n6\n-6\n0\n", errorMessage.apply(-5, 5).repeat(4), 0),
                arguments(-5, 5, "10\n-10\n6\n-6\n-5\n", errorMessage.apply(-5, 5).repeat(4), -5),
                arguments(-5, 5, "10\n-10\n6\n-6\n5\n", errorMessage.apply(-5, 5).repeat(4), 5)
        );
    }
}
