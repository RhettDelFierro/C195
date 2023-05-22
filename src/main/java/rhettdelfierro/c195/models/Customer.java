package rhettdelfierro.c195.models;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * This class is used to create Customer objects.
 */
public class Customer {
    private ObservableList<Appointment> appointments = FXCollections.observableArrayList();
    private Division division = null;
    private int customerId;
    private String customerName;
    private String address;
    private String postalCode;
    private String phone;
    private int divisionId;
    private String divisionName;

    public Customer(int customerId, String customerName, String address, String postalCode, String phone, int divisionId) {
        this.customerId = customerId;
        this.customerName = customerName;
        this.address = address;
        this.postalCode = postalCode;
        this.phone = phone;
        this.divisionId = divisionId;
    }

    public Customer(String customerName, String address, String postalCode, String phone, int divisionId) {
        this.customerName = customerName;
        this.address = address;
        this.postalCode = postalCode;
        this.phone = phone;
        this.divisionId = divisionId;
    }

    /**
     * Customer ID getter
     *
     * @return the customerId
     */
    public int getCustomerId() {
        return customerId;
    }

    /**
     * Sets the ID of the customer.
     *
     * @param customerId the new customer ID
     */
    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    /**
     * Returns the name of the customer.
     *
     * @return the customer name
     */
    public String getCustomerName() {
        return customerName;
    }

    /**
     * Sets the name of the customer.
     *
     * @param customerName the new customer name
     */
    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    /**
     * Returns the address of the customer.
     *
     * @return the customer address
     */
    public String getAddress() {
        return address;
    }

    /**
     * Sets the address of the customer.
     *
     * @param address the new customer address
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * Returns the postal code of the customer.
     *
     * @return the customer postal code
     */
    public String getPostalCode() {
        return postalCode;
    }

    /**
     * Sets the postal code of the customer.
     *
     * @param postalCode the new customer postal code
     */
    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    /**
     * Returns the phone number of the customer.
     *
     * @return the customer phone number
     */
    public String getPhone() {
        return phone;
    }

    /**
     * Sets the phone number of the customer.
     *
     * @param phone the new customer phone number
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }

    /**
     * Returns the division ID of the customer.
     *
     * @return the division ID
     */
    public int getDivisionId() {
        return divisionId;
    }

    /**
     * Sets the division ID of the customer.
     *
     * @param divisionId the new division ID
     */
    public void setDivisionId(int divisionId) {
        this.divisionId = divisionId;
    }

    /**
     * Appointments getter
     *
     * @return the appointments
     */
    public ObservableList<Appointment> getAppointments() {
        return appointments;
    }

    /**
     * Appointments setter
     *
     * @param appointments ObservableList<Appointment> the appointments to set
     */
    public void setAppointments(ObservableList<Appointment> appointments) {
        this.appointments = appointments;
    }

    /**
     * Adds appointment to list.
     *
     * @param appointment the appointment to add
     */
    public void addAppointment(Appointment appointment) {
        appointments.add(appointment);
    }

    /**
     * Returns the division of the customer.
     *
     * @return the division
     */
    public Division getDivision() {
        return division;
    }

    /**
     * Sets the division of the customer.
     *
     * @param division the new division
     */
    public void setDivision(Division division) {
        this.division = division;
    }

    /**
     * Return a string representation of the customer.
     * @return a string representation of the customer.
     */
    @Override
    public String toString() {
        return customerName;
    }

    /**
     * get division name
     * @return division name
     */
    public String getDivisionName() {
        return divisionName;
    }

    /**
     * set division name
     * @param divisionName division name
     */
    public void setDivisionName(String divisionName) {
        this.divisionName = divisionName;
    }
}
