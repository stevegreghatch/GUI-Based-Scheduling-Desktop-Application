/**
 * @author Steven Hatch - Student ID: #--
 */

package Main;

import DBUtility.DBConnection;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.util.Objects;

public class Main extends Application {

    /**
     *
     * @param primaryStage the stage to set
     * @throws Exception if exception has occurred
     */
    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("../View/FXMLLogInForm.fxml")));
        primaryStage.setTitle("QAM1 Task 1");
        primaryStage.setScene(new Scene(root, 900, 600));
        primaryStage.show();
    }

    /**
     *
     * @param args program execution
     */
    public static void main(String[] args) {
        DBConnection.startConnection();
        launch(args);
        DBConnection.closeConnection();
        System.out.println("\nThank you for evaluating my project");

    }
}
