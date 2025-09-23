package com.arkanoid.entities;

public class ExpandPaddlePowerUp extends PowerUp {
    public ExpandPaddlePowerUp(int x, int y, int width, int height) {
        super(x, y, width, height, "EXPAND_PADDLE");
    }

    @Override
    public void applyEffect(Paddle paddle) {
        // Logic làm cho paddle dài ra
    }
}
