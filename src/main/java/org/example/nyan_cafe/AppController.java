package org.example.nyan_cafe;

import javafx.animation.ScaleTransition;
import javafx.animation.SequentialTransition;
import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.List;

public class AppController {
    // region shared
    private int pickedOptionId = -1;

    @FXML
    private StackPane paneRoot;
    @FXML
    private Pane paneStart;
    @FXML
    private Pane paneOptions;
    @FXML
    private Pane paneCooking;

    private ArrayList<Pane> panes;

    public void initialize() {
        panes = new ArrayList<>();
        panes.add(paneStart);
        panes.add(paneOptions);
        panes.add(paneCooking);

        initializePageStart();
        initializePageOptions();
        initializePageCooking();

        List<Button> buttons = findAllButtons(paneRoot);
        System.out.println("Found " + buttons.size() + " buttons:");
        for (Button btn : buttons) {
            btn.setOnMouseEntered(mouseEvent -> playHoverSound());
            btn.setOnMouseClicked(mouseEvent -> playClickSound());
        }

        switchPage(Page.Start);
    }

    // Recursive method to find all buttons in a parent node
    private List<Button> findAllButtons(Parent parent) {
        List<Button> buttons = new ArrayList<>();

        for (Node node : parent.getChildrenUnmodifiable()) {
            if (node instanceof Button) {
                buttons.add((Button) node);
            }
            if (node instanceof Parent) {
                buttons.addAll(findAllButtons((Parent) node));
            }
        }
        return buttons;
    }

    private void playClickSound() {
        Media sound = new Media(getClass().getResource("media/sound_click.mp3").toExternalForm());
        MediaPlayer player = new MediaPlayer(sound);
        player.setVolume(0.5f);
        player.setOnReady(player::play);

        // Clean up after playback finishes
        player.setOnEndOfMedia(player::dispose);
    }

    private void playHoverSound() {
        Media sound = new Media(getClass().getResource("media/sound_hover.mp3").toExternalForm());
        MediaPlayer player = new MediaPlayer(sound);
        player.setVolume(0.15f);
        player.setOnReady(player::play);

        // Clean up after playback finishes
        player.setOnEndOfMedia(player::dispose);
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
            case Cooking -> {
                setPaneVisible(paneCooking);
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
        Cooking,
    }
    // endregion

    // region main
    @FXML
    private Button minimizeButton;

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
    // endregion

    // region page_start
    @FXML
    private ImageView snail;

    @FXML
    protected void onJoinButtonClick() {
        switchPage(Page.Options);
    }

    private void initializePageStart() {
        animateImageSnailMoveX(snail);
        animateImageSnailSqueezeY(snail);
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

        TranslateTransition moveDown = new TranslateTransition(Duration.millis(500), imageView);
        moveDown.setByY(-20);

        TranslateTransition moveUp = new TranslateTransition(Duration.millis(500), imageView);
        moveUp.setByY(20);

        SequentialTransition sequence = new SequentialTransition(
                moveUp,
                moveDown
        );

        sequence.setCycleCount(SequentialTransition.INDEFINITE);
        sequence.play();
    }

    //endregion

    // region page_options
    private void initializePageOptions() {

    }

    @FXML
    private void onOption0ButtonClick() {
        pickedOptionId = 0;
        switchPage(Page.Cooking);
    }

    @FXML
    private void onOption1ButtonClick() {
        pickedOptionId = 1;
        switchPage(Page.Cooking);
    }

    @FXML
    private void onOption2ButtonClick() {
        pickedOptionId = 2;
        switchPage(Page.Cooking);
    }

    @FXML
    private void onOption3ButtonClick() {
        pickedOptionId = 3;
        switchPage(Page.Cooking);
    }
    // endregion

    // region page_cooking
    private void initializePageCooking() {

    }
    // endregion
}