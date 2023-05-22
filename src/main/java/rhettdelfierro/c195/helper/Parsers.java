package rhettdelfierro.c195.helper;

public class Parsers {

    /**
     * Helper to parse strings for valid integers. Often used before Integer.parseInt() and other casting.
     * @param str the string to check.
     * @return boolean whether the string is a valid integer.
     */
    public static boolean checkValidInt(String str){
        return str.matches("^-?\\d+$");
    }

    /**
     * Helper to parse strings for valid doubles. Often used before to check if prices were correctly entered.
     * @param str string to parse
     * @return boolean whether the string is a valid double.
     */
    public static boolean checkValidDouble(String str){
        return str.matches("^[-+]?\\d*\\.?\\d+$");
    }

    /**
     * Helper to capitalize the first letter of a string.
     * @param original the string to capitalize.
     * @return the capitalized string.
     */
    public static String capitalizeFirstLetter(String original) {
        if (original == null || original.length() == 0) {
            return original;
        }
        return original.substring(0, 1).toUpperCase() + original.substring(1).toLowerCase();
    }
}
