package com.arkanoid.ui;

import com.arkanoid.Const;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;

import java.util.ArrayList;
import java.util.List;

public class MainMenu {
    private List<MenuItem> menuItems = new ArrayList<>();
    private double canvasWidth;
    private double canvasHeight;

    public MainMenu(double canvasWidth, double canvasHeight) {
        this.canvasWidth = canvasWidth;
        this.canvasHeight = canvasHeight;

        // Định nghĩa các nút menu
        double buttonWidth = 300;
        double buttonHeight = 60;
        double startX = (canvasWidth - buttonWidth) / 2;
        double startY = canvasHeight / 2.5;

        menuItems.add(new MenuItem("New Game", startX, startY, buttonWidth, buttonHeight));
        menuItems.add(new MenuItem("High Score", startX, startY + 80, buttonWidth, buttonHeight));
        menuItems.add(new MenuItem("Setting", startX, startY + 160, buttonWidth, buttonHeight));
    }

    /** Cập nhật trạng thái hover của các nút dựa trên vị trí chuột. */
    public void update(double mouseX, double mouseY) {
        for (MenuItem item : menuItems) {
            if (item.getBounds().contains(mouseX, mouseY)) {
                item.setHovered(true);
            } else {
                item.setHovered(false);
            }
        }
    }

    /** Vẽ toàn bộ menu. */
    public void render(GraphicsContext gc) {
        // Vẽ tiêu đề game
        gc.setFill(Color.WHITE);
        gc.setTextAlign(TextAlignment.CENTER);
        gc.setFont(new Font("Arial", 70));
        gc.fillText("Arkanoid", canvasWidth / 2, canvasHeight / 4);

        // Vẽ các nút
        for (MenuItem item : menuItems) {
            item.render(gc);
        }
    }

    /** Lấy danh sách các mục menu để xử lý click. */
    public List<MenuItem> getMenuItems() {
        return menuItems;
    }
}
