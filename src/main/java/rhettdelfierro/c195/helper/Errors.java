package rhettdelfierro.c195.helper;

import javafx.scene.control.Alert;

public class Errors {
    /**
     * Helper to parse strings for valid integers. Often used before Integer.parseInt() and other casting.
     * @param str the string to check.
     * @return boolean whether the string is a valid integer.
     */
    public static boolean checkValidInt(String str){
        return str.matches("^-?\\d+$");
    }

    /**
     * Helper to show error dialogs.
     * @param message the error message to display.
     */
    public static void showErrorDialog(String message){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Program error.");
        alert.setContentText(message);
        alert.showAndWait();
    }

    /**
     * Helper to show warning dialogs. This is really not used in the app as most warnings are errors.
     * @param message the warning message to display.
     */
    public static void showWarningDialog(String message){
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Continue?");
        alert.setContentText(message);
        alert.showAndWait();
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
