package sample;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

class WindowController extends Controller {
    private User user;
    @FXML protected Label labelName;
    @FXML protected Label labelInfo;

    User getUser() {
        return user;
    }

    void setUser(User user) {
        this.user = user;
    }

    @Override
    void init() {
        super.init();
        labelName.setText(user.getName());
    }

    @FXML private void onSignOut() {
        getStage().close();
        LoginController c =
                (LoginController) nextStage("forms/LoginForm.fxml", "Login");
        c.init();
    }
}
