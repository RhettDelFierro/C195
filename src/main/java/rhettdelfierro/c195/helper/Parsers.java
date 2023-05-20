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
}
