package com.arkanoid.ui;

import javafx.geometry.VPos;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.effect.DropShadow;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static com.arkanoid.game.FontManager.PAPYRUS_32;
import static com.arkanoid.game.FontManager.PAPYRUS_90;

public class PauseMenu {

    private final List<MenuItem> menuItems = new ArrayList<>();
    private final double canvasWidth;
    private final double canvasHeight;

    private final DropShadow forestGlow;
    private final Color forestGreen = Color.rgb(60, 180, 70);

    public PauseMenu(double canvasWidth, double canvasHeight) {
        this.canvasWidth = canvasWidth;
        this.canvasHeight = canvasHeight;

        // Tạo hiệu ứng phát sáng
        forestGlow = new DropShadow(25, Color.rgb(60, 180, 70, 0.8));

        double buttonWidth = 300;
        double buttonHeight = 60;
        double spacing = 20;
        double startX = (canvasWidth - buttonWidth) / 2;
        // Tính toán để căn giữa các nút
        double totalHeight = (buttonHeight * 3) + (spacing * 2);
        double startY = (canvasHeight - totalHeight) / 2;

        menuItems.add(new MenuItem("Resume", startX, startY, buttonWidth, buttonHeight));
        menuItems.add(new MenuItem("Restart Level", startX, startY + buttonHeight + spacing, buttonWidth, buttonHeight));
        menuItems.add(new MenuItem("Quit to Menu", startX, startY + (buttonHeight + spacing) * 2, buttonWidth, buttonHeight));
    }

    /** Cập nhật trạng thái hover của các nút. */
    public void update(double mouseX, double mouseY) {
        for (MenuItem item : menuItems) {
            item.setHovered(item.getBounds().contains(mouseX, mouseY));
        }
    }

    /** Vẽ menu. */
    public void render(GraphicsContext gc) {
        // Vẽ một lớp nền mờ
        gc.setFill(Color.rgb(0, 0, 0, 0.7)); // Màu đen, 70% mờ
        gc.fillRect(0, 0, canvasWidth, canvasHeight);

        UIUtils.drawStyledTitle(gc, canvasWidth, canvasHeight);

        // Vẽ các nút
        for (MenuItem item : menuItems) {
            item.render(gc);
        }
    }

    /** Lấy text của nút được click. */
    public String getClickedItem(double mouseX, double mouseY) {
        for (MenuItem item : menuItems) {
            if (item.getBounds().contains(mouseX, mouseY)) {
                return item.getText();
            }
        }
        return null;
    }
}