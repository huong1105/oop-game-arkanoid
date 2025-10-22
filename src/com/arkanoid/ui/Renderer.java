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
            case MENU:
                gm.getMainMenu().render(gc);
                break;

            case HIGH_SCORE:
                drawHighScores(gm.getHighScoreManager().getHighScores());
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
                for (GameObject obj : gm.getGameObjects()) {
                    if (obj.isActive()) {
                        obj.render(gc);
                    }
                }
                // Vẽ UI
                drawUI(gm.getScore(), gm.getLives());

                // Vẽ các thông báo đè lên màn hình
                if (gameState == GameState.PAUSED) {
                    gm.getPauseMenu().render(gc);
                } else if (gameState == GameState.GAME_OVER) {
                    drawMessage("GAME OVER");
                } else if (gameState == GameState.WIN) {
                    drawMessage("YOU WIN!");
                } else if (gameState == GameState.LEVEL_TRANSITION) {
                    drawMessage("Level " + (gm.getCurrentLevel()));
                }
                break;
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
     * Vẽ màn hình hiển thị danh sách điểm cao.
     * @param highScores Danh sách các điểm cao cần hiển thị.
     */
    private void drawHighScores(List<Integer> highScores) {
        gc.setFill(Color.WHITE);
        gc.setTextAlign(TextAlignment.CENTER);

        // Vẽ tiêu đề
        gc.setFont(new Font("Arial", 60));
        gc.fillText("High Scores", canvasWidth / 2, canvasHeight / 5);

        // Vẽ danh sách điểm
        gc.setFont(new Font("Arial", 35));

        if (highScores.isEmpty()) {
            gc.setTextAlign(TextAlignment.CENTER);
            gc.fillText("No scores yet!", canvasWidth / 2, canvasHeight / 2);
        } else {
            double startY = canvasHeight / 3;
            for (int i = 0; i < highScores.size(); i++) {
                String rank = (i + 1) + ".";
                String scoreText = String.format("%,d", highScores.get(i));

                // Căn lề cho đẹp
                gc.setTextAlign(TextAlignment.RIGHT);
                gc.fillText(rank, canvasWidth / 2 - 20, startY + i * 60);

                gc.setTextAlign(TextAlignment.LEFT);
                gc.fillText(scoreText, canvasWidth / 2, startY + i * 60);
            }
        }

        // Thêm hướng dẫn quay lại
        gc.setFont(new Font("Arial", 20));
        gc.setTextAlign(TextAlignment.CENTER);
        gc.fillText("Press ESC or Click to return to Menu", canvasWidth / 2, canvasHeight - 50);
    }


    /**
     * Vẽ các thông tin như điểm và mạng sống.
     */
    private void drawUI(int score, int lives) {
        gc.setFill(Color.WHITE);
        gc.setFont(new Font("Arial", 20));
        gc.setTextAlign(TextAlignment.LEFT);
        gc.fillText("Score: " + String.format("%,d", score), 10, 25);

        gc.setTextAlign(TextAlignment.RIGHT);
        gc.fillText("Lives: " + lives, canvasWidth - 10, 25);
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
