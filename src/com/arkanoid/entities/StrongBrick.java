package com.arkanoid.entities;
import com.arkanoid.BrickType;

public class StrongBrick extends Brick {
    public StrongBrick(int x, int y, int width, int height) {
        super(x, y, width, height, BrickType.HARD); // Cần 2 hit để vỡ
    }
}
