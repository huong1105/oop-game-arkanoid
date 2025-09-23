package com.arkanoid.core;

/**
 * Lớp cơ sở trừu tượng cho các đối tượng có thể di chuyển.
 * Kế thừa từ GameObject và thêm thuộc tính tốc độ.
 */
public abstract class MovableObject extends GameObject {
    protected int dx, dy; // Tốc độ di chuyển theo trục x và y

    public MovableObject(int x, int y, int width, int height, int dx, int dy) {
        // Gọi constructor của lớp cha (GameObject)
        super(x, y, width, height);
        this.dx = dx;
        this.dy = dy;
    }

    // Phương thức cụ thể để di chuyển đối tượng
    public void move() {
        x += dx;
        y += dy;
    }
}
