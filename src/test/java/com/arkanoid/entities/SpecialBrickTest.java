package com.arkanoid.entities;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class SpecialBrickTest {
    private SpecialBrick brick;
    @BeforeEach
    void setUp() {
        brick = new SpecialBrick(0, 0);
    }

    @Test
    void testSpecialBrickSurvivesOneHit() {
        assertTrue(brick.isActive(), "Gạch phải active lúc mới tạo");
        boolean wasDestroyed = brick.takeHit();
        assertTrue(brick.isActive(), "Gạch (3 máu) phải CÒN active sau 1 lần đập");
        assertFalse(wasDestroyed, "takeHit() nên trả về false khi gạch chưa bị phá hủy");
    }

    @Test
    void testSpecialBrickSurvivesAfterTwoHits() {
        assertTrue(brick.isActive(), "Gạch phải active lúc mới tạo");
        brick.takeHit();
        boolean wasDestroyedOnSecondHit = brick.takeHit();
        assertTrue(brick.isActive(), "Gạch (3 máu) phải ACTIVE sau 2 lần đập");
        assertFalse(wasDestroyedOnSecondHit, "takeHit() nên trả về true ở lần đập cuối cùng");
    }

    @Test
    void testSpecialBrickIsDestroyedAfterThreeHits() {
        assertTrue(brick.isActive(), "Gạch phải active lúc mới tạo");
        brick.takeHit();
        brick.takeHit();
        boolean wasDestroyedOnSecondHit = brick.takeHit();
        assertFalse(brick.isActive(), "Gạch (3 máu) phải INACTIVE sau 3 lần đập");
        assertTrue(wasDestroyedOnSecondHit, "takeHit() nên trả về true ở lần đập cuối cùng");
    }
}
