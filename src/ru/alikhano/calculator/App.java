package ru.alikhano.calculator;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class App {
    public static void main(String[] args) throws IOException {

        System.out.print("Введите выражение: ");

        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        String example = reader.readLine();

        Calculator c = new CalculatorImpl();

        System.out.print("Результат вычисления: " + c.evaluate(example));
    }
}
