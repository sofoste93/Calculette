package com.sofoste.calculette;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

class CalculatorEngineTest {
    @Test void calculatesTheFiveOriginalOperations() {
        assertEquals("12", calculate("8", "+", "4"));
        assertEquals("4", calculate("8", "−", "4"));
        assertEquals("32", calculate("8", "×", "4"));
        assertEquals("2", calculate("8", "÷", "4"));
        assertEquals("1", calculate("9", "MOD", "4"));
    }

    @Test void reportsDivisionByZeroAndClearsCleanly() {
        CalculatorEngine engine = expression("9", "÷", "0");
        engine.evaluate();
        assertEquals("Error", engine.display());
        engine.clear();
        assertEquals("0", engine.display());
    }

    @Test void acceptsOneDecimalPoint() {
        CalculatorEngine engine = new CalculatorEngine();
        engine.inputDigit("3"); engine.inputDecimal(); engine.inputDecimal(); engine.inputDigit("5");
        assertEquals("3.5", engine.display());
    }

    @Test void avoidsLeadingZeroes() {
        CalculatorEngine engine = new CalculatorEngine();
        engine.inputDigit("0"); engine.inputDigit("5");
        assertEquals("5", engine.display());
    }

    private static String calculate(String left, String operator, String right) {
        CalculatorEngine engine = expression(left, operator, right);
        engine.evaluate();
        return engine.display();
    }

    private static CalculatorEngine expression(String left, String operator, String right) {
        CalculatorEngine engine = new CalculatorEngine();
        for (char digit : left.toCharArray()) engine.inputDigit(String.valueOf(digit));
        engine.chooseOperator(operator);
        for (char digit : right.toCharArray()) engine.inputDigit(String.valueOf(digit));
        return engine;
    }
}
