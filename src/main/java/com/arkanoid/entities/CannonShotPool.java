package com.arkanoid.entities;

import java.util.ArrayList;
import java.util.List;

public class CannonShotPool {
    private static CannonShotPool instance;
    private final List<CannonShot> inactivePool = new ArrayList<>();

    private CannonShotPool() {}

    public static synchronized CannonShotPool getInstance() {
        if (instance == null) {
            instance = new CannonShotPool();
        }
        return instance;
    }

    public CannonShot getShot() {
        if (!inactivePool.isEmpty()) {
            return inactivePool.remove(0);
        }
        return null;
    }

    public void returnShot(CannonShot shot) {
        shot.setActive(false);
        inactivePool.add(shot);
    }
}