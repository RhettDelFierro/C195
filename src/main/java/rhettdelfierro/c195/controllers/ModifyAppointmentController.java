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
import java.util.ResourceBundle;

/**
 * Controller for Modify Part page.
 */
public class ModifyAppointmentController implements Initializable {

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
    void onActionUpdateAppointment(ActionEvent event) throws SQLException, IOException {
        if (titleTxt.getText().isEmpty() || descriptionTxt.getText().isEmpty() || locationTxt.getText().isEmpty() ||
                typeTxt.getText().isEmpty() || startTxt.getText().isEmpty() || endTxt.getText().isEmpty()) {
            Errors.showErrorDialog("All text fields must be filled out.");
            return;
        }

        int id = Integer.parseInt(appointmentIdTxt.getText());
        String title = titleTxt.getText();
        String description = descriptionTxt.getText();
        String location = locationTxt.getText();
        Contact contact = contactCombo.getSelectionModel().getSelectedItem();
        String type = typeTxt.getText();
        Customer customer = customerCombo.getSelectionModel().getSelectedItem();
        User user = userCombo.getSelectionModel().getSelectedItem();
        // timestamps:

        ListManagement.updateAppointment(event, newPart);
    }

    /**
     * This method is used to load the part data as chosen from the main screen's table view.
     *
     * @param appointment the appointment to load
     * @param contact the contact to load
     * @param customer the customer to load
     * @param user the user to load
     */
    public void sendAppointment(Appointment appointment, Contact contact, Customer customer, User user) {
        appointmentIdTxt.setText(String.valueOf(appointment.getAppointmentId()));
        titleTxt.setText(appointment.getTitle());
        descriptionTxt.setText(appointment.getDescription());
        locationTxt.setText(appointment.getLocation());
        typeTxt.setText(appointment.getType());
        contactCombo.setValue(contact);
        customerCombo.setValue(customer);
        userCombo.setValue(user);
        // timestamps:
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
    }
}