package com.arkanoid.game;

import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import java.io.File;

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
        String papyrusPath = "/Fonts/Papyrus.ttf";

        // Tải các kích thước Papyrus
        PAPYRUS_90 = loadFont(papyrusPath, 90, "Serif");
        PAPYRUS_32 = loadFont(papyrusPath, 32, "Serif");
        PAPYRUS_24 = loadFont(papyrusPath, 24, "Serif");
        PAPYRUS_20 = loadFont(papyrusPath, 20, "Serif");
        PAPYRUS_16 = loadFont(papyrusPath, 16, "Serif");
    }
}