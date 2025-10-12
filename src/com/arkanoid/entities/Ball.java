package com.arkanoid.entities;

import com.arkanoid.Const;
import com.arkanoid.core.MovableObject;
import javafx.scene.canvas.GraphicsContext;

// Lớp Ball kế thừa từ MovableObject
public class Ball extends MovableObject {
    private int maxSpeed = Const.INSTANCE.getBallMaxSpeed();

    public Ball(int x, int y, int diameter, int speedX, int speedY) {
        super(x, y, diameter, diameter, speedX, speedY);
    }

    public Ball() {
        super(0, 0, Const.INSTANCE.getBallDiameter(), Const.INSTANCE.getBallDiameter(), 0, 0);
    }

    public int getMaxSpeed() {
        return maxSpeed;
    }

    public void setMaxSpeed(int maxSpeed) {
        this.maxSpeed = maxSpeed;
    }

    /**
     * cập nhật vị trí bóng.
     */
    @Override
    public void update() {
        while (getSpeedX() * getSpeedX() + getSpeedY() * getSpeedY() > maxSpeed) {
            setSpeedX(getSpeedX() * 0.99);
            setSpeedY(getSpeedY() * 0.99);
        }
        setX(getX() + (int) getSpeedX());
        setY(getY() + (int) getSpeedY());
    }

    @Override
    public void render(GraphicsContext gc) { /* Logic vẽ bóng */ }

    public void reset(Paddle paddle) {
        x = Const.INSTANCE.getBallDefaultPosX();
        y = Const.INSTANCE.getBallDefaultPosY();
        width = Const.INSTANCE.getBallDiameter();
        height = Const.INSTANCE.getBallDiameter();
        speedX = Const.INSTANCE.getBallSpeedX();
        speedY = Const.INSTANCE.getBallSpeedY();
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
