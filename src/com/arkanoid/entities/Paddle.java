package com.arkanoid.entities;

import com.arkanoid.Const;
import com.arkanoid.core.MovableObject;
import javafx.scene.canvas.GraphicsContext;
import com.arkanoid.ui.SpriteManager;

// Lớp Paddle kế thừa từ MovableObject
public class Paddle extends MovableObject {
    private int speed = Const.PADDLE_DEFAULT_SPEED;
    private boolean movingLeft;
    private boolean movingRight;
    private boolean isExpanded;

    public Paddle(int x, int y, int width, int height) {
        super(x, y, width, height, 0, 0); // Vận tốc ban đầu là 0
        this.sprite = SpriteManager.getSprite(131, 1, 32, 10);
        this.isExpanded = false;
    }

    @Override
    public void update(double deltaTimeSeconds) {
        if (movingLeft && getX() > 0) {
            setSpeedX(-speed);
        } else if (movingRight && getX() + width < Const.SCREEN_WIDTH) {
            setSpeedX(speed);
        } else {
            setSpeedX(0);
        }

        super.update(deltaTimeSeconds);
    }

    @Override
    public void render(GraphicsContext gc) {
        super.render(gc);
    }

    public void reset() {
        x = Const.PADDLE_DEFAULT_POS_X;
        y = Const.PADDLE_DEFAULT_POS_Y;
        width = Const.PADDLE_WIDTH;
        height = Const.PADDLE_HEIGHT;
        isExpanded = false;
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

    public boolean isExpanded() {
        return isExpanded;
    }

    public void setExpanded(boolean expanded) {
        this.isExpanded = expanded;
    }
}