package rhettdelfierro.c195.dao;

import javafx.collections.ObservableList;
import rhettdelfierro.c195.models.Division;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DivisionStore {
    public static ObservableList<Division> fetchAll() throws SQLException {
        String sql = "SELECT * FROM FIRST_LEVEL_DIVISIONS";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        ObservableList<Division> divisions = javafx.collections.FXCollections.observableArrayList();
        while (rs.next()) {
            divisions.add(new Division(
                    rs.getInt("Division_ID"),
                    rs.getString("Division"),
                    rs.getInt("Country_ID")
            ));
        }
        return divisions;
    }
    public static ObservableList<Division> selectByCountryId(int countryId) throws SQLException {
        String sql = "SELECT * FROM FIRST_LEVEL_DIVISIONS WHERE COUNTRY_ID = ?";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setInt(1, countryId);
        ResultSet rs = ps.executeQuery();
        ObservableList<Division> divisions = javafx.collections.FXCollections.observableArrayList();
        while (rs.next()) {
            divisions.add(new Division(
                    rs.getInt("Division_ID"),
                    rs.getString("Division"),
                    rs.getInt("Country_ID")
            ));
        }
        return divisions;
    }

    public static Division selectById(int divisionId) throws SQLException {
        String sql = "SELECT * FROM FIRST_LEVEL_DIVISIONS WHERE DIVISION_ID = ?";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setInt(1, divisionId);
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            return new Division(
                    rs.getInt("Division_ID"),
                    rs.getString("Division"),
                    rs.getInt("Country_ID")
            );
        }
        return null;
    }
}
