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
import rhettdelfierro.c195.models.Contact;
import rhettdelfierro.c195.models.Customer;
import rhettdelfierro.c195.models.User;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

/**
 * MainController Initial screen of the app.
 *
 * RUNTIME ERROR: parseInt() would throw an IOException here, but we guard against that by using the checkValidInt
 *                helper functions.
 */
public class CentralController implements Initializable {
    Stage stage;
    Parent scene;

    @FXML
    private TableColumn<Appointment, Integer> appointmentIdCol;

    @FXML
    private TableColumn<Appointment, String> titleCol;

    @FXML
    private TableColumn<Appointment, String> descriptionCol;

    @FXML
    private TableColumn<Appointment, String> locationCol;

    @FXML
    private TableColumn<Appointment, Integer> contactIdCol;

    @FXML
    private TableColumn<Appointment, String> typeCol;

    @FXML
    private TableColumn<Appointment, String> startCol;

    @FXML
    private TableColumn<Appointment, String> endCol;

    @FXML
    private TableColumn<Appointment, Integer> customerIDCol;

    @FXML
    private TableColumn<Appointment, Integer> UserIdCol;

    @FXML
    private TableView<Appointment> appointmentTableView;

    @FXML
    private TableColumn<Customer, Integer> customerIdCol;

    @FXML
    private TableColumn<Customer, String> customerNameCol;

    @FXML
    private TableColumn<Customer, String> addressCol;

    @FXML
    private TableColumn<Customer, String> postalCodeCol;

    @FXML
    private TableColumn<Customer, String> phoneCol;

    @FXML
    private TableColumn<Customer, String> divisionCol;

    @FXML
    private TableView<Customer> customersTableView;

    @FXML
    private TextField searchAppointmentTxt;

    @FXML
    private TextField searchCustomerTitleTxt;

    /**
     * Helper method to change scenes
     *
     * @param event Action event
     * @throws IOException and IOException that bubbles up.
     */
    @FXML
    void onActionAddAppointment(ActionEvent event) throws IOException {
        Utils.changeScene(event, "add-appointment");
    }

    /**
     * Helper method to change scenes
     *
     * @param event Action event
     * @throws IOException an IOException that bubbles up.
     */
    @FXML
    void onActionAddCustomer(ActionEvent event) throws IOException {
        Utils.changeScene(event, "add-customer");
    }

    /**
     * Deletes parts.
     *
     * @param event Action event
     */
    @FXML
    void onActionDeleteAppointment(ActionEvent event) throws SQLException {
        Appointment partForDeletion = appointmentTableView.getSelectionModel().getSelectedItem();
        if (partForDeletion == null) {
            Errors.showErrorDialog("There is no part selected for deletion.");
        } else {
            ListManagement.deleteAppointment(appointmentTableView.getSelectionModel().getSelectedItem());
        }

    }

    /**
     * Deletes product if there are no associated parts.
     * @param event Event object.
     * @throws SQLException SQLException that will throw if the SQL fails.
     */
    @FXML
    void onActionDeleteCustomer(ActionEvent event) throws SQLException {
        Customer customerForDeletion = customersTableView.getSelectionModel().getSelectedItem();
        if (customerForDeletion == null) {
            Errors.showErrorDialog("There is no product selected for deletion.");
        }
        ListManagement.deleteCustomer(customersTableView.getSelectionModel().getSelectedItem());
    }

    /**
     * Will search for products by name or id and populate table.
     * @param event Event object.
     */
    @FXML
    void onActionFilterAppointment(ActionEvent event) throws SQLException {
        String searchText = searchAppointmentTxt.getText();
        if (searchText.isEmpty()) {
            appointmentTableView.setItems(ListManagement.getAllAppointments());
        } else {
            ObservableList<Appointment> results = ListManagement.searchByAppointmentTitle(searchText);
            if (results.isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Program error.");
                alert.setContentText("No matching appointments found for your search term: " + searchText + ".");
                alert.showAndWait();
            } else {
                appointmentTableView.setItems(results);
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
        loader.setLocation(Utils.class.getResource("/rhettdelfierro/c195/modify-appointment.fxml"));
        loader.load();
        ModifyAppointmentController controller = loader.getController();
        Appointment appointment = appointmentTableView.getSelectionModel().getSelectedItem();
        Contact contact = ListManagement.getContactById(appointment.getContactId());
        Customer customer = ListManagement.getCustomerById(appointment.getCustomerId());
        User user = ListManagement.getUserById(appointment.getUserId());
        controller.sendAppointment(appointment, contact, customer, user);

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
        loader.setLocation(Utils.class.getResource("/rhettdelfierro/c195/modify-customer.fxml"));
        loader.load();
        ModifyCustomerController controller = loader.getController();
        controller.sendCustomer(customersTableView.getSelectionModel().getSelectedItem());

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
        try {
            ListManagement.fetchAll();
            appointmentTableView.setItems(ListManagement.getAllAppointments());

            appointmentIdCol.setCellValueFactory(new PropertyValueFactory<>("appointmentId"));
            titleCol.setCellValueFactory(new PropertyValueFactory<>("title"));
            descriptionCol.setCellValueFactory(new PropertyValueFactory<>("description"));
            locationCol.setCellValueFactory(new PropertyValueFactory<>("location"));
            contactIdCol.setCellValueFactory(new PropertyValueFactory<>("contactId"));
            typeCol.setCellValueFactory(new PropertyValueFactory<>("type"));


            customersTableView.setItems(ListManagement.getAllCustomers());
            customerIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
            customerNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
            addressCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
            postalCodeCol.setCellValueFactory(new PropertyValueFactory<>("price"));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
//        partsTableView.getSelectionModel().select(Inventory.lookupAppointment(5));
    }
}