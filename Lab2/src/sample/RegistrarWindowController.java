package sample;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;

public class RegistrarWindowController extends WindowController {

    @FXML private Button buttonSwitchRegistration;
    @FXML private Label labelSwitchRegistration;
    @FXML private ListView<User> listViewStudents;
    @FXML private ListView<User> listViewProfessors;

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
