package com.arkanoid.entities;
import com.arkanoid.BrickType;
public class SpecialBrick extends Brick {
    public SpecialBrick(int x, int y) {
        super(x, y, BrickType.SPECIAL);
    }
}
