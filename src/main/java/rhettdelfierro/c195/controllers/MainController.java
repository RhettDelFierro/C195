package rhettdelfierro.c195.controllers;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import rhettdelfierro.c195.helper.Errors;
import rhettdelfierro.c195.helper.ListManagement;
import rhettdelfierro.c195.helper.Utils;
import rhettdelfierro.c195.models.Appointment;
import rhettdelfierro.c195.models.Customer;
import rhettdelfierro.c195.models.Inventory; //Inventory will be the JDBC probably.

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * MainController Initial screen of the app.
 *
 * RUNTIME ERROR: parseInt() would throw an IOException here, but we guard against that by using the checkValidInt
 *                helper functions.
 */
public class MainController implements Initializable {
    Stage stage;
    Parent scene;

    @FXML
    private TableColumn<Appointment, Integer> partIdCol;

    @FXML
    private TableColumn<Appointment, String> partNameCol;

    @FXML
    private TableColumn<Appointment, Integer> partInventoryLvlCol;

    @FXML
    private TableColumn<Appointment, Double> partPriceCol;

    @FXML
    private TableView<Appointment> partsTableView;

    @FXML
    private TableColumn<Customer, Integer> productIdCol;

    @FXML
    private TableColumn<Customer, String> productNameCol;

    @FXML
    private TableColumn<Customer, Integer> productInventoryLvlCol;

    @FXML
    private TableColumn<Customer, Double> productPriceCol;

    @FXML
    private TableView<Customer> productsTableView;

    @FXML
    private TextField searchAppointmentTxt;

    @FXML
    private TextField searchCustomerTxt;

    /**
     * Helper method to change scenes
     *
     * @param event Action event
     * @throws IOException and IOException that bubbles up.
     */
    @FXML
    void onActionAddAppointment(ActionEvent event) throws IOException {
        Utils.changeScene(event, "add-part");
    }

    /**
     * Helper method to change scenes
     *
     * @param event Action event
     * @throws IOException an IOException that bubbles up.
     */
    @FXML
    void onActionAddCustomer(ActionEvent event) throws IOException {
        Helpers.changeScene(event, "add-product");
    }

    /**
     * Deletes parts.
     *
     * @param event Action event
     */
    @FXML
    void onActionDeleteAppointment(ActionEvent event) {
        Appointment partForDeletion = partsTableView.getSelectionModel().getSelectedItem();
        if (partForDeletion == null) {
            Errors.showErrorDialog("There is no part selected for deletion.");
        } else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirm deletion.");
            alert.setContentText("Are you sure you want to delete the part: " + partForDeletion.getName() + "?");
            alert.showAndWait();
            if (alert.getResult() != ButtonType.OK) {
                return;
            }
            Inventory.deleteAppointment(partsTableView.getSelectionModel().getSelectedItem());
        }

    }

    /**
     * Deletes product if there are no associated parts.
     * @param event Event object.
     */
    @FXML
    void onActionDeleteCustomer(ActionEvent event) {
        Customer productForDeletion = productsTableView.getSelectionModel().getSelectedItem();
        if (productForDeletion == null) {
            Errors.showErrorDialog("There is no product selected for deletion.");
        } else if (productForDeletion.getAllAssociatedAppointments().size() == 0) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirm deletion.");
            alert.setContentText("Are you sure you want to delete the product: " + productForDeletion.getName() + "?");
            alert.showAndWait();
            if (alert.getResult() != ButtonType.OK) {
                return;
            }
            Inventory.deleteCustomer(productsTableView.getSelectionModel().getSelectedItem());
        } else {
            Errors.showErrorDialog("You cannot delete a product that has associated parts.");
        }
    }

    /**
     * Will search for products by name or id and populate table.
     * @param event Event object.
     */
    @FXML
    void onActionFilterAppointment(ActionEvent event) {
        String searchText = searchAppointmentTxt.getText();
        if (searchText.isEmpty()) {
            partsTableView.setItems(Inventory.getAllAppointments());
        } else {
            ObservableList<Appointment> results = Helpers.searchAppointments(searchText);
            if (results.isEmpty()) {
                Errors.showErrorDialog("No matching parts found for your search term: " + searchText + ".");
            } else {
                partsTableView.setItems(results);
            }
        }
    }

    /**
     * Will search for products by name or id and populate table.
     * @param event Event object.
     */
    @FXML
    void onActionFilterCustomer(ActionEvent event) {
        String searchText = searchCustomerTxt.getText();
        if (searchText.isEmpty()) {
            productsTableView.setItems(ListManagement.getAllCustomers());
        } else {
            ObservableList<Customer> results = ListManagement.searchCustomers(searchText);
            if (results.isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Program error.");
                alert.setContentText("No matching products found for your search term: " + searchText + ".");
                alert.showAndWait();
            } else {
                productsTableView.setItems(results);
            }
        }
    }

    /**
     * Exits program.
     *
     * @param event Action event
     */
    @FXML
    void onActionExitProgram(ActionEvent event) {
        Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        stage.close();
    }

    /**
     * Helper method to change scenes with selected Appointment loaded.
     *
     * @param event Action event
     * @throws IOException an IOEXception that will throw if the resource fails to fetch.
     */
    @FXML
    public void onActionModifyAppointment(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Helpers.class.getResource("/rhettdelfierro/c195/modify-part.fxml"));
        loader.load();
        ModifyAppointment controller = loader.getController();
        controller.sendAppointment(partsTableView.getSelectionModel().getSelectedItem());

        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        Parent scene = loader.getRoot();
        stage.setScene(new Scene(scene, 500, 530));
        stage.showAndWait();
    }

    /**
     * Helper method to change scenes with selected Customer loaded.
     *
     * RUNTIME ERROR: at javafx.fxml/javafx.fxml.FXMLLoader.load(FXMLLoader.java:2516)
     * at rhettdelfierro.c195/rhettdelfierro.c195.controllers.MainController.onActionModifyCustomer(MainController.java:218)
     *
     * I gave the wrong filepath for the resource and this caused a runtime error.
     * I resolved this error by correcting the resource path.
     *
     * @param event Action event
     * @throws IOException an IOEXCeption that throws if the resource fails to fetch.
     */
    @FXML
    public void onActionModifyCustomer(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Helpers.class.getResource("/rhettdelfierro/c195/modify-product.fxml"));
        loader.load();
        ModifyCustomer controller = loader.getController();
        controller.sendCustomer(productsTableView.getSelectionModel().getSelectedItem());

        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        Parent scene = loader.getRoot();
        stage.setScene(new Scene(scene, 1000, 600));
        stage.showAndWait();
    }

    /**
     * Initializes the controller class.
     * Populates both tables with init data primarily.
     *
     * @param url URL
     * @param resourceBundle resource bundle folder.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        partsTableView.setItems(Inventory.getAllAppointments());

        partIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        partNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        partInventoryLvlCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        partPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));

        productsTableView.setItems(Inventory.getAllCustomers());
        productIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        productNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        productInventoryLvlCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        productPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));

//        partsTableView.getSelectionModel().select(Inventory.lookupAppointment(5));
    }
}