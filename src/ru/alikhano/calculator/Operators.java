package ru.alikhano.calculator;

public enum Operators {
    PLUS('+'),
    MINUS('-'),
    MULTIPLY('*'),
    DIVIDE('/'),
    OPEN_BRACKET('('),
    CLOSE_BRACKET(')');

    private final char symbol;

    Operators(char symbol) {
        this.symbol = symbol;
    }

    public char toChar() {return symbol;}

    public static Operators isOperator(char c) {
        if (c == Operators.PLUS.toChar()) {
            return Operators.PLUS;
        } else if (c == Operators.MINUS.toChar()) {
            return Operators.MINUS;
        } else if (c == Operators.MULTIPLY.toChar()) {
            return Operators.MULTIPLY;
        } else if (c == Operators.DIVIDE.toChar()) {
            return Operators.DIVIDE;
        }
        return null;
    }
}