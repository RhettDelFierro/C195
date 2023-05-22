package rhettdelfierro.c195.models;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * This class is used to create Contact objects.
 */
public class Contact {
    private ObservableList<Appointment> appointments = FXCollections.observableArrayList();
    private int contactId;
    private String contactName;
    private String email;

    public  Contact(int contactId, String contactName, String email) {
        this.contactId = contactId;
        this.contactName = contactName;
        this.email = email;
    }

    /**
     * Sets the list of appointments.
     * @param appointments an ObservableList of Appointment objects.
     */
    public void setAppointments(ObservableList<Appointment> appointments) {
        this.appointments = appointments;
    }

    /**
     * Returns the contact's ID.
     * @return an integer representing the contact's ID.
     */
    public int getContactId() {
        return contactId;
    }

    /**
     * Sets the contact's ID.
     * @param contactId an integer representing the contact's ID.
     */
    public void setContactId(int contactId) {
        this.contactId = contactId;
    }

    /**
     * Returns the contact's name.
     * @return a String representing the contact's name.
     */
    public String getContactName() {
        return contactName;
    }

    /**
     * Sets the contact's name.
     * @param contactName a String representing the contact's name.
     */
    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    /**
     * Returns the contact's email.
     * @return a String representing the contact's email.
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets the contact's email.
     * @param email a String representing the contact's email.
     */
    public void setEmail(String email) {
        this.email = email;
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
     * Returns a String representation of the contact.
     * @return a String representing the contact.
     */
    @Override
    public String toString() {
        return contactName;
    }
}
