package com.arkanoid.game;

import com.arkanoid.ui.MainMenu;
import com.arkanoid.ui.MenuItem;
import javafx.geometry.Rectangle2D;
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

public class HighScoreMenu {

    private MainMenu mainMenu;
    private HighScoreManager highScoreManager;
    private double canvasWidth, canvasHeight;
    private final List<MenuItem> menuItems = new ArrayList<>();
    private Font titleFont;
    private Font regularFont;
    private Font scoreFont;
    private final DropShadow neonGlow;
    private final Color neonCyan = Color.rgb(0, 255, 255);

    public HighScoreMenu(double canvasWidth, double canvasHeight, MainMenu mainMenu, HighScoreManager highScoreManager) {
        this.canvasWidth = canvasWidth;
        this.canvasHeight = canvasHeight;
        this.mainMenu = mainMenu;
        this.highScoreManager = highScoreManager;

        try {
            titleFont = Font.loadFont(new File("res/fonts/Orbitron-Bold.ttf").toURI().toString(), 90);
            if (titleFont == null) throw new Exception("Font not loaded");
        } catch (Exception e) {
            titleFont = Font.font("Monospaced", 90);
        }

        try {
            regularFont = Font.loadFont(new File("res/fonts/Orbitron-Regular.ttf").toURI().toString(), 30);
            if (regularFont == null) throw new Exception("Font not loaded");
        } catch (Exception e) {
            regularFont = Font.font("Monospaced", 30);
        }

        try {
            scoreFont = Font.loadFont(new File("res/fonts/Orbitron-Regular.ttf").toURI().toString(), 35);
            if (scoreFont == null) throw new Exception("Font not loaded");
        } catch (Exception e) {
            scoreFont = Font.font("Monospaced", 35);
        }

        neonGlow = new DropShadow(25, Color.rgb(0, 255, 255, 0.8));

        double buttonWidth = 300;
        double buttonHeight = 60;
        double startX = (canvasWidth - buttonWidth) / 2;
        double buttonY = canvasHeight - buttonHeight - 80;
        menuItems.add(new MenuItem("Back", startX, buttonY, buttonWidth, buttonHeight));
    }

    public void update(double mouseX, double mouseY) {
        for (MenuItem item : menuItems) {
            item.setHovered(item.getBounds().contains(mouseX, mouseY));
        }
    }

    public void render(GraphicsContext gc) {
        if (this.mainMenu != null) {
            this.mainMenu.drawBackground(gc);
        }

        drawStyledTitle(gc, "ARKANOID");

        double subtitleY = canvasHeight / 2.5 - 40;
        double listStartY = subtitleY + 70;

        // Vẽ nội dung High Score
        gc.setFill(Color.WHITE);
        gc.setFont(regularFont); // Dùng font Orbitron-Regular 30
        gc.setTextAlign(TextAlignment.CENTER);
        gc.setTextBaseline(VPos.CENTER);
        gc.fillText("High Scores", canvasWidth / 2, subtitleY); // Đặt phía trên danh sách

        gc.setFont(scoreFont); // Dùng font Orbitron-Regular 35
        List<Integer> highScores = highScoreManager.getHighScores();

        gc.setTextAlign(TextAlignment.CENTER);

        if (highScores.isEmpty()) {
            gc.setFill(Color.WHITE);
            gc.setTextAlign(TextAlignment.CENTER);
            gc.fillText("No scores yet!", canvasWidth / 2, listStartY);
        } else {
            for (int i = 0; i < highScores.size(); i++) {
                String rank = (i + 1) + ".";
                String scoreText = String.format("%,d", highScores.get(i));

                gc.setFill(Color.WHITE);
                gc.setTextAlign(TextAlignment.RIGHT);
                gc.fillText(rank, canvasWidth / 2 - 20, listStartY + i * 60);

                gc.setTextAlign(TextAlignment.LEFT);
                gc.fillText(scoreText, canvasWidth / 2, listStartY + i * 60);
            }
        }

        // 5. Vẽ nút "Back"
        for (MenuItem item : menuItems) {
            item.render(gc);
        }
    }

    /**
     * Xử lý khi click chuột
     */
    public String handleClick(double mouseX, double mouseY) {
        for (MenuItem item : menuItems) {
            if (item.getBounds().contains(mouseX, mouseY)) {
                if ("Back".equals(item.getText())) {
                    return "BACK_TO_MENU";
                }
            }
        }
        return null;
    }

    /**
     * Phương thức trợ giúp để vẽ tiêu đề theo style của MainMenu.
     * (Code được sao chép từ MainMenu.java)
     */
    private void drawStyledTitle(GraphicsContext gc, String titleText) {
        // --- Vẽ tiêu đề ---
        gc.setFill(neonCyan);
        gc.setTextAlign(TextAlignment.CENTER);
        gc.setTextBaseline(VPos.CENTER);
        gc.setFont(titleFont);

        double titleY = canvasHeight / 4.5; // Giống Y của MainMenu

        // LẦN 1: Vẽ ÁNH SÁNG
        gc.setEffect(neonGlow);
        gc.setFill(neonCyan);
        gc.fillText(titleText, canvasWidth / 2, titleY);

        // LẦN 2: Vẽ LÕI CHỮ
        gc.setEffect(null);
        gc.setFill(Color.WHITE);
        gc.fillText(titleText, canvasWidth / 2, titleY);

        Text tempText = new Text(titleText);
        tempText.setFont(titleFont);
        double textWidth = tempText.getLayoutBounds().getWidth();
        double centerX = canvasWidth / 2;
        double mainLineY = titleY + 60;
        double thinLineY = titleY + 68;
        double lineStartX = centerX - (textWidth / 2);
        double lineEndX = centerX + (textWidth / 2);
        double glyphsStartX = lineEndX + 15;
        double glyphsWidth = 30;

        gc.setStroke(neonCyan);
        gc.setFill(neonCyan);
        gc.setLineWidth(4);
        gc.strokeLine(lineStartX, mainLineY, glyphsStartX - 5, mainLineY);
        gc.setLineWidth(1.5);
        gc.strokeLine(lineStartX - 10, thinLineY, glyphsStartX + glyphsWidth, thinLineY);
        gc.fillPolygon(
                new double[]{glyphsStartX, glyphsStartX + 8, glyphsStartX + 4},
                new double[]{mainLineY + 2, mainLineY + 2, mainLineY - 5},
                3
        );
        gc.setLineWidth(2);
        gc.strokeLine(glyphsStartX + 13, mainLineY + 2, glyphsStartX + 18, mainLineY - 7);
        gc.strokeLine(glyphsStartX + 19, mainLineY + 2, glyphsStartX + 24, mainLineY - 7);
    }
}