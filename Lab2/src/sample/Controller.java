package sample;

import javafx.stage.Stage;

class Controller {
    private Stage stage;

    Stage getStage() {
        return stage;
    }

    void setStage(Stage stage) {
        this.stage = stage;
    }

    void init() {
        stage.show();
        stage.setResizable(false);
        //stage.setMinHeight(stage.getHeight());
        //stage.setMinWidth(stage.getWidth());
    }
}
