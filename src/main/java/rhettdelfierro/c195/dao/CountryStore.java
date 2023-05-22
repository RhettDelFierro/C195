package rhettdelfierro.c195.dao;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import rhettdelfierro.c195.models.Country;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * This class is used to interact with the countries table in the database.
 */
public class CountryStore {
    /**
     * This method is used to fetch all countries from the database.
     * @return ObservableList of Country objects.
     * @throws SQLException Exception thrown if the database query fails.
     */
    public static ObservableList<Country> fetchAll() throws SQLException {
        String sql = "SELECT * FROM COUNTRIES";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        ObservableList<Country> countries = FXCollections.observableArrayList();
        while (rs.next()) {
            countries.add(new Country(
                    rs.getInt("Country_ID"),
                    rs.getString("Country")
            ));
        }

        return countries;
    }
}
