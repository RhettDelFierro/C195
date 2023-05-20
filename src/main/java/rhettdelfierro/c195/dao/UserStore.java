package rhettdelfierro.c195.dao;

import rhettdelfierro.c195.models.User;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserStore {
    public static User currentUser = null;
    public static User login(String username, String password) throws SQLException {
        String sql = "SELECT * FROM USERS WHERE User_Name = ? AND Password = ?";
        JDBC.openConnection();
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setString(1, username);
        ps.setString(2, password);
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            currentUser = new User(
                    rs.getInt("User_ID"),
                    rs.getString("User_Name"),
                    rs.getString("Password")
            );
            return currentUser;
        } else {
            return null;
        }
    }
}
