package Controller;

import DBAccess.*;
import Model.*;
import Reports.AppointmentReportMonth;
import Reports.AppointmentReportType;
import Reports.CustomerReportFirstLevelDivision;
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
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Objects;

public class ReportForm {

    @FXML ToggleGroup formControlToggleGroup;

    // report 1 -- customer appointment total
    @FXML Tab customerAppointmentsTab;
    // table view 1 -- appointment type
    @FXML TableView<AppointmentReportType> customerAppointmentsTypeTableView;
    @FXML TableColumn<AppointmentReportType, String> customerAppointmentsTypeCol;
    @FXML TableColumn<AppointmentReportType, Integer> customerAppointmentsTotalTypeCol;
    // table view 2 -- appointment month
    @FXML TableView<AppointmentReportMonth> customerAppointmentsMonthTableView;
    @FXML TableColumn<AppointmentReportMonth, String> customerAppointmentsMonthCol;
    @FXML TableColumn<AppointmentReportMonth, Integer> customerAppointmentsTotalMonthCol;

    // report 2 -- contact appointments
    @FXML Tab contactAppointmentsTab;
    @FXML TableView<Appointments> contactAppointmentsTableView;
    @FXML TableColumn<Appointments, Integer> contactAppointmentsIDCol;
    @FXML TableColumn<Appointments, String> contactAppointmentsTitleCol;
    @FXML TableColumn<Appointments, String> contactAppointmentTypeCol;
    @FXML TableColumn<Appointments, String> contactAppointmentsDescriptionCol;
    @FXML TableColumn<Appointments, LocalDateTime> contactAppointmentsStartDateAndTimeCol;
    @FXML TableColumn<Appointments, LocalDateTime> contactAppointmentsEndDateAndTimeCol;
    @FXML TableColumn<Appointments, Integer> contactAppointmentsCustomerIDCol;
    @FXML Label contactLabel;
    @FXML ComboBox<String> contactComboBox;

    // report 3 -- customers by first-level division
    @FXML Tab customerFirstLevelDivisionTab;
    @FXML TableView<CustomerReportFirstLevelDivision> customerFirstLevelDivisionTableView;
    @FXML TableColumn<CustomerReportFirstLevelDivision, String> firstLevelDivisionCol;
    @FXML TableColumn<CustomerReportFirstLevelDivision, ArrayList<String>> customerListCol;


    /**
     *
     * @throws SQLException if exception has occurred
     */
    public void initialize() throws SQLException {
        contactLabel.setVisible(false);
        contactComboBox.setVisible(false);
        contactComboBox.setEditable(true);
        contactComboBox.getEditor().setEditable(false);

        // report 1 -- table view 2 -- appointment type
        customerAppointmentsTypeCol.setCellValueFactory(new PropertyValueFactory<>("appointmentType"));
        customerAppointmentsTotalTypeCol.setCellValueFactory(new PropertyValueFactory<>("appointmentTotal"));

        // report 1 -- table view 2 -- appointment month
        customerAppointmentsMonthCol.setCellValueFactory(new PropertyValueFactory<>("appointmentMonth"));
        customerAppointmentsTotalMonthCol.setCellValueFactory(new PropertyValueFactory<>("appointmentTotal"));

        // report 2 -- appointment by contact
        contactAppointmentsIDCol.setCellValueFactory(new PropertyValueFactory<>("appointmentID"));
        contactAppointmentsTitleCol.setCellValueFactory(new PropertyValueFactory<>("appointmentTitle"));
        contactAppointmentTypeCol.setCellValueFactory(new PropertyValueFactory<>("appointmentType"));
        contactAppointmentsDescriptionCol.setCellValueFactory(new PropertyValueFactory<>("appointmentDescription"));
        contactAppointmentsStartDateAndTimeCol.setCellValueFactory(new PropertyValueFactory<>("start"));
        contactAppointmentsEndDateAndTimeCol.setCellValueFactory(new PropertyValueFactory<>("end"));
        contactAppointmentsCustomerIDCol.setCellValueFactory(new PropertyValueFactory<>("customerID"));

        // report 3 -- customers by first-level division
        firstLevelDivisionCol.setCellValueFactory(new PropertyValueFactory<>("firstLevelDivision"));
        customerListCol.setCellValueFactory(new PropertyValueFactory<>("customerList"));
    }

