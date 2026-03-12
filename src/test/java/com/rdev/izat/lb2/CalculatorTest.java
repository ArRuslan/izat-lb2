package com.rdev.izat.lb2;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CalculatorTest {
    static class LastLineLogger implements ILogger {
        public String lastLine = null;

        @Override
        public void debug(String message, Object... fmt) {
            lastLine = String.format(message, fmt);
        }

        @Override
        public void info(String message, Object... fmt) {
            lastLine = String.format(message, fmt);
        }

        @Override
        public void error(String message, Object... fmt) {
            lastLine = String.format(message, fmt);
        }
    }

    private int runProgram(String input, ILogger logger) {
        InputStream realStdIn = System.in;
        ByteArrayInputStream in = new ByteArrayInputStream(input.getBytes(StandardCharsets.UTF_8));

        System.setIn(in);
        int result = new Calculator(logger).runCli();
        System.setIn(realStdIn);

        return result;
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/test_operations.csv", numLinesToSkip = 1)
    void testOperations(String num1, String num2, String op, int ret, String expected) {
        String input = num1 + "\n" + num2 + "\n" + op + "\n";
        LastLineLogger logger = new LastLineLogger();

        int result = runProgram(input, logger);

        assertEquals(ret, result);
        assertEquals(expected, logger.lastLine);
    }

    @Test
    void testDivisionByZero() {
        String input = "1\n0\n/\n";
        LastLineLogger logger = new LastLineLogger();

        int result = runProgram(input, logger);

        assertEquals(1, result);
        assertEquals("Can't divide by zero!", logger.lastLine);
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/test_invalid_params.csv", numLinesToSkip = 1)
    void testInvalidParams(String num1, String num2, String op, String expected) {
        String input = num1 + "\n" + num2 + "\n" + op + "\n";
        LastLineLogger logger = new LastLineLogger();

        int result = runProgram(input, logger);

        assertEquals(1, result);
        assertEquals(expected, logger.lastLine);
    }
}
