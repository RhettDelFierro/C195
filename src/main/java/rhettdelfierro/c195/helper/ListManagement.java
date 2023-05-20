package rhettdelfierro.c195.helper;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import rhettdelfierro.c195.dao.*;
import rhettdelfierro.c195.models.*;

import java.io.IOException;
import java.sql.SQLException;

public class ListManagement {
    public static ObservableList<Appointment> appointments = FXCollections.observableArrayList();
    public static ObservableList<Customer> customers = FXCollections.observableArrayList();
    public static ObservableList<Country> countries = FXCollections.observableArrayList();
    public static ObservableList<Contact> contacts = FXCollections.observableArrayList();
    public static ObservableList<User> users = FXCollections.observableArrayList();
    public static User currentUser = null;

    /**
     * Will re-populate date.
     * @param user the current user once logged in.
     * @throws SQLException
     */
    public static void fetchAll(User user) throws SQLException {
        appointments = AppointmentStore.fetchAll();
        customers = CustomerStore.fetchAll();
        countries = CountryStore.fetchAll();
        contacts = ContactStore.fetchAll();
        users = UserStore.fetchAll();
        currentUser = user;
    }

    /**
     * Will re-populate date.
     * @throws SQLException
     */
    public static void fetchAll() throws SQLException {
        appointments = AppointmentStore.fetchAll();
        customers = CustomerStore.fetchAll();
        countries = CountryStore.fetchAll();
        contacts = ContactStore.fetchAll();
        users = UserStore.fetchAll();
    }

    /**
     * Getter for appointments.
     *
     * @return the appointments
     */
    public static ObservableList<Appointment> getAllAppointments() {
        return appointments;
    }

    /**
     * Getter for customers.
     *
     * @return the customers
     */
    public static ObservableList<Customer> getAllCustomers() {
        return customers;
    }

    /**
     * Getter for countries.
     *
     * @return the countries
     */
    public static ObservableList<Country> getAllCountries() {
        return countries;
    }

    /**
     * Getter for contacts.
     *
     * @return the contacts
     */
    public static ObservableList<Contact> getAllContacts() {
        return contacts;
    }

    /**
     * Getter for users.
     *
     * @return the users
     */
    public static ObservableList<User> getAllUsers() {
        return users;
    }

    /**
     * Getter for current user.
     *
     * @return the current user
     */
    public static User getCurrentUser() {
        return currentUser;
    }

    /**
     * Helper method to get appointments for this month.
     *
     * @throws SQLException
     */
    public static ObservableList<Appointment> searchAppointmentsByCurrentTime(String timeFrame) throws SQLException {
        String sql = null;
        if (timeFrame.equals("month")) {
            sql = "SELECT * FROM APPOINTMENTS WHERE MONTH(Start) = MONTH(NOW()) AND YEAR(Start) = YEAR(NOW())";
        } else {
            sql = "SELECT * FROM APPOINTMENTS WHERE WEEK(Start) = WEEK(NOW()) AND YEAR(Start) = YEAR(NOW())";
        }
        ObservableList<Appointment> appointments = AppointmentStore.selectBySimpleQuery(sql);
        return appointments;
    }

    /**
     * Delete Customer.
     * @param customer the customer to delete.
     * @throws SQLException
     */
    public static void deleteCustomer(Customer customer) throws SQLException {
        ObservableList<Appointment> appointments = AppointmentStore.selectByCustomerId(customer.getCustomerId());
        if (appointments.size() > 0) {
            Errors.showErrorDialog("Cannot delete customer with appointments. PLease delete these appointments first.");
            return;
        }
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirm deletion.");
        alert.setContentText("Are you sure you want to delete the customer: " + customer.getCustomerName() + "?");
        alert.showAndWait();
        if (alert.getResult() != ButtonType.OK) {
            return;
        }
        CustomerStore.delete(customer.getCustomerId());
        customers = CustomerStore.fetchAll();
    }

    /**
     * Delete Appointment.
     * @param appointment the appointment to delete.
     * @throws SQLException
     */
    public static void deleteAppointment(Appointment appointment) throws SQLException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirm deletion.");
        alert.setContentText("Are you sure you want to delete the appointment: " + appointment.getTitle() + "?");
        alert.showAndWait();
        if (alert.getResult() != ButtonType.OK) {
            return;
        }
        AppointmentStore.delete(appointment.getAppointmentId());
        appointments = AppointmentStore.fetchAll();
    }

    /**
     * Search for customers by given query.
     * @param query sql query string.
     * @throws SQLException
     */
    public static ObservableList<Appointment> searchByAppointmentTitle(String query) throws SQLException {
        ObservableList<Appointment> appointments = AppointmentStore.selectByTitle(query);
        return appointments;
    }

    /**
     * Create a new appointment.
     *
     * @param event the event to change the scene.
     * @param appointment the appointment to create.
     * @throws SQLException
     * @throws IOException
     */
    public static void createAppointment(ActionEvent event, Appointment appointment) throws SQLException, IOException {
        // remember you need to check if the appointment clashes with another appointment.
        // if it does, then you need to throw an error.
        int rowsAffected = AppointmentStore.insert(appointment);
        if (rowsAffected == 0) {
            Errors.showErrorDialog("Appointment clashes with another appointment. Please choose another time.");
        }
        appointments = AppointmentStore.fetchAll();
        Utils.changeScene(event, "central-view");
    }

    public static void updateAppointment(ActionEvent event, int appointmentId, String title, String description, String location, String type, String start, String end, int customerId, int userId, int contactId) throws SQLException, IOException {
        // remember you need to check if the appointment clashes with another appointment.
        // if it does, then you need to throw an error.
        AppointmentStore.update(appointmentId, title, description, location, type, start, end, customerId, userId, contactId);
        appointments = AppointmentStore.fetchAll();
        Utils.changeScene(event, "central-view");
    }

    public static Contact getContactById(int contactId) {
        for (Contact contact : contacts) {
            if (contact.getContactId() == contactId) {
                return contact;
            }
        }
        return null;
    }

    public static Customer getCustomerById(int customerId) {
        for (Customer customer : customers) {
            if (customer.getCustomerId() == customerId) {
                return customer;
            }
        }
        return null;
    }

    public static User getUserById(int userId) {
        for (User user : users) {
            if (user.getUserId() == userId) {
                return user;
            }
        }
        return null;
    }
}
