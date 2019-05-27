package sample;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class ViewGradesController extends ModalController {

    private ObservableList<Grade> gradesList;
    @FXML private TableView<Grade> tableView;

    void init(Stage parentStage, long studentId) {
        var cols = tableView.getColumns();
        var courseCol = cols.get(0);
        var gradeCol = cols.get(1);
        courseCol.setCellValueFactory(new PropertyValueFactory<>("course"));
        gradeCol.setCellValueFactory(new PropertyValueFactory<>("grade"));

        gradesList = DatabaseManager.getGradesForStudent(studentId);
        tableView.setItems(gradesList);

        super.init(parentStage);
    }
    @FXML private void onOK() {
        getStage().close();
    }
}
