package com.arkanoid;

import com.arkanoid.game.GameManager;
import com.arkanoid.ui.KeyInput;
import com.arkanoid.ui.Renderer;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class Main extends Application {
    private long lastFrameTime = 0;

    @Override
    public void start(Stage primaryStage) {
        GameManager gm = GameManager.getInstance();

        BorderPane root = new BorderPane();

        Canvas canvas = new Canvas(Const.SCREEN_WIDTH, Const.SCREEN_HEIGHT);
        GraphicsContext gc = canvas.getGraphicsContext2D();
        root.setCenter(canvas);

        Pane infoPanel = new Pane();
        infoPanel.setPrefWidth(Const.INFO_PANEL_WIDTH);
        infoPanel.setStyle(
                "-fx-background-color: #000000;" +
                        "-fx-border-color: white;" +
                        "-fx-border-width: 0 0 0 2;" +
                        "-fx-border-style: solid;"
        );
        root.setRight(infoPanel);

        Renderer renderer = new Renderer(gc, canvas.getWidth(), canvas.getHeight());

        Scene scene = new Scene(root, Const.WINDOW_WIDTH, Const.SCREEN_HEIGHT);
        primaryStage.setTitle("Arkanoid - JavaFX");
        primaryStage.setScene(scene);
        primaryStage.show();
        primaryStage.requestFocus();

        KeyInput.setupInput(scene);

        lastFrameTime = System.nanoTime();

        AnimationTimer gameLoop = new AnimationTimer() {
            @Override
            public void handle(long now) {
                double deltaTimeSeconds = (now - lastFrameTime) / 1_000_000_000.0;
                lastFrameTime = now;
                gm.update(deltaTimeSeconds);
                renderer.render(gm);
            }
        };

        gameLoop.start();
    }

    public static void main(String[] args) {
        launch(args);
    }
}