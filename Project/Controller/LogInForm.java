package Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.Node;
import javafx.stage.Stage;
import java.io.*;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;
import javax.swing.JOptionPane;

public class LogInForm {

    @FXML public Label logInFormTitleLabel;
    @FXML public TextField userIDTextField;
    @FXML public TextField passwordTextField;
    @FXML public Button submitButton;
    @FXML public Label userLocationLabel;
    String logInErrorMessage ="Invalid user ID or password";
    String logInErrorTitle = "Log-In Failed";
    boolean successfulLogIn = false;

    public void initialize() {
        languageCheck();
    }

    /**
     * sets current user location as userLocationLabel
     * displays french if user’s computer language setting = fr
     * gets all translated labels, text fields, button text, and error message text from rb properties file
     * updates formatting for new sizes
     */
    public void languageCheck() {
        ZoneId currentZone = ZoneId.systemDefault();
        userLocationLabel.setText("User Location: " + currentZone);
        Locale French = new Locale("fr", "FR");
        ResourceBundle rb = ResourceBundle.getBundle("Controller/Nat", Locale.FRENCH);
        if(Locale.getDefault().getLanguage().equals("fr")) {
            Locale.setDefault(French);
            logInFormTitleLabel.setText((rb.getString("Log-In,Form")).replaceAll(",", " "));
            userIDTextField.setPromptText((rb.getString("user,ID")).replaceAll(",", " "));
            passwordTextField.setPromptText(rb.getString("password").replaceAll(",", " "));
            submitButton.setText(rb.getString("Submit"));
            int indexOfSeparation = (currentZone.toString()).indexOf("/");
            String countryToPrint = (currentZone.toString()).substring(0, indexOfSeparation);
            String countryToPrintFR;
            if (countryToPrint.equals("Pacific") || countryToPrint.equals("America") || countryToPrint.equals("Europe")) {
                countryToPrintFR = (rb.getString(countryToPrint));
                int indexOfEnd = (currentZone.toString()).length();
                String locationToPrint = (currentZone.toString()).substring(indexOfSeparation, indexOfEnd);
                userLocationLabel.setText((rb.getString("User,Location")).replaceAll(",", " ") +
                        ": " + countryToPrintFR + locationToPrint);
            }
            else {
                userLocationLabel.setText((rb.getString("User,Location")).replaceAll(",", " ") +
                        ": " + currentZone);
            }
            logInErrorMessage = rb.getString("Invalid,user,ID,or,password").replaceAll(",", " ");
            logInErrorTitle = rb.getString("Log-In,Failed").replaceAll(",", " ");
            logInFormTitleLabel.setLayoutX(190);
            userLocationLabel.setLayoutX(350);
        }
    }

    /**
     *
     * @param event upon user pressing submit button, function verifies credentials
     *              if log-in attempt is successful, function switches scene, and checks for upcoming appointments.
     *              if unsuccessful, error message is displayed
     *
     * @throws IOException if exception has occurred
     * @throws SQLException if exception has occurred
     */
    public void logInValidation(ActionEvent event) throws IOException, SQLException {
        String userID = userIDTextField.getText();
        String password = passwordTextField.getText();
        if (userID.equals("test") && password.equals("test")) {
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("../View/FXMLCustomerForm.fxml")));
            Stage primaryStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            primaryStage.setScene(new Scene(root, 900, 600));
            CustomerForm login = new CustomerForm();
            login.checkForUpcomingAppointment();
            successfulLogIn = true;
        }
        else {
            JOptionPane.showMessageDialog(null,
                    logInErrorMessage,
                    logInErrorTitle,
                    JOptionPane.ERROR_MESSAGE);
            successfulLogIn = false;
        }
        trackUserActivity();
    }

    /**
     * logs user log-in activity to text file in root
     * appends new entries
     *
     * @throws IOException if exception has occurred
     */
    public void trackUserActivity() throws IOException {
        LocalDate attemptDate = LocalDateTime.now().toLocalDate();
        Timestamp attemptTimestamp = Timestamp.valueOf(LocalDateTime.now());
        FileWriter fileWriter = new FileWriter("login_activity.txt", true);
        PrintWriter outputFile = new PrintWriter(fileWriter);
        outputFile.print("Date: " + attemptDate + " -- ");
        outputFile.print("Timestamp: " + attemptTimestamp + " -- ");
        if (successfulLogIn) {
            outputFile.print("Status: Attempt Successful\n");
        }
        else {
            outputFile.print("Status: Attempt Not Successful\n");
        }

        outputFile.close();
    }
}
