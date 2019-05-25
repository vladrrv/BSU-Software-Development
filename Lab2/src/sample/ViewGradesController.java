package sample;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class ViewGradesController extends Controller {

    private ObservableList<Grade> gradesList;
    @FXML private TableView<Grade> tableView;

    void init(long studentId) {
        var cols = tableView.getColumns();
        var courseCol = cols.get(0);
        var gradeCol = cols.get(1);
        courseCol.setCellValueFactory(new PropertyValueFactory<>("course"));
        gradeCol.setCellValueFactory(new PropertyValueFactory<>("grade"));

        gradesList = DatabaseManager.getGrades(studentId);
        tableView.setItems(gradesList);

        Stage stage = getStage();
        stage.setResizable(false);
        stage.showAndWait();
    }
    @FXML private void onOK() {
        getStage().close();
    }
}
