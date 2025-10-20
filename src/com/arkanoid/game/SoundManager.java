package com.arkanoid.game;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import java.net.URL;

public class SoundManager {
    private static MediaPlayer musicPlayer;

    /**
     * Phát một hiệu ứng âm thanh ngắn (SFX).
     * Âm lượng được lấy từ GameSettings.
     */
    public static void playSound(String fileName) {
        try {
            URL resource = SoundManager.class.getResource("/sounds/" + fileName);
            if (resource == null) {
                System.err.println("Không tìm thấy file âm thanh: " + fileName);
                return;
            }
            Media sound = new Media(resource.toString());
            MediaPlayer sfxPlayer = new MediaPlayer(sound);
            sfxPlayer.setVolume(GameSettings.getInstance().getSfxVolume());
            sfxPlayer.play();
        } catch (Exception e) {
            System.err.println("Lỗi khi phát âm thanh: " + e.getMessage());
        }
    }

    /**
     * Bắt đầu phát nhạc nền.
     * Nhạc sẽ tự động lặp lại.
     */
    public static void playBackgroundMusic(String fileName) {
        if (musicPlayer != null) {
            musicPlayer.stop();
        }
        try {
            URL resource = SoundManager.class.getResource("/sounds/" + fileName);
            if (resource == null) {
                System.err.println("Không tìm thấy file nhạc nền: " + fileName);
                return;
            }
            Media sound = new Media(resource.toString());
            musicPlayer = new MediaPlayer(sound);
            musicPlayer.setCycleCount(MediaPlayer.INDEFINITE);
            musicPlayer.setVolume(GameSettings.getInstance().getBgmVolume());
            musicPlayer.play();
        } catch (Exception e) {
            System.err.println("Lỗi khi phát nhạc nền: " + e.getMessage());
        }
    }

    /**
     * Cập nhật âm lượng nhạc nền khi người dùng thay đổi trên thanh trượt.
     */
    public static void updateMusicVolume() {
        if (musicPlayer != null) {
            musicPlayer.setVolume(GameSettings.getInstance().getBgmVolume());
        }
    }
}
