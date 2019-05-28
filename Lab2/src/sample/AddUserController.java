package sample;

import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;

public class AddUserController extends ModalController {

    private boolean isOK = false;

    @FXML private TextField tfEmail;
    @FXML private PasswordField tfPassword;
    @FXML private TextField tfName;
    @FXML private TextField tfInfo;
    @FXML private TextField tfPhoto;

    @Override
    void init(Stage parentStage) {

        super.init(parentStage);
    }

    void init(Stage parentStage, User user) {
        tfEmail.setText(user.getEmail());
        tfPassword.setText(user.getPassword());
        tfName.setText(user.getName());
        tfInfo.setText(user.getInfo());
        super.init(parentStage);
    }

    private boolean isEmailValid(String email) {
        String regex = "^[\\w-_.+]*[\\w-_.]@([\\w]+\\.)+[\\w]+[\\w]$";
        return email.matches(regex);
    }
    private boolean isPasswordValid(String password) {
        String regex = "^.{6,}$";
        return password.matches(regex);
    }

    @FXML void onOK() {
        String email = tfEmail.getText();
        String password = tfPassword.getText();
        if (!isEmailValid(email)) {
            showError("Invalid email address");
            return;
        }
        if (DatabaseManager.doesEmailExist(email)) {
            showError("This email is already used");
            return;
        }
        if (!isPasswordValid(password)) {
            showError("Invalid password (must be at least 6 characters long)");
            return;
        }
        isOK = true;
        getStage().close();
    }

    @FXML void onCancel() {
        isOK = false;
        getStage().close();
    }

    @FXML void onBrowse() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Image File");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.bmp")
        );
        File file = fileChooser.showOpenDialog(getStage());
        if (file != null) {
            tfPhoto.setText(file.getAbsolutePath());
        }
    }

    boolean isOK() {
        return isOK;
    }

    String getEmail() {
        return tfEmail.getText();
    }
    String getPassword() {
        return tfPassword.getText();
    }
    String getName() {
        return tfName.getText();
    }
    String getInfo() {
        return tfInfo.getText();
    }
}
