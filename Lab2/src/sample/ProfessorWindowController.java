package sample;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class ProfessorWindowController extends WindowController {

    @FXML private Button buttonSelectCourses;
    @FXML private Button buttonSubmitGrades;

    @Override
    void init() {
        super.init();
        boolean isRegOpen = DatabaseManager.isRegistrationOpen();
        buttonSelectCourses.setDisable(!isRegOpen);
        buttonSubmitGrades.setDisable(isRegOpen);
    }
}
