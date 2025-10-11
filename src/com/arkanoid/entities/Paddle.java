package com.arkanoid.entities;

import com.arkanoid.Const;
import com.arkanoid.core.MovableObject;
import javafx.scene.canvas.GraphicsContext;

import java.awt.*;

// Lớp Paddle kế thừa từ MovableObject
public class Paddle extends MovableObject {
    private int speed;
    private String currentPowerUp;

    public Paddle(int x, int y, int width, int height) {
        super(x, y, width, height, 0, 0); // Vận tốc ban đầu là 0
    }

    @Override
    public void update() { /* Logic cập nhật paddle */ }

    @Override
    public void render(GraphicsContext gc) { /* Logic vẽ paddle */ }

    public void reset() {
        x = (Const.INSTANCE.getScreenWidth() / 2) - (Const.INSTANCE.getPaddleWidth() / 2);
        y = Const.INSTANCE.getScreenHeight() - Const.INSTANCE.getPaddleHeight();
        width =  Const.INSTANCE.getPaddleWidth();
        height = Const.INSTANCE.getPaddleHeight();
    }

    public void moveLeft() { /* Logic di chuyển trái */ }
    public void moveRight() { /* Logic di chuyển phải */ }
    public void applyPowerUp() { /* Logic áp dụng power-up */ }
}
