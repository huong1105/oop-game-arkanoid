package com.arkanoid.ui;

import com.arkanoid.game.GameManager;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

import java.util.ArrayList;
import java.util.List;

public class MainMenu {
    private final List<MenuItem> menuItems = new ArrayList<>();
    private double canvasWidth;
    private double canvasHeight;
    private Image backgroundImage;
    private double backgroundX = 0;
    private final double SCROLL_SPEED = 0.5;
    private double buttonWidth;
    private double buttonHeight;
    private double startX;
    private double startY;
    private final double spacingY = 80;

    public MainMenu(double canvasWidth, double canvasHeight) {
        this.canvasWidth = canvasWidth;
        this.canvasHeight = canvasHeight;

        this.buttonWidth = 350;
        this.buttonHeight = 60;
        this.startX = (canvasWidth - buttonWidth) / 2;
        this.startY = canvasHeight / 2.5;
}
    public void updateMenuState() {
        menuItems.clear();
        int maxLevel = GameManager.getInstance().getSavedLevel();

        double currentY = this.startY;
        menuItems.add(new MenuItem("New Game", startX, currentY, buttonWidth, buttonHeight));
        currentY += spacingY;
        menuItems.add(new MenuItem("Continue", startX, currentY, buttonWidth, buttonHeight));
        currentY += spacingY;
        menuItems.add(new MenuItem("High Score", startX, currentY, buttonWidth, buttonHeight));
        currentY += spacingY;
        menuItems.add(new MenuItem("Setting", startX, currentY, buttonWidth, buttonHeight));
        currentY += spacingY;
        menuItems.add(new MenuItem("Quit Game", startX, currentY, buttonWidth, buttonHeight));
    }

    public void updateBackGround() {
        backgroundX -= SCROLL_SPEED;
        if (backgroundX <= -canvasWidth) {
            backgroundX = 0;
        }
    }

    public void setBackgroundImage(Image image) {
        this.backgroundImage = image;
    }

    /** Cập nhật trạng thái hover của các nút dựa trên vị trí chuột. */
    public void update(double mouseX, double mouseY) {
        for (MenuItem item : menuItems) {
            item.setHovered(item.getBounds().contains(mouseX, mouseY));
        }
    }

    public void drawBackground(GraphicsContext gc) {
        if (backgroundImage != null) {
            gc.drawImage(backgroundImage, backgroundX, 0, canvasWidth, canvasHeight);
            gc.drawImage(backgroundImage, backgroundX + canvasWidth, 0, canvasWidth, canvasHeight);

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

    public void render(GraphicsContext gc) {

        drawBackground(gc);

        UIUtils.drawStyledTitle(gc, canvasWidth, canvasHeight);

        // Vẽ các nút
        for (MenuItem item : menuItems) {
            item.render(gc);
        }
    }
}

