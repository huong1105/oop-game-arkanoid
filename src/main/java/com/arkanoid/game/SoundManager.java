package com.arkanoid.game;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.io.BufferedInputStream;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SoundManager {
    private static MediaPlayer musicPlayer;
    private static Media backgroundMusicMedia;

    private static final int SFX_POOL_SIZE = 11; // Hồ chứa cho mỗi âm thanh
    private static final Map<String, List<Clip>> sfxClipPool = new HashMap<>();
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
            InputStream resource = SoundManager.class.getResourceAsStream("/sounds/" + fileName);
            if (resource == null) {
                System.err.println("Không tìm thấy file âm thanh SFX: " + fileName);
                return;
            }
            InputStream bufferedIn = new BufferedInputStream(resource);
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(bufferedIn);
            List<Clip> clipList = new ArrayList<>(SFX_POOL_SIZE);
            for (int i = 0; i < SFX_POOL_SIZE; i++) {
                Clip clip = AudioSystem.getClip();
                if (i > 0) {
                    resource = SoundManager.class.getResourceAsStream("/sounds/" + fileName);
                    bufferedIn = new BufferedInputStream(resource);
                    audioStream = AudioSystem.getAudioInputStream(bufferedIn);
                }

                clip.open(audioStream);
                clipList.add(clip);
            }
            sfxClipPool.put(fileName, clipList);// Lưu vào cache
        } catch (Exception e) {
            System.err.println("Lỗi khi tải trước âm thanh SFX: " + fileName + " - " + e.getMessage());
        }
    }

    /**
     * Phát một hiệu ứng âm thanh ngắn (SFX).
     * Âm lượng được lấy từ GameSettings.
     */
    public static void playSound(String fileName) {
        List<Clip> clipList = sfxClipPool.get(fileName);

        if (clipList == null || clipList.isEmpty()) {
            System.err.println("Lỗi: Âm thanh Clip '" + fileName + "' chưa được tải trước");
            return;
        }

        for (Clip clip : clipList) {
            if (!clip.isRunning()) {
                clip.setFramePosition(0);
                clip.start();
                return;
            }
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