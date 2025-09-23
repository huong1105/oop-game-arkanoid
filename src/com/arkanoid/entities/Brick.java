package com.arkanoid.entities;

import com.arkanoid.core.GameObject;

public class Brick extends GameObject {
    private int hitPoints;
    private String type;

    public Brick(int x, int y, int width, int height, int hitPoints) {
        super(x, y, width, height);
        this.hitPoints = hitPoints;
    }

    @Override
    public void update() { /* Logic cập nhật gạch */ }

    @Override
    public void render() { /* Logic vẽ gạch */ }

    public void takeHit() { this.hitPoints--; }
    public boolean isDestroyed() { return this.hitPoints <= 0; }
}
