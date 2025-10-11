package com.arkanoid.entities;

import com.arkanoid.core.GameObject;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.awt.*;

public class Brick extends GameObject {
    private int hitPoints;
    private int maxHitPoints;
    private Const.BrickType type;

    public Brick(int x, int y, int width, int height, Const.BrickType type) {
        super(x, y, width, height);
        this.hitPoints = type.getHitPoints();
        this.maxHitPoints = type.getHitPoints();
        this.type = type;
    }

    @Override
    public void update() {
        if (hitPoints <= 0) {
            setActive(false);
        }
    }

    @Override
    public void render(GraphicsContext gc) {
        if (!isActive()) {
            return;
        }
        switch (type) {
            case NORMAL:
                gc.setFill(Color.RED);
            case HARD:
                double intensity = (double) hitPoints / maxHitPoints;
                gc.setFill(Color.rgb(128, 128, 128, intensity));
            case SPECIAL:
                gc.setFill(Color.GOLD);
            default:
                gc.setFill(Color.WHITE);
        }
        gc.fillRect(x, y, width, height);
    }

    public boolean takeHit() {
        if (hitPoints > 0) {
            hitPoints--;
            if (hitPoints <= 0) {
                setActive(false);
                return true; // destroyed, trả về kiểu bool để xử lý cộng điểm.
            }
        }
        return false;
    }
    public boolean isDestroyed() {
        return this.hitPoints <= 0;
    }

    public int getScoreValue() {
        return type.getScoreValue();
    }

    public int getHitPoints() {
        return hitPoints;
    }

    public Const.BrickType getType() {
        return type;
    }
}