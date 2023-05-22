package rhettdelfierro.c195.models;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * This class is used to create Division objects.
 */
public class Division {
    private ObservableList<Customer> customers = FXCollections.observableArrayList();
    private int divisionId;
    private String division;
    private int countryId;
    private String createDate;

    public Division(int divisionId, String division, int countryId) {
        this.divisionId = divisionId;
        this.division = division;
        this.countryId = countryId;
    }

    /**
     * Gets the divisionId.
     *
     * @return the divisionId
     */
    public int getDivisionId() {
        return divisionId;
    }

    /**
     * Sets the divisionId.
     *
     * @param divisionId the divisionId to set
     */
    public void setDivisionId(int divisionId) {
        this.divisionId = divisionId;
    }

    /**
     * Gets the division.
     *
     * @return the division
     */
    public String getDivision() {
        return division;
    }

    /**
     * Sets the division.
     *
     * @param division the division to set
     */
    public void setDivision(String division) {
        this.division = division;
    }

    /**
     * Gets the countryId.
     *
     * @return the countryId
     */
    public int getCountryId() {
        return countryId;
    }

    /**
     * Sets the countryId.
     *
     * @param countryId the countryId to set
     */
    public void setCountryId(int countryId) {
        this.countryId = countryId;
    }

    /**
     * Customers getter
     *
     * @return the customers
     */
    public ObservableList<Customer> getCustomers() {
        return customers;
    }

    /**
     * Adds customer to list.
     *
     * @param customer the customer to add
     */
    public void addCustomer(Customer customer) {
        customers.add(customer);
    }

    /**
     * Returns a string representation of the division.
     * @return the division
     */
    @Override
    public String toString() {
        return division;
    }
}
