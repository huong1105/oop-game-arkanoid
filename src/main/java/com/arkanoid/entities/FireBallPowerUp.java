package com.arkanoid.entities;

import com.arkanoid.game.GameManager;

public class FireBallPowerUp extends PowerUp {
    public static final int DURATION_MILLIS = 5000;

    public FireBallPowerUp(int x, int y) {
        super(x, y, 20, 20, "FireBall", DURATION_MILLIS);
    }

    @Override
    public void applyEffect() {
        for (PowerUp p : GameManager.getInstance().getPowerUps()) {
            if (p instanceof FireBallPowerUp && p != this && p.isActivated()) {
                p.setActive(false);
            }
        }

        GameManager.getInstance().setFireBallActive(true);
        GameManager.getInstance().setFeverBallActive(false);
    }

    @Override
    public void removeEffect() {
        for (PowerUp p : GameManager.getInstance().getPowerUps()) {
            if (p instanceof FireBallPowerUp && p != this && p.isActivated()) {
                return; // Vẫn còn cái khác, không làm gì cả
            }
        }

        GameManager.getInstance().setFireBallActive(false);
    }
}
