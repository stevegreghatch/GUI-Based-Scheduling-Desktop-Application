package Controller;

import DBAccess.*;
import DBUtility.DBConnection;
import DBUtility.DBQuery;
import Model.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javax.swing.JOptionPane;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

public class AppointmentForm {

    @FXML ToggleGroup formControlToggleGroup;

    // month table view
    @FXML public Tab appTableViewMonthTabButton;
    @FXML public TableView<Appointments> appMonthTableView;
    @FXML public TableColumn<Appointments, Integer> appMonthTableViewAppointmentIDCol;
    @FXML public TableColumn<Appointments, String> appMonthTableViewTitleCol;
    @FXML public TableColumn<Appointments, String> appMonthTableViewDescriptionCol;
    @FXML public TableColumn<Appointments, String> appMonthTableViewLocationCol;
    @FXML public TableColumn<Appointments, Integer> appMonthTableViewContactCol;
    @FXML public TableColumn<Appointments, String> appMonthTableViewTypeCol;
    @FXML public TableColumn<Appointments, LocalDateTime> appMonthTableViewStartDateAndTimeCol;
    @FXML public TableColumn<Appointments, LocalDateTime> appMonthTableViewEndDateAndTimeCol;
    @FXML public TableColumn<Appointments, Integer> appMonthTableViewCustomerIDCol;

    // week table view
    @FXML public Tab appTableViewWeekTabButton;
    @FXML public TableView<Appointments> appWeekTableView;
    @FXML public TableColumn<Appointments, Integer> appWeekTableViewAppointmentIDCol;
    @FXML public TableColumn<Appointments, String> appWeekTableViewTitleCol;
    @FXML public TableColumn<Appointments, String> appWeekTableViewDescriptionCol;
    @FXML public TableColumn<Appointments, String> appWeekTableViewLocationCol;
    @FXML public TableColumn<Appointments, Integer> appWeekTableViewContactCol;
    @FXML public TableColumn<Appointments, String> appWeekTableViewTypeCol;
    @FXML public TableColumn<Appointments, LocalDateTime> appWeekTableViewStartDateAndTimeCol;
    @FXML public TableColumn<Appointments, LocalDateTime> appWeekTableViewEndDateAndTimeCol;
    @FXML public TableColumn<Appointments, Integer> appWeekTableViewCustomerIDCol;

    // all table view
    @FXML public Tab appTableViewAllTabButton;
    @FXML public TableView<Appointments> appAllTableView;
    @FXML public TableColumn<Appointments, Integer> appAllTableViewAppointmentIDCol;
    @FXML public TableColumn<Appointments, String> appAllTableViewTitleCol;
    @FXML public TableColumn<Appointments, String> appAllTableViewDescriptionCol;
    @FXML public TableColumn<Appointments, String> appAllTableViewLocationCol;
    @FXML public TableColumn<Appointments, Integer> appAllTableViewContactCol;
    @FXML public TableColumn<Appointments, String> appAllTableViewTypeCol;
    @FXML public TableColumn<Appointments, LocalDateTime> appAllTableViewStartDateAndTimeCol;
    @FXML public TableColumn<Appointments, LocalDateTime> appAllTableViewEndDateAndTimeCol;
    @FXML public TableColumn<Appointments, Integer> appAllTableViewCustomerIDCol;

    @FXML Button addAppointmentButton;
    @FXML Button updateAppointmentButton;
    @FXML Button deleteAppointmentButton;
    @FXML Button resetAppointmentButton;
    @FXML TextField appointmentTitleTextField;
    @FXML TextField appointmentDescriptionTextField;
    @FXML TextField appointmentLocationTextField;
    @FXML TextField appointmentIDTextField;
    @FXML TextField appointmentTypeTextField;
    @FXML DatePicker appointmentStartDatePicker;
    @FXML DatePicker appointmentEndDatePicker;
    @FXML ComboBox<String> appointmentStartTimeComboBox;
    @FXML ComboBox<String> appointmentEndTimeComboBox;
    @FXML TextField appointmentCustomerIDTextField;
    @FXML TextField appointmentUserIDTextField;
    @FXML ComboBox<String> appointmentContactComboBox;

