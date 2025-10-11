package com.arkanoid.entities;

public class StrongBrick extends Brick {
    public StrongBrick(int x, int y, int width, int height) {
        super(x, y, width, height, Const.BrickType.HARD); // Cần 2 hit để vỡ
    }
}
