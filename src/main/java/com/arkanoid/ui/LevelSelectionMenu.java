package com.arkanoid.ui;

import com.arkanoid.game.GameManager;
import javafx.scene.canvas.GraphicsContext;
import java.util.ArrayList;
import java.util.List;

public class LevelSelectionMenu {
    private List<MenuItem> levelButtons = new ArrayList<>();
    private MenuItem backButton;
    private int canvasHeight;
    private double canvasWidth;
    private MainMenu mainMenu;

    public LevelSelectionMenu(double canvasWidth, int canvasHeight, MainMenu mainMenu) {
        this.canvasWidth = canvasWidth;
        this.canvasHeight = canvasHeight;
        this.mainMenu = mainMenu;
        backButton = new MenuItem("Back", (canvasWidth - 300) / 2, canvasHeight - 100, 300, 60);
    }

    public void updateLevelButtons() {
        levelButtons.clear();
        int maxLevel = GameManager.getInstance().getSavedLevel();
        int totalLevels = GameManager.MAX_LEVELS;

        double totalWidth = (100 * totalLevels) + (20 * (totalLevels - 1));
        double startX = (canvasWidth - totalWidth) / 2;
        double startY = canvasHeight / 2 - 50;

        for (int i = 1; i <= totalLevels; i++) {
            MenuItem button = new MenuItem("" + i, startX + (i - 1) * 120, startY, 100, 100);
            if (i > maxLevel) {
                button.setLocked(true); // (Ví dụ)
            }
            levelButtons.add(button);
        }
    }

    public void render(GraphicsContext gc) {
        mainMenu.drawBackground(gc); // Vẽ nền
        UIUtils.drawStyledTitle(gc, canvasWidth, canvasHeight); // Vẽ tiêu đề

        for (MenuItem button : levelButtons) {
            button.render(gc); // Vẽ các nút level
        }
        backButton.render(gc); // Vẽ nút back
    }

    public void update(double mouseX, double mouseY) {
        for (MenuItem button : levelButtons) {
            button.setHovered(button.getBounds().contains(mouseX, mouseY));
        }
        backButton.setHovered(backButton.getBounds().contains(mouseX, mouseY));
    }

    // Trả về số level (1-5) hoặc 0 nếu không click
    public int getClickedLevel(double mouseX, double mouseY) {
        for (int i = 0; i < levelButtons.size(); i++) {
            MenuItem button = levelButtons.get(i);
            if (button.getBounds().contains(mouseX, mouseY) && !button.isLocked()) {
                    return i + 1; // Trả về level 1, 2, 3...
            }
        }
        return 0; // Không click
    }

    // Dùng cho nút "Back"
    public String getClickedItem(double mouseX, double mouseY) {
        if (backButton.getBounds().contains(mouseX, mouseY)) {
            return backButton.getText(); // "Back"
        }
        return null;
    }
}

