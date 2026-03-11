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

    private BigDecimal readNumber(String prompt) {
        logger.info(prompt);
        String input = scanner.nextLine().trim();

        try {
            return new BigDecimal(input, mathCtx);
        } catch (NumberFormatException e) {
            return null;
        }
    }

    public int run() {
        BigDecimal a = readNumber("First number: ");
        if (a == null) {
            logger.error("Invalid number!");
            return 1;
        }

        BigDecimal b = readNumber("Second number: ");
        if (b == null) {
            logger.error("Invalid number!");
            return 1;
        }

        logger.info("Operation (+, -, *, /): ");
        String op = scanner.nextLine().trim();

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
                    logger.error("Can't divide by zero!");
                    return 1;
                }
                result = a.divide(b, mathCtx);
                break;
            default:
                logger.error("Invalid operation!");
                return 1;
        }

        String formattedResult;

        if (!result.remainder(BigDecimal.ONE).equals(BigDecimal.ZERO)) {
            formattedResult = format.format(result);
        } else {
            formattedResult = result.toBigInteger().toString();
        }

        logger.info("Result: %s", formattedResult);

        return 0;
    }

    public static void main(String[] args) {
        int ret = new Calculator().run();
        System.exit(ret);
    }
}
