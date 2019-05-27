package sample;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.ComboBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class SubmitGradesController extends Controller {

    @FXML private ComboBox<Roster> comboBox;
    @FXML private TableView<Grade> tableView;
    private ObservableList<Roster> rosterList;
    private Roster currentRoster;
    private ObservableList<Grade> gradesList;


    void init(long professorId) {
        rosterList = DatabaseManager.getRosters(professorId);
        comboBox.setItems(rosterList);

        Stage stage = getStage();
        stage.setResizable(false);
        stage.showAndWait();
    }

    @FXML private void onSelect() {
        currentRoster = comboBox.getValue();
        gradesList = DatabaseManager.getGradesForRoster(currentRoster.getRosterId());
        var cols = tableView.getColumns();
        var studCol = cols.get(0);
        TableColumn<Grade, String> gradeCol = (TableColumn)cols.get(1);
        studCol.setCellValueFactory(new PropertyValueFactory<>("student"));
        gradeCol.setCellValueFactory(new PropertyValueFactory<>("grade"));
        gradeCol.setCellFactory(ComboBoxTableCell.forTableColumn(Grade.GRADES_STR));
        tableView.setItems(gradesList);
    }

    @FXML private void onApply() {
        if (currentRoster != null) {
            DatabaseManager.updateGrades(currentRoster.getRosterId(), gradesList);
        }
        getStage().close();
    }

    @FXML private void onCancel() {
        getStage().close();
    }
}
