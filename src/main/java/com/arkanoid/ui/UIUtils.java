package com.arkanoid.ui;

import javafx.geometry.VPos;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.effect.DropShadow;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

import static com.arkanoid.game.FontManager.PAPYRUS_90;

/**
 * Lớp tiện ích chứa các phương thức vẽ UI dùng chung.
 */
public class UIUtils {

    private static DropShadow forestGlow = null;
    private static DropShadow menuItemHoverGlow = null;

    public static Color getForestGreen() {
        return forestGreen;
    }

    private static Color forestGreen = Color.rgb(60, 180, 70);

    private UIUtils() {}


    public static DropShadow getTitleGlow() {
        if (forestGlow == null) {
            forestGlow = new DropShadow();
            forestGlow.setRadius(25);
            forestGlow.setColor(Color.rgb(60, 180, 70, 0.8));
        }
        return forestGlow;
    }

    public static DropShadow getForestGlow() {
        return forestGlow;
    }

    public static DropShadow getMenuItemHoverGlow() {
        if (menuItemHoverGlow == null) {
            menuItemHoverGlow = new DropShadow();
            menuItemHoverGlow.setRadius(20);
            menuItemHoverGlow.setColor(Color.rgb(60, 180, 70, 0.7));
        }
        return menuItemHoverGlow;
    }

    /**
     * Vẽ tiêu đề game "ARKANOID" với style thống nhất.
     * @param gc GraphicsContext để vẽ lên.
     * @param canvasWidth Chiều rộng của canvas.
     * @param canvasHeight Chiều cao của canvas.
     */
    public static void drawStyledTitle(GraphicsContext gc, double canvasWidth, double canvasHeight) {
        String titleText = "ARKANOID";

        // --- Vẽ Tiêu đề ---
        gc.setFill(forestGreen);
        gc.setTextAlign(TextAlignment.CENTER);
        gc.setTextBaseline(VPos.CENTER);
        gc.setFont(PAPYRUS_90);

        double titleY = canvasHeight / 4.5;

        // LẦN 1: Vẽ ÁNH SÁNG
        gc.setEffect(forestGlow);
        gc.setFill(forestGreen);
        gc.fillText(titleText, canvasWidth / 2, titleY);

        // LẦN 2: Vẽ LÕI CHỮ
        gc.setEffect(null);
        gc.setFill(Color.WHITE);
        gc.fillText(titleText, canvasWidth / 2, titleY);

        // --- Vẽ các đường gạch & biểu tượng ---
        Text tempText = new Text(titleText);
        tempText.setFont(PAPYRUS_90);
        double textWidth = tempText.getLayoutBounds().getWidth();
        double centerX = canvasWidth / 2;
        double mainLineY = titleY + 60;
        double thinLineY = titleY + 68;
        double lineStartX = centerX - (textWidth / 2);
        double lineEndX = centerX + (textWidth / 2);
        double glyphsStartX = lineEndX + 15;
        double glyphsWidth = 30;

        gc.setStroke(forestGreen);
        gc.setFill(forestGreen);
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