    /**
     *
     * initializes tableView columns and populates comboBox options (times in local time)
     * populates tables with appointment data
     *
     * @throws SQLException if exception has occurred
     */
    public void initialize() throws SQLException {
        appMonthTableViewAppointmentIDCol.setCellValueFactory(new PropertyValueFactory<>("appointmentID"));
        appMonthTableViewTitleCol.setCellValueFactory(new PropertyValueFactory<>("appointmentTitle"));
        appMonthTableViewDescriptionCol.setCellValueFactory(new PropertyValueFactory<>("appointmentDescription"));
        appMonthTableViewLocationCol.setCellValueFactory(new PropertyValueFactory<>("appointmentLocation"));
        appMonthTableViewContactCol.setCellValueFactory(new PropertyValueFactory<>("contactID"));
        appMonthTableViewTypeCol.setCellValueFactory(new PropertyValueFactory<>("appointmentType"));
        appMonthTableViewStartDateAndTimeCol.setCellValueFactory(new PropertyValueFactory<>("start"));
        appMonthTableViewEndDateAndTimeCol.setCellValueFactory(new PropertyValueFactory<>("end"));
        appMonthTableViewCustomerIDCol.setCellValueFactory(new PropertyValueFactory<>("customerID"));

        appWeekTableViewAppointmentIDCol.setCellValueFactory(new PropertyValueFactory<>("appointmentID"));
        appWeekTableViewTitleCol.setCellValueFactory(new PropertyValueFactory<>("appointmentTitle"));
        appWeekTableViewDescriptionCol.setCellValueFactory(new PropertyValueFactory<>("appointmentDescription"));
        appWeekTableViewLocationCol.setCellValueFactory(new PropertyValueFactory<>("appointmentLocation"));
        appWeekTableViewContactCol.setCellValueFactory(new PropertyValueFactory<>("contactID"));
        appWeekTableViewTypeCol.setCellValueFactory(new PropertyValueFactory<>("appointmentType"));
        appWeekTableViewStartDateAndTimeCol.setCellValueFactory(new PropertyValueFactory<>("start"));
        appWeekTableViewEndDateAndTimeCol.setCellValueFactory(new PropertyValueFactory<>("end"));
        appWeekTableViewCustomerIDCol.setCellValueFactory(new PropertyValueFactory<>("customerID"));

        appAllTableViewAppointmentIDCol.setCellValueFactory(new PropertyValueFactory<>("appointmentID"));
        appAllTableViewTitleCol.setCellValueFactory(new PropertyValueFactory<>("appointmentTitle"));
        appAllTableViewDescriptionCol.setCellValueFactory(new PropertyValueFactory<>("appointmentDescription"));
        appAllTableViewLocationCol.setCellValueFactory(new PropertyValueFactory<>("appointmentLocation"));
        appAllTableViewContactCol.setCellValueFactory(new PropertyValueFactory<>("contactID"));
        appAllTableViewTypeCol.setCellValueFactory(new PropertyValueFactory<>("appointmentType"));
        appAllTableViewStartDateAndTimeCol.setCellValueFactory(new PropertyValueFactory<>("start"));
        appAllTableViewEndDateAndTimeCol.setCellValueFactory(new PropertyValueFactory<>("end"));
        appAllTableViewCustomerIDCol.setCellValueFactory(new PropertyValueFactory<>("customerID"));

        ObservableList<Contacts> contactsObservableList = DBContacts.getAllContacts();
        ObservableList<String> allContactsNames = FXCollections.observableArrayList();
        contactsObservableList.forEach(contacts -> allContactsNames.add(contacts.getContactName()));
        appointmentContactComboBox.setItems(allContactsNames);
        appointmentContactComboBox.setEditable(true);
        appointmentContactComboBox.getEditor().setEditable(false);
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("HH:mm");
        ObservableList<String> appointmentTimesDuringDay = FXCollections.observableArrayList();
        LocalTime firstAppointmentTimeLDT = LocalTime.MIN.plusHours(8);
        LocalTime lastAppointmentTimeLDT = LocalTime.MAX.minusHours(1).minusMinutes(59);
        while (firstAppointmentTimeLDT.isBefore(lastAppointmentTimeLDT)) {
            appointmentTimesDuringDay.add(dateTimeFormatter.format(firstAppointmentTimeLDT));
            firstAppointmentTimeLDT = firstAppointmentTimeLDT.plusMinutes(30);
            }
        appointmentStartTimeComboBox.setItems(appointmentTimesDuringDay);
        appointmentStartTimeComboBox.setEditable(true);
        appointmentStartTimeComboBox.getEditor().setEditable(false);
        appointmentEndTimeComboBox.setItems(appointmentTimesDuringDay);
        appointmentEndTimeComboBox.setEditable(true);
        appointmentEndTimeComboBox.getEditor().setEditable(false);
        populateAppointmentDataToTableView();
    }

