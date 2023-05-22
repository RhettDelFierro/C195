package rhettdelfierro.c195.dao;

import javafx.collections.ObservableList;
import rhettdelfierro.c195.models.User;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * This class is used to interact with the users table in the database.
 */
public class UserStore {
    /**
     * This method is used to select a user by their ID.
     * @param username username of the user to be selected.
     * @param password password of the user to be selected.
     * @return User object.
     * @throws SQLException Exception thrown if the database query fails.
     */
    public static User login(String username, String password) throws SQLException {
        String sql = "SELECT * FROM USERS WHERE User_Name = ? AND Password = ?";
        JDBC.openConnection();
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setString(1, username);
        ps.setString(2, password);
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            return new User(
                    rs.getInt("User_ID"),
                    rs.getString("User_Name"),
                    rs.getString("Password")
            );
        } else {
            return null;
        }
    }

    /**
     * This method is used to fetch all users from the database.
     * @return ObservableList of User objects.
     * @throws SQLException Exception thrown if the database query fails.
     */
    public static ObservableList<User> fetchAll() throws SQLException {
        String sql = "SELECT * FROM USERS";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        ObservableList<User> users = javafx.collections.FXCollections.observableArrayList();
        while (rs.next()) {
            users.add(new User(
                    rs.getInt("User_ID"),
                    rs.getString("User_Name"),
                    "****"
            ));
        }
        return users;
    }
}
