package com.example.coffeeshopmanagement;

import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;


public class HelloController {
    @FXML
    private AnchorPane slider;

    @FXML
    private Label sliderLabel;

    @FXML
    private Label sliderText;

    @FXML
    private Button sliderButton;

    private boolean isLoginForm = true;

    @FXML
    private void sliding() {
        TranslateTransition slide = new TranslateTransition(Duration.seconds(0.7), slider);

        if (isLoginForm) {
            slide.setToX(-400);
            sliderLabel.setText("Hello, Friend!");
            sliderText.setText("Enter your details and start journey with us");
            sliderButton.setText("SIGN UP");
        } else {
            slide.setToX(0);
            sliderLabel.setText("Welcome to Coffee Manager");
            sliderText.setText("To keep connected with us please login");
            sliderButton.setText("SIGN IN");
        }

        slide.play();
        isLoginForm = !isLoginForm;
    }
}