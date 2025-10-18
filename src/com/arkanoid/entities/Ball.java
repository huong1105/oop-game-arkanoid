package com.arkanoid.entities;

import com.arkanoid.Const;
import com.arkanoid.core.MovableObject;
import javafx.scene.canvas.GraphicsContext;
import com.arkanoid.ui.SpriteManager;
import javafx.scene.paint.Color;

public class Ball extends MovableObject {
    private int maxSpeed = Const.BALL_MAXSPEED;
    private boolean started = false;

    public Ball(int x, int y, int diameter, int speedX, int speedY) {
        super(x, y, diameter, diameter, speedX, speedY);
        this.sprite = SpriteManager.getSprite(167, 1, 12, 12);
    }

    public Ball() {
        super(0, 0, Const.BALL_DIAMETER, Const.BALL_DIAMETER, 0, 0);
        this.sprite = SpriteManager.getSprite(167, 1, 12, 12);
    }

    public int getMaxSpeed() {
        return maxSpeed;
    }

    public void setMaxSpeed(int maxSpeed) {
        this.maxSpeed = maxSpeed;
    }

    public boolean isStarted() {
        return started;
    }

    public void start() {
        started = true;
    }

    public void stop() {
        started = false;
    }

    /**
     * cập nhật vị trí bóng.
     */
    @Override
    public void update() {
        if (started) {
            while (getSpeedX() * getSpeedX() + getSpeedY() * getSpeedY() > maxSpeed) {
                setSpeedX(getSpeedX() * 0.99);
                setSpeedY(getSpeedY() * 0.99);
            }
        }
        super.update();
    }

    @Override
    public void render(GraphicsContext gc) {
        super.render(gc);
    }

    public void reset(Paddle paddle) {
        x = Const.BALL_DEFAULT_POS_X;
        y = Const.BALL_DEFAULT_POS_Y;
        width = Const.BALL_DIAMETER;
        height = Const.BALL_DIAMETER;
        speedX = 0;
        speedY = 0;
        started = false;
    }

    /**
     * thay đổi hướng theo phương y.
     */
    public void reverseX() {
        setSpeedX(-getSpeedX());
    }

    /**
     * thay đổi hướng theo phương x.
     */
    public void reverseY() {
        setSpeedY(-getSpeedY());
    }
}
