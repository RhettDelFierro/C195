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
     * Gets the user ID
     *
     * @return the user ID
     */
    public int getUserId() {
        return userId;
    }

    /**
     * Sets the user ID
     *
     * @param userId the user ID to set
     */
    public void setUserId(int userId) {
        this.userId = userId;
    }

    /**
     * Gets the user name
     *
     * @return the user name
     */
    public String getUserName() {
        return userName;
    }

    /**
     * Sets the user name
     *
     * @param userName the user name to set
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }

    /**
     * Gets the password
     *
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * Sets the password
     *
     * @param password the password to set
     */
    public void setPassword(String password) {
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

    /**
     * REturn a string representation of the user.
     * @return a string representation of the user.
     */
    @Override
    public String toString() {
        return userName;
    }
}
