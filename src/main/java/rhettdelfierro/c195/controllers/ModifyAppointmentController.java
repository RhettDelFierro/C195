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
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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
    private TextField typeTxt;
    @FXML
    private TextField descriptionTxt;
    @FXML
    private TextField locationTxt;

    @FXML
    private ComboBox<Contact> contactCombo;
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
    @FXML
    private ComboBox<Customer> customerCombo;
    @FXML
    private ComboBox<User> userCombo;

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
    void onActionUpdateAppointment(ActionEvent event) throws SQLException, IOException {
        if (titleTxt.getText().isEmpty() || descriptionTxt.getText().isEmpty() || locationTxt.getText().isEmpty() ||
                typeTxt.getText().isEmpty()) {
            Errors.showErrorDialog("All text fields must be filled out.");
            return;
        }
        if (startDatePicker.getValue() == null || endDatePicker.getValue() == null) {
            Errors.showErrorDialog("All date fields must be filled out.");
            return;
        }
        // create variables for the values in the conditional below
        if (startHourCombo.getSelectionModel().getSelectedItem().isEmpty() || startMinuteCombo.getSelectionModel().getSelectedItem().isEmpty() ||
                endHourCombo.getSelectionModel().getSelectedItem().isEmpty() || endMinuteCombo.getSelectionModel().getSelectedItem().isEmpty()) {
            Errors.showErrorDialog("All time fields must be filled out.");
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
        String start = startDatePicker.getValue() + " " + startHourCombo.getSelectionModel().getSelectedItem() + ":" +
                startMinuteCombo.getSelectionModel().getSelectedItem() + ":00";
        String end = endDatePicker.getValue() + " " + endHourCombo.getSelectionModel().getSelectedItem() + ":" +
                endMinuteCombo.getSelectionModel().getSelectedItem() + ":00";
        Appointment appointment = new Appointment(id, title, description, location, type, start, end, customer.getCustomerId(), user.getUserId(), contact.getContactId());

        ListManagement.updateAppointment(event, appointment);
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
        userCombo.setValue(user);;

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime localDateTimeStart = LocalDateTime.parse(appointment.getStart(), formatter);
        LocalDateTime localDateTimeEnd = LocalDateTime.parse(appointment.getEnd(), formatter);
        startDatePicker.setValue(localDateTimeStart.toLocalDate());
        String startHour = localDateTimeStart.getHour() <= 9 ? "0" + localDateTimeStart.getHour() : String.valueOf(localDateTimeStart.getHour());
        String startMinute = localDateTimeStart.getMinute() == 0 ? "00" : String.valueOf(localDateTimeStart.getMinute());
        startHourCombo.setValue(startHour);
        startMinuteCombo.setValue(startMinute);
        String endHour = localDateTimeEnd.getHour() <= 9 ? "0" + localDateTimeEnd.getHour() : String.valueOf(localDateTimeEnd.getHour());
        String endMinute = localDateTimeEnd.getMinute() == 0 ? "00" : String.valueOf(localDateTimeEnd.getMinute());
        endDatePicker.setValue(localDateTimeEnd.toLocalDate());
        endHourCombo.setValue(endHour);
        endMinuteCombo.setValue(endMinute);
    }

    /**
     * Action event handler for clicking the Cancel Button. This will return to the main screen.
     *
     * @param event the action event
     * @throws IOException an IOException that bubbles up.
     */
    @FXML
    void onActionCancel(ActionEvent event) throws IOException {
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

        hours.addAll("00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11",
                "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23");
        minutes.addAll("00", "15", "30", "45");
        startHourCombo.setItems(hours);
        startMinuteCombo.setItems(minutes);
        endHourCombo.setItems(hours);
        endMinuteCombo.setItems(minutes);
    }
}