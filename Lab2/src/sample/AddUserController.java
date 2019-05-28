package sample;

import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

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

    @FXML void onOK() {
        // TODO: check if email already exists, if so, display error message and don't close
        isOK = true;
        getStage().close();
    }

    @FXML void onCancel() {
        isOK = false;
        getStage().close();
    }


    @FXML void onBrowse() {
        
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
