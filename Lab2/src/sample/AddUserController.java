package sample;

import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;

public class AddUserController extends ModalController {

    private boolean isOK = false;
    private String initialEmail;

    @FXML private TextField tfEmail;
    @FXML private PasswordField tfPassword;
    @FXML private TextField tfName;
    @FXML private TextField tfInfo1;
    @FXML private TextField tfInfo2;
    @FXML private TextField tfPhoto;

    @Override
    void init(Stage parentStage) {

        super.init(parentStage);
    }

    void init(Stage parentStage, User user) {
        initialEmail = user.getEmail();
        tfEmail.setText(user.getEmail());
        tfPassword.setText(user.getPassword());
        tfName.setText(user.getName());
        if (user.isStudent()) {
            tfInfo1.setText(String.valueOf(((Student)user).getCourse()));
            tfInfo2.setText(String.valueOf(((Student)user).getGroup()));
        } else if (user.isProfessor()) {
            tfInfo1.setText(((Professor)user).getDegree());
            tfInfo2.setText(((Professor)user).getDepartment());
        }
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
        if (!email.equals(initialEmail) && DatabaseManager.doesEmailExist(email)) {
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
            tfPhoto.setText(file.toURI().toString());
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
    String getInfo1() {
        return tfInfo1.getText();
    }
    String getInfo2() {
        return tfInfo2.getText();
    }
    String getPhotoURL() {
        return tfPhoto.getText();
    }

}
