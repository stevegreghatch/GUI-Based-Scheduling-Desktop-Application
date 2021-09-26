package DBAccess;

import DBUtility.DBConnection;
import Model.FirstLevelDivisions;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DBFirstLevelDivisions {
    /**
     *
     * @return observable list of all first-level division data from database
     * @throws SQLException if exception has occurred
     */
    public static ObservableList<FirstLevelDivisions> getAllFirstLevelDivisions() throws SQLException {
        ObservableList<FirstLevelDivisions> firstLevelDivisionsObservableList = FXCollections.observableArrayList();
        String sql = "SELECT * from first_level_divisions";
        PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            int divisionID = rs.getInt("Division_ID");
            String divisionName = rs.getString("Division");
            int country_ID = rs.getInt("COUNTRY_ID");
            FirstLevelDivisions firstLevelDivision = new FirstLevelDivisions(divisionID, divisionName, country_ID);
            firstLevelDivisionsObservableList.add(firstLevelDivision);
        }
        return firstLevelDivisionsObservableList;
    }
}
