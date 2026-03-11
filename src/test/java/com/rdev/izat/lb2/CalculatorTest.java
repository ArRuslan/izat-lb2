package com.rdev.izat.lb2;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.api.Test;

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
        int result = new Calculator(logger).run();
        System.setIn(realStdIn);

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
        LastLineLogger logger = new LastLineLogger();

        int result = runProgram(input, logger);

        assertEquals(1, result);
        assertEquals(expected, logger.lastLine);
    }
}
