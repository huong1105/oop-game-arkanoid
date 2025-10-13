package com.arkanoid.entities;

import com.arkanoid.Const;
import com.arkanoid.core.MovableObject;
import javafx.scene.canvas.GraphicsContext;

// Lớp Paddle kế thừa từ MovableObject
public class Paddle extends MovableObject {
    private int speed = Const.PADDLE_DEFAULT_SPEED;
    private String currentPowerUp;

    public Paddle(int x, int y, int width, int height) {
        super(x, y, width, height, 0, 0); // Vận tốc ban đầu là 0
    }

    @Override
    public void update() {
        setX(getX() + getSpeedX());
    }

    @Override
    public void render(GraphicsContext gc) { /* Logic vẽ paddle */ }

    public void reset() {
        x = Const.PADDLE_DEFAULT_POS_X;
        y = Const.PADDLE_DEFAULT_POS_Y;
        width = Const.PADDLE_WIDTH;
        height = Const.PADDLE_HEIGHT;
    }

    public void moveLeft() {
        if (getX() > 0) {
            setSpeedX(-speed);
        } else {
            setSpeedX(0);
        }
    }

    public void moveRight() {
        if (getX() + getWidth() < Const.SCREEN_WIDTH) {
            setSpeedX(speed);
        } else {
            setSpeedX(0);
        }
    }

    public void applyPowerUp() { /* Logic áp dụng power-up */ }
}
