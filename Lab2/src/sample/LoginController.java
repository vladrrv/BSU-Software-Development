package sample;

import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class LoginController extends Controller {
    @FXML private TextField tfLogin;
    @FXML private PasswordField tfPassword;

    @FXML private void onSignIn() {
        String login = tfLogin.getText(), password = tfPassword.getText();
        User user = DatabaseManager.getUser(login, password);
        if (user != null) {
            getStage().hide();
            WindowController wc;
            if (user instanceof Student) {
                wc = (WindowController) nextStage("forms/StudentWindow.fxml", "Student Window");
            } else if (user instanceof Professor) {
                wc = (WindowController) nextStage("forms/ProfessorWindow.fxml", "Professor Window");
            } else {
                wc = (WindowController) nextStage("forms/RegistrarWindow.fxml", "Registrar Window");
            }
            wc.setUser(user);
            wc.init();
        } else {
            showError("Incorrect login/password");
        }
    }
}
