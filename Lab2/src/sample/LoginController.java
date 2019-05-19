package sample;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;

public class LoginController {
    @FXML private TextField tfLogin;
    @FXML private TextField tfPassword;

    @FXML private void onSignIn() {
        String login = tfLogin.getText(), password = tfPassword.getText();
        Alert alert = new Alert(Alert.AlertType.INFORMATION, "Result");
        if (DatabaseManager.doesUserExist(login, password)) {
            alert.setContentText("Success");
        } else {
            alert.setContentText("Fail");
        }
        alert.showAndWait();
    }
}
