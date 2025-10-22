package com.arkanoid.entities;

import com.arkanoid.Const;
import com.arkanoid.core.GameObject;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Shield extends GameObject {
    private int hitPoints = 5;

    public Shield() {
        super(0, Const.SCREEN_HEIGHT - 5, Const.SCREEN_WIDTH, 20);
    }

    @Override
    public void render(GraphicsContext gc) {
        switch (hitPoints) {
            case 5:
                gc.setFill(Color.rgb(0, 255, 255));
                break;
            case 4:
                gc.setFill(Color.rgb(255, 255, 0));
                break;
            case 3:
                gc.setFill(Color.rgb(255, 128, 0));
                break;
            case 2:
                gc.setFill(Color.rgb(255, 0, 0));
                break;
            case 1:
                gc.setFill(Color.rgb(128, 0, 0));
                break;
            default:
                gc.setFill(Color.rgb(0, 0, 0));
        }
        gc.fillRect(x, y, width, height);
    }

    @Override
    public void update(double deltaTimeSeconds) {
        if (hitPoints <= 0) {
            active = false;
        }
    }

    public void hit() {
        hitPoints--;
    }

    public int getHitPoints() {
        return hitPoints;
    }

}
