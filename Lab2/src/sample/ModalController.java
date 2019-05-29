package sample;

import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class ModalController extends Controller {

    void init(Stage parentStage) {
        Stage stage = getStage();
        stage.getIcons().add(new Image(getClass().getResourceAsStream("../icon_app.png")));
        stage.initOwner(parentStage);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setResizable(false);
        stage.showAndWait();
    }

}
