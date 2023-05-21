package rhettdelfierro.c195.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import rhettdelfierro.c195.helper.ListManagement;
import rhettdelfierro.c195.models.*;

import java.net.URL;
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
        countryCombo.setValue(ListManagement.getCountryById(division.getCountryId()));
        divisionCombo.setValue(ListManagement.getDivisionById(customer.getDivisionId()));
    }

    @FXML
    void onActionCountryCombo(ActionEvent event) {
        Country country = countryCombo.getSelectionModel().getSelectedItem();
        divisionCombo.setItems(ListManagement.getDivisionsByCountryId(country.getCountryId()));
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }
}
