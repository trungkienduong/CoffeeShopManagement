package MAIN;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        // Đảm bảo rằng bạn sử dụng đường dẫn chính xác để tải tệp FXML
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/GUI/FXML/DIALOG/LoginDialog.fxml"));

        // Kích thước cửa sổ phù hợp với giao diện
        Scene scene = new Scene(loader.load(), 800, 500); // Điều chỉnh kích thước nếu cần

        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
