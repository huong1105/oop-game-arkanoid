package com.arkanoid.entities;

public class FastBallPowerUp extends PowerUp {
    public FastBallPowerUp(int x, int y, int width, int height) {
        super(x, y, width, height, "FAST_BALL");
    }

    @Override
    public void applyEffect(Paddle paddle) {
        // Logic làm cho bóng nhanh hơn (cần tham chiếu đến Ball)
    }
}
