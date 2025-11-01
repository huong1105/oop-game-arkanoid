package com.arkanoid.ui;

import javafx.geometry.Point2D;
import javafx.geometry.VPos;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static com.arkanoid.game.FontManager.PAPYRUS_90;

public class MainMenu {
    private final List<MenuItem> menuItems = new ArrayList<>();
    private double canvasWidth;
    private double canvasHeight;
    private Image backgroundImage;

    private final DropShadow forestGlow;
    private final Color forestGreen = Color.rgb(60, 180, 70); // Xanh lá

    private double backgroundX = 0;
    private final double SCROLL_SPEED = 0.5;
    // Nền sao
    private final List<Point2D> fireflies = new ArrayList<>();
    private final Random random = new Random();

    public MainMenu(double canvasWidth, double canvasHeight) {
        this.canvasWidth = canvasWidth;
        this.canvasHeight = canvasHeight;

        // Tạo hiệu ứng phát sáng
        forestGlow = new DropShadow(25, Color.rgb(60, 180, 70, 0.8));

        // Định nghĩa các nút menu
        double buttonWidth = 350;
        double buttonHeight = 60;
        double startX = (canvasWidth - buttonWidth) / 2;
        double startY = canvasHeight / 2.5;

        menuItems.add(new MenuItem("New Game", startX, startY, buttonWidth, buttonHeight));
        menuItems.add(new MenuItem("High Score", startX, startY + 80, buttonWidth, buttonHeight));
        menuItems.add(new MenuItem("Setting", startX, startY + 160, buttonWidth, buttonHeight));
        menuItems.add(new MenuItem("Quit Game", startX, startY + 240, buttonWidth, buttonHeight));
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

