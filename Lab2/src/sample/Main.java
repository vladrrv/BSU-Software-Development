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
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("forms/LoginForm.fxml"));
        Parent rootLogin = loader.load();
        Scene sceneLogin = new Scene(rootLogin);
        LoginController loginController = loader.getController();
        Stage loginStage = new Stage();
        loginController.setStage(loginStage);
        loginStage.setScene(sceneLogin);
        loginStage.setTitle("Login");
        loginController.init();
        //primaryStage.getIcons().add(new Image(getClass().getResourceAsStream("res/icon_app.png")));
    }


    public static void main(String[] args) {
        //System.setProperty("javafx.preloader", MyPreloader.class.getCanonicalName());
        launch(args);
    }
}
