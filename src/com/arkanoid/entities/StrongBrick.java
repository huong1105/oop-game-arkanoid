package com.arkanoid.entities;

public class StrongBrick extends Brick {
    public StrongBrick(int x, int y, int width, int height) {
        super(x, y, width, height, 2); // Cần 2 hit để vỡ
    }
}
