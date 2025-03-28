package GUI.CONTROLLER.PANEL;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class SplashScreenController implements Initializable {

    @FXML
    private MediaView media;

    @FXML
    private Button button;

    private MediaPlayer mediaPlayer;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        URL videoURL = getClass().getResource("/ASSETS/VIDEOS/202910-919288798.mp4");

        if (videoURL != null) {
            Media mediaFile = new Media(videoURL.toString());
            mediaPlayer = new MediaPlayer(mediaFile);
            media.setMediaPlayer(mediaPlayer);
            mediaPlayer.play();

            // Khi video kết thúc -> Chuyển sang login
            mediaPlayer.setOnEndOfMedia(this::goToLogin);
        } else {
            System.out.println("❌ Không tìm thấy file video!");
            goToLogin(); // Nếu lỗi, chuyển thẳng sang login
        }

        // Nút "Bỏ qua"
        button.setOnAction(event -> {
            if (mediaPlayer != null) {
                mediaPlayer.stop();
            }
            goToLogin();
        });
    }

    private void goToLogin() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXML/DIALOG/LoginDialog.fxml"));
            Stage stage = (Stage) button.getScene().getWindow();
            stage.setScene(new Scene(loader.load()));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("❌ Lỗi chuyển sang màn hình đăng nhập!");
        }
    }
}
