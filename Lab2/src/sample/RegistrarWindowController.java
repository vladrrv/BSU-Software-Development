package sample;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class RegistrarWindowController extends WindowController {

    @FXML private Button buttonSwitchRegistration;

    private void switchButtonLabel() {
        if (DatabaseManager.isRegistrationOpen()) {
            buttonSwitchRegistration.setText("Close Registration");
        } else {
            buttonSwitchRegistration.setText("Open Registration");
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
