package com.arkanoid.entities;

import com.arkanoid.Const;
import com.arkanoid.core.MovableObject;
import com.arkanoid.game.GameManager;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public abstract class PowerUp extends MovableObject {
    private String type;
    private int duration;
    private long startTime;
    protected Object target;

    private static final double FALL_SPEED = 2.0;

    public PowerUp(int x, int y, int width, int height, String type, int duration) {
        super(x, y, width, height, (float) 0, (float) FALL_SPEED);
        this.type = type;
        this.duration = duration;
    }

    @Override
    public void update() {
        if (!isActive()) return;

        if (startTime == 0) {
            // Chưa được kích hoạt: rơi xuống
            super.update();
            if (getY() > Const.SCREEN_HEIGHT) {
                setActive(false);
            }
        } else if (duration > 0 && System.currentTimeMillis() - startTime >= duration) {
            // Đã kích hoạt và hết thời gian: gọi removeEffect và deactivate
            removeEffect();
            setActive(false);
        }
    }

    @Override
    public void render(GraphicsContext gc) {
        if (!isActive()) return;

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
        if (this instanceof ExpandPaddlePowerUp && obj instanceof Paddle) {
            Paddle paddle = (Paddle) obj;
            if (paddle.isExpanded()) {
                return;
            }
        }

        this.target = obj;
        this.startTime = System.currentTimeMillis();
        applyEffect();
        if (this instanceof FeverBallPowerUp) {
            GameManager.getInstance().setFeverBallActive(true);
        }
    }

    public abstract void applyEffect();
    public abstract void removeEffect();
}