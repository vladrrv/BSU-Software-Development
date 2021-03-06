package sample;

import javafx.fxml.FXML;
import javafx.scene.control.Button;

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
                (RegisterForCoursesController) nextStage("forms/RegisterForCoursesForm.fxml", "Register for Courses");
        c.init(getStage(), studentId);
    }

    @FXML private void onViewGrades() {
        ViewGradesController c =
                (ViewGradesController) nextStage("forms/ViewGradesForm.fxml", "View Grades");
        c.init(getStage(), studentId);
    }

}
