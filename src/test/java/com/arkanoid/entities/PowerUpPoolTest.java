package com.arkanoid.entities;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.lang.reflect.Field;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PowerUpPoolTest {

    private PowerUpPool pool;

    @BeforeEach
    void setUp() throws Exception {
        Field instanceField = PowerUpPool.class.getDeclaredField("instance");
        instanceField.setAccessible(true);
        instanceField.set(null, null);

        pool = PowerUpPool.getInstance();
        Field mapField = PowerUpPool.class.getDeclaredField("inactivePools");
        mapField.setAccessible(true);
        ((Map<?, ?>) mapField.get(pool)).clear();
    }

    @Test
    void testGetInstanceReturnsSameInstance() {
        PowerUpPool instance1 = PowerUpPool.getInstance();
        PowerUpPool instance2 = PowerUpPool.getInstance();
        assertSame(instance1, instance2, "getInstance() phải luôn trả về cùng một đối tượng");
    }

    @Test
    void testGetFromEmptyPoolReturnsNull() {
        PowerUp retrieved = pool.getPowerUp(FastBallPowerUp.class);
        assertNull(retrieved, "Lấy từ pool rỗng phải trả về null");
    }

    @Test
    void testReturnAndGetReturnsSameObject() {
        FastBallPowerUp mockFastBall = mock(FastBallPowerUp.class);
        pool.returnPowerUp(mockFastBall);
        PowerUp retrieved = pool.getPowerUp(FastBallPowerUp.class);
        assertSame(mockFastBall, retrieved, "Đối tượng lấy ra phải là đối tượng đã trả vào");
    }

    @Test
    void testReturnSetsPowerUpInactive() {
        FastBallPowerUp mockFastBall = mock(FastBallPowerUp.class);
        pool.returnPowerUp(mockFastBall);
        verify(mockFastBall, times(1)).setActive(false);
    }

    @Test
    void testPoolsAreSeparateForDifferentTypes() {
        FastBallPowerUp mockFastBall = mock(FastBallPowerUp.class);
        ShieldPowerUp mockShield = mock(ShieldPowerUp.class);
        pool.returnPowerUp(mockFastBall);
        pool.returnPowerUp(mockShield);
        PowerUp retrievedFastBall = pool.getPowerUp(FastBallPowerUp.class);
        PowerUp retrievedShield = pool.getPowerUp(ShieldPowerUp.class);
        assertSame(mockFastBall, retrievedFastBall, "Pool FastBall phải trả về FastBall");
        assertSame(mockShield, retrievedShield, "Pool Shield phải trả về Shield");
        assertNull(pool.getPowerUp(FastBallPowerUp.class), "Pool FastBall phải rỗng sau khi lấy");
    }

    @Test
    void testFIFOBehavior() {
        FastBallPowerUp mock1 = mock(FastBallPowerUp.class);
        FastBallPowerUp mock2 = mock(FastBallPowerUp.class);
        pool.returnPowerUp(mock1);
        pool.returnPowerUp(mock2);
        PowerUp retrieved1 = pool.getPowerUp(FastBallPowerUp.class);
        PowerUp retrieved2 = pool.getPowerUp(FastBallPowerUp.class);
        assertSame(mock1, retrieved1, "Phải lấy ra mock1 trước (FIFO)");
        assertSame(mock2, retrieved2, "Phải lấy ra mock2 sau (FIFO)");
    }
}