package org.example.nyan_cafe;

import javafx.animation.*;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.List;

public class AppController {
    // region shared
    private int pickedOptionId = -1;
    private int pickedSecondaryId = -1;

    @FXML
    private StackPane paneRoot;
    @FXML
    private Pane paneStart;
    @FXML
    private Pane paneOptions;
    @FXML
    private Pane paneCooking;
    @FXML
    private Pane panePrepared;
    @FXML
    private Pane paneSecondary;
    @FXML
    private Pane paneDisgust;
    @FXML
    private Pane paneQuestion;
    @FXML
    private Pane paneSadCreeper;
    @FXML
    private Pane paneUgh;
    @FXML
    private Pane paneEnjoy;

    private ArrayList<Pane> panes;

    public void initialize() {
        panes = new ArrayList<>();
        panes.add(paneStart);
        panes.add(paneOptions);
        panes.add(paneCooking);
        panes.add(panePrepared);
        panes.add(paneSecondary);
        panes.add(paneDisgust);
        panes.add(paneQuestion);
        panes.add(paneSadCreeper);
        panes.add(paneUgh);
        panes.add(paneEnjoy);

        initializePageStart();
        initializePageOptions();
        initializePageCooking();
        initializePagePrepared();
        initializePageSecondary();
        initializePageQuestion();
        initializePageSadCreeper();
        initializePageUgh();
        initializePageEnjoy();

        List<Button> buttons = findAllButtons(paneRoot);
        System.out.println("Found " + buttons.size() + " buttons:");
        for (Button btn : buttons) {
            btn.setOnMouseEntered(mouseEvent -> playSoundOneShot("media/audio/sound_hover.mp3", 0.15));
            btn.setOnMouseClicked(mouseEvent -> playSoundOneShot("media/audio/sound_click.mp3", 0.5));
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

    private void playSoundOneShot(String path, double volume) {
        Media sound = new Media(getClass().getResource(path).toExternalForm());
        MediaPlayer player = new MediaPlayer(sound);
        player.setVolume(volume);
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
            case Prepared -> {
                String imagePath = String.format("media/page_options/dish_%d.png", pickedOptionId);
                var image = new Image(getClass().getResource(imagePath).toExternalForm());
                imagePreparedFood.setImage(image);
                setPaneVisible(panePrepared);
                playSoundOneShot("media/audio/sound_done.mp3", 0.5);
            }
            case Secondary -> {
                setPaneVisible(paneSecondary);
            }
            case Disgust -> {
                setPaneVisible(paneDisgust);
            }
            case Question -> {
                setPaneVisible(paneQuestion);
            }
            case SadCreeper -> {
                setPaneVisible(paneSadCreeper);
                restartCreeperGifAnim();
                showEnjoyPageDelayed();
            }
            case Ugh -> {
                setPaneVisible(paneUgh);
            }
            case Enjoy -> {
                String imagePath = String.format("media/page_final/dish_%d.png", pickedOptionId);
                var image = new Image(getClass().getResource(imagePath).toExternalForm());
                imageFinalMainDish.setImage(image);

                imageFinalSecondaryDish.setVisible(pickedSecondaryId != -1);

                if (pickedSecondaryId != -1)
                {
                    imagePath = String.format("media/page_final/secondary_%d.png", pickedSecondaryId);
                    image = new Image(getClass().getResource(imagePath).toExternalForm());

                    imageFinalSecondaryDish.setImage(image);
                }
                setPaneVisible(paneEnjoy);
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
        Prepared,
        Secondary,

        //espresso
        Disgust,
        Question,
        SadCreeper, // is not sure

        //sauce
        Ugh,

        Enjoy
    }
    // endregion

    // region main
    @FXML
    private Button minimizeButton;

    @FXML
    private void onMinimizeButtonClick() {
        Stage stage = (Stage) minimizeButton.getScene().getWindow();
        stage.setIconified(true);
    }

    @FXML
    private void onCloseButtonClick() {
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
    private Timeline timer;
    private int secondsRemaining = 15;
    @FXML
    private Text textCookingTimeLeft;

    private void initializePageCooking() {

        ChangeListener<Boolean> visibilityListener = (observable, oldValue, newValue) -> {
            if (newValue && paneCooking.getScene() != null && paneCooking.getScene().getWindow() != null) {
                startCookingTimer();
            }
        };

        paneCooking.visibleProperty().addListener(visibilityListener);

        if (paneCooking.isVisible() && paneCooking.getScene() != null) {
            startCookingTimer();
        }
    }

    private void startCookingTimer() {
        if (timer != null) {
            timer.stop();
        }

        secondsRemaining = 15;
        updateTimerText();

        timer = new Timeline(
                new KeyFrame(Duration.seconds(0.1), event -> {
                    secondsRemaining--;
                    if (secondsRemaining >= 0) {
                        updateTimerText();
                    } else {
                        timer.stop();
                        switchPage(Page.Prepared);
                    }
                })
        );
        timer.setCycleCount(secondsRemaining + 1);
        timer.play();
    }

    private void updateTimerText() {
        int minutes = secondsRemaining / 60;
        int seconds = secondsRemaining % 60;
        textCookingTimeLeft.setText(String.format("%d:%02d", minutes, seconds));
    }
    // endregion

    // region page_prepared
    @FXML
    private ImageView imagePreparedFood;

    private void initializePagePrepared() {

    }

    @FXML
    public void onSnoozeButtonClick() {
        switchPage(Page.Secondary);
    }
    // endregion

    // region page_secondary
    private void initializePageSecondary() {

    }

    @FXML
    public void onSecondary0ButtonClick()
    {
        pickedSecondaryId = 0;
        switchPage(Page.Disgust);
    }

    @FXML
    public void onSecondary1ButtonClick()
    {
        pickedSecondaryId = 1;
        switchPage(Page.Ugh);
    }
    // endregion

    // region page_disgust
    @FXML
    public void onSorryButtonClick() {
        switchPage(Page.Question);
    }
    // endregion

    // region page_question
    @FXML
    public void onDismissButtonClick() {
        pickedSecondaryId = -1;
        switchPage(Page.SadCreeper);
    }

    @FXML
    public void onAcceptButtonClick() {
        switchPage(Page.Enjoy);
    }

    private void initializePageQuestion() {
    }
    // endregion

    // region page_sad_creeper
    @FXML
    private ImageView imageCreeper;

    private void initializePageSadCreeper() {
    }

    private void restartCreeperGifAnim()
    {
        imageCreeper.setImage(new Image(getClass().getResource("media/page_sad_creeper/sad_creeper.gif").toExternalForm()));
    }

    private void showEnjoyPageDelayed()
    {
        Timeline timeline = new Timeline(new KeyFrame(
                Duration.seconds(6),
                event -> {
                    Platform.exit();
                    System.exit(0);
                }
        ));
        timeline.play();
    }

    // endregion

    // region page_ugh
    @FXML
    public void onUghButtonClick() {
        switchPage(Page.Enjoy);
    }

    private void initializePageUgh() {
    }
    // endregion

    // region page_enjoy
    @FXML
    private ImageView imageFinalMainDish;
    @FXML
    private ImageView imageFinalSecondaryDish;

    private void initializePageEnjoy() {
    }
    // endregion
}