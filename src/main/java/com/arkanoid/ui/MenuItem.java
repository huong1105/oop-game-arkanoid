package com.arkanoid.ui;

import javafx.geometry.Rectangle2D;
import javafx.geometry.VPos;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.TextAlignment;

import static com.arkanoid.game.FontManager.PAPYRUS_32;

public class MenuItem {
    private final String text;
    private final Rectangle2D bounds;
    private boolean hovered = false;
    private boolean isLocked = false;
    private final Color buttonFill = Color.rgb(80, 50, 15, 0.7);

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
        if (isLocked) {
            // Vẽ nút bị mờ đi (xám)
            gc.setFill(Color.rgb(50, 50, 50, 0.7)); // Nền xám đậm
            gc.fillRoundRect(bounds.getMinX(), bounds.getMinY(), bounds.getWidth(), bounds.getHeight(), arc, arc);
            gc.setStroke(Color.GRAY); // Viền xám
            gc.setLineWidth(3);
            gc.strokeRoundRect(bounds.getMinX(), bounds.getMinY(), bounds.getWidth(), bounds.getHeight(), arc, arc);
            gc.setFill(Color.GRAY); // Chữ xám
            gc.fillText(text, textX, textY);

            // (Bạn có thể thêm biểu tượng khóa ở đây nếu muốn)

        } else if (hovered) {
            gc.setEffect(UIUtils.getMenuItemHoverGlow());
            gc.setEffect(UIUtils.getForestGlow());
            gc.setFill(UIUtils.getForestGreen());
            gc.fillRoundRect(bounds.getMinX(), bounds.getMinY(), bounds.getWidth(), bounds.getHeight(), arc, arc);
            gc.setEffect(null); // Tắt hiệu ứng

            // Vẽ chữ màu đen
            gc.setFill(Color.BLACK);
            gc.fillText(text, textX, textY);
        } else {
            gc.setFill(buttonFill);
            gc.fillRoundRect(bounds.getMinX(), bounds.getMinY(), bounds.getWidth(), bounds.getHeight(), arc, arc);

            // Vẽ viền
            gc.setStroke(UIUtils.getForestGreen());
            gc.setLineWidth(3);
            gc.strokeRoundRect(bounds.getMinX(), bounds.getMinY(), bounds.getWidth(), bounds.getHeight(), arc, arc);

            // Vẽ chữ
            gc.setFill(UIUtils.getForestGreen());
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

    public void setLocked(boolean locked) {
        isLocked = locked;
    }

    public boolean isLocked() {
        return isLocked;
    }
}
