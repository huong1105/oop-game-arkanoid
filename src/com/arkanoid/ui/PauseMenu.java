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

public class PauseMenu {

    private final List<MenuItem> menuItems = new ArrayList<>();
    private final double canvasWidth;
    private final double canvasHeight;

    private Font titleFont;
    private final DropShadow neonGlow;
    private final Color neonCyan = Color.rgb(0, 255, 255);
    private final Color gridColor = Color.rgb(0, 255, 255, 0.25);

    public PauseMenu(double canvasWidth, double canvasHeight) {
        this.canvasWidth = canvasWidth;
        this.canvasHeight = canvasHeight;

        try {
            titleFont = Font.loadFont(new File("res/fonts/Orbitron-Bold.ttf").toURI().toString(), 90);
            if (titleFont == null) throw new Exception("Font not loaded");
        } catch (Exception e) {
            System.err.println("Không thể tải phông chữ 'Orbitron-Bold.ttf'. Sử dụng phông chữ mặc định.");
            titleFont = Font.font("Monospaced", 90);
        }

        // Tạo hiệu ứng phát sáng
        neonGlow = new DropShadow(25, Color.rgb(0, 255, 255, 0.8));

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

        gc.setFill(neonCyan);
        gc.setTextAlign(TextAlignment.CENTER);
        gc.setTextBaseline(VPos.CENTER);
        gc.setFont(titleFont);

        // Vẽ ÁNH SÁNG (với hiệu ứng)
        gc.setEffect(neonGlow);
        gc.setFill(neonCyan);
        gc.fillText("ARKANOID", canvasWidth / 2, canvasHeight / 4.5);

        // Vẽ LÕI CHỮ (không hiệu ứng, màu trắng)
        gc.setEffect(null); // Tắt hiệu ứng
        gc.setFill(Color.WHITE); // Màu trắng để tạo lõi sắc nét
        gc.fillText("ARKANOID", canvasWidth / 2, canvasHeight / 4.5);

        Text tempText = new Text("ARKANOID");
        tempText.setFont(titleFont); // Dùng cùng font với tiêu đề
        double textWidth = tempText.getLayoutBounds().getWidth();

        double textY = canvasHeight / 4.5;
        double centerX = canvasWidth / 2;

        // Vị trí Y của các đường gạch
        double mainLineY = textY + 60; // Đường gạch chính
        double thinLineY = textY + 68; // Đường gạch mỏng

        // Vị trí X dựa trên chiều rộng text
        double lineStartX = centerX - (textWidth / 2);
        double lineEndX = centerX + (textWidth / 2);

        // Vị trí & Kích thước của biểu tượng
        double glyphsStartX = lineEndX + 15;
        double glyphsWidth = 30;

        // Đặt màu
        gc.setStroke(neonCyan);
        gc.setFill(neonCyan);

        // Vẽ đường gạch chính (dày)
        gc.setLineWidth(4);
        gc.strokeLine(lineStartX, mainLineY, glyphsStartX - 5, mainLineY);

        // Vẽ đường gạch mỏng (dài hơn)
        gc.setLineWidth(1.5);
        gc.strokeLine(lineStartX - 10, thinLineY, glyphsStartX + glyphsWidth, thinLineY);

        // Tam giác nhỏ
        gc.fillPolygon(
                new double[]{glyphsStartX, glyphsStartX + 8, glyphsStartX + 4},
                new double[]{mainLineY + 2, mainLineY + 2, mainLineY - 5}, // Căn theo mainLineY
                3
        );

        // Hai đường chéo
        gc.setLineWidth(2);
        gc.strokeLine(glyphsStartX + 13, mainLineY + 2, glyphsStartX + 18, mainLineY - 7);
        gc.strokeLine(glyphsStartX + 19, mainLineY + 2, glyphsStartX + 24, mainLineY - 7);

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