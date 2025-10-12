package com.arkanoid;

public enum BrickType {
    NORMAL(1, 10),  // 1 hit để phá, 10 điểm
    HARD(2, 20),    // 2 hits để phá, 20 điểm
    SPECIAL(3, 50), // 1 hit, 50 điểm, drop power-up
    WALL(1000, 1000); // tường nên không cần quan tâm takeHits;

    private final int hitPoints;
    private final int scoreValue;

    BrickType(int hitPoints, int scoreValue) {
        this.hitPoints = hitPoints;
        this.scoreValue = scoreValue;
    }

    public int getHitPoints() {
        return hitPoints;
    }

    public int getScoreValue() {
        return scoreValue;
    }
}

