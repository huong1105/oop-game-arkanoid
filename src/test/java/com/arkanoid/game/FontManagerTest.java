package com.arkanoid.game;

import javafx.scene.text.Font;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class FontManagerTest {

    @Test
    void testLoadFontFallbackOnResourceNotFound() {
        String invalidPath = "/fonts/KHONG_TON_TAI.ttf";
        String fallbackFont = "Serif";
        double size = 30;
        Font mockFallback = mock(Font.class); // Font giả

        try (MockedStatic<Font> mockedFont = mockStatic(Font.class)) {
            mockedFont.when(() -> Font.font(fallbackFont, size)).thenReturn(mockFallback);
            Font result = FontManager.loadFont(invalidPath, size, fallbackFont);
            mockedFont.verify(() -> Font.loadFont(anyString(), anyDouble()), never());
            mockedFont.verify(() -> Font.font(fallbackFont, size), times(1));
            assertSame(mockFallback, result, "Phải trả về font fallback khi resource không tìm thấy");
        }
    }

    @Test
    void testLoadFontFallbackOnLoadError() {
        String validPath = "/Fonts/Papyrus.ttf";
        String fallbackFont = "Serif";
        double size = 24;

        Font mockFallback = mock(Font.class); // Font fallback giả
        try (MockedStatic<Font> mockedFont = mockStatic(Font.class)) {
            mockedFont.when(() -> Font.loadFont(anyString(), eq(size))).thenReturn(null);
            mockedFont.when(() -> Font.font(fallbackFont, size)).thenReturn(mockFallback);
            Font result = FontManager.loadFont(validPath, size, fallbackFont);
            mockedFont.verify(() -> Font.font(fallbackFont, size), times(1));
            assertSame(mockFallback, result, "Phải trả về font fallback khi Font.loadFont trả về null");
        }
    }
}