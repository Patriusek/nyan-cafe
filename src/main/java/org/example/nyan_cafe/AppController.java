package org.example.nyan_cafe;

import javafx.animation.ScaleTransition;
import javafx.animation.SequentialTransition;
import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.ArrayList;

public class AppController {
    // region shared
    @FXML
    private Pane paneStart;
    @FXML
    private Pane paneOptions;

    private ArrayList<Pane> panes;

    private MediaPlayer clickSoundPlayer;

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

        panes = new ArrayList<>();
        panes.add(paneStart);
        panes.add(paneOptions);

        initializePageStart();
        initializePageOptions();

        setupButtonAnimations(joinButton);
        setupButtonAnimations(closeButton);
        setupButtonAnimations(minimizeButton);

        switchPage(Page.Start);
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

    private void switchPage(Page page) {
        switch (page)
        {
            case Start -> {
                setPaneVisible(paneStart);
            }
            case Options -> {
                setPaneVisible(paneOptions);
            }
            default -> {
                System.err.printf("The type %s is missing case logic%n", page);
            }
        }
    }

    private void setPaneVisible(Pane pane)
    {
        for (Pane checkPane : panes) {
            checkPane.setVisible(checkPane == pane);
        }
    }

    private enum Page
    {
        Start,
        Options,
    }
    // endregion

    // region main
    @FXML
    private Button minimizeButton;
    @FXML
    private Button closeButton;

    private void initializePageStart() {
        animateImageSnailMoveX(snail);
        animateImageSnailSqueezeY(snail);
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
    // endregion

    // region page_start
    @FXML
    private ImageView snail;

    @FXML
    private Button joinButton;

    @FXML
    protected void onJoinButtonClick() {
        playClickSound();
        switchPage(Page.Options);
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

    //endregion

    // region page_options
    private void initializePageOptions() {

    }
    // endregion
}