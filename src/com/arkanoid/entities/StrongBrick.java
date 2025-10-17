package com.arkanoid.entities;
import com.arkanoid.BrickType;

public class StrongBrick extends Brick {
    public StrongBrick(int x, int y) {
        super(x, y, BrickType.HARD); // Cần 2 hit để vỡ
    }
}
