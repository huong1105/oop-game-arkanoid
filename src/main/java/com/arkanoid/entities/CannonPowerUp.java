package com.arkanoid.entities;

import com.arkanoid.Const;
import com.arkanoid.game.GameManager;

public class CannonPowerUp extends PowerUp {
    public static final int DURATION_MILLIS = 0;

    public CannonPowerUp(int x, int y) {
        super(x, y, 20, 20, "Cannon", DURATION_MILLIS);
    }

    @Override
    public void applyEffect() {
        Paddle paddle = (Paddle) target;
        paddle.enableCannon(5);
    }

    @Override
    public void removeEffect() {
        if (target instanceof Paddle) {
            ((Paddle) target).disableCannon();
        }
        PowerUpPool.getInstance().returnPowerUp(this);
    }
}