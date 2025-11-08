package com.arkanoid.entities;

import com.arkanoid.Const;
import com.arkanoid.game.GameManager;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import com.arkanoid.ui.SpriteManager;

public class MultiBallPowerUp extends PowerUp {
    private static final int DURATION = 0;

    public MultiBallPowerUp(int x, int y) {
        super(x, y, 20, 20, "MultiBall", DURATION, SpriteManager.POWERUP_MULTIBALL); // Kích thước 20x20
    }

    @Override
    public void reset(double x, double y) {
        super.reset(x, y);
    }

    @Override
    public void applyEffect() {
        GameManager gameManager = GameManager.getInstance();

        // Tìm tất cả các ball hiện tại
        List<Ball> currentBalls = gameManager.getBalls().stream()
                .filter(Objects::nonNull)
                .map(obj -> (Ball) obj)
                .toList();

        for (Ball currentBall : currentBalls) {
            Ball newBall = new Ball(
                    (int) currentBall.getX() + 5,
                    (int) currentBall.getY(),
                    Const.BALL_DIAMETER,
                    (int) -currentBall.getSpeedX(),
                    (int) currentBall.getSpeedY()
            );

            double speedX = newBall.getSpeedX();
            double speedY = newBall.getSpeedY();
            if (speedY == 0) {
                speedY = currentBall.getMaxSpeed() * 0.7;
                speedX = Math.sqrt(Math.pow(currentBall.getMaxSpeed(), 2) - Math.pow(speedY, 2));
                if (newBall.getSpeedX() < 0) {
                    speedX = -speedX;
                }
                newBall.setSpeedX(speedX);
                newBall.setSpeedY(speedY);
            }

            double currentSpeed = Math.sqrt(Math.pow(newBall.getSpeedX(), 2) + Math.pow(newBall.getSpeedY(), 2));
            if (currentSpeed != 0) {
                double scale = currentBall.getMaxSpeed() / currentSpeed;
                newBall.setSpeedX(newBall.getSpeedX() * scale);
                newBall.setSpeedY(newBall.getSpeedY() * scale);
            }

            newBall.setMaxSpeed(currentBall.getMaxSpeed());
            newBall.start();
            gameManager.addGameObject(newBall);
        }
    }

    @Override
    public void removeEffect() {
        // Không làm gì vì hiệu lực vĩnh viễn
    }
}
