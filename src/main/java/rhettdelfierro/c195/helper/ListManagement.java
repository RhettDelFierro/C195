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
import java.util.Map;
import java.util.stream.Collectors;

/**
 * This class is used to manage the lists of data that are used throughout the application.
 * Rather than the application use the Datastores themselves this acts as a sort of middleware.
 */
public class ListManagement {
    public static ObservableList<Appointment> appointments = FXCollections.observableArrayList();
    public static ObservableList<Customer> customers = FXCollections.observableArrayList();
    public static ObservableList<Country> countries = FXCollections.observableArrayList();
    public static ObservableList<Division> divisions = FXCollections.observableArrayList();
    public static ObservableList<Contact> contacts = FXCollections.observableArrayList();
    public static ObservableList<User> users = FXCollections.observableArrayList();
    public static ObservableList<DivisionReport> divisionReports = FXCollections.observableArrayList();
    public static ObservableList<MonthlyTypeReport> monthlyTypeReports = FXCollections.observableArrayList();
    public static User currentUser = null;

    /**
     * Will re-populate date.
     *
     * @param user the current user once logged in.
     * @throws SQLException
     */
    public static void fetchAll(User user) throws SQLException {
        appointments = AppointmentStore.fetchAll();
        customers = CustomerStore.fetchAll();
        countries = CountryStore.fetchAll();
        contacts = ContactStore.fetchAll();
        divisions = DivisionStore.fetchAll();
        users = UserStore.fetchAll();
        divisionReports = DivisionStore.getReports();
        currentUser = user;
        for (Country country : countries) {
            ObservableList<Division> divisions = DivisionStore.selectByCountryId(country.getCountryId());
            country.setDivisions(divisions);
        }
        for (Customer customer : customers) {
            ObservableList<Appointment> appointments = AppointmentStore.selectByCustomerId(customer.getCustomerId());
            customer.setAppointments(appointments);
            Division division = DivisionStore.selectById(customer.getDivisionId());
            customer.setDivisionName(division.getDivision());
        }
        for (Appointment appointment : appointments) {
            Contact contact = ContactStore.selectContactById(appointment.getContactId());
            appointment.setContactName(contact.getContactName());
        }
    }

