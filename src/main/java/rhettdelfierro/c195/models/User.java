package rhettdelfierro.c195.models;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class User {
    private ObservableList<Appointment> appointments = FXCollections.observableArrayList();
    private int userId;
    private String userName;
    private String password;

    public User(int userId, String userName, String password) {
        this.userId = userId;
        this.userName = userName;
        this.password = password;
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
     * Adds appointment to list.
     *
     * @param appointment the appointment to add
     */
    public void addAppointment(Appointment appointment) {
        appointments.add(appointment);
    }
}
