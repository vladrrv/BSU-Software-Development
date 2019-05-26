package sample;

import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class SubmitGradesController extends Controller {

    void init(long professorId) {

        Stage stage = getStage();
        stage.setResizable(false);
        stage.showAndWait();
    }
}
