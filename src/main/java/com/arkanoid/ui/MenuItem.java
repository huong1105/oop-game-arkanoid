package com.arkanoid.ui;

import javafx.geometry.Rectangle2D;
import javafx.geometry.VPos;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.effect.DropShadow;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;

import java.io.File;

import static com.arkanoid.game.FontManager.PAPYRUS_32;

public class MenuItem {
    private final String text;
    private final Rectangle2D bounds;
    private boolean hovered = false;

    private final Color forestGreen = Color.rgb(60, 180, 70); // Xanh lá
    private final Color buttonFill = Color.rgb(80, 50, 15, 0.7);

    private static final DropShadow forestGlow = new DropShadow(20, Color.rgb(60, 180, 70, 0.7));

    public MenuItem(String text, double x, double y, double width, double height) {
        this.text = text;
        this.bounds = new Rectangle2D(x, y, width, height);
    }

    public void render(GraphicsContext gc) {
        double arc = 20; // Độ bo tròn góc
        gc.setFont(PAPYRUS_32);
        gc.setTextAlign(TextAlignment.CENTER);
        gc.setTextBaseline(VPos.CENTER);

        double textY = bounds.getMinY() + bounds.getHeight() / 2; // Căn giữa chữ
        double textX = bounds.getMinX() + bounds.getWidth() / 2;

        // Vẽ khung
        if (hovered) {
            gc.setEffect(forestGlow);
            gc.setFill(forestGreen);
            gc.fillRoundRect(bounds.getMinX(), bounds.getMinY(), bounds.getWidth(), bounds.getHeight(), arc, arc);
            gc.setEffect(null); // Tắt hiệu ứng

            // Vẽ chữ màu đen
            gc.setFill(Color.BLACK);
            gc.fillText(text, textX, textY);
        } else {
            gc.setFill(buttonFill);
            gc.fillRoundRect(bounds.getMinX(), bounds.getMinY(), bounds.getWidth(), bounds.getHeight(), arc, arc);

            // Vẽ viền
            gc.setStroke(forestGreen);
            gc.setLineWidth(3);
            gc.strokeRoundRect(bounds.getMinX(), bounds.getMinY(), bounds.getWidth(), bounds.getHeight(), arc, arc);

            // Vẽ chữ
            gc.setFill(forestGreen);
            gc.fillText(text, textX, textY);
        }
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
