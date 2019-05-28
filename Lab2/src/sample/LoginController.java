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
            switch (user.getType()) {
                case STUDENT: {
                    wc = (WindowController) nextStage("forms/StudentWindow.fxml", "Student Window");
                    break;
                }
                case PROFESSOR: {
                    wc = (WindowController) nextStage("forms/ProfessorWindow.fxml", "Professor Window");
                    break;
                }
                case ADMIN: {
                    wc = (WindowController) nextStage("forms/RegistrarWindow.fxml", "Registrar Window");
                    break;
                }
                default: return;
            }
            wc.setUser(user);
            wc.init();
        } else {
            showError("Incorrect login/password");
        }
    }
}
