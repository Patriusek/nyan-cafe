package org.example.nyan_cafe;

import javafx.animation.ScaleTransition;
import javafx.animation.SequentialTransition;
import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import javafx.util.Duration;


public class NyanController {
    public ImageView buttonImage;

    public ImageView snail;

    @FXML
    private Button joinButton;
    @FXML
    private Button closeButton;
    @FXML
    private Button minimizeButton;

    private MediaPlayer clickSoundPlayer;

    @FXML
    protected void onJoinButtonClick() {
        playClickSound();
    }

    @FXML
    private void onMinimizeButtonClick(ActionEvent event) {
        playClickSound();
        Stage stage = (Stage) minimizeButton.getScene().getWindow();
        stage.setIconified(true);
    }

    @FXML
    private void onCloseButtonClick(ActionEvent event) {
        playClickSound();
        Platform.exit();
        System.exit(0);
    }

    public void initialize() {
        try {
            String resourcePath = "click.mp3";
            java.net.URL resourceUrl = getClass().getResource(resourcePath);
            if (resourceUrl == null) {
                System.err.println("Resource not found: " + resourcePath);
                return;
            }
            Media clickSound = new Media(resourceUrl.toExternalForm());
            clickSoundPlayer = new MediaPlayer(clickSound);
            clickSoundPlayer.setVolume(0.5); // Moderate volume
        } catch (Exception e) {
            System.err.println("Failed to load click sound: " + e.getMessage());
            e.printStackTrace();
        }

        setupButtonAnimations(joinButton);
        setupButtonAnimations(closeButton);
        setupButtonAnimations(minimizeButton);
        animateImageSnailMoveX(snail);
        animateImageSnailSqueezeY(snail);
    }

    private void playClickSound() {
        if (clickSoundPlayer != null) {
            clickSoundPlayer.stop();
            clickSoundPlayer.play();
        }
    }

    private void setupButtonAnimations(Button button) {
        ScaleTransition scaleUp = new ScaleTransition(Duration.millis(100), button);
        scaleUp.setToX(1.1);
        scaleUp.setToY(1.1);

        ScaleTransition scaleDownToNormal = new ScaleTransition(Duration.millis(100), button);
        scaleDownToNormal.setToX(1.0);
        scaleDownToNormal.setToY(1.0);

        ScaleTransition scaleDownOnClick = new ScaleTransition(Duration.millis(100), button);
        scaleDownOnClick.setToX(0.9);
        scaleDownOnClick.setToY(0.9);
        scaleDownOnClick.setAutoReverse(true);
        scaleDownOnClick.setCycleCount(2);

        button.setOnMouseEntered(event -> scaleUp.playFromStart());
        button.setOnMouseExited(event -> scaleDownToNormal.playFromStart());
        button.setOnMouseClicked(event -> scaleDownOnClick.playFromStart());
    }

    private void animateImageSnailMoveX(ImageView imageView) {
        TranslateTransition moveRight = new TranslateTransition(Duration.millis(3000), imageView);
        moveRight.setByX(300);

        ScaleTransition flipX1 = new ScaleTransition(Duration.millis(200), imageView);
        flipX1.setToX(-1);

        TranslateTransition moveLeft = new TranslateTransition(Duration.millis(3000), imageView);
        moveLeft.setByX(-300);

        ScaleTransition flipX2 = new ScaleTransition(Duration.millis(200), imageView);
        flipX2.setToX(1);

        SequentialTransition sequence = new SequentialTransition(
                moveRight,
                flipX1,
                moveLeft,
                flipX2
        );

        sequence.setCycleCount(SequentialTransition.INDEFINITE);
        sequence.play();
    }

    private void animateImageSnailSqueezeY(ImageView imageView) {
        ScaleTransition squeezeY = new ScaleTransition(Duration.millis(500), imageView);
        squeezeY.setToY(0.8);
        squeezeY.setAutoReverse(true);
        squeezeY.setCycleCount(ScaleTransition.INDEFINITE);
        squeezeY.play();
    }
}