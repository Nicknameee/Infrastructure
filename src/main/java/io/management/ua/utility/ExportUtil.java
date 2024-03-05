package io.management.ua.utility;

import io.management.ua.annotations.Export;
import io.management.ua.exceptions.DefaultException;
import io.netty.util.internal.StringUtil;
import org.apache.poi.ss.usermodel.Cell;

import java.math.BigDecimal;
import java.util.Map;
import java.util.UUID;
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

    @Export
    public static void setCellValue(Cell cell, Object value) {
        if (value instanceof String) {
            cell.setCellValue((String) value);
        } else if (value instanceof Long) {
            cell.setCellValue((Long) value);
        } else if (value instanceof Integer) {
            cell.setCellValue((Integer) value);
        } else if (value instanceof BigDecimal) {
            cell.setCellValue(((BigDecimal) value).doubleValue());
        } else if (value instanceof Boolean) {
            cell.setCellValue((Boolean) value);
        } else if (value instanceof UUID) {
            cell.setCellValue(value.toString());
        } else if (value instanceof Map<?, ?> map) {
            StringBuilder sb = new StringBuilder();

            for (Map.Entry<?, ?> entry : map.entrySet()) {
                sb.append(entry.getKey()).append(": ").append(entry.getValue()).append(", ");
            }

            String mapAsString = sb.substring(0, sb.length() - 2);
            cell.setCellValue(mapAsString);
        } else if (value instanceof String[] array) {
            String arrayAsString = String.join(", ", array);
            cell.setCellValue(arrayAsString);
        }
    }
}
