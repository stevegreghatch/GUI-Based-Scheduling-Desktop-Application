package Controller;

import DBAccess.*;
import DBUtility.DBConnection;
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
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.function.Function;
import DBUtility.DBQuery;
import javax.swing.JOptionPane;

public class CustomerForm {

    @FXML public ToggleGroup formControlToggleGroup;
    @FXML public TableView<Customers> customerTableView;
    @FXML public TableColumn<Customers, Integer> customerTableViewIDCol;
    @FXML public TableColumn<Customers, String> customerTableViewNameCol;
    @FXML public TableColumn<Customers, String> customerTableViewAddressCol;
    @FXML public TableColumn<Customers, Integer> customerTableViewFirstLevelDivisionCol;
    @FXML public TableColumn<Customers, String> customerTableViewPostalCodeCol;
    @FXML public TableColumn<Customers, String> customerTableViewPhoneCol;
    @FXML public Button customerAddButton;
    @FXML public Button customerResetButton;
    @FXML public Button customerUpdateButton;
    @FXML public Button customerDeleteButton;
    @FXML public TextField customerIDTextField;
    @FXML public TextField customerNameTextField;
    @FXML public TextField customerAddressTextField;
    @FXML public TextField customerPostalCodeTextField;
    @FXML public TextField customerPhoneNumberTextField;
    @FXML public ComboBox<String> customerCountryComboBox;
    @FXML public ComboBox<String> customerFirstLevelDivisionComboBox;
    int lastClickedButton = -1; // used with lambda #2

    /**
     *
     * initializes tableView columns, combo boxes, and populates table
     * method includes lambda #1 -- purpose is to simplify adding contact names to combo box
     *
     * @throws SQLException if exception has occurred
     */
    public void initialize() throws SQLException {
        customerTableViewIDCol.setCellValueFactory(new PropertyValueFactory<>("customerID"));
        customerTableViewNameCol.setCellValueFactory(new PropertyValueFactory<>("customerName"));
        customerTableViewAddressCol.setCellValueFactory(new PropertyValueFactory<>("customerAddress"));
        customerTableViewFirstLevelDivisionCol.setCellValueFactory(new PropertyValueFactory<>("divisionID"));
        customerTableViewPostalCodeCol.setCellValueFactory(new PropertyValueFactory<>("customerPostalCode"));
        customerTableViewPhoneCol.setCellValueFactory(new PropertyValueFactory<>("customerPhoneNumber"));

        ObservableList<Countries> allCountries = DBCountries.getAllCountries();
        ObservableList<String> countryNames = FXCollections.observableArrayList();
        for (Countries country : allCountries) {
            countryNames.add(country.getCountryName());
        }
        customerCountryComboBox.setItems(countryNames);
        customerCountryComboBox.setEditable(true);
        customerCountryComboBox.getEditor().setEditable(false);
        ObservableList<FirstLevelDivisions> allFirstLevelDivisions = DBFirstLevelDivisions.getAllFirstLevelDivisions();
        ObservableList<String> firstLevelDivisionAllNames = FXCollections.observableArrayList();

        // lambda #1
        allFirstLevelDivisions.forEach(firstLevelDivision -> firstLevelDivisionAllNames.add(firstLevelDivision.getDivisionName()));
        setCustomerOptionAddAndUpdateButtonActions();

        customerFirstLevelDivisionComboBox.setItems(firstLevelDivisionAllNames);
        customerFirstLevelDivisionComboBox.setItems(firstLevelDivisionAllNames);
        customerFirstLevelDivisionComboBox.setEditable(true);
        customerFirstLevelDivisionComboBox.getEditor().setEditable(false);
        populateCustomerDataToTableView();
    }

