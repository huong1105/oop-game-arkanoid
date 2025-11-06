package com.arkanoid.entities;

import com.arkanoid.Const;
import com.arkanoid.core.MovableObject;
import com.arkanoid.game.GameManager;
import javafx.scene.canvas.GraphicsContext;
import com.arkanoid.ui.SpriteManager;
import javafx.scene.paint.Color;

// Lớp Paddle kế thừa từ MovableObject
public class Paddle extends MovableObject {
    private int speed = Const.PADDLE_DEFAULT_SPEED;
    private boolean movingLeft;
    private boolean movingRight;
    private boolean isExpanded;
    private boolean canShoot = false;
    private int remainingShots = 0;

    public Paddle(int x, int y, int width, int height) {
        super(x, y, width, height, 0, 0); // Vận tốc ban đầu là 0
        this.sprite = SpriteManager.PADDLE_NORMAL;
        this.isExpanded = false;
    }

    @Override
    public void update(double deltaTimeSeconds) {
        if (movingLeft && getX() > 25) {
            setSpeedX(-speed);
        } else if (movingRight && getX() + width < Const.SCREEN_WIDTH - 25) {
            setSpeedX(speed);
        } else {
            setSpeedX(0);
        }

        super.update(deltaTimeSeconds);
    }

    @Override
    public void render(GraphicsContext gc) {
        if (isExpanded) {
            gc.drawImage(SpriteManager.PADDLE_EXPANDED, x, y, width, height);
        } else {
            gc.drawImage(SpriteManager.PADDLE_NORMAL, x, y, width, height);
        }

        if (canShoot && remainingShots > 0) {
            double cannonW = 16;
            double cannonH = 20;
            double cannonX = x + width / 2 - cannonW / 2;
            double cannonY = y - cannonH + 5; // nhô lên trên paddle

            // Thân pháo (hình chữ nhật đỏ sẫm)
            gc.setFill(Color.DARKRED);
            gc.fillRect(cannonX, cannonY, cannonW, cannonH);

            // Viền sáng
            gc.setStroke(Color.RED);
            gc.setLineWidth(1.5);
            gc.strokeRect(cannonX, cannonY, cannonW, cannonH);

            // Nòng pháo (hình chữ nhật cam, nhô lên trên)
            double barrelW = 5;
            double barrelH = 10;
            double barrelX = cannonX + cannonW / 2 - barrelW / 2;
            double barrelY = cannonY - barrelH;

            gc.setFill(Color.ORANGE);
            gc.fillRect(barrelX, barrelY, barrelW, barrelH);
            gc.setStroke(Color.YELLOW);
            gc.strokeRect(barrelX, barrelY, barrelW, barrelH);
        }
    }

    public void reset() {
        disableCannon();
        x = Const.PADDLE_DEFAULT_POS_X;
        y = Const.PADDLE_DEFAULT_POS_Y;
        width = Const.PADDLE_WIDTH;
        height = Const.PADDLE_HEIGHT;
        isExpanded = false;
        this.sprite = SpriteManager.PADDLE_NORMAL;
    }

    public void enableCannon(int shots) {
        this.canShoot = true;
        this.remainingShots = shots;
    }

    public void disableCannon() {
        this.canShoot = false;
        this.remainingShots = 0;
    }

    public boolean canShoot() {
        return canShoot && remainingShots > 0;
    }

    public void shoot(GameManager gm) {
        if (!canShoot()) return;

        com.arkanoid.game.SoundManager.playSound("bullet.wav");

        double shotX = getX() + getWidth() / 2 - (double) Const.CANNON_SHOT_WIDTH / 2;
        double shotY = getY() - Const.CANNON_SHOT_HEIGHT;

        CannonShot shot = CannonShotPool.getInstance().getShot();
        if (shot == null) {
            shot = new CannonShot((int) shotX, (int) shotY);
        } else {
            shot.reset(shotX, shotY);
            shot.setActive(true);
        }
        gm.addGameObject(shot);

        remainingShots--;
        if (remainingShots <= 0) {
            GameManager.getInstance().removeCannonEffect(); // GỌI TỪ ĐÂY
        }
    }

    public int getRemainingShots() { return remainingShots; }

    public boolean isMovingLeft() {
        return movingLeft;
    }

    public void setMovingLeft(boolean movingLeft) {
        this.movingLeft = movingLeft;
    }

    public boolean isMovingRight() {
        return movingRight;
    }

    public void setMovingRight(boolean movingRight) {
        this.movingRight = movingRight;
    }

    public boolean isExpanded() {
        return isExpanded;
    }

    public void setExpanded(boolean expanded) {
        this.isExpanded = expanded;
    }
}