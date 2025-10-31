package com.arkanoid.entities;

import com.arkanoid.Const;
import com.arkanoid.core.GameObject;
import com.arkanoid.game.GameManager;
import java.util.List;
import java.util.Objects;

public class FastBallPowerUp extends PowerUp {
    private static final int DURATION = 5000; // ms
    private static final double SPEED_MULTIPLIER = 2.0;

    public FastBallPowerUp(int x, int y) {
        super(x, y, 20, 20, "FastBall", DURATION);
    }

    @Override
    public void reset(double x, double y) {
        super.reset(x, y);
    }

    @Override
    public void applyEffect() {
        for (PowerUp obj : GameManager.getInstance().getPowerUps()) {
            if (obj instanceof FastBallPowerUp && obj != this && obj.isActivated()) {
                obj.durationSeconds = this.durationSeconds;
                this.durationSeconds = 0;
                return;
            }
        }

        List<Ball> currentBalls = GameManager.getInstance().getBalls().stream()
                .filter(Objects::nonNull)
                .map(obj -> (Ball) obj)
                .toList();

        for (Ball ball : currentBalls) {
            ball.setMaxSpeed((int)(ball.getMaxSpeed() * SPEED_MULTIPLIER));
            ball.setSpeedX(ball.getSpeedX() * SPEED_MULTIPLIER);
            ball.setSpeedY(ball.getSpeedY() * SPEED_MULTIPLIER);
        }
    }

    @Override
    public void removeEffect() {
        List<Ball> currentBalls = GameManager.getInstance().getBalls().stream()
                .filter(Objects::nonNull)
                .map(obj -> (Ball) obj)
                .toList();

        for (Ball ball : currentBalls) {
            ball.setMaxSpeed(Const.BALL_MAXSPEED);
        }
    }
}
