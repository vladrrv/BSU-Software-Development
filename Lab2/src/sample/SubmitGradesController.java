package sample;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class SubmitGradesController extends Controller {

    @FXML private ComboBox<Roster> comboBox;
    @FXML private TableView<Grade> tableView;
    private ObservableList<Roster> rosterList;

    void init(long professorId) {
        rosterList = DatabaseManager.getRosters(professorId);
        comboBox.setItems(rosterList);


        Stage stage = getStage();
        stage.setResizable(false);
        stage.showAndWait();
    }

    @FXML private void onSelect() {
        Roster r = comboBox.getValue();
        System.out.println(r.getRosterId());
    }

    @FXML private void onApply() {

        getStage().close();
    }

    @FXML private void onCancel() {
        getStage().close();
    }
}
