package com.arkanoid.entities;

import com.arkanoid.BrickType;
import com.arkanoid.Const;
import com.arkanoid.game.GameManager;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class ShieldPowerUp extends PowerUp {
    private Shield shield;
    private static final int DURATION = 0;

    public ShieldPowerUp(int x, int y) {
        super(x, y, 20, 20, "Shield", DURATION);
        shield = new Shield();
    }

    @Override
    public void applyEffect() {
        GameManager gm = GameManager.getInstance();
        gm.getGameObjects().removeIf(obj -> obj instanceof Shield);
        gm.addGameObject(shield);
    }

    @Override
    public void removeEffect() {
        // Không làm gì vì hiệu lực vĩnh viễn
    }
}
