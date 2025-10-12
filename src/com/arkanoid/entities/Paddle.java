package com.arkanoid.entities;

import com.arkanoid.Const;
import com.arkanoid.core.MovableObject;
import javafx.scene.canvas.GraphicsContext;

// Lớp Paddle kế thừa từ MovableObject
public class Paddle extends MovableObject {
    private int speed;
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
        x = Const.INSTANCE.getPaddleDefaultPosX();
        y = Const.INSTANCE.getPaddleDefaultPosY();
        width = Const.INSTANCE.getPaddleWidth();
        height = Const.INSTANCE.getPaddleHeight();
    }

    public void moveLeft() {
        if (getX() > 0) {
            setSpeedX(-speed);
        } else {
            setSpeedX(0);
        }
    }

    public void moveRight() {
        if (getX() + getWidth() < Const.INSTANCE.getScreenWidth()) {
            setSpeedX(speed);
        } else {
            setSpeedX(0);
        }
    }

    public void applyPowerUp() { /* Logic áp dụng power-up */ }
}
