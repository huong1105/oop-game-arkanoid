package com.arkanoid;

import com.arkanoid.game.GameManager;
import com.arkanoid.ui.KeyInput;
import com.arkanoid.ui.Renderer; // Import lớp Renderer của bạn
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) {
        GameManager gm = GameManager.getInstance();

        Pane root = new Pane();
        Canvas canvas = new Canvas(Const.INSTANCE.getScreenWidth(), Const.INSTANCE.getScreenHeight());
        root.getChildren().add(canvas);
        GraphicsContext gc = canvas.getGraphicsContext2D();

        Renderer renderer = new Renderer(gc, canvas.getWidth(), canvas.getHeight());

        Scene scene = new Scene(root);
        primaryStage.setTitle("Arkanoid - JavaFX");
        primaryStage.setScene(scene);
        primaryStage.show();

        KeyInput.setupInput(scene);

        AnimationTimer gameLoop = new AnimationTimer() {
            @Override
            public void handle(long now) {
                gm.update();
                renderer.render(gm.getGameObjects());
            }
        };
        gameLoop.start();
    }

    public static void main(String[] args) {
        launch(args);
    }
}