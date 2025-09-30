package com.arkanoid.entities;

import com.arkanoid.core.MovableObject;
import com.arkanoid.core.GameObject;

import java.awt.*;

// Lớp Ball kế thừa từ MovableObject
public class Ball extends MovableObject {
    private int speed;
    private int directionX, directionY;

    public Ball(int x, int y, int width, int height) {
        super(x, y, width, height, 1, -1); // Vận tốc ban đầu
    }

    @Override
    public void update() { /* Logic cập nhật bóng */ }

    @Override
    public void render(Graphics2D g) { /* Logic vẽ bóng */ }

    public void bounceOff(GameObject other) { /* Logic nảy bóng */ }
    public void checkCollision(GameObject other) { /* Logic kiểm tra va chạm */ }
}
