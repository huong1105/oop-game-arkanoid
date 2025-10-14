package com.arkanoid.entities;

import com.arkanoid.Const;
import com.arkanoid.core.MovableObject;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;


// Lớp Paddle kế thừa từ MovableObject
public class Paddle extends MovableObject {
    private int speed = Const.PADDLE_DEFAULT_SPEED;
    private boolean movingLeft;
    private boolean movingRight;
    private String currentPowerUp;

    public Paddle(int x, int y, int width, int height) {
        super(x, y, width, height, 0, 0); // Vận tốc ban đầu là 0
    }

    @Override
    public void update() {
        if (movingLeft ) {
            setX(getX() -speed);
        }
        if (movingRight) {
            setX(getX() + speed);
        }
    }

    @Override
    public void render(GraphicsContext gc) {
        gc.setFill(Color.rgb(0, 255, 255));
        gc.fillRect(x, y, width, height);
    }

    public void reset() {
        x = Const.PADDLE_DEFAULT_POS_X;
        y = Const.PADDLE_DEFAULT_POS_Y;
        width = Const.PADDLE_WIDTH;
        height = Const.PADDLE_HEIGHT;
    }

    public boolean isMovingLeft() {
        return movingLeft;
    }

    public void setMovingLeft(boolean movingLeft) {
        this.movingLeft = movingLeft;
    }

    public boolean isMovingRight() {
        return movingRight;
    }

    public void setMovingRight(boolean movingRight) {
        this.movingRight = movingRight;
    }

    public void applyPowerUp() { /* Logic áp dụng power-up */ }
}
