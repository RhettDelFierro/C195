package rhettdelfierro.c195.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import rhettdelfierro.c195.dao.UserStore;
import rhettdelfierro.c195.helper.DateTime;
import rhettdelfierro.c195.helper.Utils;
import rhettdelfierro.c195.models.User;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ResourceBundle;

/**
 * Controller for Login page.
 */
public class LoginController implements Initializable {
    Stage stage;
    Parent scene;
    @FXML
    private Label currentTimezoneLabel;

    @FXML
    private Button exitButton;

    @FXML
    private Button loginButton;

    @FXML
    private Label loginLabel;

    @FXML
    private Label passwordLabel;

    @FXML
    private Label timezoneLabel;

    @FXML
    private Label usernameLabel;
    @FXML
    private TextField usernameTxt;
    @FXML
    private TextField passwordTxt;

    private String error;
    private String cancel;
    private String errorMessage;
    private String continueMessage;


    /**
     * Exits the application.
     * @param event
     */
    @FXML
    void onActionExit(ActionEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }

    /**
     * Handles the login logic.
     * @param event
     * @throws IOException IOException possible during changing scenes.
     * @throws SQLException SQLException possible during login when querying the database.
     */
    @FXML
    void onActionLogin(ActionEvent event) throws IOException, SQLException {
        String username = usernameTxt.getText();
        String password = passwordTxt.getText();
        User user = UserStore.login(username, password);

        if (user != null) {
            try {
                Utils.writeFile("User " + username + " successfully logged-in at");
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(Utils.class.getResource("/rhettdelfierro/c195/central-view.fxml"));
                loader.load();
                CentralController controller = loader.getController();
                controller.sendUser(user);

                stage = (Stage)((Button)event.getSource()).getScene().getWindow();
                Parent scene = loader.getRoot();
                stage.setScene(new Scene(scene, 1500, 530));
                stage.showAndWait();
            } catch (RuntimeException e) {
                e.printStackTrace();
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle(error);
            alert.setContentText(errorMessage);
            alert.showAndWait();
            Utils.writeFile("User " + username + " gave invalid log-in at");
        }
    }

    /**
     * Initializes the login view. Sets the labels and buttons to the correct language.
     * @param location
     * @param resources resource bundle.
     */
    @Override
    public void initialize(java.net.URL location, java.util.ResourceBundle resources) {
        ResourceBundle rb = ResourceBundle.getBundle("bundle/lang", java.util.Locale.getDefault());
        loginLabel.setText(rb.getString("login"));
        usernameLabel.setText(rb.getString("username"));
        passwordLabel.setText(rb.getString("password"));
        timezoneLabel.setText(rb.getString("timezone"));
        loginButton.setText(rb.getString("login"));
        exitButton.setText(rb.getString("exit"));
        error = rb.getString("error");
        cancel = rb.getString("cancel");
        errorMessage = rb.getString("error_login");
        continueMessage = rb.getString("ok");
        currentTimezoneLabel.setText(DateTime.getCurrentTimezone());
    }
}
