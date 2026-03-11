package com.rdev.izat.lb2;

import java.util.Scanner;

public class Calculator {
    private final Scanner scanner = new Scanner(System.in);

    private Double readNumber(String prompt) {
        System.out.println(prompt);
        String input = scanner.nextLine().trim();

        try {
            double num = Double.parseDouble(input);

            if (Double.isNaN(num) || Double.isInfinite(num)) {
                return null;
            }

            return num;
        } catch (NumberFormatException e) {
            return null;
        }
    }

    public int run() {
        Double a = readNumber("First number: ");
        if (a == null) {
            System.out.println("Invalid number!");
            return 1;
        }

        Double b = readNumber("Second number: ");
        if (b == null) {
            System.out.println("Invalid number!");
            return 1;
        }

        System.out.println("Operation (+, -, *, /): ");
        String op = scanner.nextLine().trim();

        double result;

        switch (op) {
            case "+":
                result = a + b;
                break;
            case "-":
                result = a - b;
                break;
            case "*":
                result = a * b;
                break;
            case "/":
                if (b == 0) {
                    System.out.println("Can't divide by zero!");
                    return 1;
                }
                result = a / b;
                break;
            default:
                System.out.println("Invalid operation!");
                return 1;
        }

        if (result % 1 != 0) {
            System.out.printf("Result: %.3f%n", result);
        } else {
            System.out.println("Result: " + (long) result);
        }

        return 0;
    }

    public static void main(String[] args) {
        int ret = new Calculator().run();
        System.exit(ret);
    }
}
