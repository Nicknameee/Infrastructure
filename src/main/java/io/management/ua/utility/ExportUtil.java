package io.management.ua.utility;

import io.management.ua.annotations.Export;
import io.management.ua.exceptions.DefaultException;
import io.netty.util.internal.StringUtil;

import java.util.regex.Pattern;

public class ExportUtil {
    @Export
    public static String convertFieldNameToTitle(String fieldName) {
        StringBuilder result = new StringBuilder();
        char[] field = fieldName.toCharArray();
        for (int iteration = 0; iteration < field.length; iteration++) {
            if (iteration == 0) {
                result.append(Character.toUpperCase(field[iteration]));
            } else {
                if (Character.isUpperCase(field[iteration]) && Character.isLowerCase(field[iteration - 1])) {
                    result.append(" ");
                }
                result.append(field[iteration]);
            }
        }

        return result.toString().trim();
    }

    @Export
    public static String validateFilename(String filename) {
        if (StringUtil.isNullOrEmpty(filename) || !Pattern.matches("^[a-zA-Z0-9]*$", filename)) {
            throw new DefaultException("Filename {} is invalid", filename);
        }

        return filename;
    }
}
