package com.arkanoid;

import com.arkanoid.game.GameManager;
import com.arkanoid.game.GameState;
import com.arkanoid.ui.HudManager;
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
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

public class Main extends Application {
    private long lastFrameTime = 0;
    private HudManager hudManager;

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
                        "-fx-border-width: 0 0 0 2;" + // Chỉ viền trái 2px
                        "-fx-border-style: solid;"
        );

        hudManager = new HudManager(infoPanel);

        Renderer renderer = new Renderer(gc, canvas.getWidth(), canvas.getHeight());

        Scene scene = new Scene(root, Const.WINDOW_WIDTH, Const.SCREEN_HEIGHT);
        primaryStage.setTitle("Arkanoid - JavaFX");
        primaryStage.setScene(scene);
        primaryStage.show();
        primaryStage.requestFocus();

        KeyInput.setupInput(scene);

        lastFrameTime = System.nanoTime();

        AnimationTimer gameLoop = createGameLoop(gm, renderer, root, infoPanel, canvas);
        gameLoop.start();
    }

    private AnimationTimer createGameLoop(GameManager gm, Renderer renderer, BorderPane root, Pane infoPanel, Canvas canvas) {
        return new AnimationTimer() {
            @Override
            public void handle(long now) {
                double deltaTimeSeconds = (now - lastFrameTime) / 1_000_000_000.0;
                lastFrameTime = now;

                // Cập nhật logic game
                gm.update(deltaTimeSeconds);
                GameState currentState = gm.getGameState();

                // Kiểm tra trạng thái để ẩn/hiện cột thông tin
                boolean isFullScreenState = (currentState == GameState.MENU ||
                        currentState == GameState.HIGH_SCORE ||
                        currentState == GameState.SETTINGS);

                if (isFullScreenState) {
                    if (root.getRight() != null) {
                        root.setRight(null);
                    }
                    canvas.setWidth(Const.WINDOW_WIDTH);
                    canvas.setHeight(Const.SCREEN_HEIGHT);
                    renderer.updateCanvasSize(Const.WINDOW_WIDTH, Const.SCREEN_HEIGHT);
                    gm.getMainMenu().updateBackGround();

                } else {
                    // Hiện infoPanel
                    if (root.getRight() == null) {
                        root.setRight(infoPanel);
                    }
                    // Thu nhỏ Canvas về kích thước game
                    canvas.setWidth(Const.SCREEN_WIDTH);
                    canvas.setHeight(Const.SCREEN_HEIGHT);
                    renderer.updateCanvasSize(Const.SCREEN_WIDTH, Const.SCREEN_HEIGHT);

                    // --- Cập nhật HUD (giờ gọi qua HudManager) ---
                    hudManager.update(gm);
                }

                // Vẽ game
                renderer.render(gm);
            }
        };
    }

    public static void main(String[] args) {
        launch(args);
    }
}
