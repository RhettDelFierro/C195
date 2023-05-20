package rhettdelfierro.c195.models;

public class Appointment {
    private int appointmentId;
    private String title;
    private String description;
    private String location;
    private String type;
    private String start;
    private String end;
    private int customerId;
    private int userId;
    private int contactId;
    private String contactName;

    public Appointment(int appointmentId, String title, String description, String location, String type, String start, String end, int customerId, int userId, int contactId) {
        this.appointmentId = appointmentId;
        this.title = title;
        this.description = description;
        this.location = location;
        this.type = type;
        this.start = start;
        this.end = end;
        this.customerId = customerId;
        this.userId = userId;
        this.contactId = contactId;
    }

    public Appointment(String title, String description, String location, String type, String start, String end, int customerId, int userId, int contactId) {
        this.title = title;
        this.description = description;
        this.location = location;
        this.type = type;
        this.start = start;
        this.end = end;
        this.customerId = customerId;
        this.userId = userId;
        this.contactId = contactId;
    }

    /**
     * Returns the ID of the appointment.
     *
     * @return the appointment ID
     */
    public int getAppointmentId() {
        return appointmentId;
    }

    /**
     * Sets the ID of the appointment.
     *
     * @param appointmentId the new appointment ID
     */
    public void setAppointmentId(int appointmentId) {
        this.appointmentId = appointmentId;
    }

    /**
     * Returns the title of the appointment.
     *
     * @return the appointment title
     */
    public String getTitle() {
        return title;
    }

    /**
     * Sets the title of the appointment.
     *
     * @param title the new appointment title
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Returns the description of the appointment.
     *
     * @return the appointment description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets the description of the appointment.
     *
     * @param description the new appointment description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Returns the location of the appointment.
     *
     * @return the appointment location
     */
    public String getLocation() {
        return location;
    }

    /**
     * Sets the location of the appointment.
     *
     * @param location the new appointment location
     */
    public void setLocation(String location) {
        this.location = location;
    }

    /**
     * Returns the type of the appointment.
     *
     * @return the appointment type
     */
    public String getType() {
        return type;
    }

    /**
     * Sets the type of the appointment.
     *
     * @param type the new appointment type
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * Returns the start time of the appointment.
     *
     * @return the appointment start time
     */
    public String getStart() {
        return start;
    }

    /**
     * Sets the start time of the appointment.
     *
     * @param start the new appointment start time
     */
    public void setStart(String start) {
        this.start = start;
    }

    /**
     * Returns the end time of the appointment.
     *
     * @return the appointment end time
     */
    public String getEnd() {
        return end;
    }

    /**
     * Sets the end time of the appointment.
     *
     * @param end the new appointment end time
     */
    public void setEnd(String end) {
        this.end = end;
    }

    /**
     * Returns the customer ID associated with the appointment.
     *
     * @return the associated customer ID
     */
    public int getCustomerId() {
        return customerId;
    }

    /**
     * Sets the customer ID associated with the appointment.
     *
     * @param customerId the new associated customer ID
     */
    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    /**
     * Returns the user ID associated with the appointment.
     *
     * @return the associated user ID
     */
    public int getUserId() {
        return userId;
    }

    /**
     * Sets the user ID associated with the appointment.
     *
     * @param userId the new associated user ID
     */
    public void setUserId(int userId) {
        this.userId = userId;
    }

    /**
     * Returns the contact ID associated with the appointment.
     *
     * @return the associated contact ID
     */
    public int getContactId() {
        return contactId;
    }

    /**
     * Sets the contact ID associated with the appointment.
     *
     * @param contactId the new associated contact ID
     */
    public void setContactId(int contactId) {
        this.contactId = contactId;
    }

    /**
     * Returns the contact name associated with the appointment.
     *
     * @return the associated contact name
     */
    public String getContactName() {
        return contactName;
    }

    /**
     * Sets the contact name associated with the appointment.
     *
     * @param contactName the new associated contact name
     */
    public void setContactName(String contactName) {
        this.contactName = contactName;
    }
}
