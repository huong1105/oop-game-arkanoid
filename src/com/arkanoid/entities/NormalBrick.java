package com.arkanoid.entities;

public class NormalBrick extends Brick {
    public NormalBrick(int x, int y, int width, int height) {
        super(x, y, width, height, 1); // Cần 1 hit để vỡ
    }
}
