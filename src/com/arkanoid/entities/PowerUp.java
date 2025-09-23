package com.arkanoid.entities;

import com.arkanoid.core.GameObject;

public class PowerUp extends GameObject {
    private String type;
    private int duration;

    public PowerUp(int x, int y, int width, int height, String type) {
        super(x, y, width, height);
        this.type = type;
    }

    @Override
    public void update() { /* Logic cập nhật power-up */ }

    @Override
    public void render() { /* Logic vẽ power-up */ }

    public void applyEffect(Paddle paddle) { /* Logic áp dụng hiệu ứng */ }
    public void removeEffect(Paddle paddle) { /* Logic gỡ hiệu ứng */ }
}
