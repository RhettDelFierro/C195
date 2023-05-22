package rhettdelfierro.c195.helper;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class Utils {
    /**
     * Helper method to change scenes
     *
     * @param event the action event
     * @param viewName the name of the view to change to (add-part, modify-part, add-product, modify-product)
     * @throws IOException if getResource() fails to find the .fxml file
     */
    public static void changeScene(ActionEvent event, String viewName) throws IOException {
        int width = viewName == "central-view" || viewName == "reports" ? 1500 : 750;
        Stage stage = (Stage) ((Button)event.getSource()).getScene().getWindow();
        Parent scene = FXMLLoader.load(Utils.class.getResource("/rhettdelfierro/c195/" + viewName + ".fxml"));
        stage.setScene(new Scene(scene, width, 530));
        stage.show();
    }

}
