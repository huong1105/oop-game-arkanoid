package com.arkanoid.entities;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ExplosiveBrickTest {
    @Test
    void testExplosiveBrickIsDestroyedAfterOneHit() {
        ExplosiveBrick brick = new ExplosiveBrick(0, 0);
        assertTrue(brick.isActive(), "Viên gạch phải active lúc mới tạo");
        boolean wasDestroyed = brick.takeHit();
        assertFalse(brick.isActive(), "Gạch phải bị inactive sau khi takeHit()");
        assertTrue(wasDestroyed, "takeHit() nên trả về true khi phá hủy gạch");
    }
}
