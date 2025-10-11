package com.arkanoid.entities;

import java.util.List;

public class MultiBallPowerup extends PowerUp {
    private List<Ball> gameBalls; // danh sách bóng đang tồn tại trong game

    public MultiBallPowerup(int x, int y, int width, int height, List<Ball> gameBalls) {
        super(x, y, width, height, "MultiBall");
        this.gameBalls = gameBalls;
    }

    @Override
    public void applyEffect(Object target) {
        if (target instanceof Ball) {
            Ball originalBall = (Ball) target;

            // Tạo 2 bóng mới từ bóng gốc
            Ball ball1 = new Ball(
                    (int) originalBall.getX(),
                    (int) originalBall.getY(),
                    (int) originalBall.getWidth(),
                    (int) (-originalBall.getSpeedX()), // đi ngược hướng
                    (int) (originalBall.getSpeedY())
            );

            Ball ball2 = new Ball(
                    (int) originalBall.getX(),
                    (int) originalBall.getY(),
                    (int) originalBall.getWidth(),
                    (int) (originalBall.getSpeedX() * 0.8),
                    (int) (-originalBall.getSpeedY()) // đảo hướng Y
            );

            // thêm bóng mới vào danh sách
            gameBalls.add(ball1);
            gameBalls.add(ball2);

            System.out.println("🎱 MultiBall! Tổng số bóng hiện tại: " + gameBalls.size());
        }
    }

    @Override
    public void removeEffect(Object target) {}
}
