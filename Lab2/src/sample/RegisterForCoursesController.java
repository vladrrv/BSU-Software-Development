package sample;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class RegisterForCoursesController extends ModalController {

    private final int primaryLimit = 2;
    private final int alternateLimit = 1;

    private long studentId;
    private ObservableList<CourseOffering> offeringsList;

    @FXML private TableView<CourseOffering> tableView;

    void init(Stage parentStage, long studentId) {
        this.studentId = studentId;
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

        offeringsList = DatabaseManager.getOfferingsForStudent(studentId);
        tableView.setItems(offeringsList);

        super.init(parentStage);
    }

    @FXML private void onApply() {
        int countAlternate = 0, countPrimary = 0;
        for (var offering : offeringsList) {
            countPrimary += offering.isPrimary()? 1: 0;
            countAlternate += offering.isAlternate()? 1: 0;
        }
        if (countPrimary == primaryLimit && countAlternate == alternateLimit) {
            getStage().close();
            DatabaseManager.updateStudentOfferings(studentId, offeringsList);
        } else {
            showError(String.format(
                    "You must select %d primary and %d alternate courses.",
                    primaryLimit, alternateLimit));
        }
    }
    @FXML private void onCancel() {
        getStage().close();
    }
}
