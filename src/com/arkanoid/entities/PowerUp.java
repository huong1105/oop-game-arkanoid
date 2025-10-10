package com.arkanoid.entities;

import com.arkanoid.core.GameObject;
import javafx.scene.canvas.GraphicsContext;

import java.awt.*;

public abstract class PowerUp extends GameObject {
    private String type;
    private int duration;

    public PowerUp(int x, int y, int width, int height, String type) {
        super(x, y, width, height);
        this.type = type;
    }

    @Override
    public void update() { /* Logic cập nhật power-up */ }

    @Override
    public void render(GraphicsContext gc) { /* Logic vẽ power-up */ }

    public abstract void applyEffect(Object obj);

    public abstract void removeEffect(Object obj);
}
