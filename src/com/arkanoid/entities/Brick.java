package com.arkanoid.entities;

import com.arkanoid.core.GameObject;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Brick extends GameObject {
    private int hitPoints;
    private String type;

    public Brick(int x, int y, int width, int height, int hitPoints, String type) {
        super(x, y, width, height);
        this.hitPoints = hitPoints;
        this.type = type;
    }

    @Override
    public void update() {
        if (isDestroyed()) {
            return;
        }
        // Có thể thêm logic animation hoặc hiệu ứng ở đây
    }

    @Override
    public void render(GraphicsContext gc) {
        if (!isDestroyed()) {
            // Chọn màu dựa trên type
            switch (type != null ? type : "") {
                case "HARD":
                    gc.setFill(Color.BLUE);
                    break;
                case "SPECIAL":
                    gc.setFill(Color.GREEN);
                    break;
                default:
                    gc.setFill(Color.RED);
                    break;
            }
            gc.fillRect(x, y, width, height);
            gc.setStroke(Color.BLACK);
            gc.strokeRect(x, y, width, height);
        }
    }

    public void takeHit() {
        this.hitPoints--;
    }

    public boolean isDestroyed() {
        return this.hitPoints <= 0;
    }

    public int getScore() {
        return switch (type != null ? type : "") {
            case "HARD" -> 200;
            case "SPECIAL" -> 500;
            default -> 100;
        };
    }

    public int getHitPoints() {
        return hitPoints;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}