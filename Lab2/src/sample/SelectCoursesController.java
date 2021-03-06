package sample;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class SelectCoursesController extends ModalController {

    private long professorId;
    private ObservableList<Course> courseList;

    @FXML private TableView<Course> tableView;

    void init(Stage parentStage, long professorId) {
        this.professorId = professorId;
        var cols = tableView.getColumns();
        var courseCol = cols.get(0);
        var selectedCol = cols.get(1);
        courseCol.setCellValueFactory(new PropertyValueFactory<>("description"));
        selectedCol.setCellValueFactory(new PropertyValueFactory<>("selected"));
        selectedCol.setCellFactory(tc -> new CheckBoxTableCell<>());
        courseList = DatabaseManager.getCoursesForProfessor(professorId);
        tableView.setItems(courseList);

        super.init(parentStage);
    }


    @FXML private void onApply() {
        getStage().close();
        DatabaseManager.updateCourseOfferings(professorId, courseList);
    }

    @FXML private void onCancel() {
        getStage().close();
    }
}