    /**
     * Will re-populate date.
     *
     * @throws SQLException
     */
    public static void fetchAll() throws SQLException {
        appointments = AppointmentStore.fetchAll();
        customers = CustomerStore.fetchAll();
        countries = CountryStore.fetchAll();
        divisions = DivisionStore.fetchAll();
        contacts = ContactStore.fetchAll();
        users = UserStore.fetchAll();
        divisionReports = DivisionStore.getReports();

        for (Country country : countries) {
            ObservableList<Division> divisions = DivisionStore.selectByCountryId(country.getCountryId());
            country.setDivisions(divisions);
        }
        for (Customer customer : customers) {
            ObservableList<Appointment> appointments = AppointmentStore.selectByCustomerId(customer.getCustomerId());
            customer.setAppointments(appointments);
            Division division = DivisionStore.selectById(customer.getDivisionId());
            customer.setDivisionName(division.getDivision());
        }
        for (Appointment appointment : appointments) {
            Contact contact = ContactStore.selectContactById(appointment.getContactId());
            appointment.setContactName(contact.getContactName());
        }
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
     * Getter for divisions.
     *
     * @return the divisions
     */
    public static ObservableList<Division> getAllDivisions() {
        return divisions;
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
     * Getter for division reports.
     *
     * @return the division reports
     */
    public static ObservableList<DivisionReport> getAllDivisionReports() {
        return divisionReports;
    }

    /**
     * Getter for monthly type reports.
     * Lambda function to utilize de-limiters to split the type and month.
     * Another lambda function was used as a callback function to add the new monthly type report to the list.
     *
     * @return the monthly type reports
     */
    public static ObservableList<MonthlyTypeReport> getAllMonthlyTypeReports() {
        Map<String, Long> counts = appointments.stream()
                .collect(Collectors.groupingBy(a -> a.getType() + "<->" + DateTime.getMonthFromTime(a.getStart()), Collectors.counting()));

        ObservableList<MonthlyTypeReport> monthlyReports = FXCollections.observableArrayList();
        counts.forEach((key, value) -> {
            String[] parts = key.split("<->");
            monthlyReports.add(new MonthlyTypeReport(parts[0], parts[1], value.intValue()));
        });
        monthlyTypeReports = monthlyReports;

        return monthlyTypeReports;
    }


    /**
     * Getter for divisions by countryId.
     *
     * @param countryId the country id
     * @return the divisions
     */
    public static ObservableList<Division> getDivisionsByCountryId(int countryId) {
        ObservableList<Division> divisionsByCountryId = FXCollections.observableArrayList();
        for (Division division : divisions) {
            if (division.getCountryId() == countryId) {
                divisionsByCountryId.add(division);
            }
        }
        return divisionsByCountryId;
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
     *
     * @param customer the customer to delete.
     * @throws SQLException
     */
    public static int deleteCustomer(Customer customer) throws SQLException {
        ObservableList<Appointment> appointments = AppointmentStore.selectByCustomerId(customer.getCustomerId());
        if (appointments.size() > 0) {
            Errors.showErrorDialog("Cannot delete customer with appointments. Please delete these appointments first.");
            return 0;
        }
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirm deletion.");
        alert.setContentText("Are you sure you want to delete the customer: " + customer.getCustomerName() + "?");
        alert.showAndWait();
        if (alert.getResult() != ButtonType.OK) {
            return 0;
        }
        int rowsAffected = CustomerStore.delete(customer.getCustomerId());
        if (rowsAffected > 0) {
            fetchAll();
        }
        return rowsAffected;
    }

    /**
     * Delete Appointment.
     *
     * @param appointment the appointment to delete.
     * @throws SQLException
     */
    public static int deleteAppointment(Appointment appointment) throws SQLException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirm deletion.");
        alert.setContentText("Are you sure you want to delete the appointment: " + appointment.getTitle() + "?");
        alert.showAndWait();
        if (alert.getResult() != ButtonType.OK) {
            return 0;
        }
        int rowsAffected = AppointmentStore.delete(appointment.getAppointmentId());
        if (rowsAffected > 0) {
            fetchAll();
        }
        return rowsAffected;
    }

    /**
     * Search for customers by given query.
     *
     * @param query sql query string.
     * @throws SQLException
     */
    public static ObservableList<Appointment> searchByAppointmentTitle(String query) throws SQLException {
        ObservableList<Appointment> appointments = AppointmentStore.selectByTitle(query);
        return appointments;
    }

    /**
     * Create a new appointment.
     * Lambda function used here as a callback. There is another similar function but different in updateAppointment,
     * this makes a good use case for create a lambda function.
     *
     * @param event       the event to change the scene.
     * @param appointment the appointment to create.
     * @throws SQLException
     * @throws IOException
     */
    public static void createAppointment(ActionEvent event, Appointment appointment) throws SQLException, IOException {
        // remember you need to check if the appointment clashes with another appointment.
        // if it does, then you need to throw an error.
        if (DateTime.isBeforeCurrentTime(appointment.getStart())) {
            Errors.showErrorDialog("Appointment cannot be in the past.");
            return;
        }
        if (DateTime.isEndTimeBeforeStartTime(appointment.getStart(), appointment.getEnd())) {
            Errors.showErrorDialog("Appointment cannot end before it starts.");
            return;
        }
        if (DateTime.isMoreThanOneDay(appointment.getStart(), appointment.getEnd())) {
            Errors.showErrorDialog("Appointment may not span more than one day.");
            return;
        }
        if (!DateTime.isBetweenBusinessHours(appointment.getStart(), appointment.getEnd())) {
            Errors.showErrorDialog("Appointment cannot be outside of business hours: 8AM-10PM EST.");
            return;
        }
        ObservableList<Appointment> filteredList = appointments.filtered(a -> DateTime.areIntervalsOverlapping(appointment.getStart(), appointment.getEnd(), a.getStart(), a.getEnd()));
        if (filteredList.size() > 0) {
            Errors.showErrorDialog("Appointment clashes with another appointment. Please choose another time.");
            return;
        }
        if (appointment.getStart() == appointment.getEnd()) {
            Errors.showErrorDialog("Appointment must be at least 15 minutes.");
            return;
        }
        int rowsAffected = AppointmentStore.insert(appointment);
        if (rowsAffected == 0) {
            Errors.showErrorDialog("An error has occurred trying to save to the database.");
        }
        fetchAll();
        Utils.changeScene(event, "central-view");
    }

    /**
     * Update an appointment.
     * Lambda function used here as a callback. There is another similar function but different in createAppointment,
     * this makes a good use case for create a lambda function.
     *
     * @param event       the event to change the scene.
     * @param appointment the appointment to update.
     * @throws SQLException
     * @throws IOException
     */
    public static void updateAppointment(ActionEvent event, Appointment appointment) throws SQLException, IOException {
        if (DateTime.isBeforeCurrentTime(appointment.getStart())) {
            Errors.showErrorDialog("Appointment cannot be in the past.");
            return;
        }
        if (DateTime.isEndTimeBeforeStartTime(appointment.getStart(), appointment.getEnd())) {
            Errors.showErrorDialog("Appointment cannot end before it starts.");
            return;
        }
        if (appointment.getStart() == appointment.getEnd()) {
            Errors.showErrorDialog("Appointment must be at least 15 minutes.");
            return;
        }
        if (DateTime.isMoreThanOneDay(appointment.getStart(), appointment.getEnd())) {
            Errors.showErrorDialog("Appointment may not span more than one day.");
            return;
        }
        if (!DateTime.isBetweenBusinessHours(appointment.getStart(), appointment.getEnd())) {
            Errors.showErrorDialog("Appointment cannot be outside of business hours: 8AM-10PM EST.");
            return;
        }



        ObservableList<Appointment> filteredList = appointments.filtered(a -> (a.getAppointmentId() != appointment.getAppointmentId()) &&
                DateTime.areIntervalsOverlapping(appointment.getStart(), appointment.getEnd(), a.getStart(), a.getEnd()));
        if (filteredList.size() > 0) {
            Errors.showErrorDialog("Appointment clashes with another appointment. Please choose another time.");
            return;
        }


        int rowsAffected = AppointmentStore.update(appointment);
        if (rowsAffected == 0) {
            Errors.showErrorDialog("An error has occurred trying to save to the database.");
        }
        fetchAll();
        Utils.changeScene(event, "central-view");
    }

    /**
     * Create a new customer.
     *
     * @param event    the event to change the scene.
     * @param customer the customer to create.
     * @throws SQLException
     * @throws IOException
     */
    public static void createCustomer(ActionEvent event, Customer customer) throws SQLException, IOException {
        int rowsAffected = CustomerStore.insert(customer);
        if (rowsAffected == 0) {
            Errors.showErrorDialog("An error has occurred trying to save to the database.");
        }
        fetchAll();
        Utils.changeScene(event, "central-view");
    }

    /**
     * Update a customer.
     *
     * @param event    the event to change the scene.
     * @param customer the customer to update.
     * @throws SQLException
     * @throws IOException
     */
    public static void updateCustomer(ActionEvent event, Customer customer) throws SQLException, IOException {
        int rowsAffected = CustomerStore.update(customer);
        if (rowsAffected == 0) {
            Errors.showErrorDialog("An error has occurred trying to save to the database.");
        }
        fetchAll();
        Utils.changeScene(event, "central-view");
    }

    /**
     * Get a country by id.
     *
     * @param countryId
     * @return country
     */
    public static Country getCountryById(int countryId) {
        for (Country country : countries) {
            if (country.getCountryId() == countryId) {
                return country;
            }
        }
        return null;
    }

    /**
     * Get a division by id.
     *
     * @param divisionId
     * @return division
     */
    public static Division getDivisionById(int divisionId) {
        for (Division division : divisions) {
            if (division.getDivisionId() == divisionId) {
                return division;
            }
        }
        return null;
    }

    /**
     * Get an appointment by id.
     *
     * @param appointmentId
     * @return appointment
     */
    public static Appointment getAppointmentById(int appointmentId) {
        for (Appointment appointment : appointments) {
            if (appointment.getAppointmentId() == appointmentId) {
                return appointment;
            }
        }
        return null;
    }

    /**
     * Get a contact by id.
     *
     * @param contactId
     * @return contact
     */
    public static Contact getContactById(int contactId) {
        for (Contact contact : contacts) {
            if (contact.getContactId() == contactId) {
                return contact;
            }
        }
        return null;
    }

    /**
     * Get a customer by id.
     *
     * @param customerId
     * @return customer
     */
    public static Customer getCustomerById(int customerId) {
        for (Customer customer : customers) {
            if (customer.getCustomerId() == customerId) {
                return customer;
            }
        }
        return null;
    }

    /**
     * Get a user by id.
     *
     * @param userId
     * @return user
     */
    public static User getUserById(int userId) {
        for (User user : users) {
            if (user.getUserId() == userId) {
                return user;
            }
        }
        return null;
    }

    /**
     * Get all appointments for a given month.
     * Lambda used here as a first class function for filtering.
     *
     * @param month the month to get appointments for.
     * @return an observable list of appointments.
     */
    public static ObservableList<MonthlyTypeReport> getAppointmentsByMonth(String month) {
        return monthlyTypeReports.filtered(m -> m.getMonth().equals(month));
    }

    /**
     * Get all appointments for a given contact.
     * Lambda used here as a first class function for filtering.
     *
     * @param contact the contact to get appointments for.
     * @return an observable list of appointments.
     */
    public static ObservableList<Appointment> getAppointmentsByContact(Contact contact) {
        return appointments.filtered(a -> a.getContactId() == contact.getContactId());
    }

    /**
     * Check if the current user has any appointments within 15 minutes.
     * Lambda used here as a first class function for filtering.
     *
     * @return the appointment the current user has within 15 minutes.
     */
    public static ObservableList<Appointment> getCurrentUserAppointmentsWithin15Minutes() {
        return appointments.filtered(a -> a.getUserId() == currentUser.getUserId() &&
                DateTime.isWithin15Minutes(a.getStart()));
    }

    /**
     * Logs current user out of the app.
     */
    public static void logout() {
        currentUser = null;
    }
}
