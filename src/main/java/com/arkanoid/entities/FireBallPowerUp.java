package com.arkanoid.entities;

import com.arkanoid.Const;
import com.arkanoid.game.GameManager;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class FireBallPowerUp extends PowerUp {
    public static final int DURATION_MILLIS = 5000;

    public FireBallPowerUp(int x, int y) {
        super(x, y, 20, 20, "FireBall", DURATION_MILLIS);
    }

    @Override
    public void applyEffect() {
        GameManager gm = GameManager.getInstance();
        for (Ball ball : gm.getBalls()) {
            if (ball.isActive()) {
                ball.setFireBall(true);  // Thêm method này vào Ball
                ball.setMaxSpeed(ball.getMaxSpeed() * 2.0);  // Tăng speed
            }
        }
        gm.setFeverBallActive(false);  // Tắt fever nếu đang on (ưu tiên?)
    }

    @Override
    public void removeEffect() {
        GameManager gm = GameManager.getInstance();
        for (Ball ball : gm.getBalls()) {
            if (ball.isActive()) {
                ball.setFireBall(false);
                ball.setMaxSpeed(Const.BALL_MAXSPEED);  // Reset speed
            }
        }
    }
}