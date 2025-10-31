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

    public void reset(double x, double y) {
        this.x = x;
        this.y = y;
        this.bounds = new javafx.geometry.Rectangle2D(x, y, width, height);

        this.active = true;
        this.isActivated = false;
        this.target = null;
        this.timeRemaining = this.durationSeconds;

        this.speedX = 0;
        this.speedY = FALL_SPEED;
    }

    @Override
    public void update(double deltaTimeSeconds) {
        if (!isActive()) return;

        if (!isActivated) {
            super.update(deltaTimeSeconds);
            if (getY() > Const.SCREEN_HEIGHT) {
                PowerUpPool.getInstance().returnPowerUp(this);
            }
        } else {
            if (durationSeconds > 0) {
                timeRemaining -= deltaTimeSeconds;
                if (timeRemaining <= 0) {
                    removeEffect();
                    this.active = false;
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
            case "Shield":
                gc.setFill(Color.CYAN);
                break;
            case "FireBall":
                gc.setFill(Color.ORANGE);
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
            PowerUpPool.getInstance().returnPowerUp(this);
        }
    }

    public abstract void applyEffect();

    public abstract void removeEffect();

    public boolean isActivated() {
        return isActivated;
    }
}
