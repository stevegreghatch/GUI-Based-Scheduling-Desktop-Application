package DBAccess;

import DBUtility.DBConnection;
import Model.Users;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DBUsers{
    /**
     *
     * @return observable list of all user data from database
     * @throws SQLException if exception has occurred
     */
    public static ObservableList<Users> getAllUsers() throws SQLException {
        ObservableList<Users> usersObservableList = FXCollections.observableArrayList();
        String sql = "SELECT * from users";
        PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            int userID = rs.getInt("User_ID");
            String userName = rs.getString("User_Name");
            String userPassword = rs.getString("Password");
            Users user = new Users(userID, userName, userPassword);
            usersObservableList.add(user);
        }
        return usersObservableList;
    }
}
