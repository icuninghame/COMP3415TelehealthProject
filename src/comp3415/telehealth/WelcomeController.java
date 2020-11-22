package comp3415.telehealth;

import comp3415.telehealth.db.Login;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class WelcomeController implements Initializable {

    // Variable names used here *must match* the fx:id set in the FXML file for that component.
    @FXML private Label welcomeText;
    @FXML private Button loginBtn;
    @FXML private TextField usernameField;
    @FXML private PasswordField passwordField;
    @FXML private ChoiceBox userTypePicker;

    /**
     * Initializes the controller class
     * @param location
     * @param resources
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {

        // (Initial attributes set in the welcome.fxml file)

        welcomeText.setText("Welcome to LU Telehealth! \n" + "Please log in or register below.");
        userTypePicker.setTooltip(new Tooltip("Select the type of user you are."));
        userTypePicker.setItems(FXCollections.observableArrayList(
                "Patient",
                new Separator(),
                "Doctor")
        );
        userTypePicker.setValue("Patient");

    }


    /**
     * This method is called when the login button is pushed.
     * Handles the user's log in request.
     * @param event the (Mouse)Event associated with this method call
     */
    public void loginUser(ActionEvent event) {
        // Required variables
        Login log = new Login();
        String userType = "Patient";
        Parent dashViewParent;

        try{
            // storing login information from user input:
            String usertype;
            String username = usernameField.getText();
            String password = new String(passwordField.getText());

            // User type selected:
            userType = userTypePicker.getValue().toString();
            // Set the appropriate dashboard based on user selection:
            if (userType.equals("Doctor"))
                dashViewParent = FXMLLoader.load(getClass().getResource("view/doctorDashboard.fxml"));
            else
                dashViewParent = FXMLLoader.load(getClass().getResource("view/patientDashboard.fxml"));

            // Login user, then show the dashboard.
            if(Login.isLogin(username, password, userType)){
                // Prepare the scene and stage:
                Scene dashViewScene = new Scene(dashViewParent);

                // This line gets the Stage (window) info from the button being clicked
                Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();

                // Set the window to the new scene:
                window.setScene(dashViewScene);
                window.show();
            }
            else{ // if username password incorrect
                welcomeText.setText("Invalid credentials. Please try again.");
            }
        }
        catch(Exception e){
            welcomeText.setText("Login Error. Please try again.");
        }

    }


    /**
     * This method is called when the register button is pushed.
     * Handles the user's registration request.
     * @param event the (Mouse)Event associated with this method call
     */
    public void registerUser(ActionEvent event) throws IOException
    {

    }

}
