package com.arkanoid.core;

public abstract class GameObject {
    // Thuộc tính được bảo vệ để lớp con có thể truy cập
    protected int x, y;
    protected int width, height;

    // Constructor (Hàm khởi tạo)
    public GameObject(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    // Các phương thức trừu tượng buộc lớp con phải triển khai
    public abstract void update();
    public abstract void render();

    // Getters để các lớp khác có thể đọc thông tin (Tính đóng gói)
    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
}
