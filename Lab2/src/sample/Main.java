package sample;

import javafx.application.Application;
import javafx.application.Preloader;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    private static final int COUNT_LIMIT = 5000;
    @Override
    public void init() {
        for (int i = 0; i < COUNT_LIMIT; i++) {
            double progress = (100 * i) / COUNT_LIMIT;
            notifyPreloader(new Preloader.ProgressNotification(progress));
        }
    }

    @Override
    public void start(Stage primaryStage) {
        LoginController loginController =
                (LoginController) Controller.nextStage("forms/LoginForm.fxml", "Login");
        loginController.init();
    }


    public static void main(String[] args) {
        System.setProperty("javafx.preloader", MyPreloader.class.getCanonicalName());
        launch(args);
    }
}