    // report 1 -----
    /**
     * gets list of appointment types from all appointment data
     * gets list of appointment types without duplicates
     * gets total of type occurrences, creates instance of class and sets list for tableview
     *
     * @throws SQLException if exception has occurred
     */
    public void populateAppointmentTypesAndTotals() throws SQLException {
        String typeToSet;
        int totalToSet;
        ObservableList<Appointments> allAppointmentData = DBAppointments.getAllAppointments();
        ObservableList<String> appointmentTypes = FXCollections.observableArrayList();
        allAppointmentData.forEach(appointments -> appointmentTypes.add(appointments.getAppointmentType()));
        ObservableList<String> appointmentTypesWithoutDuplicates = FXCollections.observableArrayList();
        for (Appointments appointment : allAppointmentData) {
            String type = appointment.getAppointmentType();
            if (!appointmentTypesWithoutDuplicates.contains(type)) {
                appointmentTypesWithoutDuplicates.add(type);
            }
        }
        ObservableList<AppointmentReportType> appointmentReportTypes = FXCollections.observableArrayList();
        for (String type : appointmentTypesWithoutDuplicates) {
            int total = Collections.frequency(appointmentTypes, type);
            typeToSet = type;
            totalToSet = total;
            AppointmentReportType typeInstance = new AppointmentReportType(typeToSet, totalToSet);
            appointmentReportTypes.add(typeInstance);
        }
        customerAppointmentsTypeTableView.setItems(appointmentReportTypes);
    }

    /**
     * gets list of appointment months from all appointment data
     * gets list of months without duplicates
     * gets total of month occurrences, creates instance of class and sets list for tableview
     *
     * @throws SQLException if exception has occurred
     */
    public void populateAppointmentMonthsAndTotals() throws SQLException {
        String monthToSet;
        int totalToSet;
        ObservableList<Appointments> allAppointmentData = DBAppointments.getAllAppointments();
        ObservableList<Month> appointmentMonths = FXCollections.observableArrayList();
        for (Appointments appointment : allAppointmentData) {
            Month month = appointment.getStart().getMonth();
            appointmentMonths.add(month);
        }
        ObservableList<Month> appointmentMonthsWithoutDuplicates = FXCollections.observableArrayList();
        for (Month month : appointmentMonths) {
            if (!appointmentMonthsWithoutDuplicates.contains(month)) {
                appointmentMonthsWithoutDuplicates.add(month);
            }
        }
        ObservableList<AppointmentReportMonth> appointmentReportMonths = FXCollections.observableArrayList();
        for (Month month : appointmentMonthsWithoutDuplicates) {
            int total = Collections.frequency(appointmentMonths, month);
            monthToSet = month.name();
            totalToSet = total;
            AppointmentReportMonth monthInstance = new AppointmentReportMonth(monthToSet, totalToSet);
            appointmentReportMonths.add(monthInstance);
        }
        customerAppointmentsMonthTableView.setItems(appointmentReportMonths);
    }

    /**
     * combines above functions
     * @throws SQLException if exception has occurred
     */
    public void populateTotalCustomerAppointments() throws SQLException {
        populateAppointmentTypesAndTotals();
        populateAppointmentMonthsAndTotals();
    }

    // report 2 -----

