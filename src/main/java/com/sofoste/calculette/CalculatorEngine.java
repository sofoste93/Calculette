package com.sofoste.calculette;

import java.math.BigDecimal;
import java.math.MathContext;

/**
 * Holds the calculator state without knowing anything about Swing.
 * This separation keeps the arithmetic easy to read, reuse and test.
 */
public final class CalculatorEngine {
    private static final MathContext PRECISION = MathContext.DECIMAL64;

    private String display = "0";
    private BigDecimal accumulator;
    private String pendingOperator;
    private boolean startNewNumber = true;

    public String display() {
        return display;
    }

    public String pendingOperator() {
        return pendingOperator == null ? "" : pendingOperator;
    }

    public void inputDigit(String digit) {
        if (startNewNumber || "Error".equals(display)) {
            display = digit;
            startNewNumber = false;
        } else if ("0".equals(display)) {
            display = digit;
        } else {
            display += digit;
        }
    }

    public void inputDecimal() {
        if (startNewNumber || "Error".equals(display)) {
            display = "0.";
            startNewNumber = false;
        } else if (!display.contains(".")) {
            display += ".";
        }
    }

    public void chooseOperator(String operator) {
        if (accumulator != null && !startNewNumber) {
            evaluate();
        }
        if (!"Error".equals(display)) {
            accumulator = new BigDecimal(display);
            pendingOperator = operator;
            startNewNumber = true;
        }
    }

    public void evaluate() {
        if (pendingOperator == null || startNewNumber || "Error".equals(display)) return;

        BigDecimal right = new BigDecimal(display);
        try {
            BigDecimal result = switch (pendingOperator) {
                case "+" -> accumulator.add(right, PRECISION);
                case "−" -> accumulator.subtract(right, PRECISION);
                case "×" -> accumulator.multiply(right, PRECISION);
                case "÷" -> accumulator.divide(right, PRECISION);
                case "MOD" -> accumulator.remainder(right, PRECISION);
                default -> throw new IllegalStateException("Unknown operator: " + pendingOperator);
            };
            display = format(result);
        } catch (ArithmeticException exception) {
            display = "Error";
        }

        accumulator = null;
        pendingOperator = null;
        startNewNumber = true;
    }

    public void toggleSign() {
        if (!"0".equals(display) && !"Error".equals(display)) {
            display = display.startsWith("-") ? display.substring(1) : "-" + display;
        }
    }

    public void backspace() {
        if (startNewNumber || "Error".equals(display)) return;
        display = display.length() <= 1 ? "0" : display.substring(0, display.length() - 1);
        if ("-".equals(display)) display = "0";
    }

    public void clear() {
        display = "0";
        accumulator = null;
        pendingOperator = null;
        startNewNumber = true;
    }

    private static String format(BigDecimal value) {
        BigDecimal normalized = value.stripTrailingZeros();
        return normalized.compareTo(BigDecimal.ZERO) == 0 ? "0" : normalized.toPlainString();
    }
}
