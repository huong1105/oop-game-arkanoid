package com.arkanoid.ui;

import com.arkanoid.core.GameObject;
import com.arkanoid.game.GameManager;
import com.arkanoid.game.GameState;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;

import java.util.List;

public class Renderer {
    private GraphicsContext gc;
    private double canvasWidth;
    private double canvasHeight;

    public Renderer(GraphicsContext gc, double canvasWidth, double canvasHeight) {
        this.gc = gc;
        this.canvasWidth = canvasWidth;
        this.canvasHeight = canvasHeight;
        gc.setImageSmoothing(false);
    }

    /**
     * Phương thức render chính, được gọi mỗi khung hình.
     * Nó sẽ vẽ các thành phần khác nhau tùy thuộc vào trạng thái hiện tại của game.
     */
    public void render(GameManager gm) {
        // Lấy thông tin cần thiết từ GameManager
        GameState gameState = gm.getGameState();
        List<GameObject> objects = gm.getGameObjects();
        int score = gm.getScore();
        int lives = gm.getLives();

        // 1. Xóa màn hình
        clear();

        // 2. Vẽ các thành phần dựa trên trạng thái game
        if (gm.getGameState() == GameState.MENU) {
            gm.getMainMenu().render(gc);
        } else {
            // Vẽ tất cả các đối tượng game (paddle, ball, bricks)
            for (GameObject obj : objects) {
                if (obj.isActive()) {
                    obj.render(gc);
                }
            }

            // Vẽ giao diện người dùng (UI)
            drawUI(score, lives);

            // Vẽ các thông báo đè lên màn hình
            if (gameState == GameState.PAUSED) {
                drawMessage("PAUSED");
            } else if (gameState == GameState.GAME_OVER) {
                drawMessage("GAME OVER");
            } else if (gameState == GameState.WIN) {
                drawMessage("YOU WIN!");
            } else if (gameState == GameState.LEVEL_TRANSITION) {
                drawMessage("Level " + (gm.getCurrentLevel() + 1));
            }
        }
    }

    /**
     * Xóa toàn bộ canvas và tô màu đen.
     */
    private void clear() {
        gc.setFill(Color.BLACK);
        gc.fillRect(0, 0, canvasWidth, canvasHeight);
    }

    /**
     * Vẽ màn hình Menu chính.
     */
    private void drawMenuScreen() {
        gc.setFill(Color.WHITE);
        gc.setTextAlign(TextAlignment.CENTER);

        // Vẽ tiêu đề
        gc.setFont(new Font("Arial", 70));
        gc.fillText("Arkanoid", canvasWidth / 2, canvasHeight / 3);

        // Vẽ hướng dẫn
        gc.setFont(new Font("Arial", 30));
        gc.fillText("Press ENTER to Start", canvasWidth / 2, canvasHeight / 2);
    }

    /**
     * Vẽ các thông tin như điểm và mạng sống.
     */
    private void drawUI(int score, int lives) {
        gc.setFill(Color.WHITE);
        gc.setFont(new Font("Arial", 20));
        gc.setTextAlign(TextAlignment.LEFT);
        gc.fillText("Score: " + score, 10, 25);

        gc.setTextAlign(TextAlignment.RIGHT);
        gc.fillText("Lives: " + lives, canvasWidth - 10, 25);
    }

    /**
     * Vẽ một thông báo lớn ở giữa màn hình.
     */
    private void drawMessage(String message) {
        gc.setFill(Color.rgb(0, 0, 0, 0.6));
        gc.fillRect(0, 0, canvasWidth, canvasHeight);

        gc.setFill(Color.WHITE);
        gc.setFont(new Font("Arial", 50));
        gc.setTextAlign(TextAlignment.CENTER);
        gc.fillText(message, canvasWidth / 2, canvasHeight / 2);
    }
}