    /**
     *
     * checks for appointments with 15 minutes -- requirement 3E
     * requirements note, "You must use "test" as the username and password to login to your application." therefore
     * user 1 was considered to be the only active user
     * displays appropriate message upon log-in
     *
     * @throws SQLException if exception has occurred
     */
    public void checkForUpcomingAppointment() throws SQLException {
        int currentUserID = 1;
        LocalDateTime timeOfLogInNow = LocalDateTime.now();
        LocalDateTime timeOfLogInBottomWindow = LocalDateTime.now().minusMinutes(15);
        LocalDateTime timeOfLogInTopWindow = LocalDateTime.now().plusMinutes(15);
        LocalDateTime startDateTimeToCheck;
        LocalDateTime endDateTimeToCheck;
        int appointmentIDToDisplay = 0;
        LocalDateTime startDateTimeToDisplay = null;
        LocalDateTime endDateTimeToDisplay = null;
        boolean hasUpcomingAppointment = false;
        boolean hasCurrentAppointment = false;
        ObservableList<Appointments> appointmentsObservableList = DBAppointments.getAllAppointments();
        for (Appointments appointment : appointmentsObservableList) {
            startDateTimeToCheck = appointment.getStart();
            endDateTimeToCheck = appointment.getEnd();
            int userIDsToCheck = appointment.getUserID();
            if ((userIDsToCheck == currentUserID) &&
                    (startDateTimeToCheck.isAfter(timeOfLogInBottomWindow) || startDateTimeToCheck.isEqual(timeOfLogInBottomWindow)) &&
                            (startDateTimeToCheck.isBefore(timeOfLogInTopWindow) || (startDateTimeToCheck.isEqual(timeOfLogInTopWindow)))) {
                appointmentIDToDisplay = appointment.getAppointmentID();
                startDateTimeToDisplay = startDateTimeToCheck;
                endDateTimeToDisplay = endDateTimeToCheck;
                hasUpcomingAppointment = true;
            }
            else if ((userIDsToCheck == currentUserID) &&
                    (timeOfLogInNow.isAfter(startDateTimeToCheck) || timeOfLogInNow.isEqual(startDateTimeToCheck)) &&
                            (timeOfLogInNow.isBefore(endDateTimeToCheck) || (timeOfLogInNow.isEqual(endDateTimeToCheck)))) {
                System.out.println(timeOfLogInNow);
                System.out.println(startDateTimeToCheck);
                System.out.println(endDateTimeToCheck);
                appointmentIDToDisplay = appointment.getAppointmentID();
                startDateTimeToDisplay = startDateTimeToCheck;
                endDateTimeToDisplay = endDateTimeToCheck;
                hasCurrentAppointment = true;
            }
        }
        if (hasUpcomingAppointment) {
            JOptionPane.showMessageDialog(null,
                    "There is an appointment within 15 minutes" +
                            "\nAppointment ID: " + appointmentIDToDisplay +
                            "\nStart Date: " + startDateTimeToDisplay.toLocalDate() +
                            "\nEnd Date: " + endDateTimeToDisplay.toLocalDate() +
                            "\nStart Time: " + startDateTimeToDisplay.toLocalTime() +
                            "\nEnd Time: " + endDateTimeToDisplay.toLocalTime(),
                    "Upcoming Appointment", JOptionPane.WARNING_MESSAGE);
        }
        else if (hasCurrentAppointment) {
            JOptionPane.showMessageDialog(null,
                    "There is a current appointment" +
                            "\nAppointment ID: " + appointmentIDToDisplay +
                            "\nStart Date: " + startDateTimeToDisplay.toLocalDate() +
                            "\nEnd Date: " + endDateTimeToDisplay.toLocalDate() +
                            "\nStart Time: " + startDateTimeToDisplay.toLocalTime() +
                            "\nEnd Time: " + endDateTimeToDisplay.toLocalTime(),
                    "Current Appointment", JOptionPane.WARNING_MESSAGE);
        }
        else {
            JOptionPane.showMessageDialog(null,
                    "No upcoming appointments",
                    "Appointments", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    /**
     *
     * @throws SQLException if exception has occurred
     */
    public void populateCustomerDataToTableView() throws SQLException {
        ObservableList<Customers> allCustomersList = DBCustomers.getAllCustomers();
        customerTableView.setItems(allCustomersList);
    }

    /**
     *
     * gets all customer data for selected customer and populates appropriate boxes
     * matches division ID with division name to set division combo box
     * matches country with division ID to set country combo box
     * prevents adding and allows updating, deleting, and resetting
     *
     * @throws SQLException if exception has occurred
     */
    public void populateCustomerDataToBoxes() throws SQLException {
        Customers selectedCustomer = customerTableView.getSelectionModel().getSelectedItem();
        if (selectedCustomer != null) {
            ObservableList<Countries> allCountries = DBCountries.getAllCountries();
            ObservableList<FirstLevelDivisions> allFirstLevelDivisions = DBFirstLevelDivisions.getAllFirstLevelDivisions();
            ObservableList<String> firstLevelDivisionAllNames = FXCollections.observableArrayList();
            for (FirstLevelDivisions firstLevelDivision : allFirstLevelDivisions) {
                firstLevelDivisionAllNames.add(firstLevelDivision.getDivisionName());
            }
            customerFirstLevelDivisionComboBox.setItems(firstLevelDivisionAllNames);
            customerIDTextField.setText(String.valueOf((selectedCustomer.getCustomerID())));
            customerNameTextField.setText(selectedCustomer.getCustomerName());
            customerAddressTextField.setText(selectedCustomer.getCustomerAddress());
            customerPostalCodeTextField.setText(selectedCustomer.getCustomerPostalCode());
            customerPhoneNumberTextField.setText(selectedCustomer.getCustomerPhoneNumber());
            int divisionIDToSet = selectedCustomer.getDivisionID();
            String divisionNameToSet = "";
            int countryIDToSet;
            String countryNameToSet = "";
            for (FirstLevelDivisions firstLevelDivision : allFirstLevelDivisions) {
                if (divisionIDToSet == firstLevelDivision.getDivisionID()) {
                    divisionNameToSet = firstLevelDivision.getDivisionName();
                    countryIDToSet = firstLevelDivision.getCountry_ID();
                    for (Countries country : allCountries) {
                        if (countryIDToSet == country.getCountryID()) {
                            countryNameToSet = country.getCountryName();
                        }
                    }
                }
            }
            customerFirstLevelDivisionComboBox.setValue(divisionNameToSet);
            customerCountryComboBox.setValue(countryNameToSet);
            customerAddButton.setDisable(true);
            customerUpdateButton.setDisable(false);
            customerDeleteButton.setDisable(false);
            customerResetButton.setDisable(false);
        }
    }

    /**
     *
     * filters first level divisions based on country combo box selection
     *
     * @throws SQLException if exception has occurred
     */
    public void filterFirstLevelDivisionComboBox () throws SQLException {
        ObservableList<FirstLevelDivisions> allFirstLevelDivisions = DBFirstLevelDivisions.getAllFirstLevelDivisions();
        ObservableList<String> firstLevelDivisionNamesUS = FXCollections.observableArrayList();
        ObservableList<String> firstLevelDivisionNamesUK = FXCollections.observableArrayList();
        ObservableList<String> firstLevelDivisionNamesCanada = FXCollections.observableArrayList();
        for (FirstLevelDivisions firstLevelDivision : allFirstLevelDivisions) {
            if (firstLevelDivision.getCountry_ID() == 1) {
                firstLevelDivisionNamesUS.add(firstLevelDivision.getDivisionName());
            }
            else if (firstLevelDivision.getCountry_ID() == 2) {
                firstLevelDivisionNamesUK.add(firstLevelDivision.getDivisionName());
            }
            else if (firstLevelDivision.getCountry_ID() == 3) {
                firstLevelDivisionNamesCanada.add(firstLevelDivision.getDivisionName());
            }
        }
        String selectedCountry = customerCountryComboBox.getSelectionModel().getSelectedItem();
        if (selectedCountry.equals("U.S")) {
            customerFirstLevelDivisionComboBox.setItems(firstLevelDivisionNamesUS);
        }
        else if (selectedCountry.equals("UK")) {
            customerFirstLevelDivisionComboBox.setItems(firstLevelDivisionNamesUK);
        }
        else if (selectedCountry.equals("Canada")) {
            customerFirstLevelDivisionComboBox.setItems(firstLevelDivisionNamesCanada);
        }
    }

    /**
     * resets boxes for appropriate user interaction
     *
     */
    public void resetBoxes() {
        customerTableView.getSelectionModel().clearSelection();
        customerIDTextField.clear();
        customerIDTextField.setDisable(true);
        customerNameTextField.clear();
        customerAddressTextField.clear();
        customerPostalCodeTextField.clear();
        customerPhoneNumberTextField.clear();
        customerCountryComboBox.setValue("");
        customerFirstLevelDivisionComboBox.setValue("");
        customerAddButton.setDisable(false);
        customerUpdateButton.setDisable(true);
        customerDeleteButton.setDisable(true);
        customerResetButton.setDisable(true);
    }

    /**
     *
     * method references lambda #2 -- purpose is to confirm successful add/update and to simplify code in add and update
     * functions
     *
     * @param string beginning part of total string ("Customer")
     * @param fn variable string based on which button is clicked (" added to database" or "updated in database"
     * @return appropriate user notification (ex. Customer added to database or Customer updated in database)
     */
    public String notifyUserAppropriateMessage(String string, Function<String, String> fn) {
        return fn.apply(string);
    }

    /**
     *  lambda #2 -- purpose is to confirm successful add/update and to simplify code in add and update functions
     */
    public void consoleNotification() {
        Function<String, String> fn = null;
        if (lastClickedButton == 1) { fn = parameter -> parameter + " added to database"; }
        if (lastClickedButton == 2) { fn = parameter -> parameter + " updated in database"; }
        String userNotification = notifyUserAppropriateMessage("Customer", fn);
        System.out.println(userNotification);
        lastClickedButton = -1;
    }

    /**
     * sets button values for lambda #2
     */
    public void setCustomerOptionAddAndUpdateButtonActions() {
        customerAddButton.setOnAction(e -> { lastClickedButton = 1;try { addCustomer();
        } catch (SQLException throwables) { throwables.printStackTrace();}});
        customerUpdateButton.setOnAction(e -> { lastClickedButton = 2;try { updateCustomer();
        } catch (SQLException throwables) { throwables.printStackTrace();}});
    }

    // customer options  -----
    /**
     *
     * verifies that all inputs have value
     * auto-generates customer ID
     * matches division name string with division ID
     * gets and executes prepared insert statement
     * calls appropriate functions to display data and reset boxes
     *
     * @throws SQLException if exception has occurred
     */
    public void addCustomer() throws SQLException {
        if (!customerNameTextField.getText().equals("") && !customerAddressTextField.getText().equals("") &&
                !customerCountryComboBox.getValue().equals("") && !customerFirstLevelDivisionComboBox.getValue().equals("") &&
                !customerPostalCodeTextField.getText().equals("") && !customerPhoneNumberTextField.getText().equals("")) {
            int lastID = 0;
            ObservableList<Customers> allCustomersList = DBCustomers.getAllCustomers();
            for (Customers customer : allCustomersList) {
                lastID = customer.getCustomerID();
            }
            int idToAdd = lastID + 1;
            String nameToAdd = customerNameTextField.getText();
            String addressToAdd = customerAddressTextField.getText();
            String postalCodeToAdd = customerPostalCodeTextField.getText();
            String phoneNumberToAdd = customerPhoneNumberTextField.getText();
            int firstLevelDivisionIDToAdd = 0;
            String selectedFirstLevelDivision = customerFirstLevelDivisionComboBox.getSelectionModel().getSelectedItem();
            ObservableList<FirstLevelDivisions> allFirstLevelDivisions = DBFirstLevelDivisions.getAllFirstLevelDivisions();
            for (FirstLevelDivisions firstLevelDivision : allFirstLevelDivisions) {
                if (selectedFirstLevelDivision.equals(firstLevelDivision.getDivisionName())) {
                    firstLevelDivisionIDToAdd = firstLevelDivision.getDivisionID();
                }
            }
            LocalDateTime createdDateToAdd = LocalDateTime.now();
            String createdByToAdd = "admin";
            Timestamp lastUpdateToAdd = Timestamp.valueOf(LocalDateTime.now());
            String lastUpdatedByToAdd = "admin";
            String insertStatement = "INSERT INTO customers (Customer_ID, Customer_Name, Address, Postal_Code, Phone, " +
                    "Create_Date, Created_By, Last_Update, Last_Updated_By, Division_ID) " +
                    "VALUES (?,?,?,?,?,?,?,?,?,?)";
            DBQuery.setPreparedStatement(DBConnection.getConnection(), insertStatement);
            PreparedStatement ps = DBQuery.getPreparedStatement();
            ps.setInt(1, idToAdd);
            ps.setString(2, nameToAdd);
            ps.setString(3, addressToAdd);
            ps.setString(4, postalCodeToAdd);
            ps.setString(5, phoneNumberToAdd);
            ps.setTimestamp(6, Timestamp.valueOf(createdDateToAdd));
            ps.setString(7, createdByToAdd);
            ps.setTimestamp(8, lastUpdateToAdd);
            ps.setString(9, lastUpdatedByToAdd);
            ps.setInt(10, firstLevelDivisionIDToAdd);
            ps.execute();
            populateCustomerDataToTableView();
            consoleNotification();
            resetBoxes();
        }
        else JOptionPane.showMessageDialog(null, "Customer Data Incomplete",
                "Customer Not Added" , JOptionPane.ERROR_MESSAGE);
    }

    /**
     *
     * gets data that was populated in boxes
     * matches first-level division name with ID
     * gets and executes prepared update statement
     * calls appropriate functions to display data and reset boxes
     *
     * @throws SQLException if exception has occurred
     */
    public void updateCustomer() throws SQLException {
        int idToUpdate = Integer.parseInt(customerIDTextField.getText());
        String nameToUpdate = customerNameTextField.getText();
        String addressToUpdate = customerAddressTextField.getText();
        String postalCodeToUpdate = customerPostalCodeTextField.getText();
        String phoneNumberToUpdate = customerPhoneNumberTextField.getText();
        String firstLevelDivisionStringToUpdate = customerFirstLevelDivisionComboBox.getValue();
        int firstLevelDivisionIntToUpdate = 0;
        ObservableList<FirstLevelDivisions> allFirstLevelDivisions = DBFirstLevelDivisions.getAllFirstLevelDivisions();
        for (FirstLevelDivisions firstLevelDivision : allFirstLevelDivisions) {
            if (firstLevelDivisionStringToUpdate.equals(firstLevelDivision.getDivisionName())) {
                firstLevelDivisionIntToUpdate = firstLevelDivision.getDivisionID();
            }
        }
        Timestamp lastUpdateToUpdate = Timestamp.valueOf(LocalDateTime.now());
        String lastUpdatedByToUpdate = "admin";
        String updateStatement = "UPDATE customers SET Customer_ID = ?, Customer_Name = ?, Address = ?, " +
                "Postal_Code = ?, Phone = ?, Last_Update = ?, Last_Updated_By = ?, " +
                "Division_ID = ? WHERE Customer_ID = ?";
        DBQuery.setPreparedStatement(DBConnection.getConnection(), updateStatement);
        PreparedStatement ps = DBQuery.getPreparedStatement();
        ps.setInt(1, idToUpdate);
        ps.setString(2, nameToUpdate);
        ps.setString(3, addressToUpdate);
        ps.setString(4, postalCodeToUpdate);
        ps.setString(5, phoneNumberToUpdate);
        ps.setTimestamp(6, lastUpdateToUpdate);
        ps.setString(7, lastUpdatedByToUpdate);
        ps.setInt(8, firstLevelDivisionIntToUpdate);
        ps.setInt(9, idToUpdate);
        ps.execute();
        populateCustomerDataToTableView();
        consoleNotification();
        resetBoxes();
    }

    /**
     *
     * gets selected customer ID
     * deletes customer appointments first per requirement 2
     * gets and executes prepared delete statement
     * displays change, notifies user, and resets boxes
     *
     * @throws SQLException if exception has occurred
     */
    public void deleteCustomer() throws SQLException {

        String deleteStatement = "DELETE FROM customers WHERE Customer_ID = ?";
        DBQuery.setPreparedStatement(DBConnection.getConnection(), deleteStatement);
        PreparedStatement ps = DBQuery.getPreparedStatement();
        int customerIDToDelete = customerTableView.getSelectionModel().getSelectedItem().getCustomerID();
        ObservableList<Appointments> appointmentsObservableList = DBAppointments.getAllAppointments();
        for (Appointments appointment : appointmentsObservableList) {
            int customerIDToCheck = appointment.getCustomerID();
            int appointmentIDToDelete = appointment.getAppointmentID();
            String appointmentTypeToDisplay = appointment.getAppointmentType();
            if (customerIDToDelete == customerIDToCheck) {
                String deleteStatementAppointments = "DELETE FROM appointments WHERE Appointment_ID = ?";
                DBQuery.setPreparedStatement(DBConnection.getConnection(), deleteStatementAppointments);
                PreparedStatement psAppointments = DBQuery.getPreparedStatement();
                psAppointments.setInt(1, appointmentIDToDelete);
                psAppointments.execute();
                JOptionPane.showMessageDialog(null,
                        "\nAppointment_ID: " + appointmentIDToDelete +
                        "\nType of Appointment: " + appointmentTypeToDisplay,
                        "Appointment Deleted From Database", JOptionPane.INFORMATION_MESSAGE);
            }
        }
        ps.setInt(1, customerIDToDelete);
        ps.execute();
        populateCustomerDataToTableView();
        resetBoxes();
        JOptionPane.showMessageDialog(null,
                "Customer ID: " + customerIDToDelete,
                "Customer deleted from database", JOptionPane.INFORMATION_MESSAGE);

    }

    // scene changes -----
    /**
     *
     * @param event on radio button press - switch from CustomerForm to AppointmentForm
     * @throws IOException if exception has occurred
     */
    public void radioToAppointmentFormScene(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("../View/FXMLAppointmentForm.fxml")));
        Stage primaryStage = (Stage)((Node)event.getSource()).getScene().getWindow();
        primaryStage.setScene(new Scene(root, 900, 600));
    }

    /**
     *
     * @param event on radio button press - switch from CustomerForm to ReportForm
     * @throws IOException if exception has occurred
     */
    public void radioToReportFormScene(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("../View/FXMLReportForm.fxml")));
        Stage primaryStage = (Stage)((Node)event.getSource()).getScene().getWindow();
        primaryStage.setScene(new Scene(root, 900, 600));
    }
}
