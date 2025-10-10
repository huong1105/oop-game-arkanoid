package com.arkanoid.entities;

public class ExpandPaddlePowerUp extends PowerUp {

    public ExpandPaddlePowerUp(int x, int y, int width, int height) {
        super(x, y, width, height, "ExpandPaddle");
    }

    @Override
    public void applyEffect(Object obj) {
        if (obj instanceof Paddle) {
            Paddle paddle = (Paddle) obj;
            paddle.setWidth(paddle.getWidth() * 2.0);
        }
    }

    @Override
    public void removeEffect(Object obj) {
        if (obj instanceof Paddle) {
            Paddle paddle = (Paddle) obj;
            paddle.setWidth(paddle.getWidth() / 2.0);
        }
    }
}
