package rhettdelfierro.c195.dao;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import rhettdelfierro.c195.models.Division;
import rhettdelfierro.c195.models.DivisionReport;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * This class is used to interact with the first_level_divisions table in the database.
 */
public class DivisionStore {
    /**
     * This method is used to fetch all divisions from the database.
     * @return ObservableList of Division objects.
     * @throws SQLException Exception thrown if the database query fails.
     */
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

    /**
     * This method is used to fetch all divisions from the database by country ID.
     * @param countryId ID of the country to fetch divisions for.
     * @return ObservableList of Division objects.
     * @throws SQLException Exception thrown if the database query fails.
     */
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

    /**
     * This method is used to fetch a division from the database by ID.
     * @param divisionId ID of the division to fetch.
     * @return Division object.
     * @throws SQLException Exception thrown if the database query fails.
     */
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

    /**
     * This method is used to get a count of instances each division appears for a customer.
     * Basically to track how many customers live in each State/Providence.
     * @return DivisionReport object.
     * @throws SQLException Exception thrown if the database query fails.
     */
    public static ObservableList<DivisionReport> getReports() throws SQLException {
        String sql = "SELECT fld.Division, COUNT(cus.Division_ID) AS Count FROM first_level_divisions AS fld LEFT JOIN customers AS cus ON fld.Division_ID = cus.Division_ID GROUP BY fld.Division;";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        ObservableList<DivisionReport> divisionReports = FXCollections.observableArrayList();
        while (rs.next()) {
            divisionReports.add(new DivisionReport(
                            rs.getString("DIVISION"),
                            rs.getInt("COUNT")
                    )
            );
        }
        return divisionReports;
    }
}
