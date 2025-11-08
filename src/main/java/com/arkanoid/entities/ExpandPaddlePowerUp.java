package com.arkanoid.entities;

import com.arkanoid.Const;
import com.arkanoid.game.GameManager;
import com.arkanoid.ui.SpriteManager;

public class ExpandPaddlePowerUp extends PowerUp {
    private static final int DURATION = 10000;
    private static final double WIDTH_MULTIPLIER = 2.0;

    public ExpandPaddlePowerUp(int x, int y) {
        super(x, y, 20, 20, "ExpandPaddle", DURATION, SpriteManager.POWERUP_EXPAND);
    }

    @Override
    public void reset(double x, double y) {
        super.reset(x, y);
    }

    @Override
    public void applyEffect() {
        for (PowerUp p : GameManager.getInstance().getPowerUps()) {
            if (p instanceof ExpandPaddlePowerUp && p != this && p.isActivated()) {
                p.setActive(false);
            }
        }

        Paddle paddle = GameManager.getInstance().getPaddle();
        paddle.setWidth(Const.PADDLE_WIDTH * WIDTH_MULTIPLIER);
        paddle.setExpanded(true);

        if (paddle.getX() + paddle.getWidth() > Const.SCREEN_WIDTH - 25) {
            paddle.setX(Const.SCREEN_WIDTH - paddle.getWidth() - 25);
        }
    }

    @Override
    public void removeEffect() {
        for (PowerUp p : GameManager.getInstance().getPowerUps()) {
            if (p instanceof ExpandPaddlePowerUp && p != this && p.isActivated()) {
                return;
            }
        }

        Paddle paddle = GameManager.getInstance().getPaddle();
        paddle.setWidth(Const.PADDLE_WIDTH); // Reset về hằng số gốc
        paddle.setExpanded(false);

        if (paddle.getX() + paddle.getWidth() > Const.SCREEN_WIDTH - 25) {
            paddle.setX(Const.SCREEN_WIDTH - paddle.getWidth() - 25);
        }
    }
}
