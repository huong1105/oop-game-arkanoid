package com.arkanoid.entities;

import com.arkanoid.game.GameManager;

public class FeverBallPowerUp extends PowerUp {
    private static final int DURATION = 8000; // ms
    private static final double SCORE_MULTIPLIER = 2.0;
    private static final int LIFE_PENALTY_MULTIPLIER = 2;

    public FeverBallPowerUp(int x, int y) {
        super(x, y, 20, 20, "FeverBall", DURATION);
    }

    public void reset(double x, double y) {
        super.reset(x, y);
    }

    @Override
    public void applyEffect() {
        for (PowerUp obj : GameManager.getInstance().getPowerUps()) {
            if ((obj instanceof FeverBallPowerUp) && (obj != this) && obj.isActivated()) {
                obj.durationSeconds = this.durationSeconds;
                this.durationSeconds = 0;
                return;
            }
        }

        GameManager.getInstance().setFeverBallActive(true);
    }

    @Override
    public void removeEffect() {
        GameManager.getInstance().setFeverBallActive(false);
    }

    public static double getScoreMultiplier() {
        return SCORE_MULTIPLIER;
    }

    public static int getLifePenaltyMultiplier() {
        return LIFE_PENALTY_MULTIPLIER;
    }
}