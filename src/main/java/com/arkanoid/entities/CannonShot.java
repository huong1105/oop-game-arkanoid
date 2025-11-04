package com.arkanoid.entities;

import com.arkanoid.Const;
import com.arkanoid.core.MovableObject;
import com.arkanoid.game.GameManager;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class CannonShot extends MovableObject {

    public CannonShot(int x, int y) {
        super(x, y, Const.CANNON_SHOT_WIDTH, Const.CANNON_SHOT_HEIGHT, 0, (float) -Const.CANNON_SHOT_SPEED);
        // Không cần sprite → vẽ bằng hình chữ nhật
    }

    @Override
    public void update(double deltaTimeSeconds) {
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
        // VẼ ĐẠN BẰNG RECTANGLE
        gc.setFill(Color.ORANGERED);
        gc.fillRect(x, y, width, height);
        gc.setStroke(Color.YELLOW);
        gc.strokeRect(x, y, width, height);
    }
}