package com.arkanoid.entities;

import com.arkanoid.BrickType;
import com.arkanoid.ui.SpriteManager;
import javafx.scene.image.Image;

public class Wall extends Brick {
    public Wall(int x, int y) {
        super(x, y, BrickType.WALL);
    }

    public Wall(int x, int y, int width, int height, Image sprite) {
        super(x, y, width, height, BrickType.WALL);
        this.sprite = sprite;
    }
}
