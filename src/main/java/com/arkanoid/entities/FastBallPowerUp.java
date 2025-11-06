package com.arkanoid.entities;

import com.arkanoid.Const;
import com.arkanoid.game.GameManager;

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
                obj.setActive(false);
            }
        }
        double newSpeed = Const.BALL_MAXSPEED * SPEED_MULTIPLIER;
        for (Ball ball : GameManager.getInstance().getBalls()) {
            ball.setMaxSpeed(newSpeed);
            double currentSpeed = Math.sqrt(ball.getSpeedX() * ball.getSpeedX() + ball.getSpeedY() * ball.getSpeedY());
            if (currentSpeed > 0) {
                ball.setSpeedX((ball.getSpeedX() / currentSpeed) * newSpeed);
                ball.setSpeedY((ball.getSpeedY() / currentSpeed) * newSpeed);
            }
        }
    }

    @Override
    public void removeEffect () {
        for (PowerUp p : GameManager.getInstance().getPowerUps()) {
            if (p instanceof FastBallPowerUp && p != this && p.isActivated()) {
                return;
            }
        }

        for (Ball ball : GameManager.getInstance().getBalls()) {
            ball.setMaxSpeed(Const.BALL_MAXSPEED);
            double currentSpeed = Math.sqrt(ball.getSpeedX() * ball.getSpeedX() + ball.getSpeedY() * ball.getSpeedY());

            if (currentSpeed > 0) {
                ball.setSpeedX((ball.getSpeedX() / currentSpeed) * Const.BALL_MAXSPEED);
                ball.setSpeedY((ball.getSpeedY() / currentSpeed) * Const.BALL_MAXSPEED);
            }
        }
    }
}
