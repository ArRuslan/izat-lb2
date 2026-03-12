package com.rdev.izat.lb2;

import java.math.BigDecimal;
import java.math.MathContext;
import java.text.DecimalFormat;
import java.util.Scanner;

public class Calculator {
    private static final MathContext mathCtx = MathContext.UNLIMITED;
    private static final DecimalFormat format = new DecimalFormat("0.000");
    private static final ILogger defaultLogger = new ILogger() {
        private void log(String message, Object... fmt) {
            System.out.printf(message + "%n", fmt);
        }

        @Override
        public void debug(String message, Object... fmt) {
            log(message + "%n", fmt);
        }

        @Override
        public void info(String message, Object... fmt) {
            log(message + "%n", fmt);
        }

        @Override
        public void error(String message, Object... fmt) {
            log(message + "%n", fmt);
        }
    };

    private final Scanner scanner = new Scanner(System.in);
    private final ILogger logger;

    public Calculator() {
        this(defaultLogger);
    }

    public Calculator(ILogger logger) {
        this.logger = logger;
    }

    private BigDecimal parseNumberOrNull(String number) {
        try {
            return new BigDecimal(number, mathCtx);
        } catch (NumberFormatException e) {
            return null;
        }
    }

    private String readLine(String prompt) {
        logger.info(prompt);
        return scanner.nextLine().trim();
    }

    private BigDecimal readNumber(String prompt) {
        String input = readLine(prompt);
        return parseNumberOrNull(input);
    }

    public String run(String aStr, String bStr, String op) throws CalculatorException {
        BigDecimal a = parseNumberOrNull(aStr);
        if (a == null) {
            throw new CalculatorException("Invalid number!");
        }

        BigDecimal b = parseNumberOrNull(bStr);
        if (b == null) {
            throw new CalculatorException("Invalid number!");
        }

        BigDecimal result;

        switch (op) {
            case "+":
                logger.debug("ADD: %s %s", a, b);
                result = a.add(b);
                break;
            case "-":
                logger.debug("SUB: %s %s", a, b);
                result = a.subtract(b);
                break;
            case "*":
                logger.debug("MUL: %s %s", a, b);
                result = a.multiply(b);
                break;
            case "/":
                logger.debug("DIV: %s %s", a, b);
                if (b.equals(BigDecimal.ZERO)) {
                    throw new CalculatorException("Can't divide by zero!");
                }
                result = a.divide(b, mathCtx);
                break;
            default:
                throw new CalculatorException("Invalid operation!");
        }

        if (!result.remainder(BigDecimal.ONE).equals(BigDecimal.ZERO)) {
            return format.format(result);
        } else {
            return result.toBigInteger().toString();
        }
    }

    public int runCli() {
        String a = readLine("First number: ");
        String b = readLine("Second number: ");
        String op = readLine("Operation (+, -, *, /): ");

        String result;

        try {
            result = run(a, b, op);
        } catch (CalculatorException e) {
            logger.error(e.getMessage());
            return 1;
        }

        logger.info("Result: %s", result);

        return 0;
    }

    public static void main(String[] args) {
        int ret = new Calculator().runCli();
        System.exit(ret);
    }

    public static class CalculatorException extends Exception {
        public CalculatorException(String msg) {
            super(msg);
        }
    }
}
