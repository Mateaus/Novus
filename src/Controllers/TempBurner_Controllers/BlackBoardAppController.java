package Controllers.TempBurner_Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

import java.net.URL;
import java.util.ResourceBundle;

public class BlackBoardAppController implements Initializable {

    @FXML private WebView blackboardWV;
    private WebEngine webEngine;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        webEngine = blackboardWV.getEngine();
        webEngine.load("https://sso-dl.it.utsa.edu/");
    }

    public void reloadPage(ActionEvent event) {
        webEngine.reload();
    }
}
