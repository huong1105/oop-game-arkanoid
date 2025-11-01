package com.arkanoid.game;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedConstruction;
import org.mockito.MockedStatic;

import java.lang.reflect.Field;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class SoundManagerTest {

    private Map<String, Media> sfxMap;
    @BeforeEach
    void setUp() throws Exception {
        Field sfxField = SoundManager.class.getDeclaredField("sfx");
        sfxField.setAccessible(true);
        sfxMap = (Map<String, Media>) sfxField.get(null);
        sfxMap.clear();
    }

    @Test
    void testPlaySoundSuccessfully() {
        String soundFile = "brick_break.wav";
        double testVolume = 0.77;
        GameSettings mockSettings = mock(GameSettings.class);
        Media mockMedia = mock(Media.class);
        sfxMap.put(soundFile, mockMedia);
        try (MockedStatic<GameSettings> mockedSettings = mockStatic(GameSettings.class)) {
            mockedSettings.when(GameSettings::getInstance).thenReturn(mockSettings);
            when(mockSettings.getSfxVolume()).thenReturn(testVolume);
            try (MockedConstruction<MediaPlayer> playerConstruction =
                         mockConstruction(MediaPlayer.class, (mockPlayer, context) -> {
                             assertEquals(mockMedia, context.arguments().get(0));
                         })) {
                SoundManager.playSound(soundFile);
                assertEquals(1, playerConstruction.constructed().size());
                MediaPlayer mockPlayer = playerConstruction.constructed().get(0);
                verify(mockPlayer).setVolume(testVolume);
                verify(mockPlayer).setOnEndOfMedia(any());
                verify(mockPlayer).play();
            }
        }
    }

    @Test
    void testPlaySoundNotPreloaded() {
        sfxMap.clear();
        assertDoesNotThrow(() -> SoundManager.playSound("file_khong_ton_tai.wav"));
    }
}