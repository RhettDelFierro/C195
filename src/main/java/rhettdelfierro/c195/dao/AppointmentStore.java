package rhettdelfierro.c195.dao;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import rhettdelfierro.c195.helper.DateTime;
import rhettdelfierro.c195.models.Appointment;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Contains methods for interacting with the database for Appointment objects.
 */
public class AppointmentStore {

    /**
     * Inserts new appointment into the database.
     * @param appointment the appointment to insert
     * @return the number of rows affected
     * @throws SQLException if there is an error executing the SQL query.
     */
    public static int insert(Appointment appointment) throws SQLException {
        String sql = "INSERT INTO APPOINTMENTS (Title, Description, Location, Type, Start, End, Customer_ID, User_ID, Contact_ID) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setString(1, appointment.getTitle());
        ps.setString(2, appointment.getDescription());
        ps.setString(3, appointment.getLocation());
        ps.setString(4, appointment.getType());
        ps.setString(5, DateTime.convertFromLocalToUTC(appointment.getStart()));
        ps.setString(6, DateTime.convertFromLocalToUTC(appointment.getEnd()));
        ps.setInt(7, appointment.getCustomerId());
        ps.setInt(8, appointment.getUserId());
        ps.setInt(9, appointment.getContactId());
        int rowsAffected = ps.executeUpdate();
        return rowsAffected;
    }

    /**
     * Updates an existing appointment in the database.
     * @param appointment the appointment to update
     * @return the number of rows affected
     * @throws SQLException if there is an error executing the SQL query.
     */
    public static int update(Appointment appointment) throws SQLException {
        String sql = "UPDATE APPOINTMENTS SET Title = ?, Description = ?, Location = ?, Type = ?, Start = ?, End = ?, Customer_ID = ?, User_ID = ?, Contact_ID = ? WHERE Appointment_ID = ?";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setString(1, appointment.getTitle());
        ps.setString(2, appointment.getDescription());
        ps.setString(3, appointment.getLocation());
        ps.setString(4, appointment.getType());
        ps.setString(5, DateTime.convertFromLocalToUTC(appointment.getStart()));
        ps.setString(6, DateTime.convertFromLocalToUTC(appointment.getEnd()));
        ps.setInt(7, appointment.getCustomerId());
        ps.setInt(8, appointment.getUserId());
        ps.setInt(9, appointment.getContactId());
        ps.setInt(10, appointment.getAppointmentId());
        int rowsAffected = ps.executeUpdate();
        return rowsAffected;
    }

    /**
     * Deletes an appointment from the database.
     * @param appointmentId the ID of the appointment to delete
     * @return the number of rows affected
     * @throws SQLException if there is an error executing the SQL query.
     */
    public static int delete(int appointmentId) throws SQLException {
        String sql = "DELETE FROM APPOINTMENTS WHERE Appointment_ID = ?";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setInt(1, appointmentId);
        int rowsAffected = ps.executeUpdate();
        return rowsAffected;
    }

    /**
     * Fetches all appointments from the database.
     * @return ObservableList<Appointment>
     * @throws SQLException if there is an error executing the SQL query.
     */
    public static ObservableList<Appointment> fetchAll() throws SQLException {
        String sql = "SELECT * FROM APPOINTMENTS";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        ObservableList<Appointment> appointments = FXCollections.observableArrayList();
        while (rs.next()) {
            appointments.add(new Appointment(
                    rs.getInt("Appointment_ID"),
                    rs.getString("Title"),
                    rs.getString("Description"),
                    rs.getString("Location"),
                    rs.getString("Type"),
                    DateTime.convertFromUTCToLocal(rs.getString("Start")),
                    DateTime.convertFromUTCToLocal(rs.getString("End")),
                    rs.getInt("Customer_ID"),
                    rs.getInt("User_ID"),
                    rs.getInt("Contact_ID")
            ));
        }
        return appointments;
    }

    /**
     * Fetches appointments by Customer_ID
     * @param customerId int
     * @return ObservableList<Appointment>
     */
    public static ObservableList<Appointment> selectByCustomerId(int customerId) throws SQLException {
        String sql = "SELECT * FROM APPOINTMENTS WHERE Customer_ID = ?";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setInt(1, customerId);
        ResultSet rs = ps.executeQuery();
        ObservableList<Appointment> appointments = FXCollections.observableArrayList();
        while (rs.next()) {
            appointments.add(new Appointment(
                    rs.getInt("Appointment_ID"),
                    rs.getString("Title"),
                    rs.getString("Description"),
                    rs.getString("Location"),
                    rs.getString("Type"),
                    DateTime.convertFromUTCToLocal(rs.getString("Start")),
                    DateTime.convertFromUTCToLocal(rs.getString("End")),
                    rs.getInt("Customer_ID"),
                    rs.getInt("User_ID"),
                    rs.getInt("Contact_ID")
            ));
        }
        return appointments;
    }

    /**
     * Performs simple SQL queries, no indices. Mainly for fetching week and monthly data.
     * @param sql String SQL query.
     * @return ObservableList<Appointment>
     */
    public static ObservableList<Appointment> selectBySimpleQuery(String sql) throws SQLException {
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        ObservableList<Appointment> appointments = FXCollections.observableArrayList();
        while (rs.next()) {
            appointments.add(new Appointment(
                    rs.getInt("Appointment_ID"),
                    rs.getString("Title"),
                    rs.getString("Description"),
                    rs.getString("Location"),
                    rs.getString("Type"),
                    DateTime.convertFromUTCToLocal(rs.getString("Start")),
                    DateTime.convertFromUTCToLocal(rs.getString("End")),
                    rs.getInt("Customer_ID"),
                    rs.getInt("User_ID"),
                    rs.getInt("Contact_ID")
            ));
        }
        return appointments;
    }

    /**
     * Fetches appointments by title
     * @param title Appointment title.
     * @return ObservableList<Appointment>
     */
    public static ObservableList<Appointment> selectByTitle(String title) throws SQLException {
        String sql = "SELECT * FROM APPOINTMENTS WHERE TITLE LIKE ?";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setString(1, "%" + title + "%");
        ResultSet rs = ps.executeQuery();
        ObservableList<Appointment> appointments = FXCollections.observableArrayList();
        while (rs.next()) {
            appointments.add(new Appointment(
                    rs.getInt("Appointment_ID"),
                    rs.getString("Title"),
                    rs.getString("Description"),
                    rs.getString("Location"),
                    rs.getString("Type"),
                    DateTime.convertFromUTCToLocal(rs.getString("Start")),
                    DateTime.convertFromUTCToLocal(rs.getString("End")),
                    rs.getInt("Customer_ID"),
                    rs.getInt("User_ID"),
                    rs.getInt("Contact_ID")
            ));
        }
        return appointments;
    }
}