    /**
     *
     * populates appointment data to appointmentTableView
     * gets all appointment data from DB via DBAccess.DBAppointments
     * filters appointments to appropriate tableView (month vs week) and adds all appointments to all
     *
     * @throws SQLException if exception has occurred
     */
    public void populateAppointmentDataToTableView() throws SQLException {
        ObservableList<Appointments> allAppointmentsList = DBAppointments.getAllAppointments();
        ObservableList<Appointments> appointmentsMonth = FXCollections.observableArrayList();
        ObservableList<Appointments> appointmentsWeek = FXCollections.observableArrayList();

        LocalDateTime currentMonthStart = LocalDateTime.now().minusSeconds(1);
        LocalDateTime currentMonthEnd = LocalDateTime.now().plusMonths(1);

        LocalDateTime currentWeekStart = LocalDateTime.now().minusSeconds(1);
        LocalDateTime currentWeekEnd = LocalDateTime.now().plusWeeks(1);

        for (Appointments appointment : allAppointmentsList) {
            if (appointment.getEnd().isAfter(currentMonthStart) && appointment.getEnd().isBefore(currentMonthEnd)) {
                appointmentsMonth.add(appointment);
            }
            if (appointment.getEnd().isAfter(currentWeekStart) && appointment.getEnd().isBefore(currentWeekEnd)) {
                appointmentsWeek.add(appointment);
            }
        }
        appMonthTableView.setItems(appointmentsMonth);
        appWeekTableView.setItems(appointmentsWeek);
        appAllTableView.setItems(allAppointmentsList);
    }

    /**
     * gets selected appointment data and sets data to boxes
     * matches contact ID with contact name to display in comboBox
     * prevents adding and allows updating, deleting, and resetting
     *
     * @throws SQLException if exception has occurred
     */
    public void populateAppointmentDataToBoxes() throws SQLException {
        Appointments selectedAppointment = null;
        if (appTableViewMonthTabButton.isSelected()) {
            selectedAppointment = appMonthTableView.getSelectionModel().getSelectedItem();
        }
        if (appTableViewWeekTabButton.isSelected()) {
            selectedAppointment = appWeekTableView.getSelectionModel().getSelectedItem();
        }
        if (appTableViewAllTabButton.isSelected()) {
            selectedAppointment = appAllTableView.getSelectionModel().getSelectedItem();
        }
        if (selectedAppointment != null) {
            appointmentIDTextField.setText(String.valueOf((selectedAppointment.getAppointmentID())));
            appointmentTitleTextField.setText(selectedAppointment.getAppointmentTitle());
            appointmentDescriptionTextField.setText(selectedAppointment.getAppointmentDescription());
            appointmentLocationTextField.setText(selectedAppointment.getAppointmentLocation());
            int contactIDToDisplay = selectedAppointment.getContactID();
            String contactNameToDisplay = "";
            ObservableList<Contacts> contactsObservableList = DBContacts.getAllContacts();
            for (Contacts contact : contactsObservableList) {
                if (contactIDToDisplay == contact.getId()) {
                    contactNameToDisplay = contact.getContactName();
                }
            }
            appointmentContactComboBox.setValue(contactNameToDisplay);
            appointmentTypeTextField.setText(selectedAppointment.getAppointmentType());
            appointmentStartDatePicker.setValue(selectedAppointment.getStart().toLocalDate());
            appointmentEndDatePicker.setValue(selectedAppointment.getEnd().toLocalDate());
            appointmentStartTimeComboBox.setValue(selectedAppointment.getStart().toLocalTime().toString());
            appointmentEndTimeComboBox.setValue(selectedAppointment.getEnd().toLocalTime().toString());
            appointmentCustomerIDTextField.setText(String.valueOf(selectedAppointment.getCustomerID()));
            appointmentUserIDTextField.setText(String.valueOf(selectedAppointment.getUserID()));
            addAppointmentButton.setDisable(true);
            updateAppointmentButton.setDisable(false);
            deleteAppointmentButton.setDisable(false);
            resetAppointmentButton.setDisable(false);
        }
    }

    /**
     * resets boxes for appropriate user interaction
     *
     */
    public void resetBoxes() {
        appMonthTableView.getSelectionModel().clearSelection();
        appointmentTitleTextField.clear();
        appointmentDescriptionTextField.clear();
        appointmentLocationTextField.clear();
        appointmentIDTextField.clear();
        appointmentTypeTextField.clear();
        appointmentStartDatePicker.setValue(null);
        appointmentEndDatePicker.setValue(null);
        appointmentStartTimeComboBox.setValue("");
        appointmentEndTimeComboBox.setValue("");
        appointmentCustomerIDTextField.clear();
        appointmentUserIDTextField.clear();
        appointmentContactComboBox.setValue("");
        addAppointmentButton.setDisable(false);
        updateAppointmentButton.setDisable(true);
        deleteAppointmentButton.setDisable(true);
        resetAppointmentButton.setDisable(true);
    }

