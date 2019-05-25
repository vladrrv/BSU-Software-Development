package sample;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class LoginController extends Controller {
    @FXML private TextField tfLogin;
    @FXML private TextField tfPassword;


    @FXML private void onSignIn() {
        String login = tfLogin.getText(), password = tfPassword.getText();
        long userId = DatabaseManager.getLoginId(login, password);
        if (userId > 0) {
            getStage().hide();
            User user = new User(userId);
            DatabaseManager.setUserFields(user);
            WindowController wc;
            switch (user.getType()) {
                case STUDENT: {
                    wc = (WindowController) nextStage("StudentWindow.fxml", "Student Window");
                    break;
                }
                case PROFESSOR: {
                    wc = (WindowController) nextStage("ProfessorWindow.fxml", "Professor Window");
                    break;
                }
                case REGISTRAR: {
                    wc = (WindowController) nextStage("RegistrarWindow.fxml", "Registrar Window");
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
