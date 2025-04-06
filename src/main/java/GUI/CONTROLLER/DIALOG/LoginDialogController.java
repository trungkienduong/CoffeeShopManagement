package GUI.CONTROLLER.DIALOG;

import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;

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

    private boolean isLoginForm = true;

    // Initialize method to set up the events and components
    @FXML
    public void initialize() {
        setupPasswordVisibilityToggles();
        syncPasswordFields();
        setupEmailValidation();
        setupRoleComboBox();
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
            if (!isValidEmail(newValue)) {
                emailRegister.setStyle("-fx-border-color: red;");
            } else {
                emailRegister.setStyle("");
            }
        });
    }

    // Validate email format
    private boolean isValidEmail(String email) {
        String emailRegex = "^[A-Za-z0-9+_.-]+@(.+)$";
        return email.isEmpty() || email.matches(emailRegex);
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
        roleComboBox.getItems().addAll("Manager", "Employee");
    }
}
