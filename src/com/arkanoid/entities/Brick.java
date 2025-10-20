package com.arkanoid.entities;

import com.arkanoid.Const;
import com.arkanoid.core.GameObject;
import com.arkanoid.ui.SpriteManager;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import com.arkanoid.BrickType;

public class Brick extends GameObject {
    private int hitPoints;
    private int maxHitPoints;
    private BrickType type;

    public Brick(int x, int y, BrickType type) {
        super(x, y, Const.BRICK_WIDTH, Const.BRICK_HEIGHT);
        this.hitPoints = type.getHitPoints();
        this.maxHitPoints = type.getHitPoints();
        this.type = type;
        setSpriteByType(type);
    }

    /**
     * Phương thức private để gán sprite dựa trên BrickType.
     */
    private void setSpriteByType(BrickType type) {
        switch (type) {
            case NORMAL:
                this.sprite = SpriteManager.getSprite(1, 1, 24, 10);
                break;
            case HARD:
                this.sprite = SpriteManager.getSprite(27, 1, 24, 10);
                break;
            case SPECIAL:
                this.sprite = SpriteManager.getSprite(53, 1, 24, 10);
                break;
            case EXPLOSIVE:
                this.sprite = SpriteManager.getSprite(79, 1, 24, 10);
                break;
            case WALL:
                this.sprite = SpriteManager.getSprite(105, 1, 24, 10);
                break;
        }
    }

    @Override
    public void update() {
        if (hitPoints <= 0) {
            setActive(false);
        }
    }

    @Override
    public void render(GraphicsContext gc) {
        if (!isActive()) {
            return;
        }

        super.render(gc);

        if (type == BrickType.HARD && hitPoints < maxHitPoints) {
            Image crackedSprite = SpriteManager.getSprite(27, 13, 24, 10);
            if (crackedSprite != null) {
                gc.drawImage(crackedSprite, x, y, width, height);
            }
        }

        else if (type == BrickType.SPECIAL && hitPoints < maxHitPoints) {
            if (hitPoints == 2) {
                Image crackedSpriteLight = SpriteManager.getSprite(53, 13, 24, 10);
                if (crackedSpriteLight != null) {
                    gc.drawImage(crackedSpriteLight, x, y, width, height);
                }
            } else if (hitPoints == 1) {
                Image crackedSpriteHeavy = SpriteManager.getSprite(53, 25, 24, 10);
                if (crackedSpriteHeavy != null) {
                    gc.drawImage(crackedSpriteHeavy, x, y, width, height);
                }
            }
        }
    }

    public boolean takeHit() {
        if (hitPoints > 0) {
            hitPoints--;
            if (hitPoints <= 0) {
                setActive(false);
                return true; // destroyed, trả về kiểu bool để xử lý cộng điểm.
            }
        }
        return false;
    }
    public boolean isDestroyed() {
        return this.hitPoints <= 0;
    }

    public int getScoreValue() {
        return type.getScoreValue();
    }

    public int getHitPoints() {
        return hitPoints;
    }

    public BrickType getType() {
        return type;
    }
}
