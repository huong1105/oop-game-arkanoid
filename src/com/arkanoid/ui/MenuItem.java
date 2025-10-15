package com.arkanoid.ui;

import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;

public class MenuItem {
    private String text;
    private Rectangle2D bounds;
    private boolean hovered = false;

    public MenuItem(String text, double x, double y, double width, double height) {
        this.text = text;
        this.bounds = new Rectangle2D(x, y, width, height);
    }

    public void render(GraphicsContext gc) {
        // Vẽ khung
        if (hovered) {
            gc.setFill(Color.web("#EEEEEE")); // Màu nền khi di chuột vào
            gc.setStroke(Color.WHITE); // Màu viền khi di chuột vào
        } else {
            gc.setFill(Color.web("#333333")); // Màu nền mặc định
            gc.setStroke(Color.web("#EEEEEE")); // Màu viền mặc định
        }
        gc.setLineWidth(2);
        gc.fillRoundRect(bounds.getMinX(), bounds.getMinY(), bounds.getWidth(), bounds.getHeight(), 20, 20);
        gc.strokeRoundRect(bounds.getMinX(), bounds.getMinY(), bounds.getWidth(), bounds.getHeight(), 20, 20);

        // Vẽ chữ
        if (hovered) {
            gc.setFill(Color.BLACK);
        } else {
            gc.setFill(Color.WHITE);
        }
        gc.setFont(new Font("Arial", 32));
        gc.setTextAlign(TextAlignment.CENTER);
        gc.fillText(text, bounds.getMinX() + bounds.getWidth() / 2, bounds.getMinY() + bounds.getHeight() / 2 + 10);
    }

    // Getters và Setters
    public Rectangle2D getBounds() {
        return bounds;
    }

    public void setHovered(boolean hovered) {
        this.hovered = hovered;
    }

    public String getText() {
        return text;
    }
}
