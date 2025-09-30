package com.arkanoid.core;

public abstract class MovableObject extends GameObject {
    protected float speedX;
    protected float speedY;

    public MovableObject(int x, int y, int width, int height, float speedX, float speedY) {
        super(x, y, width, height);
        this.speedX = speedX;
        this.speedY = speedY;
    }

    @Override
    public void update() {
        x += speedX;
        y += speedY;
    }
}
