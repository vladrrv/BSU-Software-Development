package sample;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class StudentWindowController extends WindowController {

    @FXML private Label labelCourse;
    @FXML private Label labelGroup;
    @FXML private Button buttonRegister;
    @FXML private Button buttonViewGrades;

    @Override
    void init() {
        super.init();
    }


}
