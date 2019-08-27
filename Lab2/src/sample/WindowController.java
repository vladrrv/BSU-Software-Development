package sample;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

class WindowController extends Controller {
    private User user;
    private final String DEFAULT_PHOTO_URL = getClass().getResource("../default_photo.png").toString();
    @FXML protected Label labelName;
    @FXML protected Label labelInfo;
    @FXML protected ImageView ivPhoto;

    User getUser() {
        return user;
    }

    void setUser(User user) {
        this.user = user;
    }

    private boolean testImage(String url) {
        try {
            BufferedImage image = ImageIO.read(new URL(url));
            return image != null;
        } catch (MalformedURLException e) {
            // TODO Auto-generated catch block
            System.err.println("URL error with image");
            e.printStackTrace();
            showError("Could not load image");
        } catch (IOException e) {
            System.err.println("IO error with image");
            e.printStackTrace();
            showError("Could not load image");
        }
        return false;
    }

    @Override
    void init() {
        super.init();
        labelName.setText(user.getName());
        if (user.isStudent() || user.isProfessor()) {
            labelInfo.setText(user.getInfo());
            String url = user.getPhotoURL();
            if (url == null || url.isEmpty() || !testImage(url)) {
                ivPhoto.setImage(new Image(DEFAULT_PHOTO_URL));
            } else {
                ivPhoto.setImage(new Image(url));
            }
        }
    }

    @FXML private void onSignOut() {
        getStage().close();
        LoginController c =
                (LoginController) nextStage("forms/LoginForm.fxml", "Login");
        c.init();
    }
}
