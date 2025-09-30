package com.arkanoid.core;

import java.awt.Graphics2D;
import java.awt.Rectangle;

public abstract class GameObject {
    protected int x, y, width, height;
    protected boolean active = true;

    public GameObject(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public abstract void update();
    public abstract void render(Graphics2D g);

    public Rectangle getBounds() {
        return new Rectangle(x, y, width, height);
    }

    public boolean isActive() { return active; }
    public void setActive(boolean active) { this.active = active; }
    public int getX() { return x; }
    public int getY() { return y; }
    public int getWidth() { return width; }
    public int getHeight() { return height; }
    public int setY(int y) { this.y = y; return y; }
    public int setX(int x) { this.x = x; return x; }
    public int setWidth(int width) { this.width = width; return width; }
    public int setHeight(int height) { this.height = height; return height; }
}
