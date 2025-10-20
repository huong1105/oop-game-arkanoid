package com.arkanoid.ui;

import com.arkanoid.game.GameSettings;
import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;

public class SettingsMenu {
    private Rectangle2D sfxSliderBounds, bgmSliderBounds, backBtnBounds;
    private double canvasWidth, canvasHeight;
    private boolean isBackHovered = false; 

    public SettingsMenu(double canvasWidth, double canvasHeight) {
        this.canvasWidth = canvasWidth;
        this.canvasHeight = canvasHeight;

        double sliderWidth = 400;
        double sliderHeight = 20;
        double centerX = canvasWidth / 2;

        sfxSliderBounds = new Rectangle2D(centerX - sliderWidth / 2, 250, sliderWidth, sliderHeight);
        bgmSliderBounds = new Rectangle2D(centerX - sliderWidth / 2, 350, sliderWidth, sliderHeight);

        double buttonWidth = 300;
        double buttonHeight = 60;
        backBtnBounds = new Rectangle2D((canvasWidth - buttonWidth) / 2, 480, buttonWidth, buttonHeight);
    }

    public void update(double mouseX, double mouseY) {
        isBackHovered = backBtnBounds.contains(mouseX, mouseY);
    }

    public void render(GraphicsContext gc) {
        GameSettings settings = GameSettings.getInstance();

        gc.setFill(Color.WHITE);
        gc.setTextAlign(TextAlignment.CENTER);
        gc.setFont(new Font("Arial", 60));
        gc.fillText("Settings", canvasWidth / 2, 150);

        drawSlider(gc, "Sound Effects", sfxSliderBounds, settings.getSfxVolume());
        drawSlider(gc, "Background Music", bgmSliderBounds, settings.getBgmVolume());

        drawBackButton(gc);
    }

    private void drawSlider(GraphicsContext gc, String label, Rectangle2D bounds, double value) {
        gc.setFill(Color.WHITE);
        gc.setFont(new Font("Arial", 30));
        gc.setTextAlign(TextAlignment.CENTER);
        gc.fillText(label, canvasWidth / 2, bounds.getMinY() - 20);

        gc.setFill(Color.GRAY);
        gc.fillRoundRect(bounds.getMinX(), bounds.getMinY(), bounds.getWidth(), bounds.getHeight(), 10, 10);

        gc.setFill(Color.DODGERBLUE);
        gc.fillRoundRect(bounds.getMinX(), bounds.getMinY(), bounds.getWidth() * value, bounds.getHeight(), 10, 10);

        gc.setFill(Color.WHITE);
        double handleX = bounds.getMinX() + bounds.getWidth() * value;
        gc.fillOval(handleX - 15, bounds.getMinY() - 5, 30, 30);
    }

    /**
     * Vẽ nút Back.
     */
    private void drawBackButton(GraphicsContext gc) {
        // Vẽ khung
        if (isBackHovered) {
            gc.setFill(Color.web("#EEEEEE"));
            gc.setStroke(Color.WHITE);
        } else {
            gc.setFill(Color.web("#333333"));
            gc.setStroke(Color.web("#EEEEEE"));
        }
        gc.setLineWidth(2);
        gc.fillRoundRect(backBtnBounds.getMinX(), backBtnBounds.getMinY(), backBtnBounds.getWidth(), backBtnBounds.getHeight(), 20, 20);
        gc.strokeRoundRect(backBtnBounds.getMinX(), backBtnBounds.getMinY(), backBtnBounds.getWidth(), backBtnBounds.getHeight(), 20, 20);

        // Vẽ chữ
        if (isBackHovered) {
            gc.setFill(Color.BLACK);
        } else {
            gc.setFill(Color.WHITE);
        }
        gc.setFont(new Font("Arial", 32));
        gc.setTextAlign(TextAlignment.CENTER);
        gc.fillText("Back", backBtnBounds.getMinX() + backBtnBounds.getWidth() / 2, backBtnBounds.getMinY() + backBtnBounds.getHeight() / 2 + 10);
    }

    /**
     * Cập nhật giá trị của thanh trượt dựa trên tọa độ chuột.
     * Được gọi bởi cả sự kiện click và kéo chuột.
     */
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

    /** Xử lý khi chuột được kéo. */
    public void handleMouseDrag(double mouseX, double mouseY) {
        updateSliderValue(mouseX, mouseY);
    }

    /** Xử lý khi click chuột. */
    public String handleClick(double mouseX, double mouseY) {
        updateSliderValue(mouseX, mouseY);

        if (backBtnBounds.contains(mouseX, mouseY)) {
            return "BACK_TO_MENU";
        }
        return null;
    }
}
