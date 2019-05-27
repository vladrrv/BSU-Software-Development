package sample;

import javafx.stage.Modality;
import javafx.stage.Stage;

public class ModalController extends Controller {

    void init(Stage parentStage) {
        Stage stage = getStage();
        stage.initOwner(parentStage);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setResizable(false);
        stage.showAndWait();
    }

}
