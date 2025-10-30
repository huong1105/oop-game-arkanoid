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

    private static Font loadFont(String path, double size, String fallback) {
        try {
            Font font = Font.loadFont(new File(path).toURI().toString(), size);
            if (font == null) throw new Exception("Font not loaded");
            return font;
        } catch (Exception e) {
            System.err.println("Không thể tải font: " + path + ". Sử dụng fallback: " + fallback);
            return Font.font(fallback, size);
        }
    }

    /**
     * Tải tất cả các font chữ cần thiết
     */
    public static void preload() {
        String papyrusPath = "res/fonts/papyrus.ttf";

        // Tải các kích thước Papyrus
        PAPYRUS_90 = loadFont(papyrusPath, 90, "Serif");
        PAPYRUS_32 = loadFont(papyrusPath, 32, "Serif");
        PAPYRUS_24 = loadFont(papyrusPath, 24, "Serif");
        PAPYRUS_20 = loadFont(papyrusPath, 20, "Serif");
        PAPYRUS_16 = loadFont(papyrusPath, 16, "Serif");
    }
}