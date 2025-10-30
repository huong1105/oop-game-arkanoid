package com.arkanoid.game;

import com.arkanoid.ui.MainMenu;
import com.arkanoid.ui.MenuItem;
import com.arkanoid.ui.UIUtils;
import javafx.geometry.Rectangle2D;
import javafx.geometry.VPos;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.effect.DropShadow;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

import java.util.ArrayList;
import java.util.List;

import static com.arkanoid.game.FontManager.PAPYRUS_90;
import static com.arkanoid.game.FontManager.PAPYRUS_32;

public class SettingsMenu {
    private Rectangle2D sfxSliderBounds, bgmSliderBounds;
    private double canvasWidth, canvasHeight;
    private MainMenu mainMenu;

    private final List<MenuItem> menuItems = new ArrayList<>();
    private final DropShadow forestGlow;
    private final Color forestGreen = Color.rgb(60, 180, 70);

    public SettingsMenu(double canvasWidth, double canvasHeight, MainMenu mainMenu) {
        this.canvasWidth = canvasWidth;
        this.canvasHeight = canvasHeight;
        this.mainMenu = mainMenu;

        // --- Nạp font và hiệu ứng (từ MainMenu.java) ---

        forestGlow = new DropShadow(25, Color.rgb(0, 255, 255, 0.8));

        double contentStartY = canvasHeight / 2.5;
        double sliderWidth = 400;
        double sliderHeight = 20;
        double centerX = canvasWidth / 2;

        sfxSliderBounds = new Rectangle2D(centerX - sliderWidth / 2, contentStartY, sliderWidth, sliderHeight);
        bgmSliderBounds = new Rectangle2D(centerX - sliderWidth / 2, contentStartY + 100, sliderWidth, sliderHeight);

        double buttonWidth = 350;
        double buttonHeight = 60;
        double startX = (canvasWidth - buttonWidth) / 2;
        menuItems.add(new MenuItem("Back", startX, 480, buttonWidth, buttonHeight));
    }

    public void update(double mouseX, double mouseY) {
        // Cập nhật hover cho nút "Back"
        for (MenuItem item : menuItems) {
            item.setHovered(item.getBounds().contains(mouseX, mouseY));
        }
    }

    public void render(GraphicsContext gc) {
        if (this.mainMenu != null) {
            this.mainMenu.drawBackground(gc);
        }
        UIUtils.drawStyledTitle(gc, canvasWidth, canvasHeight);
        GameSettings settings = GameSettings.getInstance();
        drawSlider(gc, "Sound Effects", sfxSliderBounds, settings.getSfxVolume());
        drawSlider(gc, "Background Music", bgmSliderBounds, settings.getBgmVolume());

        // 4. Vẽ nút "Back"
        for (MenuItem item : menuItems) {
            item.render(gc);
        }
    }

    private void drawSlider(GraphicsContext gc, String label, Rectangle2D bounds, double value) {
        gc.setFill(Color.WHITE);
        gc.setFont(PAPYRUS_32);
        gc.setTextAlign(TextAlignment.CENTER);
        gc.fillText(label, canvasWidth / 2, bounds.getMinY() - 30); // Tăng khoảng cách label

        gc.setFill(Color.GRAY);
        gc.fillRoundRect(bounds.getMinX(), bounds.getMinY(), bounds.getWidth(), bounds.getHeight(), 10, 10);

        gc.setFill(Color.GREEN);
        gc.fillRoundRect(bounds.getMinX(), bounds.getMinY(), bounds.getWidth() * value, bounds.getHeight(), 10, 10);

        gc.setFill(Color.WHITE);
        double handleX = bounds.getMinX() + bounds.getWidth() * value;
        gc.fillOval(handleX - 15, bounds.getMinY() - 5, 30, 30);
    }

    /**
     * Xử lý khi chuột được kéo (giữ nguyên).
     */
    public void handleMouseDrag(double mouseX, double mouseY) {
        updateSliderValue(mouseX, mouseY);
    }

    /**
     * Xử lý khi click chuột.
     */
    public String handleClick(double mouseX, double mouseY) {
        updateSliderValue(mouseX, mouseY); // Xử lý click thanh trượt

        for (MenuItem item : menuItems) {
            if (item.getBounds().contains(mouseX, mouseY) && "Back".equals(item.getText())) {
                return "BACK_TO_MENU";
            }
        }
        return null;
    }

    private void updateSliderValue(double mouseX, double mouseY) {
        if (sfxSliderBounds.contains(mouseX, mouseY)) {
            double newValue = (mouseX - sfxSliderBounds.getMinX()) / sfxSliderBounds.getWidth();
            GameSettings.getInstance().setSfxVolume(newValue);
        }
        if (bgmSliderBounds.contains(mouseX, mouseY)) {
            double newValue = (mouseX - bgmSliderBounds.getMinX()) / bgmSliderBounds.getWidth();
            GameSettings.getInstance().setBgmVolume(newValue);
        }
    }
}
