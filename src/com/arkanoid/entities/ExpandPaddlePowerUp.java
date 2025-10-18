package com.arkanoid.entities;

import com.arkanoid.Const;
import com.arkanoid.game.GameManager;

public class ExpandPaddlePowerUp extends PowerUp {
    private static final int DURATION = 10; // 10 giây
    private static final double WIDTH_MULTIPLIER = 2.0;
    private double originalWidth;

    public ExpandPaddlePowerUp(int x, int y) {
        super(x, y, 20, 20, "ExpandPaddle", DURATION);
    }

    @Override
    public void applyEffect() {
        GameManager gameManager = GameManager.getInstance();
        Paddle paddle = gameManager.getPaddle();
        if (!paddle.isExpanded()) {
            originalWidth = paddle.getWidth();
            paddle.setWidth(paddle.getWidth() * WIDTH_MULTIPLIER);
            if (paddle.getX() + paddle.getWidth() > Const.SCREEN_WIDTH) {
                paddle.setX(Const.SCREEN_WIDTH - paddle.getWidth());
            }
            paddle.setExpanded(true);
        }
    }

    @Override
    public void removeEffect() {
        GameManager gameManager = GameManager.getInstance();
        Paddle paddle = gameManager.getPaddle();
        paddle.setWidth(originalWidth);
        if (paddle.getX() + paddle.getWidth() > Const.SCREEN_WIDTH) {
            paddle.setX(Const.SCREEN_WIDTH - paddle.getWidth());
        }
        paddle.setExpanded(false);
    }
}