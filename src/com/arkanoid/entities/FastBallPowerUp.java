package com.arkanoid.entities;

import com.arkanoid.Const;
import com.arkanoid.game.GameManager;
import java.util.List;
import java.util.stream.Collectors;

public class FastBallPowerUp extends PowerUp {
    private static final int DURATION = 5000;
    private static final double SPEED_MULTIPLIER = 2.0;

    public FastBallPowerUp(int x, int y) {
        super(x, y, 20, 20, "FastBall", DURATION);
    }

    @Override
    public void applyEffect() {
        GameManager gameManager = GameManager.getInstance();
        List<Ball> currentBalls = gameManager.getGameObjects().stream()
                .filter(obj -> obj instanceof Ball)
                .map(obj -> (Ball) obj)
                .collect(Collectors.toList());

        for (Ball ball : currentBalls) {
            ball.setMaxSpeed((int)(ball.getMaxSpeed() * SPEED_MULTIPLIER));
            ball.setSpeedX(ball.getSpeedX() * SPEED_MULTIPLIER);
            ball.setSpeedY(ball.getSpeedY() * SPEED_MULTIPLIER);
        }
    }

    @Override
    public void removeEffect() {
        GameManager gameManager = GameManager.getInstance();
        List<Ball> currentBalls = gameManager.getGameObjects().stream()
                .filter(obj -> obj instanceof Ball)
                .map(obj -> (Ball) obj)
                .collect(Collectors.toList());

        for (Ball ball : currentBalls) {
            ball.setMaxSpeed((int)(ball.getMaxSpeed() / SPEED_MULTIPLIER));
            ball.setSpeedX(ball.getSpeedX() / SPEED_MULTIPLIER);
            ball.setSpeedY(ball.getSpeedY() / SPEED_MULTIPLIER);
        }
    }
}