package com.arkanoid.game;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class SoundManager {
    private static MediaPlayer musicPlayer;
    private static Media backgroundMusicMedia;

    // Key: Tên file, Value: File âm thanh
    private static final Map<String, Media> sfx = new HashMap<>();
    /**
     * Tải trước các file âm thanh nặng.
     */
    public static void preload() {
        try {
            URL resource = SoundManager.class.getResource("/sounds/background_music.mp3");
            if (resource == null) {
                System.err.println("Không tìm thấy file nhạc nền: background_music.mp3");
                return;
            }
            backgroundMusicMedia = new Media(resource.toString());
        } catch (Exception e) {
            System.err.println("Lỗi khi tải trước nhạc nền: " + e.getMessage());
        }

        loadSfx("bullet.wav");
        loadSfx("lost.wav");
        loadSfx("win.wav");
        loadSfx("lose.wav");
        loadSfx("hit.wav");
    }

    private static void loadSfx(String fileName) {
        try {
            URL resource = SoundManager.class.getResource("/sounds/" + fileName);
            if (resource == null) {
                System.err.println("Không tìm thấy file âm thanh SFX: " + fileName);
                return;
            }
            Media sound = new Media(resource.toString());
            sfx.put(fileName, sound); // Lưu vào cache
        } catch (Exception e) {
            System.err.println("Lỗi khi tải trước âm thanh SFX: " + fileName + " - " + e.getMessage());
        }
    }

    /**
     * Phát một hiệu ứng âm thanh ngắn (SFX).
     * Âm lượng được lấy từ GameSettings.
     */
    public static void playSound(String fileName) {
        Media sound = sfx.get(fileName);

        if (sound == null) {
            System.err.println("Lỗi: Âm thanh '" + fileName + "' chưa được tải trước (chưa có trong preload())");
            return;
        }

        try {
            MediaPlayer sfxPlayer = new MediaPlayer(sound);
            sfxPlayer.setVolume(GameSettings.getInstance().getSfxVolume());
            sfxPlayer.setOnEndOfMedia(sfxPlayer::dispose);
            sfxPlayer.play();
        } catch (Exception e) {
            System.err.println("Lỗi khi phát âm thanh: " + e.getMessage());
        }
    }

    /**
     * Bắt đầu phát nhạc nền.
     * Nhạc sẽ tự động lặp lại.
     */
    public static void playBackgroundMusic() {
        if (backgroundMusicMedia == null) {
            System.err.println("Nhạc nền chưa được tải! (preload() chưa chạy?)");
            return;
        }

        if (musicPlayer == null || musicPlayer.getStatus() != MediaPlayer.Status.PLAYING) {

            if (musicPlayer != null) {
                musicPlayer.stop();
            }

            try {
                musicPlayer = new MediaPlayer(backgroundMusicMedia);
                musicPlayer.setCycleCount(MediaPlayer.INDEFINITE);
                musicPlayer.setVolume(GameSettings.getInstance().getBgmVolume());
                musicPlayer.play();
            } catch (Exception e) {
                System.err.println("Lỗi khi phát nhạc nền: " + e.getMessage());
            }
        }
    }

    public static void stopBackgroundMusic() {
        if (musicPlayer != null) {
            musicPlayer.stop();
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
