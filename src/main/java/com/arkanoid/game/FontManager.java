package com.arkanoid.game;

import javafx.scene.text.Font;

/**
 * Tải trước tất cả các font chữ của game một lần duy nhất.
 */
public class FontManager {
    public static Font PAPYRUS_24; // Dùng cho tiêu đề HUD "ARKANOID"
    public static Font PAPYRUS_20; // Dùng cho giá trị (điểm, màn)
    public static Font PAPYRUS_16; // Dùng cho nhãn (SCORE, LEVEL)

    public static Font PAPYRUS_90; // Dùng cho Tiêu đề Menu
    public static Font PAPYRUS_32; // Dùng cho các nút MenuItem

    public static Font loadFont(String path, double size, String fallback) {
        try {
            java.net.URL fontUrl = FontManager.class.getResource(path);

            if (fontUrl == null) {
                throw new Exception("Resource not found: " + path);
            }

            String urlString = fontUrl.toExternalForm();
            Font font = Font.loadFont(urlString, size);

            if (font == null) {
                throw new Exception("Font.loadFont returned null (file font bị lỗi?)");
            }
            return font;

        } catch (Exception a) {
            System.err.println("Không thể tải font: " + path + ". Lỗi: " + a.getMessage() + ". Sử dụng fallback: " + fallback);
            return Font.font(fallback, size);
        }
    }

    /**
     * Tải tất cả các font chữ cần thiết
     */
    public static void preload() {
        String pixelPath = "/Fonts/PixeloidMono-d94EV.ttf";

        // Tải các kích thước Papyrus
        PAPYRUS_90 = loadFont(pixelPath, 90, "Serif");
        PAPYRUS_32 = loadFont(pixelPath, 32, "Serif");
        PAPYRUS_24 = loadFont(pixelPath, 24, "Serif");
        PAPYRUS_20 = loadFont(pixelPath, 20, "Serif");
        PAPYRUS_16 = loadFont(pixelPath, 16, "Serif");
    }
}
