package com.rdev.izat.lb2;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.api.Test;

public class CalculatorTest {
    private int runProgram(String input, StringBuilder output) {
        ByteArrayInputStream in = new ByteArrayInputStream(input.getBytes(StandardCharsets.UTF_8));
        ByteArrayOutputStream out = new ByteArrayOutputStream();

        System.setIn(in);
        System.setOut(new PrintStream(out));

        int result = new Calculator().run();
        output.append(out);

        return result;
    }

    @ParameterizedTest
    @CsvSource({
            "1,2,+,0,Result: 3",
            "1,2,-,0,Result: -1",
            "1,2,*,0,Result: 2",
            "1,2,/,0,Result: 0.500",
            "2,1,/,0,Result: 2",
            "-1,2,+,0,Result: 1",
            "0.1,0.2,*,0,Result: 0.020",
            "18446744073709551616,18446744073709551616,*,0,Result: 340282366920938463463374607431768211456",
    })
    void testOperations(String num1, String num2, String op, int ret, String expected) {
        String input = num1 + "\n" + num2 + "\n" + op + "\n";
        StringBuilder output = new StringBuilder();

        int result = runProgram(input, output);
        assertEquals(ret, result);

        String[] outputLines = output.toString().split(System.lineSeparator());
        assertEquals(expected, outputLines[outputLines.length - 1]);
    }

    @Test
    void testDivisionByZero() {
        String input = "1\n0\n/\n";
        StringBuilder output = new StringBuilder();

        int result = runProgram(input, output);
        assertEquals(1, result);

        String[] outputLines = output.toString().split(System.lineSeparator());
        assertEquals("Can't divide by zero!", outputLines[outputLines.length - 1]);
    }

    @ParameterizedTest
    @CsvSource({
            "1,a,+,Invalid number!",
            "a,2,-,Invalid number!",
            "a,b,*,Invalid number!",
            "1,2,//,Invalid operation!",
            "inf,2,+,Invalid number!",
            "1,-inf,+,Invalid number!",
            "nan,2,+,Invalid number!",
            "1,-nan,+,Invalid number!"
    })
    void testInvalidParams(String num1, String num2, String op, String expected) {

        String input = num1 + "\n" + num2 + "\n" + op + "\n";
        StringBuilder output = new StringBuilder();

        int result = runProgram(input, output);
        assertEquals(1, result);

        String[] outputLines = output.toString().split(System.lineSeparator());
        assertEquals(expected, outputLines[outputLines.length - 1]);
    }
}
