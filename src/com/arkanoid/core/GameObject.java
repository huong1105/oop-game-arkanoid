package com.arkanoid.core;

import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.shape.Rectangle;

public abstract class GameObject {

    protected double x, y, width, height;
    protected boolean active = true;
    protected Image sprite;
    protected Rectangle2D bounds;

    public GameObject(double x, double y, double width, double height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.bounds = new Rectangle2D(x, y, width, height);
    }

    public abstract void update();

    /**
     * Sửa đổi phương thức render
     */
    public void render(GraphicsContext gc) {
        if (sprite != null && active) {
            gc.drawImage(sprite, x, y, width, height);
        }
    }

    public Rectangle2D getBounds() {
        return bounds;
    }

    public Image getSprite() {
        return sprite;
    }

    public boolean intersects(GameObject other) {
        return this.getBounds().intersects(other.getBounds());
    }

    /**
     * Phương thức tính toán và trả về vùng giao nhau của 2 Rectangle2D.
     * Trả về null nếu chúng không giao nhau.
     * @param other - vật giao với vật hiện tại
     * @return - Rectangle2D là vùng giao của 2 vật hoặc null.
     */
    public Rectangle2D intersection(Rectangle2D other){
        Rectangle2D thisRec = this.getBounds();
        //tính tọa độ góc trên trái
        double newX = Math.max(thisRec.getMinX(), other.getMinX());
        double newY = Math.max(thisRec.getMinY(), other.getMinY());
        //tính tọa độ góc dưới phải
        double newMaxX = Math.min(thisRec.getMaxX(), other.getMaxX());
        double newMaxY = Math.min(thisRec.getMaxY(), other.getMaxY());
        //tính độ dài rộng của vùng giao
        double newWidth = newMaxX - newX;
        double newHeight = newMaxY - newY;
        //trả về vùng giao nếu có
        if (newWidth > 0 && newHeight > 0) {
            return new Rectangle2D(newX, newY, newWidth, newHeight);
        } else {
            return null;
        }
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
