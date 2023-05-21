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

    @FXML
    void onActionCountryCombo(ActionEvent event) {
        Country country = countryCombo.getSelectionModel().getSelectedItem();
        divisionCombo.setItems(ListManagement.getDivisionsByCountryId(country.getCountryId()));
    }

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

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        countryCombo.setItems(ListManagement.getAllCountries());
        divisionCombo.setItems(ListManagement.getAllDivisions());
    }
}
