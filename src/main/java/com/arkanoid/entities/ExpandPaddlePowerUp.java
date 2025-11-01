package com.arkanoid.entities;

import com.arkanoid.Const;
import com.arkanoid.core.GameObject;
import com.arkanoid.game.GameManager;

public class ExpandPaddlePowerUp extends PowerUp {
    private static final int DURATION = 10000;
    private static final double WIDTH_MULTIPLIER = 2.0;
    private double originalWidth;

    public ExpandPaddlePowerUp(int x, int y) {
        super(x, y, 20, 20, "ExpandPaddle", DURATION);
    }

    @Override
    public void reset(double x, double y) {
        super.reset(x, y);
        this.originalWidth = 0;
    }

    @Override
    public void applyEffect() {
        Paddle paddle = GameManager.getInstance().getPaddle();
        for (PowerUp obj : GameManager.getInstance().getPowerUps()) {
            if (obj instanceof ExpandPaddlePowerUp && obj != this && obj.isActivated()) {
                obj.durationSeconds = this.durationSeconds;
                this.durationSeconds = 0;
                return;
            }
        }

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
        this.originalWidth = 0;
    }
}