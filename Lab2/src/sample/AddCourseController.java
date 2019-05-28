package sample;

import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;

public class AddCourseController extends ModalController {

    private boolean isOK = false;

    @FXML private TextField tfDescription;
    @FXML private TextField tfPrice;

    @Override
    void init(Stage parentStage) {
        super.init(parentStage);
    }

    void init(Stage parentStage, Course course) {
        tfDescription.setText(course.getDescription());
        tfPrice.setText(String.valueOf(course.getPrice()));
        super.init(parentStage);
    }

    private boolean isPriceValid(String email) {
        String regex = "^[\\w-_.+]*[\\w-_.]@([\\w]+\\.)+[\\w]+[\\w]$";
        return email.matches(regex);
    }

    @FXML void onOK() {
        String description = tfDescription.getText();
        String price = tfPrice.getText();
        if (!isPriceValid(price)) {
            showError("Invalid price");
            return;
        }
        isOK = true;
        getStage().close();
    }

    @FXML void onCancel() {
        isOK = false;
        getStage().close();
    }

    boolean isOK() {
        return isOK;
    }

    String getDescription() {
        return tfDescription.getText();
    }
    int getPrice() {
        return Integer.parseInt(tfPrice.getText());
    }
}
