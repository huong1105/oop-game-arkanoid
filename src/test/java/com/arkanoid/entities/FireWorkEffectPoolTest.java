package com.arkanoid.entities;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class FireWorkEffectPoolTest {

    private FireWorkEffectPool pool;
    @BeforeEach
    void setUp() throws Exception {
        Field instanceField = FireWorkEffectPool.class.getDeclaredField("instance");
        instanceField.setAccessible(true);
        instanceField.set(null, null);

        pool = FireWorkEffectPool.getInstance();
    }

    @Test
    void testGetInstanceReturnsSameInstance() {
        FireWorkEffectPool instance1 = FireWorkEffectPool.getInstance();
        FireWorkEffectPool instance2 = FireWorkEffectPool.getInstance();

        assertSame(instance1, instance2, "getInstance() phải luôn trả về cùng một đối tượng");
    }

    @Test
    void testGetFromEmptyPoolCreatesNewEffect() {
        FireWorkEffect effect = pool.getEffect();
        assertNotNull(effect, "Lấy từ pool rỗng phải tạo hiệu ứng mới");
    }

    @Test
    void testReturnAndGetReturnsSameObject() {
        FireWorkEffect mockEffect = mock(FireWorkEffect.class);
        pool.returnEffect(mockEffect);
        FireWorkEffect retrieved = pool.getEffect();
        assertSame(mockEffect, retrieved, "Đối tượng lấy ra phải là đối tượng đã trả vào");
    }

    @Test
    void testReturnSetsEffectInactive() {
        FireWorkEffect mockEffect = mock(FireWorkEffect.class);
        pool.returnEffect(mockEffect);
        verify(mockEffect, times(1)).setActive(false);
    }

    @Test
    void testPoolCreatesNewWhenEmptied() {
        FireWorkEffect mockEffect = mock(FireWorkEffect.class);
        pool.returnEffect(mockEffect);
        FireWorkEffect retrieved = pool.getEffect();
        assertSame(mockEffect, retrieved, "Lần lấy đầu tiên phải là mockEffect");
        FireWorkEffect newEffect = pool.getEffect();
        assertNotNull(newEffect, "Phải tạo đối tượng mới khi pool rỗng");
        assertNotSame(mockEffect, newEffect, "Đối tượng mới không được là đối tượng cũ");
    }

    @Test
    void testFIFOBehavior() {
        FireWorkEffect mock1 = mock(FireWorkEffect.class);
        FireWorkEffect mock2 = mock(FireWorkEffect.class);

        pool.returnEffect(mock1);
        pool.returnEffect(mock2);

        FireWorkEffect retrieved1 = pool.getEffect();
        FireWorkEffect retrieved2 = pool.getEffect();

        assertSame(mock1, retrieved1, "Phải lấy ra mock1 trước (FIFO)");
        assertSame(mock2, retrieved2, "Phải lấy ra mock2 sau (FIFO)");
    }
}