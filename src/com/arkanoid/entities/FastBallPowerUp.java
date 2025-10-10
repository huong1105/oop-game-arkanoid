package com.arkanoid.entities;

public class FastBallPowerUp extends PowerUp {
    public FastBallPowerUp(int x, int y, int width, int height) {
        super(x, y, width, height, "FastBall");
    }

    @Override
    public void applyEffect(Object obj) {
        if (obj instanceof Ball) {
            Ball ball = (Ball) obj;
            ball.setMaxSpeed(ball.getMaxSpeed() * 2);
        }
    }

    @Override
    public void removeEffect(Object obj) {
        if (obj instanceof Ball) {
            Ball ball = (Ball) obj;
            ball.setMaxSpeed(ball.getMaxSpeed() / 2);
        }
    }
}
