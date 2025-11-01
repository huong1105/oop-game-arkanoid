package com.arkanoid.entities;

import org.junit.jupiter.api.BeforeEach; // Import thêm BeforeEach
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class StrongBrickTest {

    private StrongBrick brick;
    @BeforeEach
    void setUp() {
        brick = new StrongBrick(0, 0);
    }

    @Test
    void testStrongBrickSurvivesOneHit() {
        assertTrue(brick.isActive(), "Gạch phải active lúc mới tạo");
        boolean wasDestroyed = brick.takeHit();
        assertTrue(brick.isActive(), "Gạch (2 máu) phải CÒN active sau 1 lần đập");
        assertFalse(wasDestroyed, "takeHit() nên trả về false khi gạch chưa bị phá hủy");
    }

    @Test
    void testStrongBrickIsDestroyedAfterTwoHits() {
        assertTrue(brick.isActive(), "Gạch phải active lúc mới tạo");
        brick.takeHit();
        boolean wasDestroyedOnSecondHit = brick.takeHit();
        assertFalse(brick.isActive(), "Gạch (2 máu) phải INACTIVE sau 2 lần đập");
        assertTrue(wasDestroyedOnSecondHit, "takeHit() nên trả về true ở lần đập cuối cùng");
    }
}