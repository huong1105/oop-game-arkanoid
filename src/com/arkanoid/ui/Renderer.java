package com.arkanoid.ui;

import com.arkanoid.core.GameObject;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.util.List;

public class Renderer {
    private GraphicsContext gc;
    private double canvasWidth;
    private double canvasHeight;

    public Renderer(GraphicsContext gc, double canvasWidth, double canvasHeight) {
        this.gc = gc;
        this.canvasWidth = canvasWidth;
        this.canvasHeight =  canvasHeight;
    }

    public void clear() {
        gc.setFill(Color.BLACK);
        gc.fillRect(0, 0, canvasWidth, canvasHeight);
    }

    /**
     * Vẽ một đối tượng GameObject lên canvas.
     * Giả sử mỗi GameObject đều có sprite (hình ảnh).
     * @param obj Đối tượng cần vẽ.
     */
    public void draw(GameObject obj) {
        if (obj.getSprite() != null) {
            gc.drawImage(obj.getSprite(), obj.getX(), obj.getY(), obj.getWidth(), obj.getHeight());
        } else {
            gc.setFill(Color.WHITE);
            gc.fillRect(obj.getX(), obj.getY(), obj.getWidth(), obj.getHeight());
        }
    }

    /**
     * Vẽ tất cả các đối tượng trong một danh sách.
     * @param objects Danh sách các đối tượng cần vẽ.
     */
    public void render(List<GameObject> objects) {
        clear();
        for (GameObject obj : objects) {
            if (obj.isActive()) {
                draw(obj);
            }
        }
    }
}