    /**
     * shows selection box and label
     * sets contact names in comboBox
     *
     * @throws SQLException if exception has occurred
     */
    public void populateAppointmentsByContact() throws SQLException {
        contactLabel.setVisible(true);
        contactComboBox.setVisible(true);
        ObservableList<Contacts> allContacts = DBContacts.getAllContacts();
        ObservableList<String> contactNames = FXCollections.observableArrayList();
        for (Contacts contacts : allContacts) {
            String contactName = contacts.getContactName();
            contactNames.add(contactName);
        }
        contactComboBox.setItems(contactNames);
    }

    /**
     * gets selected contact name from combo box
     * matches selected name with contact ID from all contact data
     * gets selected contact appointment data from all appointment data
     *
     * @throws SQLException if exception has occurred
     */
    public void getSpecificContactAppointmentData() throws SQLException {
        int selectedContactID = 0;
        Appointments selectedContactAppointmentData;
        String selectedContactName = contactComboBox.getSelectionModel().getSelectedItem();
        if (selectedContactName != null) {

            ObservableList<Contacts> allContactData = DBContacts.getAllContacts();
            for (Contacts contact : allContactData) {
                if (selectedContactName.equals(contact.getContactName())) {
                    selectedContactID = contact.getId();
                }
            }
            ObservableList<Appointments> allAppointmentData = DBAppointments.getAllAppointments();
            ObservableList<Appointments> appointmentDataForSelectedContact = FXCollections.observableArrayList();
            for (Appointments appointment : allAppointmentData) {
                if (selectedContactID == appointment.getContactID()) {
                    selectedContactAppointmentData = appointment;
                    appointmentDataForSelectedContact.add(selectedContactAppointmentData);
                }
            }
            contactAppointmentsTableView.setItems(appointmentDataForSelectedContact);
        }
    }

    // report 3 -----

    /**
     * matches first-level division ID with customer division ID and prevents duplicates
     *
     * @throws SQLException if exception has occurred
     */
    public void populateCustomersByFirstLevelDivision() throws SQLException {
        String firstLevelDivisionToSet ;
        ArrayList<String> customerListToSet;
        CustomerReportFirstLevelDivision record;
        String previousSetDivisionName = "";

        ObservableList<FirstLevelDivisions> allFirstLevelDivisionData = DBFirstLevelDivisions.getAllFirstLevelDivisions();
        ObservableList<Customers> allCustomerData = DBCustomers.getAllCustomers();
        ObservableList<CustomerReportFirstLevelDivision> observableListToSet = FXCollections.observableArrayList();

        for (FirstLevelDivisions firstLevelDivision : allFirstLevelDivisionData) {
            customerListToSet = new ArrayList<>();
            for (Customers customer : allCustomerData) {
                if (firstLevelDivision.getDivisionID() == customer.divisionID) {
                    firstLevelDivisionToSet = firstLevelDivision.getDivisionName();
                    customerListToSet.add(customer.getCustomerName());
                    record = new CustomerReportFirstLevelDivision(firstLevelDivisionToSet, customerListToSet);
                    if (!record.getFirstLevelDivision().equals(previousSetDivisionName)) {
                        observableListToSet.add(record);
                        previousSetDivisionName = record.getFirstLevelDivision();
                    }
                }
            }
        }
        customerFirstLevelDivisionTableView.setItems(observableListToSet);
    }

    // scene changes  -----

    /**
     *
     * @param event on radio button press - switch from ReportForm to CustomerForm
     * @throws IOException if exception has occurred
     */
    public void radioToCustomerFormScene(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("../View/FXMLCustomerForm.fxml")));
        Stage primaryStage = (Stage)((Node)event.getSource()).getScene().getWindow();
        primaryStage.setScene(new Scene(root, 900, 600));
    }

    /**
     *
     * @param event on radio button press - switch from ReportForm to AppointmentForm
     * @throws IOException if exception has occurred
     */
    public void radioToAppointmentFormScene(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("../View/FXMLAppointmentForm.fxml")));
        Stage primaryStage = (Stage)((Node)event.getSource()).getScene().getWindow();
        primaryStage.setScene(new Scene(root, 900, 600));
    }
}
