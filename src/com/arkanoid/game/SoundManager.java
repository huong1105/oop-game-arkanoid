package com.arkanoid.game;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import java.net.URL;

public class SoundManager {
    public static void playSound(String fileName) {
        try {
            URL resource = SoundManager.class.getResource("/sounds/" + fileName);
            if (resource == null) {
                System.err.println("Không tìm thấy file âm thanh: " + fileName);
                return;
            }
            Media sound = new Media(resource.toString());
            MediaPlayer mediaPlayer = new MediaPlayer(sound);
            mediaPlayer.play();
        } catch (Exception e) {
            System.err.println("Lỗi khi phát âm thanh: " + e.getMessage());
        }
    }
}