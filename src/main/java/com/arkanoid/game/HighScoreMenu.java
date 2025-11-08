package com.arkanoid.game;

import com.arkanoid.ui.MainMenu;
import com.arkanoid.ui.MenuItem;
import com.arkanoid.ui.UIUtils;
import javafx.geometry.VPos;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.TextAlignment;

import java.util.ArrayList;
import java.util.List;

import static com.arkanoid.game.FontManager.PAPYRUS_32;

public class HighScoreMenu {

    private MainMenu mainMenu;
    private HighScoreManager highScoreManager;
    private double canvasWidth, canvasHeight;
    private final List<MenuItem> menuItems = new ArrayList<>();

    public HighScoreMenu(double canvasWidth, double canvasHeight, MainMenu mainMenu, HighScoreManager highScoreManager) {
        this.canvasWidth = canvasWidth;
        this.canvasHeight = canvasHeight;
        this.mainMenu = mainMenu;
        this.highScoreManager = highScoreManager;

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

        UIUtils.drawStyledTitle(gc, canvasWidth, canvasHeight);

        double subtitleY = canvasHeight / 2.5 - 40;
        double listStartY = subtitleY + 70;

        // Vẽ nội dung High Score
        gc.setFill(Color.WHITE);
        gc.setFont(PAPYRUS_32);
        gc.setTextAlign(TextAlignment.CENTER);
        gc.setTextBaseline(VPos.CENTER);
        gc.fillText("High Scores", canvasWidth / 2, subtitleY); // Đặt phía trên danh sách

        gc.setFont(PAPYRUS_32);
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
}