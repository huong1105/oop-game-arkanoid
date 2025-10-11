package com.arkanoid.entities;

public class SpecialBrick extends Brick {
    public SpecialBrick(int x, int y, int width, int height) {
        super(x, y, width, height, Const.BrickType.SPECIAL);
    }
}
