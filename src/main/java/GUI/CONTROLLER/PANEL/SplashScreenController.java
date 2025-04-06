package GUI.CONTROLLER.PANEL;

import javafx.animation.FadeTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javafx.util.Duration;

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
        setupVideo();
        setupMediaPlayer();
    }

    private void setupVideo() {
        String videoPath = getClass().getResource("/ASSETS/VIDEOS/202910-919288798.mp4").toExternalForm();
        Media media = new Media(videoPath);
        mediaPlayer = new MediaPlayer(media);
        mediaView.setMediaPlayer(mediaPlayer);

        mediaView.setPreserveRatio(false);
        mediaView.fitWidthProperty().bind(rootPane.widthProperty());
        mediaView.fitHeightProperty().bind(rootPane.heightProperty());
    }

    private void setupMediaPlayer() {
        mediaPlayer.setOnEndOfMedia(this::openLoginScreen);
        mediaPlayer.play();
    }

    @FXML
    private void handleSkip() {
        openLoginScreen();
    }

    private void openLoginScreen() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXML/DIALOG/LoginDialog.fxml"));
            AnchorPane loginRoot = loader.load();
            switchToLoginScreen(loginRoot);
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Error loading login screen");
        }
    }

    private void switchToLoginScreen(AnchorPane loginRoot) {
        // Tạo Scene cho màn hình Login
        Scene loginScene = new Scene(loginRoot);
        Stage stage = (Stage) rootPane.getScene().getWindow();

        // Đặt độ mờ cho các màn hình trước khi chuyển cảnh
        rootPane.setOpacity(1);
        loginRoot.setOpacity(0);

        // Đặt Scene cho Stage
        stage.setScene(loginScene);

        // Fade out từ Splash screen
        FadeTransition fadeOut = new FadeTransition(Duration.millis(400), rootPane);
        fadeOut.setFromValue(1.0);
        fadeOut.setToValue(0.0);

        // Fade in cho Login screen
        FadeTransition fadeIn = new FadeTransition(Duration.millis(400), loginRoot);
        fadeIn.setFromValue(0.0);
        fadeIn.setToValue(1.0);

        // Chạy các hiệu ứng Fade
        fadeOut.play();
        fadeIn.play();

        // Sau khi fade out hoàn tất, cleanup Splash screen
        fadeOut.setOnFinished(e -> cleanupSplashScreen());
    }

    private void cleanupSplashScreen() {
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            mediaPlayer.dispose();
        }
    }
}
