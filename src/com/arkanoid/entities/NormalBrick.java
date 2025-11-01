package com.arkanoid.entities;

import com.arkanoid.BrickType;

public class NormalBrick extends Brick {
    public NormalBrick(int x, int y) {
        super(x, y, BrickType.NORMAL);
    }
}
