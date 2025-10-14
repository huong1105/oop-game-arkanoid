package com.arkanoid.ui;

import com.arkanoid.core.GameObject;
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
    }

    public void render(List<GameObject> objects,GameState gameState) {
        clear();
        for (GameObject obj : objects) {
            if (obj.isActive()) {
                obj.render(gc);
            }
        }

        // Vẽ các thông báo trạng thái game
        if (gameState == GameState.PAUSED) {
            drawMessage("PAUSED");
        } else if (gameState == GameState.GAME_OVER) {
            drawMessage("GAME OVER");
        }
    }

    private void clear() {
        gc.setFill(Color.BLACK);
        gc.fillRect(0, 0, canvasWidth, canvasHeight);
    }

    private void draw(GameObject obj) {
        if (obj.getSprite() != null) {
            gc.drawImage(obj.getSprite(), obj.getX(), obj.getY(), obj.getWidth(), obj.getHeight());
        } else {
            gc.setFill(Color.MAGENTA); // Màu báo lỗi nếu không có sprite
            gc.fillRect(obj.getX(), obj.getY(), obj.getWidth(), obj.getHeight());
        }
    }

    private void drawMessage(String message) {
        gc.setFill(Color.rgb(0, 0, 0, 0.5));
        gc.fillRect(0, 0, canvasWidth, canvasHeight);

        gc.setFill(Color.WHITE);
        gc.setFont(new Font("Arial", 50));
        gc.setTextAlign(TextAlignment.CENTER);
        gc.fillText(message, canvasWidth / 2, canvasHeight / 2);
    }
}
