package com.arkanoid.entities;
import com.arkanoid.BrickType;
public class SpecialBrick extends Brick {
    public SpecialBrick(int x, int y, int width, int height) {
        super(x, y, width, height, BrickType.SPECIAL);
    }
}
