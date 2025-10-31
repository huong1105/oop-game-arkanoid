package com.arkanoid.entities;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Quản lý các "hồ chứa" cho TẤT CẢ các loại PowerUp.
 */
public class PowerUpPool {
    private static PowerUpPool instance;

    // Sử dụng Map để chứa nhiều hồ, mỗi hồ cho một loại PowerUp
    // Key: Là Class
    // Value: Là danh sách các PowerUp không hoạt động
    private final Map<Class<? extends PowerUp>, List<PowerUp>> inactivePools;

    private PowerUpPool() {
        inactivePools = new HashMap<>();
    }

    public static synchronized PowerUpPool getInstance() {
        if (instance == null) {
            instance = new PowerUpPool();
        }
        return instance;
    }

    /**
     * Lấy một PowerUp từ hồ.
     * Trả về null nếu hồ của loại đó rỗng.
     */
    public PowerUp getPowerUp(Class<? extends PowerUp> type) {
        List<PowerUp> pool = inactivePools.get(type);

        if (pool != null && !pool.isEmpty()) {
            return pool.removeFirst();
        } else {
            return null;
        }
    }

    /**
     * Trả một PowerUp về hồ.
     */
    public void returnPowerUp(PowerUp powerUp) {
        powerUp.setActive(false);

        Class<? extends PowerUp> type = powerUp.getClass();

        List<PowerUp> pool = inactivePools.computeIfAbsent(type, k -> new ArrayList<>());

        pool.add(powerUp);
    }
}