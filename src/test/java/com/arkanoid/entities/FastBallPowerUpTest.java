package com.arkanoid.entities;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class FastBallPowerUpTest {
    @Test
    void testFastBallPowerUpCreation() {
        FastBallPowerUp powerUp = new FastBallPowerUp(100, 200);
        assertEquals(100, powerUp.getX());
        assertEquals(200, powerUp.getY());
        assertEquals("FastBall", powerUp.getType());
        assertEquals(5, powerUp.durationSeconds);

        assertTrue(powerUp.isActive());
        assertFalse(powerUp.isActivated());
    }
}
