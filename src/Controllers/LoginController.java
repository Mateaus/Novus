package Controllers;

import Classes.Context;
import HttpRequests.HttpHandler;
import HttpRequests.LoginRequest;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import java.net.URL;
import java.util.Enumeration;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutionException;


public class LoginController implements Initializable {

    @FXML private Button loginBtn;
    @FXML private Pane logLayout;
    @FXML private TextField emailTF, passTF;
    @FXML private Label connectionLb;

    private String email, password;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Any actions done to buttons,etc will be passed through here first.
        // Example: Button1 changes when it's pressed. It will read this function
        // to fetch the action.

        loginBtn.setOnAction(
                event -> {
                    email = emailTF.getText().toString();
                    password = passTF.getText().toString();
                    try {
                        LoginRequest loginRequest = new LoginRequest(email, password);
                        HttpHandler httpHandler = new HttpHandler(loginRequest.getLoginRequestUrl(), loginRequest.getValuePairs());
                        HttpResponse httpResponse = httpHandler.HttpResponseRequest(httpHandler.HttpPostRequest());
                        String responsejson = EntityUtils.toString(httpResponse.getEntity());

                        JSONObject jsonObject = new JSONObject(responsejson);
                        String studentId = jsonObject.get("user_id").toString();
                        String studentName = jsonObject.get("name").toString();
                        Context.getInstance().currentUserInformation().setStudentId(studentId);
                        Context.getInstance().currentUserInformation().setStudentName(studentName);

                        if(loginRequest.checkRequest(responsejson).equals(true)){
                            // Stage now under windows, calls scene and loads the scene.
                            FXMLLoader loader = new FXMLLoader(getClass().getResource("/resources/layout/userInterface_layout.fxml"));
                            Pane userInterface = loader.load(); // userInterface holds the loader information
                            Scene scene = new Scene(userInterface); // new scene holding userInterface

                            Stage windows = (Stage) ((Node)event.getSource()).getScene().getWindow(); // hides previous window;
                            windows.setScene(scene);
                            windows.show();
                        } else {
                            connectionLb.setText("Wrong username or password");
                        }
                    } catch (Exception e) {
                        System.out.println(e);
                    }
                }
        );

        logLayout.setOnKeyPressed(
                 event -> {
                    switch (event.getCode()) {
                        case ENTER:
                            email = emailTF.getText().toString();
                            password = passTF.getText().toString();
                            try {
                                LoginRequest loginRequest = new LoginRequest(email, password);
                                HttpHandler httpHandler = new HttpHandler(loginRequest.getLoginRequestUrl(), loginRequest.getValuePairs());
                                HttpResponse httpResponse = httpHandler.HttpResponseRequest(httpHandler.HttpPostRequest());
                                String responsejson = EntityUtils.toString(httpResponse.getEntity());
                                EntityUtils.consume(httpResponse.getEntity());

                                JSONObject jsonObject = new JSONObject(responsejson);
                                String studentId = jsonObject.get("user_id").toString();
                                String studentName = jsonObject.get("name").toString();
                                Context.getInstance().currentUserInformation().setStudentId(studentId);
                                Context.getInstance().currentUserInformation().setStudentName(studentName);

                                if(loginRequest.checkRequest(responsejson).equals(true)){
                                    // Stage now under windows, calls scene and loads the scene.
                                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/resources/layout/userInterface_layout.fxml"));
                                    Pane userInterface = loader.load(); // userInterface holds the loader information
                                    Scene scene = new Scene(userInterface); // new scene holding userInterface
                                    // Calling the controller class before we load it to pass information.

                                    Stage windows = (Stage) ((Node)event.getSource()).getScene().getWindow(); // hides previous window;
                                    windows.setScene(scene);
                                    windows.show();
                                } else {
                                    connectionLb.setText("Wrong username or password");
                                }
                            } catch(Exception e) {

                            }
                    }
                }
        );
    }

    /* We will be calling this function every time we want to return to the main menu/log screen */
    public void changeToMainScreen(ActionEvent event) throws Exception {
        // Loads the main/log layout
        Pane main = (Pane) FXMLLoader.load(getClass().getResource("/resources/layout/login_layout.fxml"));
        Scene mainScreen = new Scene(main);

        // This line gets the Stage information
        Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
        window.setScene(mainScreen);
        window.show(); // changes to the new window
    }

    public void changeToRegisterScreen(ActionEvent event) throws Exception {
        // Loads the registration layout
        Pane registration = (Pane) FXMLLoader.load(getClass().getResource("/resources/layout/registration_layout.fxml"));
        Scene registrationScreen = new Scene(registration);

        // This line gets the Stage information
        Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
        window.setScene(registrationScreen);
        window.show(); // changes to the new window
    }

}
