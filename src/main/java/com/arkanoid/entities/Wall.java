package com.arkanoid.entities;

import com.arkanoid.BrickType;

public class Wall extends Brick {
    public Wall(int x, int y) {
        super(x, y, BrickType.WALL);
    }
}
