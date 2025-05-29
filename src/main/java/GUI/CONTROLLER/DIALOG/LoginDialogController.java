package GUI.CONTROLLER.DIALOG;

import BUS.RoleListBUS;
import BUS.UserBUS;
import MODEL.RoleList;
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

    @FXML private TextField usernameLogin;
    @FXML private PasswordField passwordLogin;
    @FXML private TextField passwordLoginText;
    @FXML private FontAwesomeIconView eyeIconLogin;
    @FXML private Button loginButton;

    @FXML private TextField usernameRegister;
    @FXML private TextField emailRegister;
    @FXML private PasswordField passwordRegister;
    @FXML private TextField passwordRegisterText;
    @FXML private FontAwesomeIconView eyeIconRegister;
    @FXML private ComboBox<String> roleComboBox;
    @FXML private Button registerButton;

    @FXML private AnchorPane slider;
    @FXML private Label sliderLabel;
    @FXML private Label sliderText;
    @FXML private Button sliderButton;

    private UserBUS userBUS = UserBUS.getInstance();
    private boolean isLoginForm = true;

    @FXML
    public void initialize() {
        setupPasswordVisibilityToggles();
        syncPasswordFields();
        setupEmailValidation();
        setupRoleComboBox();

        usernameLogin.setOnAction(event -> handleLogin());
        passwordLogin.setOnAction(event -> handleLogin());
    }

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

    private void setupPasswordVisibilityToggles() {
        eyeIconLogin.setOnMouseClicked(this::toggleLoginPassword);
        eyeIconRegister.setOnMouseClicked(this::toggleRegisterPassword);
    }

    private void syncPasswordFields() {
        passwordLogin.textProperty().bindBidirectional(passwordLoginText.textProperty());
        passwordRegister.textProperty().bindBidirectional(passwordRegisterText.textProperty());
    }

    private void setupEmailValidation() {
        emailRegister.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!UserBUS.isValidEmail(newValue)) {
                emailRegister.setStyle("-fx-border-color: red; -fx-border-width: 2;");
            } else {
                emailRegister.setStyle("");
            }
        });
    }

    @FXML
    private void toggleLoginPassword(MouseEvent event) {
        togglePasswordVisibility(passwordLogin, passwordLoginText, eyeIconLogin);
    }

    private void toggleRegisterPassword(MouseEvent event) {
        togglePasswordVisibility(passwordRegister, passwordRegisterText, eyeIconRegister);
    }

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

    private void setupRoleComboBox() {
        roleComboBox.getItems().addAll(RoleListBUS.getInstance().getRoleNamesForComboBox());
    }

    @FXML
    private void handleLogin() {
        String username = usernameLogin.getText();
        String password = passwordLogin.getText();

        if (userBUS.checkLogin(username, password)) {
            UTIL.Session.currentUsername = username;

            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXML/PANEL/CoffeeShopGUI.fxml"));
                AnchorPane mainPane = loader.load();
                Stage currentStage = (Stage) loginButton.getScene().getWindow();
                Scene scene = new Scene(mainPane);
                currentStage.setScene(scene);
                currentStage.setTitle("Coffee Manager");
            } catch (IOException e) {
                e.printStackTrace();
                showAlert(Alert.AlertType.ERROR, "Error", "Cannot load main interface.");
            }
        } else {
            usernameLogin.setStyle("-fx-border-color: red; -fx-border-width: 2;");
            passwordLogin.setStyle("-fx-border-color: red; -fx-border-width: 2;");
            showAlert(Alert.AlertType.ERROR, "Login Failed", "Invalid username or password.");
        }
    }

    @FXML
    private void handleRegister() {
        String username = usernameRegister.getText();
        String email = emailRegister.getText();
        String password = passwordRegister.getText();
        String roleName = roleComboBox.getValue();

        if (UserBUS.isValidEmail(email)) {
            int roleId = RoleListBUS.getInstance().getRoleIdByName(roleName);
            RoleList roleObj = RoleListBUS.getInstance().getRoleById(roleId);

            User newUser = new User(username, password, email, roleObj);

            if (userBUS.insertUser(newUser)) {
                sliding();
            } else {
                showAlert(Alert.AlertType.ERROR, "Registration Failed", "Email or username already exists.");
            }
        } else {
            showAlert(Alert.AlertType.ERROR, "Invalid Email", "Please provide a valid email.");
        }
    }

    private void showAlert(Alert.AlertType type, String title, String message) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
