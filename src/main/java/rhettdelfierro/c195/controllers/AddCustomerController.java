package rhettdelfierro.c195.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import rhettdelfierro.c195.helper.Errors;
import rhettdelfierro.c195.helper.ListManagement;
import rhettdelfierro.c195.helper.Utils;
import rhettdelfierro.c195.models.Country;
import rhettdelfierro.c195.models.Customer;
import rhettdelfierro.c195.models.Division;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class AddCustomerController implements Initializable {

    @FXML
    private TextField appointmentIdTxt;
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
    @FXML
    void onActionCountryCombo(ActionEvent event) {
        Country country = countryCombo.getSelectionModel().getSelectedItem();
        divisionCombo.setItems(ListManagement.getDivisionsByCountryId(country.getCountryId()));
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
//        Country country = countryCombo.getSelectionModel().getSelectedItem();
        Customer customer = new Customer(name, address, postalCode, phone, division.getDivisionId());
        ListManagement.createCustomer(event, customer);
    }

    /**
     * Action event handler for clicking the Cancel Button. This will return to the main screen.
     *
     * @param event the action event
     * @throws IOException an IOException that bubbles up.
     */
    @FXML
    void onActionCancelUpdatePart(ActionEvent event) throws IOException {
        Utils.changeScene(event, "central-view");
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        countryCombo.setItems(ListManagement.getAllCountries());
    }
}