    // appointment options -----

    /**
     *
     * verifies that all inputs have value
     * auto-generates appointment ID
     * matches contact name with contact ID to add correct ID to database based on name
     * gets local date from date picker boxes
     * gets local time from combo boxes
     * converts local date and local time to local date time
     *
     * checks to see if time and day are outside of business operation
     * checks to see if appointments are overlapping for customer
     *
     * gets and executes prepared insert statement
     * localDateTime automatically converted from local time to UTC by MySQL Workbench
     * calls appropriate functions to display data and reset boxes
     *
     * @throws SQLException if exception has occurred
     */
    public void addAppointment() throws SQLException {

        // verify that all boxes have inputs
        if (!appointmentTitleTextField.getText().equals("") && !appointmentDescriptionTextField.getText().equals("") &&
                !appointmentLocationTextField.getText().equals("") && !appointmentContactComboBox.getValue().equals("") &&
                !appointmentTypeTextField.getText().equals("") && !appointmentCustomerIDTextField.getText().equals("") &&
                !appointmentUserIDTextField.getText().equals("") && (appointmentStartDatePicker.getValue() != null) &&
                (appointmentEndDatePicker.getValue() != null) && !appointmentStartTimeComboBox.getValue().equals("") &&
                !appointmentEndTimeComboBox.getValue().equals("")) {
            // verify that there is a matching foreign key for customer ID and user ID
            ObservableList<Customers> allCustomerData = DBCustomers.getAllCustomers();
            ObservableList<Integer> allCustomerIDs = FXCollections.observableArrayList();
            for (Customers customer : allCustomerData) {
                allCustomerIDs.add(customer.getCustomerID());
            }
            if (!allCustomerIDs.contains(Integer.parseInt(appointmentCustomerIDTextField.getText()))) {
                JOptionPane.showMessageDialog(null, "No Matching Customer ID",
                        "Appointment Not Added" , JOptionPane.ERROR_MESSAGE);
                return;
            }
            ObservableList<Users> allUserData = DBUsers.getAllUsers();
            ObservableList<Integer> allUserIDs = FXCollections.observableArrayList();
            for (Users user : allUserData) {
                allUserIDs.add(user.getUserID());
            }
            if (!allUserIDs.contains(Integer.parseInt(appointmentUserIDTextField.getText()))) {
                JOptionPane.showMessageDialog(null, "No Matching User ID",
                        "Appointment Not Added" , JOptionPane.ERROR_MESSAGE);
                return;
            }

            int lastAppointmentID = 0;
            ObservableList<Appointments> allAppointmentsList = DBAppointments.getAllAppointments();
            for (Appointments appointment : allAppointmentsList) {
                lastAppointmentID = appointment.getAppointmentID();
            }
            int appointmentIDToAdd = lastAppointmentID + 1;
            String titleToAdd = appointmentTitleTextField.getText();
            String descriptionToAdd = appointmentDescriptionTextField.getText();
            String locationToAdd = appointmentLocationTextField.getText();
            String typeToAdd = appointmentTypeTextField.getText();
            String contactNameToAdd = appointmentContactComboBox.getValue();
            int contactIDToAdd = 0;
            ObservableList<Contacts> contactsObservableList = DBContacts.getAllContacts();
            for (Contacts contact : contactsObservableList) {
                if (contactNameToAdd.equals(contact.getContactName())) {
                    contactIDToAdd = contact.getId();
                }
            }
            LocalDate startLocalDate = appointmentStartDatePicker.getValue();
            LocalDate endLocalDate = appointmentEndDatePicker.getValue();
            DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");
            LocalTime startLocalTime = LocalTime.parse(appointmentStartTimeComboBox.getValue(), timeFormatter);
            LocalTime endLocalTime = LocalTime.parse(appointmentEndTimeComboBox.getValue(), timeFormatter);
            LocalDateTime startLocalDateTimeToAdd = LocalDateTime.of(startLocalDate, startLocalTime);
            LocalDateTime endLocalDateTimeToAdd = LocalDateTime.of(endLocalDate, endLocalTime);

            // outside of business operation check -- requirement 3D
            // start time
            ZonedDateTime startLDTToZDT = ZonedDateTime.of(startLocalDateTimeToAdd, ZoneId.systemDefault());
            ZonedDateTime startZDTToZDTEST = startLDTToZDT.withZoneSameInstant(ZoneId.of("America/New_York"));
            LocalTime startAppointmentTimeToCheck = startZDTToZDTEST.toLocalTime();
            DayOfWeek startAppointmentDayToCheck = startZDTToZDTEST.toLocalDate().getDayOfWeek();
            int startAppointmentDayToCheckInt = startAppointmentDayToCheck.getValue();
            // end time
            ZonedDateTime endLDTToZDT = ZonedDateTime.of(endLocalDateTimeToAdd, ZoneId.systemDefault());
            ZonedDateTime endZDTToZDTEST = endLDTToZDT.withZoneSameInstant(ZoneId.of("America/New_York"));
            LocalTime endAppointmentTimeToCheck = endZDTToZDTEST.toLocalTime();
            DayOfWeek endAppointmentDayToCheck = endZDTToZDTEST.toLocalDate().getDayOfWeek();
            int endAppointmentDayToCheckInt = endAppointmentDayToCheck.getValue();

            // business operation hours/days
            LocalTime startOfBusinessHours = LocalTime.of(8, 0, 0);
            LocalTime endOfBusinessHours = LocalTime.of(22, 0, 0);
            int startOfWeekInt = DayOfWeek.MONDAY.getValue();
            int endOfWeekInt = DayOfWeek.FRIDAY.getValue();
            // time and day checks
            if (startAppointmentTimeToCheck.isBefore(startOfBusinessHours) ||
                    startAppointmentTimeToCheck.isAfter(endOfBusinessHours) ||
                    endAppointmentTimeToCheck.isBefore(startOfBusinessHours) ||
                    endAppointmentTimeToCheck.isAfter(endOfBusinessHours)) {
                JOptionPane.showMessageDialog(null, "Time is outside of business hours\n" +
                                "Please schedule between 08:00 and 22:00 EST",
                        "Appointment Not Added", JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (startAppointmentDayToCheckInt < startOfWeekInt || startAppointmentDayToCheckInt > endOfWeekInt ||
                    endAppointmentDayToCheckInt < startOfWeekInt || endAppointmentDayToCheckInt > endOfWeekInt) {
                JOptionPane.showMessageDialog(null, "Day is outside of business hours\n" +
                                "Please schedule between Monday and Friday",
                        "Appointment Not Added", JOptionPane.ERROR_MESSAGE);
                return;
            }

            Timestamp createdDateToAdd = Timestamp.valueOf(LocalDateTime.now());
            String createdByToAdd = "admin";
            Timestamp lastUpdateToAdd = Timestamp.valueOf(LocalDateTime.now());
            String lastUpdatedByToAdd = "admin";
            int customerIDToAdd = Integer.parseInt(appointmentCustomerIDTextField.getText());

            // overlapping appointments -- requirement 3D
            ObservableList<Appointments> allAppointments = DBAppointments.getAllAppointments();
            for (Appointments appointment : allAppointments) {
                LocalDateTime startTimesToCheck = appointment.getStart();
                LocalDateTime endTimesToCheck = appointment.getEnd();
                int customerIDsToCheck = appointment.getCustomerID();
                if (customerIDToAdd == customerIDsToCheck &&
                        (startLocalDateTimeToAdd.isEqual(startTimesToCheck) ||
                                startLocalDateTimeToAdd.isAfter(startTimesToCheck)) &&
                        (startLocalDateTimeToAdd.isEqual(endTimesToCheck) ||
                                startLocalDateTimeToAdd.isBefore(endTimesToCheck))) {
                    JOptionPane.showMessageDialog(null, "Start time overlaps with existing " +
                                    "appointment for customer", "Error - Appointment Not Added",
                            JOptionPane.ERROR_MESSAGE);
                    return;
                }
                if (customerIDToAdd == customerIDsToCheck &&
                        (endLocalDateTimeToAdd.isEqual(startTimesToCheck) ||
                                endLocalDateTimeToAdd.isAfter(startTimesToCheck)) &&
                        (endLocalDateTimeToAdd.isEqual(endTimesToCheck) ||
                                endLocalDateTimeToAdd.isBefore(endTimesToCheck))) {
                    JOptionPane.showMessageDialog(null, "End time overlaps with existing " +
                                    "appointment for customer", "Error - Appointment Not Added",
                            JOptionPane.ERROR_MESSAGE);
                    return;
                }
            }
            int userIDToAdd = Integer.parseInt(appointmentUserIDTextField.getText());
            String insertStatement = "INSERT INTO appointments (Appointment_ID, Title, Description, Location, Type, " +
                    "Start, End, Create_Date, Created_By, Last_Update, Last_Updated_By, Customer_ID, User_ID, Contact_ID) " +
                    "VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

            DBQuery.setPreparedStatement(DBConnection.getConnection(), insertStatement);
            PreparedStatement ps = DBQuery.getPreparedStatement();
            ps.setInt(1, appointmentIDToAdd);
            ps.setString(2, titleToAdd);
            ps.setString(3, descriptionToAdd);
            ps.setString(4, locationToAdd);
            ps.setString(5, typeToAdd);
            ps.setTimestamp(6, Timestamp.valueOf(startLocalDateTimeToAdd));
            ps.setTimestamp(7, Timestamp.valueOf(endLocalDateTimeToAdd));
            ps.setTimestamp(8, createdDateToAdd);
            ps.setString(9, createdByToAdd);
            ps.setTimestamp(10, lastUpdateToAdd);
            ps.setString(11, lastUpdatedByToAdd);
            ps.setInt(12, customerIDToAdd);
            ps.setInt(13, userIDToAdd);
            ps.setInt(14, contactIDToAdd);
            ps.execute();
            populateAppointmentDataToTableView();
            System.out.println("Appointment added to database");
            resetBoxes();
        }
        else JOptionPane.showMessageDialog(null, "Appointment Data Incomplete",
                "Appointment Not Added" , JOptionPane.ERROR_MESSAGE);
    }

    /**
     *
     * gets data that was populated in boxes
     * matches contact name with contact ID to add correct ID to database based on name
     *
     * gets new local date from date picker boxes
     * gets new local time from combo boxes
     * converts new local date and new local time to new local date time
     *
     * checks to see if new time and new day are outside of business operation
     * checks to see if appointments are overlapping for customer (excluding same appointment id)
     *
     * gets and executes prepared update statement
     * localDateTime automatically converted from local time to UTC by MySQL Workbench
     * calls appropriate functions to display data and reset boxes
     *
     * @throws SQLException if exception has occurred
     */
    public void updateAppointment() throws SQLException {
        // verify that there is a matching foreign key for customer ID and user ID
        ObservableList<Customers> allCustomerData = DBCustomers.getAllCustomers();
        ObservableList<Integer> allCustomerIDs = FXCollections.observableArrayList();
        for (Customers customer : allCustomerData) {
            allCustomerIDs.add(customer.getCustomerID());
        }
        if (!allCustomerIDs.contains(Integer.parseInt(appointmentCustomerIDTextField.getText()))) {
            JOptionPane.showMessageDialog(null, "No Matching Customer ID",
                    "Appointment Not Added" , JOptionPane.ERROR_MESSAGE);
            return;
        }
        ObservableList<Users> allUserData = DBUsers.getAllUsers();
        ObservableList<Integer> allUserIDs = FXCollections.observableArrayList();
        for (Users user : allUserData) {
            allUserIDs.add(user.getUserID());
        }
        if (!allUserIDs.contains(Integer.parseInt(appointmentUserIDTextField.getText()))) {
            JOptionPane.showMessageDialog(null, "No Matching User ID",
                    "Appointment Not Added" , JOptionPane.ERROR_MESSAGE);
            return;
        }

        int appointmentIDToUpdate = Integer.parseInt(appointmentIDTextField.getText());
        String titleToUpdate = appointmentTitleTextField.getText();
        String descriptionToUpdate = appointmentDescriptionTextField.getText();
        String locationToUpdate = appointmentLocationTextField.getText();
        String typeToUpdate = appointmentTypeTextField.getText();
        String contactNameToUpdate = appointmentContactComboBox.getValue();
        int contactIDToUpdate = 0;
        ObservableList<Contacts> contactsObservableList = DBContacts.getAllContacts();
        for (Contacts contact : contactsObservableList) {
            if (contactNameToUpdate.equals(contact.getContactName())) {
                contactIDToUpdate = contact.getId();
            }
        }
        LocalDate startLocalDate = appointmentStartDatePicker.getValue();
        LocalDate endLocalDate = appointmentEndDatePicker.getValue();
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");
        LocalTime startLocalTime = LocalTime.parse(appointmentStartTimeComboBox.getValue(), timeFormatter);
        LocalTime endLocalTime = LocalTime.parse(appointmentEndTimeComboBox.getValue(), timeFormatter);
        LocalDateTime startLocalDateTimeToUpdate = LocalDateTime.of(startLocalDate, startLocalTime);
        LocalDateTime endLocalDateTimeToUpdate = LocalDateTime.of(endLocalDate, endLocalTime);

        // outside of business operation check -- requirement 3D
        // start time
        ZonedDateTime startLDTToZDT = ZonedDateTime.of(startLocalDateTimeToUpdate, ZoneId.systemDefault());
        ZonedDateTime startZDTToZDTEST = startLDTToZDT.withZoneSameInstant(ZoneId.of("America/New_York"));
        LocalTime startAppointmentTimeToCheck = startZDTToZDTEST.toLocalTime();
        DayOfWeek startAppointmentDayToCheck = startZDTToZDTEST.toLocalDate().getDayOfWeek();
        int startAppointmentDayToCheckInt = startAppointmentDayToCheck.getValue();
        // end time
        ZonedDateTime endLDTToZDT = ZonedDateTime.of(endLocalDateTimeToUpdate, ZoneId.systemDefault());
        ZonedDateTime endZDTToZDTEST = endLDTToZDT.withZoneSameInstant(ZoneId.of("America/New_York"));
        LocalTime endAppointmentTimeToCheck = endZDTToZDTEST.toLocalTime();
        DayOfWeek endAppointmentDayToCheck = endZDTToZDTEST.toLocalDate().getDayOfWeek();
        int endAppointmentDayToCheckInt = endAppointmentDayToCheck.getValue();
        // business operation hours/days
        LocalTime startOfBusinessHours = LocalTime.of(8, 0, 0);
        LocalTime endOfBusinessHours = LocalTime.of(22, 0, 0);
        int startOfWeekInt = DayOfWeek.MONDAY.getValue();
        int endOfWeekInt = DayOfWeek.FRIDAY.getValue();
        // time and day checks
        if (startAppointmentTimeToCheck.isBefore(startOfBusinessHours) ||
                startAppointmentTimeToCheck.isAfter(endOfBusinessHours) ||
                endAppointmentTimeToCheck.isBefore(startOfBusinessHours) ||
                endAppointmentTimeToCheck.isAfter(endOfBusinessHours)) {
            JOptionPane.showMessageDialog(null, "Time is outside of business hours\n" +
                            "Please schedule between 08:00 and 22:00 EST",
                    "Error - Appointment Not Updated", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (startAppointmentDayToCheckInt < startOfWeekInt || startAppointmentDayToCheckInt > endOfWeekInt ||
                endAppointmentDayToCheckInt < startOfWeekInt || endAppointmentDayToCheckInt > endOfWeekInt) {
            JOptionPane.showMessageDialog(null, "Day is outside of business hours\n" +
                            "Please schedule between Monday and Friday",
                    "Error - Appointment Not Updated", JOptionPane.ERROR_MESSAGE);
            return;
        }
        Timestamp lastUpdateToUpdate = Timestamp.valueOf(LocalDateTime.now());
        String lastUpdatedByToUpdate = "admin";
        int customerIDToUpdate = Integer.parseInt(appointmentCustomerIDTextField.getText());

        // overlapping appointments -- requirement 3D
        ObservableList<Appointments> allAppointments = DBAppointments.getAllAppointments();
        int selectedAppointmentID = 0;
        if (appTableViewMonthTabButton.isSelected()) {
            selectedAppointmentID = appMonthTableView.getSelectionModel().getSelectedItem().getAppointmentID();
        }
        if (appTableViewWeekTabButton.isSelected()) {
            selectedAppointmentID = appWeekTableView.getSelectionModel().getSelectedItem().getAppointmentID();

        }
        if (appTableViewAllTabButton.isSelected()) {
            selectedAppointmentID = appAllTableView.getSelectionModel().getSelectedItem().getAppointmentID();
        }
        for (Appointments appointment : allAppointments) {
            LocalDateTime startTimesToCheck = appointment.getStart();
            LocalDateTime endTimesToCheck = appointment.getEnd();
            int customerIDsToCheck = appointment.getCustomerID();
            if ((appointment.getAppointmentID() != selectedAppointmentID) &&
                    (customerIDToUpdate == customerIDsToCheck) &&
                    (startLocalDateTimeToUpdate.isEqual(startTimesToCheck) ||
                            startLocalDateTimeToUpdate.isAfter(startTimesToCheck)) &&
                    (startLocalDateTimeToUpdate.isEqual(endTimesToCheck) ||
                            startLocalDateTimeToUpdate.isBefore(endTimesToCheck))) {
                JOptionPane.showMessageDialog(null, "New start time overlaps with existing " +
                                "appointment for customer", "Error - Appointment Not Updated",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }
            if ((appointment.getAppointmentID() != selectedAppointmentID) &&
                    (customerIDToUpdate == customerIDsToCheck) &&
                    (endLocalDateTimeToUpdate.isEqual(startTimesToCheck) ||
                            endLocalDateTimeToUpdate.isAfter(startTimesToCheck)) &&
                    (endLocalDateTimeToUpdate.isEqual(endTimesToCheck) ||
                            endLocalDateTimeToUpdate.isBefore(endTimesToCheck))) {
                JOptionPane.showMessageDialog(null, "New end time overlaps with existing " +
                                "appointment for customer", "Error - Appointment Not Updated",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }
        }
        int userIDToUpdate = Integer.parseInt(appointmentUserIDTextField.getText());
        String updateStatement = "UPDATE appointments SET Appointment_ID = ?, Title = ?, Description = ?, " +
                "Location = ?, Type = ?, Start = ?, End = ?, Last_Update = ?, Last_Updated_By = ?, " +
                "Customer_ID = ?, User_ID = ?, Contact_ID = ? WHERE Appointment_ID = ?";
        DBQuery.setPreparedStatement(DBConnection.getConnection(), updateStatement);
        PreparedStatement ps = DBQuery.getPreparedStatement();
        ps.setInt(1, appointmentIDToUpdate);
        ps.setString(2, titleToUpdate);
        ps.setString(3, descriptionToUpdate);
        ps.setString(4, locationToUpdate);
        ps.setString(5, typeToUpdate);
        ps.setTimestamp(6, Timestamp.valueOf(startLocalDateTimeToUpdate));
        ps.setTimestamp(7, Timestamp.valueOf(endLocalDateTimeToUpdate));
        ps.setTimestamp(8, lastUpdateToUpdate);
        ps.setString(9, lastUpdatedByToUpdate);
        ps.setInt(10, customerIDToUpdate);
        ps.setInt(11, userIDToUpdate);
        ps.setInt(12, contactIDToUpdate);
        ps.setInt(13, appointmentIDToUpdate);
        ps.execute();
        populateAppointmentDataToTableView();
        System.out.println("Appointment updated in database");
        resetBoxes();
    }

    /**
     *
     * gets selected appointment ID
     * gets and executes prepared delete statement
     * displays change, notifies user, and resets boxes
     *
     * @throws SQLException if exception has occurred
     */
    public void deleteAppointment() throws SQLException {
        String deleteStatement = "DELETE FROM appointments WHERE Appointment_ID = ?";
        DBQuery.setPreparedStatement(DBConnection.getConnection(), deleteStatement);
        PreparedStatement ps = DBQuery.getPreparedStatement();
        int appointmentIDToDelete = 0;
        String appointmentType = "";
        if (appTableViewMonthTabButton.isSelected()) {
            appointmentIDToDelete = appMonthTableView.getSelectionModel().getSelectedItem().getAppointmentID();
            appointmentType = appMonthTableView.getSelectionModel().getSelectedItem().getAppointmentType();
        }
        if (appTableViewWeekTabButton.isSelected()) {
            appointmentIDToDelete = appWeekTableView.getSelectionModel().getSelectedItem().getAppointmentID();
            appointmentType = appWeekTableView.getSelectionModel().getSelectedItem().getAppointmentType();
        }
        if (appTableViewAllTabButton.isSelected()) {
            appointmentIDToDelete = appAllTableView.getSelectionModel().getSelectedItem().getAppointmentID();
            appointmentType = appAllTableView.getSelectionModel().getSelectedItem().getAppointmentType();
        }
        ps.setInt(1, appointmentIDToDelete);
        ps.execute();
        populateAppointmentDataToTableView();
        System.out.println("Appointment deleted from database");
        JOptionPane.showMessageDialog(null,
                "\nAppointment_ID: " + appointmentIDToDelete +
                        "\nType of Appointment: " + appointmentType,
                "Appointment Deleted from Database", JOptionPane.INFORMATION_MESSAGE);
        resetBoxes();
    }

    // scene changes -----
    /**
     *
     * @param event on radio button press - switch from AppointmentForm to CustomerForm
     * @throws IOException if exception has occurred
     */
    public void radioToCustomerFormScene(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("../View/FXMLCustomerForm.fxml")));
        Stage primaryStage = (Stage)((Node)event.getSource()).getScene().getWindow();
        primaryStage.setScene(new Scene(root, 900, 600));
    }

    /**
     *
     * @param event on radio button press - switch from AppointmentForm to ReportForm
     * @throws IOException if exception has occurred
     */
    public void radioToReportFormScene(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("../View/FXMLReportForm.fxml")));
        Stage primaryStage = (Stage)((Node)event.getSource()).getScene().getWindow();
        primaryStage.setScene(new Scene(root, 900, 600));
    }
}
