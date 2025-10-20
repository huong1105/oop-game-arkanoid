package com.arkanoid;

import com.arkanoid.game.GameManager;
import com.arkanoid.ui.KeyInput;
import com.arkanoid.ui.Renderer;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class Main extends Application {
    private long lastFrameTime = 0;

    @Override
    public void start(Stage primaryStage) {
        GameManager gm = GameManager.getInstance();
        Pane root = new Pane();
        Canvas canvas = new Canvas(Const.SCREEN_WIDTH, Const.SCREEN_HEIGHT);
        root.getChildren().add(canvas);
        GraphicsContext gc = canvas.getGraphicsContext2D();

        Renderer renderer = new Renderer(gc, canvas.getWidth(), canvas.getHeight());

        Scene scene = new Scene(root);
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
