package org.example.nyan_cafe;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

public class NyanCafe extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(NyanCafe.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 650, 700);

        // Set transparent background (must come before stage style)
        scene.setFill(Color.TRANSPARENT);

        // Keep undecorated style but add transparency support
        stage.initStyle(StageStyle.TRANSPARENT);

        // Maintain existing draggable functionality
        makeWindowDraggable(scene, stage);

        // Keep window non-resizable
        stage.setResizable(false);

        // Set the scene and show stage
        stage.setScene(scene);

        // Optional: Add platform-specific transparency fixes
        //applyTransparencyWorkaround();

        stage.show();
    }

    public static void main(String[] args) {
        // Optional: Set system properties for better transparency handling
        System.setProperty("prism.order", "sw");
        System.setProperty("prism.lcdtext", "false");
        launch();
    }

    // Existing draggable window implementation (unchanged)
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
/*
    // New method for platform-specific transparency fixes
    private void applyTransparencyWorkaround() {
        try {
            // Workaround for Windows transparency issues
            if (System.getProperty("os.name").toLowerCase().contains("win")) {
                com.sun.glass.ui.Window.getWindows().get(0).setBackground(
                        new com.sun.glass.ui.Color(0, 0, 0, 0)
                );
            }
        } catch (Exception e) {
            // Fallback if platform-specific fix fails
            System.err.println("Transparency workaround failed: " + e.getMessage());
        }
    }*/
}