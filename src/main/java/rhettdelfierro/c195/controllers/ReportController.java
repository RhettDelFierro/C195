package rhettdelfierro.c195.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import rhettdelfierro.c195.helper.ListManagement;
import rhettdelfierro.c195.helper.Utils;
import rhettdelfierro.c195.models.Appointment;
import rhettdelfierro.c195.models.Contact;
import rhettdelfierro.c195.models.DivisionReport;
import rhettdelfierro.c195.models.MonthlyTypeReport;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class ReportController implements Initializable {

    @FXML
    private TableColumn<DivisionReport, String> divisionCol;
    @FXML
    private TableColumn<DivisionReport, Integer> divisionCountCol;
    @FXML
    private TableView<DivisionReport> divisionTable;
    //<-------------
    @FXML
    private ComboBox<String> monthCombo;
    @FXML
    private TableColumn<MonthlyTypeReport, String> monthCol;
    @FXML
    private TableColumn<MonthlyTypeReport, String> monthTypeCol;
    @FXML
    private TableColumn<MonthlyTypeReport, Integer> typeCountCol;
    @FXML
    private TableView<MonthlyTypeReport> typeTable;
    //<------------
    @FXML
    private ComboBox<Contact> contactCombo;
    @FXML
    private TableColumn<Appointment, Integer> appointmentIdCol;
    @FXML
    private TableColumn<Appointment, String> titleCol;
    @FXML
    private TableColumn<Appointment, String> appointmentTypeCol;
    @FXML
    private TableColumn<Appointment, String> descriptionCol;
    @FXML
    private TableColumn<Appointment, String> startDateCol;
    @FXML
    private TableColumn<Appointment, String> endDateCol;
    @FXML
    private TableColumn<Appointment, String> customerIdCol;
    @FXML
    private TableView<Appointment> appointmentsTable;
    ObservableList<String> months = FXCollections.observableArrayList();
    @FXML
    void onActionCancel(ActionEvent event) throws IOException {
        Utils.changeScene(event, "central-view");
    }

    @FXML
    void onActionContactCombo(ActionEvent event) {
        Contact contact = contactCombo.getSelectionModel().getSelectedItem();
        if (contact != null) {
            appointmentsTable.setItems(ListManagement.getAppointmentsByContact(contact));
        }
    }

    @FXML
    void onActionMonthCombo(ActionEvent event) {
        String month = monthCombo.getValue();
        if (month != null) {
            typeTable.setItems(ListManagement.getAppointmentsByMonth(month));
        }
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            ListManagement.fetchAll();
            months.addAll("January", "February", "March", "April", "May", "June", "July", "August", "September",
                    "October", "November", "December");
            monthCombo.setItems(months);
            monthCombo.setPromptText("Select Month");
            contactCombo.setItems(ListManagement.getAllContacts());
            contactCombo.setPromptText("Select Contact");
            appointmentsTable.setItems(ListManagement.getAllAppointments());
            appointmentIdCol.setCellValueFactory(new PropertyValueFactory<>("appointmentId"));
            titleCol.setCellValueFactory(new PropertyValueFactory<>("title"));
            appointmentTypeCol.setCellValueFactory(new PropertyValueFactory<>("type"));
            descriptionCol.setCellValueFactory(new PropertyValueFactory<>("description"));
            startDateCol.setCellValueFactory(new PropertyValueFactory<>("start"));
            endDateCol.setCellValueFactory(new PropertyValueFactory<>("end"));
            customerIdCol.setCellValueFactory(new PropertyValueFactory<>("customerId"));
            divisionTable.setItems(ListManagement.getAllDivisionReports());
            divisionCol.setCellValueFactory(new PropertyValueFactory<>("division"));
            divisionCountCol.setCellValueFactory(new PropertyValueFactory<>("count"));
            typeTable.setItems(ListManagement.getAllMonthlyTypeReports());
            monthCol.setCellValueFactory(new PropertyValueFactory<>("month"));
            monthTypeCol.setCellValueFactory(new PropertyValueFactory<>("type"));
            typeCountCol.setCellValueFactory(new PropertyValueFactory<>("count"));

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
