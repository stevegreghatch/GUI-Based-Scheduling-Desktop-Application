package DBAccess;

import DBUtility.DBConnection;
import Model.Appointments;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.sql.*;
import java.time.LocalDateTime;

public class DBAppointments {
    /**
     *
     * @return observable list of all appointment data from database with timestamps converted to local time
     * @throws SQLException if exception has occurred
     */
    public static ObservableList<Appointments> getAllAppointments() throws SQLException {
        ObservableList<Appointments> appointmentsObservableList = FXCollections.observableArrayList();
        String sql = "SELECT * from appointments";
        PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            int appointmentID = rs.getInt("Appointment_ID");
            String appointmentTitle = rs.getString("Title");
            String appointmentDescription = rs.getString("Description");
            String appointmentLocation = rs.getString("Location");
            String appointmentType = rs.getString("Type");

            // times converted to local time of user
            LocalDateTime start = rs.getTimestamp("Start").toLocalDateTime();
            LocalDateTime end = rs.getTimestamp("End").toLocalDateTime();

            int customerID = rs.getInt("Customer_ID");
            int userID = rs.getInt("User_ID");
            int contactID = rs.getInt("Contact_ID");
            Appointments appointment = new Appointments(appointmentID, appointmentTitle, appointmentDescription,
                    appointmentLocation, appointmentType, start, end, customerID, userID, contactID);
            appointmentsObservableList.add(appointment);
        }
        return appointmentsObservableList;
    }
}
