package com.arkanoid.ui;

import javafx.geometry.Point2D;
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
import java.util.Random;

public class MainMenu {
    private final List<MenuItem> menuItems = new ArrayList<>();
    private final double canvasWidth;
    private final double canvasHeight;

    private Font titleFont;
    private final DropShadow neonGlow;
    private final Color neonCyan = Color.rgb(0, 255, 255);
    private final Color gridColor = Color.rgb(0, 255, 255, 0.25); // Màu lưới mờ

    // Nền sao
    private final List<Point2D> stars = new ArrayList<>();
    private final Random random = new Random();

    public MainMenu(double canvasWidth, double canvasHeight) {
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

        // Định nghĩa các nút menu
        double buttonWidth = 350;
        double buttonHeight = 60;
        double startX = (canvasWidth - buttonWidth) / 2;
        double startY = canvasHeight / 2.5;

        menuItems.add(new MenuItem("New Game", startX, startY, buttonWidth, buttonHeight));
        menuItems.add(new MenuItem("High Score", startX, startY + 80, buttonWidth, buttonHeight));
        menuItems.add(new MenuItem("Setting", startX, startY + 160, buttonWidth, buttonHeight));
        menuItems.add(new MenuItem("Quit Game", startX, startY + 240, buttonWidth, buttonHeight));

        initializeStars();
    }

    private void initializeStars() {
        for (int i = 0; i < 150; i++) {
            stars.add(new Point2D(random.nextDouble() * canvasWidth, random.nextDouble() * canvasHeight));
        }
    }

    /** Cập nhật trạng thái hover của các nút dựa trên vị trí chuột. */
    public void update(double mouseX, double mouseY) {
        for (MenuItem item : menuItems) {
            item.setHovered(item.getBounds().contains(mouseX, mouseY));
        }
    }

    /** Vẽ nền (sao và lưới). */
    private void drawBackground(GraphicsContext gc) {
        // 1. Vẽ các ngôi sao
        gc.setFill(Color.WHITE);
        for (Point2D star : stars) {
            double size = 1 + random.nextDouble() * 2; // Kích thước sao ngẫu nhiên
            gc.fillOval(star.getX(), star.getY(), size, size);
        }

        // 2. Vẽ lưới phối cảnh
        gc.setStroke(gridColor);
        gc.setLineWidth(1.5);

        double horizonY = canvasHeight * 0.4; // Điểm tụ
        double vanishingPointX = canvasWidth / 2;

        // Vẽ các đường ngang
        double y = horizonY;
        double step = 2;
        while (y < canvasHeight) {
            gc.strokeLine(0, y, canvasWidth, y);
            y += step;
            step *= 1.1; // Các đường xa nhau dần
        }

        // Vẽ các đường hội tụ
        int numLines = 12;
        for (int i = -numLines; i <= numLines; i++) {
            if (i == 0) continue;
            double bottomX = vanishingPointX + i * (canvasWidth / (numLines * 1.2));
            gc.strokeLine(vanishingPointX, horizonY, bottomX, canvasHeight);
        }
    }

    /** Vẽ toàn bộ menu. */
    public void render(GraphicsContext gc) {
        drawBackground(gc);

        // Vẽ tiêu đề game
        gc.setFill(neonCyan);
        gc.setTextAlign(TextAlignment.CENTER);
        gc.setTextBaseline(VPos.CENTER);
        gc.setFont(titleFont);

        // LẦN 1: Vẽ ÁNH SÁNG (với hiệu ứng)
        gc.setEffect(neonGlow);
        gc.setFill(neonCyan); // Màu của quầng sáng
        gc.fillText("ARKANOID", canvasWidth / 2, canvasHeight / 4.5);

        // LẦN 2: Vẽ LÕI CHỮ (không hiệu ứng, màu trắng)
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

    public String getClickedItem(double mouseX, double mouseY) {
        for (MenuItem item : menuItems) {
            if (item.getBounds().contains(mouseX, mouseY)) {
                return item.getText();
            }
        }
        return null;
    }
}
