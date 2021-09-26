package DBAccess;

import DBUtility.DBConnection;
import Model.Customers;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DBCustomers {
    /**
     *
     * @return observable list of all customer data from database
     * @throws SQLException if exception has occurred
     */
    public static ObservableList<Customers> getAllCustomers() throws SQLException {
        ObservableList<Customers> customersObservableList = FXCollections.observableArrayList();
        String sql = "SELECT * from customers";
        PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            int customerID = rs.getInt("Customer_ID");
            String customerName = rs.getString("Customer_Name");
            String customerAddress = rs.getString("Address");
            String customerPostalCode = rs.getString("Postal_Code");
            String customerPhone = rs.getString("Phone");
            int divisionID = rs.getInt("Division_ID");
            Customers customer = new Customers(customerID, customerName, customerAddress, customerPostalCode, customerPhone, divisionID);
            customersObservableList.add(customer);
        }
        return customersObservableList;
    }
}
