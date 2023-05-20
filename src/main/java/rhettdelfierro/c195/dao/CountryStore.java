package rhettdelfierro.c195.dao;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import rhettdelfierro.c195.models.Country;
import rhettdelfierro.c195.models.Division;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CountryStore {
    public static ObservableList<Country> countries = FXCollections.observableArrayList();

    public static void fetchAll() throws SQLException {
        String sql = "SELECT * FROM COUNTRIES";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        countries.clear();
        while (rs.next()) {
            countries.add(new Country(
                    rs.getInt("Country_ID"),
                    rs.getString("Country")
            ));
        }

        for (Country country : countries) {
            ObservableList<Division> divisions = DivisionStore.selectById(country.getId());
            country.setDivisions(divisions);
        }
    }
}
