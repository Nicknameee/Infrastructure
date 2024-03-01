package io.management.ua.utility;

public class Pattern {
    public static final String EMAIL = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
    public static final String TELEGRAM_USERNAME = "^[a-zA-Z0-9]{5,}$";
    public static final String WEBSITE = "^(https?:\\/\\/)[^\\s\\/$.?#].[^\\s]*$";
    public static final String PHONE = "^[\\+]?[(]?[0-9]{3}[)]?[-\\s\\.]?[0-9]{3}[-\\s\\.]?[0-9]{4,6}$";
    public static String renderException(String exception, Object ... args) {
        for (Object arg : args) {
            exception = exception.replaceFirst("\\{\\}", String.valueOf(arg));
        }

        return exception;
    }
}
