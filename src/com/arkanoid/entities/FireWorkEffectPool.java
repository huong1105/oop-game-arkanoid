package com.arkanoid.entities;

import java.util.ArrayList;
import java.util.List;

/**
 * Quản lý một "hồ chứa" các đối tượng FireWorkEffect để tái sử dụng.
 * Áp dụng mẫu Singleton.
 */
public class FireWorkEffectPool {
    private static FireWorkEffectPool instance;
    private final List<FireWorkEffect> inactiveEffects = new ArrayList<>();

    private FireWorkEffectPool() { }

    public static synchronized FireWorkEffectPool getInstance() {
        if (instance == null) {
            instance = new FireWorkEffectPool();
        }
        return instance;
    }

    /**
     * Lấy một hiệu ứng từ hồ, hoặc tạo mới nếu hồ rỗng.
     */
    public FireWorkEffect getEffect() {
        if (inactiveEffects.isEmpty()) {
            return new FireWorkEffect(0, 0, 0, 0);
        } else {
            return inactiveEffects.removeFirst();
        }
    }

    /**
     * Trả một hiệu ứng về hồ để tái sử dụng.
     */
    public void returnEffect(FireWorkEffect effect) {
        effect.setActive(false);
        inactiveEffects.add(effect);
    }
}