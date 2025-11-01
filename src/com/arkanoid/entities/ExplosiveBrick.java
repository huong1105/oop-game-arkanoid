package com.arkanoid.entities;

import com.arkanoid.BrickType;

public class ExplosiveBrick extends Brick {
    public ExplosiveBrick(int x, int y) {
        super(x, y, BrickType.EXPLOSIVE);
    }
}
