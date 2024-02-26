package io.management.ua.utility;

import io.management.ua.annotations.Export;

import java.util.Random;

public class CodeGenerator {
    @Export
    public static String generateCode() {
        return String.valueOf(new Random().nextInt((int) 10E3, (int) 10E4));
    }

    @Export
    public static String generateCode(String pattern) {
        StringBuilder code = new StringBuilder();

        for (Character symbol : pattern.toCharArray()) {
            switch (symbol) {
                case 'i' -> code.append(new Random().nextInt(0, 10));
                case 's' -> code.append((char) new Random().nextInt('A', (int) 'Z' + 1));
                case '-' -> code.append('-');
            }
        }

        return code.toString();
    }
}
