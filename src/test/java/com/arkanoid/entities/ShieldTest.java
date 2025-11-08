package com.arkanoid.entities;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ShieldTest {
    private Shield shield;
    @BeforeEach
    void setUp() {
        shield = new Shield();
    }
    @Test
    void testShieldSurvivesAfterOneHit() {
        assertTrue(shield.isActive(), "Khiên phải active lúc mới tạo");
        shield.hit();
        shield.update(0);
        assertTrue(shield.isActive(), "Khiên phải active khi còn máu");
        assertEquals(shield.getHitPoints(), 4, "Khiên (5 máu) phải còn 4 máu sau 1 lần đập");
    }

    @Test
    void testShieldSurvivesAfterTwoHits() {
        assertTrue(shield.isActive(), "Khiên phải active lúc mới tạo");
        shield.hit();
        shield.hit();
        shield.update(0);
        assertTrue(shield.isActive(), "Khiên phải active khi còn máu");
        assertEquals(shield.getHitPoints(), 3, "Khiên (5 máu) phải còn 3 máu sau 2 lần đập");
    }

    @Test
    void testShieldSurvivesAfterThreeHits() {
        assertTrue(shield.isActive(), "Khiên phải active lúc mới tạo");
        for(int i = 0; i < 3; i++) {
            shield.hit();
        }
        shield.update(0);
        assertTrue(shield.isActive(), "Khiên phải active khi còn máu");
        assertEquals(shield.getHitPoints(), 2, "Khiên (5 máu) phải còn 2 máu sau 3 lần đập");
    }

    @Test
    void testShieldSurvivesAfterFourHits() {
        assertTrue(shield.isActive(), "Khiên phải active lúc mới tạo");
        shield.getHitPoints();
        for(int i = 0; i < 4; i++) {
            shield.hit();
        }
        shield.update(0);
        assertTrue(shield.isActive(), "Khiên phải active khi còn máu");
        assertEquals(shield.getHitPoints(), 1, "Khiên (5 máu) phải còn 1 máu sau 4 lần đập");
    }

    @Test
    void testSpecialshieldIsDestroyedAfterFiveHits() {
        assertTrue(shield.isActive(), "Khiên phải active lúc mới tạo");
        for(int i = 0; i < 5; i++) {
            shield.hit();
        }
        shield.update(0);
        assertEquals(shield.getHitPoints(), 0, "Khiên (5 máu) phải còn 0 máu sau 5 lần đập");
        assertFalse(shield.isActive(), "Khiên phải không active khi hết máu");
    }
}
