package rhettdelfierro.c195.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import rhettdelfierro.c195.helper.Errors;
import rhettdelfierro.c195.helper.ListManagement;
import rhettdelfierro.c195.helper.Utils;
import rhettdelfierro.c195.models.Appointment;
import rhettdelfierro.c195.models.Contact;
import rhettdelfierro.c195.models.Customer;
import rhettdelfierro.c195.models.User;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalTime;
import java.util.ResourceBundle;

public class AddAppointmentController implements Initializable {

    @FXML
    private TextField appointmentIdTxt;
    @FXML
    private TextField titleTxt;
    @FXML
    private TextField descriptionTxt;
    @FXML
    private TextField locationTxt;
    @FXML
    private ComboBox<Contact> contactCombo;
    @FXML
    private TextField typeTxt;
    @FXML
    private TextField startTxt;
    @FXML
    private TextField endTxt;
    @FXML
    private ComboBox<Customer> customerCombo;
    @FXML
    private ComboBox<User> userCombo;

    /**
     * Action event handler for clicking the Save Button. This will update and save the appointment to the Database
     * and reroute the user to the main screen.
     * @param event the action event
     * @throws IOException throws an IOException that bubbles up.
     * @throws SQLException throws an SQLException that bubbles up.
     */
    @FXML
    void onActionSaveAppointment(ActionEvent event) throws SQLException, IOException {
//        int id = Integer.parseInt(appointmentIdTxt.getText());
        String title = titleTxt.getText();
        String description = descriptionTxt.getText();
        String location = locationTxt.getText();
        Contact contact = contactCombo.getSelectionModel().getSelectedItem();
        String type = typeTxt.getText();
        Customer customer = customerCombo.getSelectionModel().getSelectedItem();
        User user = userCombo.getSelectionModel().getSelectedItem();
        if (titleTxt.getText().isEmpty() || descriptionTxt.getText().isEmpty() || locationTxt.getText().isEmpty() ||
                typeTxt.getText().isEmpty() || startTxt.getText().isEmpty() || endTxt.getText().isEmpty()) {
            Errors.showErrorDialog("All text fields must be filled out.");
            return;
        }
        if (contact == null || customer == null || user == null) {
            Errors.showErrorDialog("All combo boxes must have a selection.");
            return;
        }

        // timestamps:
        Appointment appointment = new Appointment(title, description, location, type, startTxt.getText(), endTxt.getText(), customer.getCustomerId(), user.getUserId(), contact.getContactId());
        ListManagement.createAppointment(event,appointment);
    }

    /**
     * Action event handler for clicking the Cancel Button. This will return to the main screen.
     *
     * @param event the action event
     * @throws IOException an IOException that bubbles up.
     */
    @FXML
    void onActionCancelUpdatePart(ActionEvent event) throws IOException {
        Utils.changeScene(event, "central-view");
    }

    /**
     * Initializes this controller class from implementing the Initializable interface.
     * Unused here but required for the interface.
     *
     * @param url the url
     * @param resourceBundle the resource bundle folder
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        contactCombo.setItems(ListManagement.getAllContacts());
        customerCombo.setItems(ListManagement.getAllCustomers());
        userCombo.setItems(ListManagement.getAllUsers());
        contactCombo.setPromptText("Select Contact");
        customerCombo.setPromptText("Select Customer");
        userCombo.setPromptText("Select User");

        LocalTime start = LocalTime.of(12,0);
        LocalTime end = LocalTime.of(12, 30);
    }
}
