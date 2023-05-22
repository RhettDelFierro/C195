package rhettdelfierro.c195.dao;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import rhettdelfierro.c195.models.Contact;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * This class is used to interact with the contacts table in the database.
 */
public class ContactStore {
    /**
     * This method is used to fetch all contacts from the database.
     * @return ObservableList of Contact objects.
     * @throws SQLException
     */
    public static ObservableList<Contact> fetchAll() throws SQLException {
        String sql = "SELECT * FROM CONTACTS";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        ObservableList<Contact> contacts = FXCollections.observableArrayList();
        while (rs.next()) {
            contacts.add(new Contact(
                    rs.getInt("Contact_ID"),
                    rs.getString("Contact_Name"),
                    rs.getString("Email")
            ));
        }
        return contacts;
    }

    /**
     * This method is used to select a contact by their ID.
     * @param contactId ID of the contact to be selected.
     * @return Contact object.
     * @throws SQLException Exception thrown if the database query fails.
     */
    public static Contact selectContactById(int contactId) throws SQLException {
        String sql = "SELECT * FROM CONTACTS WHERE Contact_ID = ?";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setInt(1, contactId);
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            return new Contact(
                    rs.getInt("Contact_ID"),
                    rs.getString("Contact_Name"),
                    rs.getString("Email")
            );
        } else {
            return null;
        }
    }
}
