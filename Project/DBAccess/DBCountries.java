package DBAccess;

import DBUtility.DBConnection;
import Model.Countries;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.sql.*;

public class DBCountries {
    /**
     *
     * @return observable list of all country data from database
     * @throws SQLException if exception has occurred
     */
    public static ObservableList<Countries> getAllCountries() throws SQLException {
        ObservableList<Countries> countriesObservableList = FXCollections.observableArrayList();
        String sql = "SELECT * from countries";
        PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            int countryID = rs.getInt("Country_ID");
            String countryName = rs.getString("Country");
            Countries country = new Countries(countryID, countryName);
            countriesObservableList.add(country);
        }
        return countriesObservableList;
    }
}
