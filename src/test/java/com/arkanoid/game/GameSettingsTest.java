package com.arkanoid.game;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;

import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class GameSettingsTest {

    private GameSettings settings;

    @BeforeEach
    void setUp() throws Exception {
        Field instanceField = GameSettings.class.getDeclaredField("instance");
        instanceField.setAccessible(true);
        instanceField.set(null, null);

        settings = GameSettings.getInstance();
    }

    @Test
    void testSingletonInstance() {
        GameSettings instance1 = GameSettings.getInstance();
        GameSettings instance2 = GameSettings.getInstance();
        assertSame(instance1, instance2, "getInstance() phải luôn trả về cùng một instance");
    }

    @Test
    void testDefaultValues() {
        assertEquals(0.8, settings.getSfxVolume(), "Âm lượng SFX mặc định phải là 0.8");
        assertEquals(0.5, settings.getBgmVolume(), "Âm lượng BGM mặc định phải là 0.5");
    }

    @Test
    void testSetSfxVolumeClamping() {
        settings.setSfxVolume(1.5);
        assertEquals(1.0, settings.getSfxVolume(), "Giá trị > 1.0 phải bị kẹp về 1.0");

        settings.setSfxVolume(-0.5);
        assertEquals(0.0, settings.getSfxVolume(), "Giá trị < 0.0 phải bị kẹp về 0.0");

        settings.setSfxVolume(0.7);
        assertEquals(0.7, settings.getSfxVolume(), "Giá trị hợp lệ phải được set");
    }

    @Test
    void testSetBgmVolumeCallsSoundManagerUpdate() {
        try (MockedStatic<SoundManager> mockedSoundManager = mockStatic(SoundManager.class)) {
            settings.setBgmVolume(0.4);
            mockedSoundManager.verify(SoundManager::updateMusicVolume, times(1));
            assertEquals(0.4, settings.getBgmVolume());
        }
    }
}