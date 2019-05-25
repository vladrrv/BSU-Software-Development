package sample;

import javafx.fxml.FXML;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class RegisterForCoursesController extends Controller {

    private long studentId;

    @FXML private TableView<CourseOffering> tableView;

    void init(long studentId) {
        this.studentId = studentId;

        var cols = tableView.getColumns();
        cols.get(0).setCellValueFactory(new PropertyValueFactory<>("course"));
        cols.get(1).setCellValueFactory(new PropertyValueFactory<>("teacher"));
        cols.get(2).setCellValueFactory(new PropertyValueFactory<>("primary"));
        cols.get(2).setCellFactory(tc -> new CheckBoxTableCell<>());
        cols.get(3).setCellValueFactory(new PropertyValueFactory<>("alternate"));
        cols.get(3).setCellFactory(tc -> new CheckBoxTableCell<>());

        tableView.setItems(DatabaseManager.getOfferings(studentId));

        Stage stage = getStage();
        stage.setResizable(false);
        stage.showAndWait();
    }

    @FXML private void onApply() {
        var offerings = tableView.getItems();
        int countAlternate = 0, countPrimary = 0;
        for (var offering : offerings) {
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
