package sample;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;

public class RegistrarWindowController extends WindowController {

    @FXML private Button buttonSwitchRegistration;
    @FXML private Label labelSwitchRegistration;
    @FXML private ListView<User> listViewStudents;
    @FXML private ListView<User> listViewProfessors;

    private ObservableList<User> studentList;
    private ObservableList<User> professorList;

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
        studentList = DatabaseManager.getStudents();
        professorList = DatabaseManager.getProfessors();
        listViewStudents.setItems(studentList);
        listViewProfessors.setItems(professorList);
    }

    @FXML private void onSwitchRegistration() {
        DatabaseManager.switchRegistration();
        switchButtonLabel();
    }

    @FXML private void onAddProfessor() {

    }

    @FXML private void onEditProfessor() {

    }

    @FXML private void onDeleteProfessor() {

    }

    @FXML private void onAddStudent() {

    }

    @FXML private void onEditStudent() {

    }

    @FXML private void onDeleteStudent() {

    }
}

