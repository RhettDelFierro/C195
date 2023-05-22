package rhettdelfierro.c195.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import rhettdelfierro.c195.helper.Errors;
import rhettdelfierro.c195.helper.ListManagement;
import rhettdelfierro.c195.helper.Utils;
import rhettdelfierro.c195.models.*;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
/**
 * Controller for Modify Customer page.
 */
public class ModifyCustomerController implements Initializable {
    @FXML
    private TextField customerIdTxt;
    @FXML
    private TextField nameTxt;
    @FXML
    private TextField addressTxt;
    @FXML
    private TextField phoneTxt;
    @FXML
    private ComboBox<Country> countryCombo;
    @FXML
    private ComboBox<Division> divisionCombo;
    @FXML
    private TextField postalCodeTxt;

    /**
     * Used to load the customer app when visiting the page from the main scren.
     * @param customer the customer chosen.
     */
    public void sendCustomer(Customer customer) {
        customerIdTxt.setText(String.valueOf(customer.getCustomerId()));
        nameTxt.setText(customer.getCustomerName());
        addressTxt.setText(customer.getAddress());
        phoneTxt.setText(customer.getPhone());
        postalCodeTxt.setText(customer.getPostalCode());
        Division division = ListManagement.getDivisionById(customer.getDivisionId());
        divisionCombo.setItems(ListManagement.getDivisionsByCountryId(division.getCountryId()));
        countryCombo.setValue(ListManagement.getCountryById(division.getCountryId()));
        divisionCombo.setValue(ListManagement.getDivisionById(customer.getDivisionId()));
    }

    /**
     * Action event handler for changing country.
     * This will update the division combo box to only show divisions from the selected country.
     * @param event the action event
     */
    @FXML
    void onActionCountryCombo(ActionEvent event) {
        Country country = countryCombo.getSelectionModel().getSelectedItem();
        divisionCombo.setItems(ListManagement.getDivisionsByCountryId(country.getCountryId()));
    }

    /**
     * Action event handler for changing division.
     * This will update the country combo box to show the country of the selected division.
     * @param event the action event
     */
    @FXML
    void onActionDivisionCombo(ActionEvent event) {
        Division division = divisionCombo.getSelectionModel().getSelectedItem();
        Country country = countryCombo.getSelectionModel().getSelectedItem();
        if (country == null || country.getCountryId() != division.getCountryId()) {
            countryCombo.setValue(ListManagement.getCountryById(division.getCountryId()));
        }
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
     * Action event handler for clicking the Save Button. This will save the updated customer to the database.
     *
     * @param event the action event
     * @throws SQLException a SQLException that bubbles up.
     * @throws IOException an IOException that bubbles up.
     */
    @FXML
    void onActionSaveCustomer(ActionEvent event) throws SQLException, IOException {
        if (countryCombo.getSelectionModel().getSelectedItem() == null) {
            Errors.showErrorDialog("Please select a country.");
            return;
        }
        if (divisionCombo.getSelectionModel().getSelectedItem() == null) {
            Errors.showErrorDialog("Please select a division.");
            return;
        }
        if (nameTxt.getText().isEmpty() || addressTxt.getText().isEmpty() || phoneTxt.getText().isEmpty() || postalCodeTxt.getText().isEmpty()) {
            Errors.showErrorDialog("Please ensure all text fields are filled out.");
            return;
        }

        String name = nameTxt.getText();
        String address = addressTxt.getText();
        String phone = phoneTxt.getText();
        String postalCode = postalCodeTxt.getText();
        Division division = divisionCombo.getSelectionModel().getSelectedItem();
        Customer customer = new Customer(name, address, postalCode, phone, division.getDivisionId());
        ListManagement.updateCustomer(event, customer);
    }

    /**
     * Initializes the controller class. Mainly used to populate the combo boxes.
     * @param url the url
     * @param rb the resource bundle
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        countryCombo.setItems(ListManagement.getAllCountries());
        divisionCombo.setItems(ListManagement.getAllDivisions());
    }
}
