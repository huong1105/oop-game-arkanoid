package com.arkanoid.ui;

import com.arkanoid.entities.*;
import com.arkanoid.game.GameManager;
import com.arkanoid.game.GameState;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;

import java.util.List;

public class Renderer {
    private final GraphicsContext gc;
    private double canvasWidth;
    private double canvasHeight;

    public Renderer(GraphicsContext gc, double canvasWidth, double canvasHeight) {
        this.gc = gc;
        this.canvasWidth = canvasWidth;
        this.canvasHeight = canvasHeight;
        gc.setImageSmoothing(false);
    }

    public void updateCanvasSize(double width, double height) {
        this.canvasWidth = width;
        this.canvasHeight = height;
    }
    /**
     * Phương thức render chính, được gọi mỗi khung hình.
     * Nó sẽ vẽ các thành phần khác nhau tùy thuộc vào trạng thái hiện tại của game.
     */
    public void render(GameManager gm) {
        // Lấy thông tin cần thiết từ GameManager
        GameState gameState = gm.getGameState();
        clear();

        switch (gameState) {
            case LOADING:
                drawLoadingScreen();
                break;

            case MENU:
                gm.getMainMenu().render(gc);
                break;

            case HIGH_SCORE:
                gm.getHighScoreMenu().render(gc);
                break;

            case SETTINGS:
                gm.getSettingsMenu().render(gc);
                break;

            case PLAYING:
            case PAUSED:
            case READY:
            case GAME_OVER:
            case WIN:
            case LEVEL_TRANSITION:
                // Vẽ các đối tượng game
                if (gm.getPaddle() != null) {
                    gm.getPaddle().render(gc);
                }

                for (Brick b : gm.getBricks()) {
                    if (b.isActive()) b.render(gc);
                }

                for (Ball b : gm.getBalls()) {
                    if (b.isActive()) b.render(gc);
                }

                for (PowerUp p : gm.getPowerUps()) {
                    if (p.isActive()) p.render(gc);
                }

                for (Shield s : gm.getShields()) {
                    if (s.isActive()) s.render(gc);
                }

                for (FireWorkEffect e : gm.getEffects()) {
                    if (e.isActive()) e.render(gc);
                }

                // Vẽ các thông báo đè lên màn hình
                if (gameState == GameState.PAUSED) {
                    gm.getPauseMenu().render(gc);
                } else if (gameState == GameState.GAME_OVER) {
                    drawMessage("GAME OVER");
                } else if (gameState == GameState.WIN) {
                    drawMessage("YOU WIN!");
                } else if (gameState == GameState.LEVEL_TRANSITION) {
                    drawMessage("Level " + (gm.getCurrentLevel() + 1));
                }
                break;
        }
    }

    private void drawLoadingScreen() {
        gc.setFill(Color.WHITE);
        gc.setFont(new Font("Arial", 40));
        gc.setTextAlign(TextAlignment.CENTER);
        gc.fillText("Loading...", canvasWidth / 2, canvasHeight / 2);
    }

    /**
     * Xóa toàn bộ canvas và tô màu đen.
     */
    private void clear() {
        gc.setFill(Color.BLACK);
        gc.fillRect(0, 0, canvasWidth, canvasHeight);
    }

    /**
     * Vẽ một thông báo lớn ở giữa màn hình.
     */
    private void drawMessage(String message) {
        // Vẽ một lớp nền mờ để làm nổi bật thông báo
        gc.setFill(Color.rgb(0, 0, 0, 0.6));
        gc.fillRect(0, 0, canvasWidth, canvasHeight);

        gc.setFill(Color.WHITE);
        gc.setFont(new Font("Arial", 50));
        gc.setTextAlign(TextAlignment.CENTER);
        gc.fillText(message, canvasWidth / 2, canvasHeight / 2);
    }
}
