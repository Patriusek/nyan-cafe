package org.example.nyan_cafe;

import javafx.animation.ScaleTransition;
import javafx.animation.SequentialTransition;
import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.Console;

public class NyanController {
    public ImageView buttonImage;

    public ImageView ulitkins;

    @FXML
    private Button joinButton;

    @FXML
    private Button closeButton;

    @FXML
    private Button minimizeButton;

    @FXML
    protected void onJoinButtonClick() {

    }

    @FXML
    private void onMinimizeButtonClick(ActionEvent event) {
        Stage stage = (Stage) minimizeButton.getScene().getWindow();
        stage.setIconified(true);
    }

    @FXML
    private void onCloseButtonClick(ActionEvent event) {
        Platform.exit();
        System.exit(0);
    }

    public void initialize() {
        // Apply animations to all buttons
        setupButtonAnimations(joinButton);
        setupButtonAnimations(closeButton);
        setupButtonAnimations(minimizeButton);
        animateImage(ulitkins);
        squeezeImage(ulitkins);
    }

    private void setupButtonAnimations(Button button) {
        // Hover animation: Scale up to 1.1x
        ScaleTransition scaleUp = new ScaleTransition(Duration.millis(100), button);
        scaleUp.setToX(1.1);
        scaleUp.setToY(1.1);

        // Mouse exit animation: Scale back to 1.0x
        ScaleTransition scaleDownToNormal = new ScaleTransition(Duration.millis(100), button);
        scaleDownToNormal.setToX(1.0);
        scaleDownToNormal.setToY(1.0);

        // Click animation: Scale down to 0.9x and back
        ScaleTransition scaleDownOnClick = new ScaleTransition(Duration.millis(100), button);
        scaleDownOnClick.setToX(0.9);
        scaleDownOnClick.setToY(0.9);
        scaleDownOnClick.setAutoReverse(true); // Return to original size
        scaleDownOnClick.setCycleCount(2); // Play forward and back

        // Set event handlers
        button.setOnMouseEntered(event -> scaleUp.playFromStart());
        button.setOnMouseExited(event -> scaleDownToNormal.playFromStart());
        button.setOnMouseClicked(event -> scaleDownOnClick.playFromStart());
    }

    private void animateImage(ImageView imageView) {
        TranslateTransition moveRight = new TranslateTransition(Duration.millis(3000), imageView);
        moveRight.setByX(300); // Move 100 pixels to the right

        ScaleTransition flipX1 = new ScaleTransition(Duration.millis(200), imageView);
        flipX1.setToX(-1); // Negative scale on X-axis to flip

        TranslateTransition moveLeft = new TranslateTransition(Duration.millis(3000), imageView);
        moveLeft.setByX(-300); // Move 100 pixels to the left

        ScaleTransition flipX2 = new ScaleTransition(Duration.millis(200), imageView);
        flipX2.setToX(1); // Positive scale to return to normal

        SequentialTransition sequence = new SequentialTransition(
                moveRight,
                flipX1,
                moveLeft,
                flipX2
        );

        sequence.setCycleCount(SequentialTransition.INDEFINITE);
        sequence.play();
    }

    private void squeezeImage(ImageView imageView) {
        ScaleTransition squeezeY = new ScaleTransition(Duration.millis(500), imageView);
        squeezeY.setToY(0.8);
        squeezeY.setAutoReverse(true);
        squeezeY.setCycleCount(ScaleTransition.INDEFINITE);
        squeezeY.play();
    }
}