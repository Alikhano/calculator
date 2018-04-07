package ru.alikhano.calculator;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.LinkedList;
import java.util.Locale;
import java.util.NoSuchElementException;

import static ru.alikhano.calculator.Operators.isOperator;

public class CalculatorImpl implements Calculator {

    @Override
    public String evaluate(String s) {
        LinkedList<Double> numbers = new LinkedList<>();
        LinkedList<Operators> operators = new LinkedList<>();
        String c = s.replaceAll(" ", "");
        char[] digits = c.toCharArray();
        boolean isBinary = true;

        for (int i = 0; i < digits.length; i++) {

            char ch = digits[i];

            if (ch == Operators.OPEN_BRACKET.toChar()) {
                isBinary = true;
                operators.add(Operators.OPEN_BRACKET);
                continue;
            }
            else if (ch == Operators.CLOSE_BRACKET.toChar()) {
                try {
                    while (operators.getLast() != Operators.OPEN_BRACKET) {
                        if (!applyOperation(numbers, operators.removeLast().toChar())) {
                            return null;
                        }
                    }
                    operators.removeLast();
                }

                catch (NoSuchElementException e) {
                    return "Некорректно закрыты скобки, результат - " + null;
                }
            }
            else {
                Operators operator = isOperator(ch);
                if (operator != null && !((operator == Operators.MINUS || operator == Operators.PLUS) && isBinary)) {

                    try {
                        while (!operators.isEmpty() && definePriority(operators.getLast().toChar()) >= definePriority(operator.toChar())) {
                            if (!applyOperation(numbers, operators.removeLast().toChar())) {
                                return null;
                            }
                        }
                        operators.add(operator);
                    }
                    catch (NoSuchElementException e) {
                        return "Ошибка в операторах, результат - " + null;
                    }
                }
                else {
                    String operand = "";
                    if (ch == Operators.MINUS.toChar() || ch == Operators.PLUS.toChar()) {
                        operand += digits[i++];
                    }
                    while (i < digits.length && (Character.isDigit(digits[i]) || digits[i] == '.')) {
                        operand += digits[i++];
                    }
                    --i;
                    try {
                        numbers.add(Double.parseDouble(operand));
                    } catch (NumberFormatException e) {

                        return "Некорректно заданы операнды, результат - " + null;
                    }
                }
                isBinary = false;
            }
        }
        try {
            while (!operators.isEmpty()) {
                if (!applyOperation(numbers, operators.removeLast().toChar())) {
                    return null;
                }
            }
        }
        catch (NoSuchElementException e) {
            return "Некорректно введены скобки, результат - " + null;
        }
        return roundFunction(numbers.get(0));
    }

    public static int definePriority(char c) {
        switch (c) {
            case '+':
            case '-' :
                return 1;
            case '/':
            case '*':
                return 2;
        }
        return 0;
    }

    public static boolean applyOperation(LinkedList<Double> numbers,char opType) {
        double number2 = numbers.removeLast();
       double number1 = numbers.removeLast();
       Double res = null;

        switch (opType) {
            case '+':
                res =(number1 + number2);
                numbers.add(res);
                break;
            case '-':
                res = (number1 - number2);
                numbers.add(res);
                break;
            case '*':
                res = (number1 * number2);
                numbers.add(res);
                break;
            case '/':
                if (number2 != 0) {
                    res = (number1/number2);
                    numbers.add(res);
                }
                else {
                    System.out.println("Нельзя делить на ноль!");
                }
                break;
            default: res = 0.0;
            break;
        }

        return res != null ;
    }

    public static String roundFunction (double number) {
        DecimalFormat decimalFormat = new DecimalFormat("#.####", DecimalFormatSymbols.getInstance(Locale.ENGLISH));
        return decimalFormat.format(number);
    }

}





