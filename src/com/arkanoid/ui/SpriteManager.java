package com.arkanoid.ui;

import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.WritableImage;

/**
 * Quản lý việc tải và cắt các sprite từ một spritesheet duy nhất.
 */
public class SpriteManager {
    public static Image BACKGROUND_GAME;

    private static Image spritesheet;
    private static Image entitySpritesheet;

    public static Image BRICK_NORMAL;
    public static Image BRICK_HARD;
    public static Image BRICK_SPECIAL;
    public static Image BRICK_EXPLOSIVE;
    public static Image BRICK_WALL;

    public static Image BORDER_12x12;

    public static Image BRICK_HARD_CRACKED;
    public static Image BRICK_SPECIAL_LIGHT;
    public static Image BRICK_SPECIAL_HEAVY;

    public static Image BALL_FIRE;
    public static Image BALL_NORMAL;
    public static Image PADDLE_NORMAL;
    public static Image PADDLE_EXPANDED;

    public static void preload() {
        try {
            spritesheet = new Image(SpriteManager.class.getResourceAsStream("/Images/blocks_bricks.png"));
        } catch (Exception e) {
            System.err.println("Lỗi: Không thể tải spritesheet 'blocks_bricks.png' từ thư mục 'res/images/'");
            e.printStackTrace();
        }

        try {
            entitySpritesheet = new Image(SpriteManager.class.getResourceAsStream("/Images/asset.png"));
        } catch (Exception e) {
            System.err.println("Lỗi: Không thể tải file 'asset.png': " + e.getMessage());
            e.printStackTrace();
        }

        try {
            String bgPath = "/Images/Background1.png";
            BACKGROUND_GAME = new Image(SpriteManager.class.getResourceAsStream(bgPath));
        } catch (Exception e) {
            System.err.println("Lỗi: Không thể tải file ảnh nền: " + e.getMessage());
            BACKGROUND_GAME = null;
        }

        BRICK_NORMAL = getSprite(spritesheet, 1, 1, 24, 10);
        BRICK_HARD = getSprite(spritesheet, 27, 1, 24, 10);
        BRICK_SPECIAL = getSprite(spritesheet, 53, 1, 24, 10);
        BRICK_EXPLOSIVE = getSprite(spritesheet, 79, 1, 24, 10);
        BRICK_WALL = getSprite(spritesheet, 105, 1, 24, 10);

        BRICK_HARD_CRACKED = getSprite(spritesheet, 27, 13, 24, 10);
        BRICK_SPECIAL_LIGHT = getSprite(spritesheet, 53, 13, 24, 10);
        BRICK_SPECIAL_HEAVY = getSprite(spritesheet, 53, 25, 24, 10);

        BALL_NORMAL = getSprite(entitySpritesheet, 10, 4, 42, 42);
        BALL_FIRE = getSprite(entitySpritesheet, 10, 4, 42, 42);

        PADDLE_NORMAL = getSprite(entitySpritesheet, 14, 65, 256, 64);
        PADDLE_EXPANDED = getSprite(entitySpritesheet, 22, 188, 352, 64);

        BORDER_12x12 = getSprite(spritesheet, 167, 1, 12, 12);
    }

    /**
     * Cắt và trả về một sprite cụ thể từ spritesheet.
     */
    public static Image getSprite(Image sheet, int x, int y, int width, int height) {
        if (sheet == null) {
            System.err.println("Spritesheet là null! Không thể cắt sprite.");
            return null;
        }
        PixelReader reader = sheet.getPixelReader();
        return new WritableImage(reader, x, y, width, height);
    }
}
