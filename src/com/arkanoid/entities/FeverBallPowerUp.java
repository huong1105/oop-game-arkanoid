package com.arkanoid.entities;

import com.arkanoid.Const;
import com.arkanoid.game.GameManager;
import java.util.List;
import java.util.stream.Collectors;

public class FeverBallPowerUp extends PowerUp {
    private static final int DURATION = 8000; // ms
    private static final double SCORE_MULTIPLIER = 2.0;
    private static final int LIFE_PENALTY_MULTIPLIER = 2;

    public FeverBallPowerUp(int x, int y) {
        super(x, y, 20, 20, "FeverBall", DURATION);
    }

    @Override
    public void applyEffect() {
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