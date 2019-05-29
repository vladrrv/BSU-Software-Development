package sample;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

class Controller {
    private Stage stage;

    Stage getStage() {
        return stage;
    }

    void setStage(Stage stage) {
        this.stage = stage;
    }

    static void showError(String errorMessage) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText("");
        alert.setContentText(errorMessage);
        alert.showAndWait();
    }

    static Controller nextStage(String fxmlName, String title) {
        Controller c = null;
        try {
            FXMLLoader loader = new FXMLLoader(Controller.class.getResource(fxmlName));
            Parent root = loader.load();
            c = loader.getController();
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.setTitle(title);
            c.setStage(stage);
        } catch (IOException e) {
            e.printStackTrace();
            showError(e.getMessage());
        }
        return c;
    }

    void init() {
        stage.show();
        stage.setResizable(false);
        stage.getIcons().add(new Image(getClass().getResourceAsStream("../icon_app.png")));
    }
}
