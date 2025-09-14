package me.goowen.projects.utilities;

/**
 * NumericChecker provided by StackOverflow
 */
public class NumericChecker {
    public static boolean isNumeric(final String string) {
        // null or empty
        if (string == null || string.length() == 0) {
            return false;
        }
        for (char c : string.toCharArray()) {
            if (!Character.isDigit(c)) {
                return false;
            }
        }
        return true;
    }
}
