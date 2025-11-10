package com.arkanoid.game;

public class GameSettings {
    private static GameSettings instance;

    private double sfxVolume;      // Âm lượng hiệu ứng (0.0 -> 1.0)
    private double bgmVolume;      // Âm lượng nhạc nền (0.0 -> 1.0)

    private GameSettings() {
        // Giá trị mặc định
        this.sfxVolume = 0.6;
        this.bgmVolume = 0.5;

    }

    public static synchronized GameSettings getInstance() {
        if (instance == null) {
            instance = new GameSettings();
        }
        return instance;
    }

    // Getters and Setters
    public double getSfxVolume() {
        return sfxVolume;
    }

    public void setSfxVolume(double sfxVolume) {
        this.sfxVolume = Math.max(0.0, Math.min(1.0, sfxVolume));
    }

    public double getBgmVolume() {
        return bgmVolume;
    }

    public void setBgmVolume(double bgmVolume) {
        this.bgmVolume = Math.max(0.0, Math.min(1.0, bgmVolume));
        SoundManager.updateMusicVolume();
    }
}
