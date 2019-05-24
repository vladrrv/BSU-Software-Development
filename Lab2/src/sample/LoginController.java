package sample;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginController extends Controller {
    @FXML private TextField tfLogin;
    @FXML private TextField tfPassword;

    private WindowController nextStage(String fxmlName, String title) {
        WindowController wc = null;
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlName));
            Parent root = loader.load();
            wc = loader.getController();
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.setTitle(title);
            wc.setStage(stage);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return wc;
    }


    @FXML private void onSignIn() {
        String login = tfLogin.getText(), password = tfPassword.getText();
        long userId = DatabaseManager.getLoginId(login, password);
        if (userId > 0) {
            User user = new User(userId);
            getStage().hide();
            User.UserType userType = DatabaseManager.getUserType(user);
            user.setType(userType);
            String userName = DatabaseManager.getUserName(user);
            user.setName(userName);
            WindowController wc;
            switch (userType) {
                case STUDENT: {
                    wc = nextStage("StudentWindow.fxml", "Student Window");
                    break;
                }
                case PROFESSOR: {
                    wc = nextStage("ProfessorWindow.fxml", "Professor Window");
                    break;
                }
                case REGISTRAR: {
                    wc = nextStage("RegistrarWindow.fxml", "Registrar Window");
                    break;
                }
                default: return;
            }
            wc.setUser(user);
            wc.init();
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Login error");
            alert.setHeaderText("");
            alert.setContentText("Incorrect login/password");
            alert.showAndWait();
        }
    }
}
