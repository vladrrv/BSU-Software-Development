package sample;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class RegistrarWindowController extends WindowController {

    @FXML private Button buttonSwitchRegistration;
    @FXML private Label labelSwitchRegistration;

    private void switchButtonLabel() {
        if (DatabaseManager.isRegistrationOpen()) {
            buttonSwitchRegistration.setText("Close");
            labelSwitchRegistration.setText("Registration is open");
        } else {
            buttonSwitchRegistration.setText("Open");
            labelSwitchRegistration.setText("Registration is closed");
        }
    }

    @Override
    void init() {
        super.init();
        switchButtonLabel();
    }

    @FXML private void onSwitchRegistration() {
        DatabaseManager.switchRegistration();
        switchButtonLabel();
    }
}
