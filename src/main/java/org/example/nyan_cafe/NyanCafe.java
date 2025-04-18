package org.example.nyan_cafe;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

public class NyanCafe extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(NyanCafe.class.getResource("main.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 650, 700);

        scene.setFill(Color.TRANSPARENT);
        stage.initStyle(StageStyle.TRANSPARENT);

        makeWindowDraggable(scene, stage);

        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        System.setProperty("prism.order", "sw");
        System.setProperty("prism.lcdtext", "false");
        launch();
    }

    private void makeWindowDraggable(Scene scene, Stage stage) {
        final double[] xOffset = new double[1];
        final double[] yOffset = new double[1];

        scene.setOnMousePressed(event -> {
            xOffset[0] = event.getSceneX();
            yOffset[0] = event.getSceneY();
        });

        scene.setOnMouseDragged(event -> {
            stage.setX(event.getScreenX() - xOffset[0]);
            stage.setY(event.getScreenY() - yOffset[0]);
        });
    }
}