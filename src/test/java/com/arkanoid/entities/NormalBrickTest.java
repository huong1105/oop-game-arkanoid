package com.arkanoid.entities;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class NormalBrickTest {

    @Test
    void testNormalBrickIsDestroyedAfterOneHit() {
        NormalBrick brick = new NormalBrick(0, 0);
        assertTrue(brick.isActive(), "Viên gạch phải active lúc mới tạo");
        boolean wasDestroyed = brick.takeHit();
        assertFalse(brick.isActive(), "Gạch phải bị inactive sau khi takeHit()");
        assertTrue(wasDestroyed, "takeHit() nên trả về true khi phá hủy gạch");
    }
}