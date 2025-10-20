package com.arkanoid.core;

import javafx.geometry.Rectangle2D;

public abstract class MovableObject extends GameObject {
    protected double speedX;
    protected double speedY;

    public MovableObject(int x, int y, int width, int height, float speedX, float speedY) {
        super(x, y, width, height);
        this.speedX = speedX;
        this.speedY = speedY;
    }

    public void setSpeedX(double speedX) {
        this.speedX = speedX;
    }

    public void setSpeedY(double speedY) {
        this.speedY = speedY;
    }

    public double getSpeedX() {
        return speedX;
    }

    public double getSpeedY() {
        return speedY;
    }

    @Override
    public void update(double deltaTimeSeconds) {
        x += speedX * deltaTimeSeconds;
        y += speedY * deltaTimeSeconds;
        this.bounds = new Rectangle2D(x, y, width, height);
    }

}
