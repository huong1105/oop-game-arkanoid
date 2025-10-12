package com.arkanoid.entities;

import com.arkanoid.BrickType;

public class NormalBrick extends Brick {
    public NormalBrick(int x, int y, int width, int height) {
        super(x, y, width, height, BrickType.NORMAL);
    }
}
