package rhettdelfierro.c195.models;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Customer {
    private ObservableList<Appointment> appointments = FXCollections.observableArrayList();
    private int customerId;
    private String customerName;
    private String address;
    private String postalCode;
    private String phone;
    private int divisionId;

    public Customer(int customerId, String customerName, String address, String postalCode, String phone, int divisionId) {
        this.customerId = customerId;
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
     * Appointments getter
     *
     * @return the appointments
     */
    public ObservableList<Appointment> getAppointments() {
        return appointments;
    }

    /**
     *  Appointments setter
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


}
