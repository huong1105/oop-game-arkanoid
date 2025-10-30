package com.arkanoid.ui;

import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.WritableImage;

/**
 * Quản lý việc tải và cắt các sprite từ một spritesheet duy nhất.
 */
public class SpriteManager {

    private static Image spritesheet;

    public static Image BRICK_NORMAL;
    public static Image BRICK_HARD;
    public static Image BRICK_SPECIAL;
    public static Image BRICK_EXPLOSIVE;
    public static Image BRICK_WALL;

    public static Image BRICK_HARD_CRACKED;
    public static Image BRICK_SPECIAL_LIGHT;
    public static Image BRICK_SPECIAL_HEAVY;

    public static Image BALL_FIRE;
    public static Image BALL_NORMAL;

    public static void preload() {
        try {
            spritesheet = new Image(SpriteManager.class.getResourceAsStream("/Images/blocks_bricks.png"));
        } catch (Exception e) {
            System.err.println("Lỗi: Không thể tải spritesheet 'blocks_bricks.png' từ thư mục 'res/images/'");
            e.printStackTrace();
        }

        BRICK_NORMAL = getSprite(1, 1, 24, 10);
        BRICK_HARD = getSprite(27, 1, 24, 10);
        BRICK_SPECIAL = getSprite(53, 1, 24, 10);
        BRICK_EXPLOSIVE = getSprite(79, 1, 24, 10);
        BRICK_WALL = getSprite(105, 1, 24, 10);

        BRICK_HARD_CRACKED = getSprite(27, 13, 24, 10);
        BRICK_SPECIAL_LIGHT = getSprite(53, 13, 24, 10);
        BRICK_SPECIAL_HEAVY = getSprite(53, 25, 24, 10);

        BALL_NORMAL = getSprite(167, 1, 12, 12);
        BALL_FIRE = getSprite(168, 1, 12, 12);
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
