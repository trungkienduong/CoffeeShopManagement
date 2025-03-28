package GUI.CONTROLLER.PANEL;

import javafx.fxml.FXML;
import javafx.scene.layout.StackPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class SplashScreenController {
    @FXML
    private StackPane rootPane;

    @FXML
    private MediaView mediaView;

    @FXML
    private Button skipButton;

    private MediaPlayer mediaPlayer;

    @FXML
    public void initialize() {
        String videoPath = getClass().getResource("/ASSETS/VIDEOS/202910-919288798.mp4").toExternalForm();
        Media media = new Media(videoPath);
        mediaPlayer = new MediaPlayer(media);
        mediaView.setMediaPlayer(mediaPlayer);

        // Ensure video fills the window completely
        mediaView.setPreserveRatio(false);
        mediaView.fitWidthProperty().bind(rootPane.widthProperty());
        mediaView.fitHeightProperty().bind(rootPane.heightProperty());

        // Center the MediaView
        mediaView.setX(0);
        mediaView.setY(0);

        mediaPlayer.play();
        mediaPlayer.setOnEndOfMedia(this::closeSplashScreen);
    }

    @FXML
    private void handleSkip() {
        closeSplashScreen();
    }

    private void closeSplashScreen() {
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            mediaPlayer.dispose();
        }

        Stage stage = (Stage) rootPane.getScene().getWindow();
        stage.close();

        // Launch your main application here
        // launchMainApplication();
    }
}