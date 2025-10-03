package com.arkanoid.core;

import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public abstract class GameObject {

    protected double x, y, width, height;
    protected boolean active = true;
    protected Image sprite;

    public GameObject(double x, double y, double width, double height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public abstract void update();
    
    public abstract void render(GraphicsContext gc);

    public Image getSprite() {
        return sprite;
    }

    public Rectangle2D getBounds() {
        return new Rectangle2D(x, y, width, height);
    }

    public boolean intersects(GameObject other) {
        return this.getBounds().intersects(other.getBounds());
    }

    // Getters and Setters
    public boolean isActive() { return active; }
    public void setActive(boolean active) { this.active = active; }
    public double getX() { return x; }
    public void setX(double x) { this.x = x; }
    public double getY() { return y; }
    public void setY(double y) { this.y = y; }
    public double getWidth() { return width; }
    public void setWidth(double width) { this.width = width; }
    public double getHeight() { return height; }
    public void setHeight(double height) { this.height = height; }
}