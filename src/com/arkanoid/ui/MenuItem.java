package com.arkanoid.ui;

import javafx.geometry.Rectangle2D;
import javafx.geometry.VPos;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.effect.DropShadow;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;

import java.io.File;

public class MenuItem {
    private final String text;
    private final Rectangle2D bounds;
    private boolean hovered = false;

    private Font buttonFont;
    private final Color neonCyan = Color.rgb(0, 255, 255);
    private final Color buttonFill = Color.rgb(0, 50, 70, 0.4);

    private static final DropShadow neonGlow = new DropShadow(20, Color.rgb(0, 255, 255, 0.7));

    public MenuItem(String text, double x, double y, double width, double height) {
        this.text = text;
        this.bounds = new Rectangle2D(x, y, width, height);

        try {
            buttonFont = Font.loadFont(new File("res/fonts/Orbitron-Regular.ttf").toURI().toString(), 32);
            if (buttonFont == null) throw new Exception("Font not loaded");
        } catch (Exception e) {
            System.err.println("Không thể tải phông chữ 'Orbitron-Regular.ttf'. Sử dụng phông chữ mặc định.");
            buttonFont = Font.font("Monospaced", 32);
        }
    }

    public void render(GraphicsContext gc) {
        double arc = 20; // Độ bo tròn góc
        gc.setFont(buttonFont);
        gc.setTextAlign(TextAlignment.CENTER);
        gc.setTextBaseline(VPos.CENTER);

        double textY = bounds.getMinY() + bounds.getHeight() / 2; // Căn giữa chữ
        double textX = bounds.getMinX() + bounds.getWidth() / 2;

        // Vẽ khung
        if (hovered) {
            gc.setEffect(neonGlow);
            gc.setFill(neonCyan);
            gc.fillRoundRect(bounds.getMinX(), bounds.getMinY(), bounds.getWidth(), bounds.getHeight(), arc, arc);
            gc.setEffect(null); // Tắt hiệu ứng

            // Vẽ chữ màu đen
            gc.setFill(Color.BLACK);
            gc.fillText(text, textX, textY);
        } else {
            gc.setFill(buttonFill);
            gc.fillRoundRect(bounds.getMinX(), bounds.getMinY(), bounds.getWidth(), bounds.getHeight(), arc, arc);

            // Vẽ viền
            gc.setStroke(neonCyan);
            gc.setLineWidth(3);
            gc.strokeRoundRect(bounds.getMinX(), bounds.getMinY(), bounds.getWidth(), bounds.getHeight(), arc, arc);

            // Vẽ chữ
            gc.setFill(neonCyan);
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
