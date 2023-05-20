package rhettdelfierro.c195.models;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Division {
    private ObservableList<Customer> customers = FXCollections.observableArrayList();
    private int divisionId;
    private String division;
    private String createDate;

    public Division(int divisionId, String division) {
        this.divisionId = divisionId;
        this.division = division;
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
}
