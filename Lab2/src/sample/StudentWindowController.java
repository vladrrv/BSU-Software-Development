package sample;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class StudentWindowController extends WindowController {

    private long studentId;

    @FXML private Button buttonRegister;
    @FXML private Button buttonViewGrades;

    @Override
    void init() {
        super.init();
        boolean isRegOpen = DatabaseManager.isRegistrationOpen();
        buttonRegister.setDisable(!isRegOpen);
        buttonViewGrades.setDisable(isRegOpen);
        studentId = DatabaseManager.getStudentId(getUser());
    }

    @FXML private void onRegister() {
        RegisterForCoursesController c =
                (RegisterForCoursesController) nextStage("RegisterForCoursesForm.fxml", "Register for Courses");
        c.setParentStage(getStage());
        c.init(studentId);
    }

}
