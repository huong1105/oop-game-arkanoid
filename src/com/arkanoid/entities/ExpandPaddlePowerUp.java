package com.arkanoid.entities;

import com.arkanoid.Const;
import com.arkanoid.game.GameManager;

public class ExpandPaddlePowerUp extends PowerUp {
    private static final int DURATION = 10000;
    private static final double WIDTH_MULTIPLIER = 2.0;
    private double originalWidth;

    public ExpandPaddlePowerUp(int x, int y) {
        super(x, y, 20, 20, "ExpandPaddle", DURATION);
    }

    @Override
    public void applyEffect() {
        Paddle paddle = GameManager.getInstance().getPaddle();
        if (paddle.isExpanded()) return;

        originalWidth = paddle.getWidth();
        paddle.setWidth(paddle.getWidth() * WIDTH_MULTIPLIER);
        if (paddle.getX() + paddle.getWidth() > Const.SCREEN_WIDTH) {
            paddle.setX(Const.SCREEN_WIDTH - paddle.getWidth());
        }
        paddle.setExpanded(true);
    }

    @Override
    public void removeEffect() {
        if (originalWidth == 0) return;
        Paddle paddle = GameManager.getInstance().getPaddle();
        paddle.setWidth(originalWidth);
        if (paddle.getX() + paddle.getWidth() > Const.SCREEN_WIDTH) {
            paddle.setX(Const.SCREEN_WIDTH - paddle.getWidth());
        }
        paddle.setExpanded(false);
    }
}