package sample;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class ProfessorWindowController extends WindowController {

    private long professorId;

    @FXML private Button buttonSelectCourses;
    @FXML private Button buttonSubmitGrades;

    @Override
    void init() {
        super.init();
        boolean isRegOpen = DatabaseManager.isRegistrationOpen();
        buttonSelectCourses.setDisable(!isRegOpen);
        buttonSubmitGrades.setDisable(isRegOpen);
        professorId = DatabaseManager.getProfessorId(getUser());
    }

    @FXML private void onSelectCourses() {
        SelectCoursesController c =
                (SelectCoursesController) nextStage("forms/SelectCoursesForm.fxml", "Select Courses");
        c.setParentStage(getStage());
        c.init(professorId);
    }

    @FXML private void onSubmitGrades() {
        SubmitGradesController c =
                (SubmitGradesController) nextStage("forms/SubmitGradesForm.fxml", "Submit Grades");
        c.setParentStage(getStage());
        c.init(professorId);
    }
}
