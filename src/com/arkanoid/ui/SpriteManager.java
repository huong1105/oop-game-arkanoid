package com.arkanoid.ui;

import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.WritableImage;

/**
 * Quản lý việc tải và cắt các sprite từ một spritesheet duy nhất.
 */
public class SpriteManager {

    private static Image spritesheet;

    public static void preload() {
        try {
            spritesheet = new Image(SpriteManager.class.getResourceAsStream("/Images/blocks_bricks.png"));
        } catch (Exception e) {
            System.err.println("Lỗi: Không thể tải spritesheet 'blocks_bricks.png' từ thư mục 'res/images/'");
            e.printStackTrace();
        }
    }

    /**
     * Cắt và trả về một sprite cụ thể từ spritesheet.
     */
    public static Image getSprite(int x, int y, int width, int height) {
        if (spritesheet == null) {
            return null;
        }
        PixelReader reader = spritesheet.getPixelReader();
        return new WritableImage(reader, x, y, width, height);
    }
}
