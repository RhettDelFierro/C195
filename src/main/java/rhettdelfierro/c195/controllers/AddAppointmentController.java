package rhettdelfierro.c195.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
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
    private TextField typeTxt;
    @FXML
    private ComboBox<Contact> contactCombo;
    @FXML
    private ComboBox<Customer> customerCombo;
    @FXML
    private ComboBox<User> userCombo;
    @FXML
    private DatePicker startDatePicker;
    @FXML
    private ComboBox<String> startHourCombo;
    @FXML
    private ComboBox<String> startMinuteCombo;
    @FXML
    private DatePicker endDatePicker;
    @FXML
    private ComboBox<String> endHourCombo;
    @FXML
    private ComboBox<String> endMinuteCombo;
    ObservableList<String> hours = FXCollections.observableArrayList();
    ObservableList<String> minutes = FXCollections.observableArrayList();

    /**
     * Action event handler for clicking the Save Button. This will update and save the appointment to the Database
     * and reroute the user to the main screen.
     * @param event the action event
     * @throws IOException throws an IOException that bubbles up.
     * @throws SQLException throws an SQLException that bubbles up.
     */
    @FXML
    void onActionSaveAppointment(ActionEvent event) throws SQLException, IOException {
        if (titleTxt.getText().isEmpty() || descriptionTxt.getText().isEmpty() || locationTxt.getText().isEmpty() ||
                typeTxt.getText().isEmpty()) {
            Errors.showErrorDialog("All text fields must be filled out.");
            return;
        }
        if (startDatePicker.getValue() == null || endDatePicker.getValue() == null) {
            Errors.showErrorDialog("All date fields must be filled out.");
            return;
        }
        if (startHourCombo.getSelectionModel().isEmpty() || startMinuteCombo.getSelectionModel().isEmpty() ||
                endHourCombo.getSelectionModel().isEmpty() || endMinuteCombo.getSelectionModel().isEmpty()) {
            Errors.showErrorDialog("All time fields must be filled out.");
            return;
        }

        String title = titleTxt.getText();
        String description = descriptionTxt.getText();
        String location = locationTxt.getText();
        Contact contact = contactCombo.getSelectionModel().getSelectedItem();
        String type = typeTxt.getText();
        Customer customer = customerCombo.getSelectionModel().getSelectedItem();
        User user = userCombo.getSelectionModel().getSelectedItem();
        // timestamps:
        String start = startDatePicker.getValue() + " " + startHourCombo.getSelectionModel().getSelectedItem() + ":" +
                startMinuteCombo.getSelectionModel().getSelectedItem() + ":00";
        String end = endDatePicker.getValue() + " " + endHourCombo.getSelectionModel().getSelectedItem() + ":" +
                endMinuteCombo.getSelectionModel().getSelectedItem() + ":00";
        Appointment appointment = new Appointment(title, description, location, type, start, end, customer.getCustomerId(), user.getUserId(), contact.getContactId());
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

        hours.addAll("00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11",
                "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23");
        minutes.addAll("00", "15", "30", "45");
        startHourCombo.setItems(hours);
        startMinuteCombo.setItems(minutes);
        endHourCombo.setItems(hours);
        endMinuteCombo.setItems(minutes);

    }
}
