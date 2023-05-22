package rhettdelfierro.c195.helper;

import javafx.scene.control.Alert;

/**
 * Helper class to show error and warning dialogs.
 */
public class Errors {

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

}
