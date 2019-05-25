package sample;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class RegisterForCoursesController extends Controller {

    private ObservableList<CourseOffering> offeringsList;

    @FXML private TableView<CourseOffering> tableView;

    void init(long studentId) {
        var cols = tableView.getColumns();
        var courseCol = cols.get(0);
        var techerCol = cols.get(1);
        var primaryCol = cols.get(2);
        var alternateCol = cols.get(3);
        courseCol.setCellValueFactory(new PropertyValueFactory<>("course"));
        techerCol.setCellValueFactory(new PropertyValueFactory<>("teacher"));
        primaryCol.setCellValueFactory(new PropertyValueFactory<>("primary"));
        primaryCol.setCellFactory(tc -> new CheckBoxTableCell<>());
        alternateCol.setCellValueFactory(new PropertyValueFactory<>("alternate"));
        alternateCol.setCellFactory(tc -> new CheckBoxTableCell<>());

        offeringsList = DatabaseManager.getOfferings(studentId);
        tableView.setItems(offeringsList);

        Stage stage = getStage();
        stage.setResizable(false);
        stage.showAndWait();
    }

    @FXML private void onApply() {
        int countAlternate = 0, countPrimary = 0;
        for (var offering : offeringsList) {
            countPrimary += offering.isPrimary()? 1: 0;
            countAlternate += offering.isAlternate()? 1: 0;
        }
        if (countPrimary == 4 && countAlternate == 2) {
            getStage().close();
            // TODO: update database according to selection
        } else {
            showError("You must select 4 primary and 2 alternate courses.");
        }
    }
    @FXML private void onCancel() {
        getStage().close();
    }
}
