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
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.media.AudioClip;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.File;
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

        List<Button> buttons = findAllNodes(paneRoot, Button.class);

        for (Button btn : buttons) {
            btn.addEventHandler(MouseEvent.MOUSE_CLICKED, mouseEvent -> {
                playSoundOneShot("media/audio/sound_click.mp3", 0.5);
            });
            btn.addEventHandler(MouseEvent.MOUSE_ENTERED, mouseEvent -> {
                playSoundOneShot("media/audio/sound_hover.mp3", 0.15);
            });
        }

        List<StackPane> stackPanes = findAllNodes(paneRoot, StackPane.class);

        for (StackPane stackPane : stackPanes) {
            stackPane.addEventHandler(MouseEvent.MOUSE_CLICKED, mouseEvent -> {
                playSoundOneShot("media/audio/sound_click.mp3", 0.5);
            });
            stackPane.addEventHandler(MouseEvent.MOUSE_ENTERED, mouseEvent -> {
                playSoundOneShot("media/audio/sound_hover.mp3", 0.15);
            });
        }


        Font font = Font.loadFont(getClass().getResource("media/fonts/pixelify_sans.ttf").toExternalForm(), 32);
        System.out.println("Font loaded: " + (font != null ? font.getName() : "null"));

        switchPage(Page.Start);
    }

    private <T extends Node> List<T> findAllNodes(Parent parent, Class<T> nodeType) {
        List<T> nodes = new ArrayList<>();

        for (Node node : parent.getChildrenUnmodifiable()) {
            if (nodeType.isInstance(node)) {
                nodes.add(nodeType.cast(node));
            }
            if (node instanceof Parent) {
                nodes.addAll(findAllNodes((Parent) node, nodeType));
            }
        }
        return nodes;
    }

    private void playSoundOneShot(String path, double volume) {
        try {
            AudioClip sound = new AudioClip(getClass().getResource(path).toExternalForm());
            sound.setVolume(volume);
            sound.play();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void switchPage(Page page) {
        switch (page)
        {
            case Start:
                setPaneVisible(paneStart);
                break;
            case Options:
                setPaneVisible(paneOptions);
                break;
            case Cooking:
                setPaneVisible(paneCooking);
                break;
            case Prepared:
                String imagePath = "media/page_options/dish_" + pickedOptionId + ".png";
                Image image = new Image(getClass().getResource(imagePath).toExternalForm());
                imagePreparedFood.setImage(image);
                setPaneVisible(panePrepared);
                break;
            case Secondary:
                setPaneVisible(paneSecondary);
                break;
        case Disgust:
                setPaneVisible(paneDisgust);
                break;
            case Question:
                setPaneVisible(paneQuestion);
                break;
            case SadCreeper:
                setPaneVisible(paneSadCreeper);
                restartCreeperGifAnim();
                showEnjoyPageDelayed();
                playCreeperSoundDelayed();
                break;
            case Ugh:
                setPaneVisible(paneUgh);
                break;
            case Enjoy:
                imagePath = "media/page_final/dish_" + pickedOptionId + ".png";
                image = new Image(getClass().getResource(imagePath).toExternalForm());
                imageFinalMainDish.setImage(image);

                imageFinalSecondaryDish.setVisible(pickedSecondaryId != -1);

                if (pickedSecondaryId != -1)
                {
                    imagePath = "media/page_final/secondary_" + pickedSecondaryId + ".png";
                    image = new Image(getClass().getResource(imagePath).toExternalForm());

                    imageFinalSecondaryDish.setImage(image);
                }
                setPaneVisible(paneEnjoy);
                break;
            default:
                System.err.printf("The type %s is missing case logic%n", page);
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
                        playSoundOneShot("media/audio/sound_done.mp3", 0.5);
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
        textCookingTimeLeft.setText(minutes + ":" + seconds);
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
        switchPage(Page.Question);
    }
    // endregion

    // region page_disgust
    @FXML
    public void onSorryButtonClick() {
        switchPage(Page.Enjoy);
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
        switchPage(Page.Ugh);
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

    private void playCreeperSoundDelayed()
    {
        Timeline timeline = new Timeline(new KeyFrame(
                Duration.seconds(4.9),
                event -> {
                    playSoundOneShot("media/audio/sound_creeper.mp3", 0.5f);
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