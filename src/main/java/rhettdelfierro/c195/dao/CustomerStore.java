package rhettdelfierro.c195.dao;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import rhettdelfierro.c195.models.Appointment;
import rhettdelfierro.c195.models.Customer;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CustomerStore {
    public static ObservableList<Customer> customers = FXCollections.observableArrayList();
    public static int insert(String name, String address, String postalCode, String phone, int divisionId) throws SQLException {
        String sql = "INSERT INTO CUSTOMERS (Customer_Name, Address, Postal_Code, Phone, Division_ID) VALUES (?, ?, ?, ?, ?)";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setString(1, name);
        ps.setString(2, address);
        ps.setString(3, postalCode);
        ps.setString(4, phone);
        ps.setInt(5, divisionId);
        int rowsAffected = ps.executeUpdate();
        return rowsAffected;
    }

    public static int update(int customerId, String name, String address, String postalCode, String phone, int divisionId) throws SQLException {
        String sql = "UPDATE CUSTOMERS SET Customer_Name = ?, Address = ?, Postal_Code = ?, Phone = ?, Division_ID = ? WHERE Customer_ID = ?";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setString(1, name);
        ps.setString(2, address);
        ps.setString(3, postalCode);
        ps.setString(4, phone);
        ps.setInt(5, divisionId);
        ps.setInt(6, customerId);
        int rowsAffected = ps.executeUpdate();
        return rowsAffected;
    }

    public static int delete(int customerId) throws SQLException {
        String sql = "DELETE FROM CUSTOMERS WHERE Customer_ID = ?";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setInt(1, customerId);
        int rowsAffected = ps.executeUpdate();
        return rowsAffected;
    }

    public static void fetchAll() throws SQLException {
        String sql = "SELECT * FROM CUSTOMERS";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        ObservableList<Customer> customers = FXCollections.observableArrayList();
        while (rs.next()) {
            customers.add(new Customer(
                    rs.getInt("Customer_ID"),
                    rs.getString("Customer_Name"),
                    rs.getString("Address"),
                    rs.getString("Postal_Code"),
                    rs.getString("Phone"),
                    rs.getInt("Division_ID")
            ));
        }

        for (Customer customer : customers) {
            ObservableList<Appointment> appointments = AppointmentStore.selectByCustomerId(customer.getCustomerId());
            customer.setAppointments(appointments);
        }
    }

    public static ObservableList<Customer> select(int customerId) throws SQLException {
        String sql = "SELECT * FROM CUSTOMERS WHERE Customer_ID = ?";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setInt(1, customerId);
        ResultSet rs = ps.executeQuery();
        ObservableList<Customer> customers = FXCollections.observableArrayList();
        while (rs.next()) {
            customers.add(new Customer(
                    rs.getInt("Customer_ID"),
                    rs.getString("Customer_Name"),
                    rs.getString("Address"),
                    rs.getString("Postal_Code"),
                    rs.getString("Phone"),
                    rs.getInt("Division_ID")
            ));
        }
        for (Customer customer : customers) {
            ObservableList<Appointment> appointments = AppointmentStore.selectByCustomerId(customer.getCustomerId());
            customer.setAppointments(appointments);
        }
        return customers;
    }
}
