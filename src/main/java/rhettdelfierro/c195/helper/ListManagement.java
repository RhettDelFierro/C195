package rhettdelfierro.c195.helper;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import rhettdelfierro.c195.dao.AppointmentStore;
import rhettdelfierro.c195.dao.ContactStore;
import rhettdelfierro.c195.dao.CountryStore;
import rhettdelfierro.c195.dao.CustomerStore;
import rhettdelfierro.c195.models.*;

import java.sql.SQLException;

public class ListManagement {
    public static ObservableList<Appointment> appointments = FXCollections.observableArrayList();
    public static ObservableList<Customer> customers = FXCollections.observableArrayList();
    public static ObservableList<Country> countries = FXCollections.observableArrayList();
    public static ObservableList<Contact> contacts = FXCollections.observableArrayList();
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
    }

    /**
     * Getter for appointments.
     */
    public static ObservableList<Appointment> getAllAppointments() {
        return appointments;
    }

    /**
     * Getter for customers.
     */
    public static ObservableList<Customer> getAllCustomers() {
        return customers;
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
}
