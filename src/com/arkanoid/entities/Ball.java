package com.arkanoid.entities;

import com.arkanoid.core.MovableObject;
import com.arkanoid.core.GameObject;

import java.awt.*;

// Lớp Ball kế thừa từ MovableObject
public class Ball extends MovableObject {
    private int maxSpeed;

    public Ball(int x, int y, int diameter, int speedX, int speedY) {
        super(x, y, diameter, diameter, speedX, speedY);
    }

    /**
     * cập nhật vị trí bóng.
     */
    @Override
    public void update() {
        while (getSpeedX() * getSpeedX() + getSpeedY() * getSpeedY() > maxSpeed){
            setSpeedX(getSpeedX() * 0.99);
            setSpeedY(getSpeedY() * 0.99);
        }
        setX(getX() + (int)getSpeedX());
        setY(getY() + (int)getSpeedY());
    }

    @Override
    public void render(Graphics2D g) { /* Logic vẽ bóng */ }

    /**
     * thay đổi hướng theo phương y.
     */
    private void reverseX() {
        setSpeedX(-getSpeedX());
    }

    /**
     * thay đổi hướng theo phương x.
     */
    private void reverseY() {
        setSpeedY(-getSpeedY());
    }
}
