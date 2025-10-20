package com.arkanoid.entities;

import com.arkanoid.Const;
import com.arkanoid.core.MovableObject;
import com.arkanoid.game.GameManager;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public abstract class PowerUp extends MovableObject {
    protected double durationSeconds;
    protected Object target;
    protected String type;
    private boolean isActivated = false;
    private double timeRemaining;

    private static final double FALL_SPEED = 120.0;

    public PowerUp(int x, int y, int width, int height, String type, int durationMillis) {
        super(x, y, width, height, (float) 0, (float) FALL_SPEED);
        this.type = type;

        this.durationSeconds = durationMillis / 1000.0;
        this.timeRemaining = this.durationSeconds;
    }

    @Override
    public void update(double deltaTimeSeconds) {
        if (!isActive()) return;

        if (!isActivated) {
            super.update(deltaTimeSeconds);
            if (getY() > Const.SCREEN_HEIGHT) {
                setActive(false);
            }
        } else {
            if (durationSeconds > 0) {
                timeRemaining -= deltaTimeSeconds;
                if (timeRemaining <= 0) {
                    removeEffect();
                    setActive(false);
                }
            }
        }
    }

    @Override
    public void render(GraphicsContext gc) {
        if (!isActive() || isActivated) return; // Chỉ render khi chưa activate

        switch (type) {
            case "MultiBall":
                gc.setFill(Color.GREEN);
                break;
            case "FastBall":
                gc.setFill(Color.RED);
                break;
            case "ExpandPaddle":
                gc.setFill(Color.WHITE);
                break;
            case "FeverBall":
                gc.setFill(Color.YELLOW);
                break;
            default:
                gc.setFill(Color.YELLOW);
        }
        gc.fillRect(x, y, width, height);
    }

    public void activate(Object obj) {
        if (isActivated) return;

        this.isActivated = true;
        this.target = obj;
        this.speedX = 0;
        this.speedY = 0;
        applyEffect();

        if (durationSeconds == 0) {
            setActive(false); // Đối với power-up không có thời gian
        }
    }

    public abstract void applyEffect();
    public abstract void removeEffect();
}