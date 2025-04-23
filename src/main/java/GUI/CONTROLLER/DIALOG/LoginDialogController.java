package GUI.CONTROLLER.DIALOG;

import BUS.RoleListBUS;
import BUS.UserBUS;
import MODEL.User;
import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;


public class LoginDialogController {

    // Login Form UI Elements
    @FXML private TextField usernameLogin;
    @FXML private PasswordField passwordLogin;
    @FXML private TextField passwordLoginText;
    @FXML private FontAwesomeIconView eyeIconLogin;
    @FXML private Button loginButton;

    // Register Form UI Elements
    @FXML private TextField usernameRegister;
    @FXML private TextField emailRegister;
    @FXML private PasswordField passwordRegister;
    @FXML private TextField passwordRegisterText;
    @FXML private FontAwesomeIconView eyeIconRegister;
    @FXML private ComboBox<String> roleComboBox;
    @FXML private Button registerButton;

    // Slider UI Elements
    @FXML private AnchorPane slider;
    @FXML private Label sliderLabel;
    @FXML private Label sliderText;
    @FXML private Button sliderButton;

    private UserBUS userBUS = UserBUS.getInstance();

    private boolean isLoginForm = true;

    // Initialize method to set up the events and components
    @FXML
    public void initialize() {
        setupPasswordVisibilityToggles();
        syncPasswordFields();
        setupEmailValidation();
        setupRoleComboBox();

        // Thêm sự kiện cho các trường usernameLogin và passwordLogin để gọi handleLogin khi nhấn Enter
        usernameLogin.setOnAction(event -> handleLogin());  // Nhấn Enter trong usernameLogin
        passwordLogin.setOnAction(event -> handleLogin());  // Nhấn Enter trong passwordLogin
    }

    // Toggle between Login and Register form on slider
    @FXML
    private void sliding() {
        TranslateTransition slide = new TranslateTransition(Duration.seconds(0.7), slider);

        if (isLoginForm) {
            slide.setToX(-400);
            sliderLabel.setText("Hello, Friend!");
            sliderText.setText("Enter your details and start your journey with us");
            sliderButton.setText("SIGN UP");
        } else {
            slide.setToX(0);
            sliderLabel.setText("Welcome to Coffee Manager");
            sliderText.setText("To stay connected with us, please login");
            sliderButton.setText("SIGN IN");
        }

        slide.play();
        isLoginForm = !isLoginForm;
    }

    // Set up password visibility toggle events
    private void setupPasswordVisibilityToggles() {
        eyeIconLogin.setOnMouseClicked(this::toggleLoginPassword);
        eyeIconRegister.setOnMouseClicked(this::toggleRegisterPassword);
    }

    // Bind password fields to show text in TextField as well
    private void syncPasswordFields() {
        passwordLogin.textProperty().bindBidirectional(passwordLoginText.textProperty());
        passwordRegister.textProperty().bindBidirectional(passwordRegisterText.textProperty());
    }

    // Setup email validation on the register form
    private void setupEmailValidation() {
        emailRegister.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!UserBUS.isValidEmail(newValue)) {  // Sử dụng isValidEmail từ UserBUS
                emailRegister.setStyle("-fx-border-color: red; -fx-border-width: 2;");
            } else {
                emailRegister.setStyle("");  // Nếu email hợp lệ, xóa viền đỏ
            }
        });
    }

    // Toggle login password visibility
    private void toggleLoginPassword(MouseEvent event) {
        togglePasswordVisibility(passwordLogin, passwordLoginText, eyeIconLogin);
    }

    // Toggle register password visibility
    private void toggleRegisterPassword(MouseEvent event) {
        togglePasswordVisibility(passwordRegister, passwordRegisterText, eyeIconRegister);
    }

    // Helper method to toggle password visibility between PasswordField and TextField
    private void togglePasswordVisibility(PasswordField passwordField, TextField textField, FontAwesomeIconView eyeIcon) {
        if (passwordField.isVisible()) {
            passwordField.setVisible(false);
            textField.setVisible(true);
            eyeIcon.setGlyphName("EYE_SLASH");
        } else {
            passwordField.setVisible(true);
            textField.setVisible(false);
            eyeIcon.setGlyphName("EYE");
        }
    }

    // Set up role ComboBox for selecting user role
    private void setupRoleComboBox() {
        roleComboBox.getItems().addAll(RoleListBUS.getInstance().getRoleNamesForComboBox());
    }

    // -------------------- Handle Login --------------------
    @FXML
    private void handleLogin() {
        String username = usernameLogin.getText();
        String password = passwordLogin.getText();

        if (userBUS.checkLogin(username, password)) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXML/PANEL/CoffeeShopGUI.fxml"));
                AnchorPane mainPane = loader.load();
                Stage currentStage = (Stage) loginButton.getScene().getWindow();
                Scene scene = new Scene(mainPane);
                currentStage.setScene(scene);
                currentStage.setTitle("Coffee Manager");
            } catch (IOException e) {
                e.printStackTrace();
//                showAlert(Alert.AlertType.ERROR, "Error", "Cannot load main interface.");
            }
        } else {
            // Chỉ khi đăng nhập sai mới đổi màu viền của ô nhập
            usernameLogin.setStyle("-fx-border-color: red; -fx-border-width: 2;");
            passwordLogin.setStyle("-fx-border-color: red; -fx-border-width: 2;");
//            showAlert(Alert.AlertType.ERROR, "Login Failed", "Invalid username or password.");
        }
    }

    // -------------------- Handle Register --------------------
    @FXML
    private void handleRegister() {
        String username = usernameRegister.getText();
        String email = emailRegister.getText();
        String password = passwordRegister.getText();
        String role = roleComboBox.getValue();

        if (UserBUS.isValidEmail(email)) {
            // Giả sử roleComboBox trả về ID số (int) thay vì chuỗi
            int roleId = RoleListBUS.getInstance().getRoleIdByName(role); // Dùng phương thức này để lấy ID từ tên role

            User newUser = new User(username, password, email, roleId); // Truyền ID role vào constructor

            if (userBUS.insertUser(newUser)) {
//                showAlert(Alert.AlertType.INFORMATION, "Registration Successful", "Account successfully created for " + username);
                sliding(); // Trượt về màn hình đăng nhập
            } else {
//                showAlert(Alert.AlertType.ERROR, "Registration Failed", "Email or username already exists.");
            }
        } else {
//            showAlert(Alert.AlertType.ERROR, "Invalid Email", "Please provide a valid email.");
        }
    }

  /*  // -------------------- Show Alert --------------------
    private void showAlert(Alert.AlertType type, String title, String message) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }*/

}
