package com.arkanoid.entities;

import com.arkanoid.Const;
import com.arkanoid.core.MovableObject;
import com.arkanoid.ui.SpriteManager;
import javafx.scene.canvas.GraphicsContext;

public class CannonShot extends MovableObject {

    public CannonShot(int x, int y) {
        super(x, y, Const.CANNON_SHOT_WIDTH, Const.CANNON_SHOT_HEIGHT, 0, (float) -Const.CANNON_SHOT_SPEED);
        this.sprite = SpriteManager.BULLET;
    }

    @Override
    public void update(double deltaTimeSeconds) {
        if (!active) return;
        super.update(deltaTimeSeconds);
        if (getY() < -height) {
            CannonShotPool.getInstance().returnShot(this);
        }
    }

    public void reset(double x, double y) {
        this.x = x;
        this.y = y;
        this.active = true;
        this.speedY = -Const.CANNON_SHOT_SPEED;
    }

    @Override
    public void render(GraphicsContext gc) {
        if (!active) return;
        super.render(gc);
    }
}
