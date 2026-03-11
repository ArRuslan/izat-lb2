package com.rdev.izat.lb2;

import java.math.BigDecimal;
import java.math.MathContext;
import java.text.DecimalFormat;
import java.util.Scanner;

public class Calculator {
    private static final MathContext mathCtx = MathContext.UNLIMITED;
    private static final DecimalFormat format = new DecimalFormat("0.000");
    private final Scanner scanner = new Scanner(System.in);

    private BigDecimal readNumber(String prompt) {
        System.out.println(prompt);
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
            System.out.println("Invalid number!");
            return 1;
        }

        BigDecimal b = readNumber("Second number: ");
        if (b == null) {
            System.out.println("Invalid number!");
            return 1;
        }

        System.out.println("Operation (+, -, *, /): ");
        String op = scanner.nextLine().trim();

        BigDecimal result;

        switch (op) {
            case "+":
                result = a.add(b);
                break;
            case "-":
                result = a.subtract(b);
                break;
            case "*":
                result = a.multiply(b);
                break;
            case "/":
                if (b.equals(BigDecimal.ZERO)) {
                    System.out.println("Can't divide by zero!");
                    return 1;
                }
                result = a.divide(b, mathCtx);
                break;
            default:
                System.out.println("Invalid operation!");
                return 1;
        }

        String formattedResult;

        if (!result.remainder(BigDecimal.ONE).equals(BigDecimal.ZERO)) {
            formattedResult = format.format(result);
        } else {
            formattedResult = result.toBigInteger().toString();
        }

        System.out.printf("Result: %s%n", formattedResult);

        return 0;
    }

    public static void main(String[] args) {
        int ret = new Calculator().run();
        System.exit(ret);
    }
}
