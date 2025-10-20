package com.arkanoid.game;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Quản lý việc đọc, ghi và sắp xếp điểm cao.
 * File điểm cao sẽ được lưu tại thư mục gốc của dự án với tên "highscores.dat".
 */
public class HighScoreManager {

    private static final String HIGHSCORE_FILE = "highscores.dat";
    private static final int MAX_SCORES = 5;

    private List<Integer> highScores;

    public HighScoreManager() {
        highScores = new ArrayList<>();
        loadHighScores();
    }

    /**
     * Tải danh sách điểm cao từ file.
     * Nếu file không tồn tại, một danh sách trống sẽ được tạo.
     */
    @SuppressWarnings("unchecked")
    public void loadHighScores() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(HIGHSCORE_FILE))) {
            highScores = (List<Integer>) ois.readObject();
        } catch (FileNotFoundException e) {
            System.out.println("File điểm cao không tồn tại. Sẽ tạo file mới khi có điểm.");
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Lỗi khi tải điểm cao: " + e.getMessage());
        }
    }

    /**
     * Lưu danh sách điểm cao hiện tại vào file.
     */
    public void saveHighScores() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(HIGHSCORE_FILE))) {
            oos.writeObject(highScores);
        } catch (IOException e) {
            System.err.println("Lỗi khi lưu điểm cao: " + e.getMessage());
        }
    }

    /**
     * Thêm một điểm mới vào danh sách nếu nó đủ cao.
     * Danh sách sẽ được sắp xếp và chỉ giữ lại 5 điểm cao nhất.
     */
    public void addScore(int score) {
        highScores.add(score);
        // Sắp xếp danh sách theo thứ tự giảm dần
        Collections.sort(highScores, Collections.reverseOrder());

        // Nếu danh sách lớn hơn 5, xóa điểm thấp nhất
        while (highScores.size() > MAX_SCORES) {
            highScores.remove(highScores.size() - 1);
        }

        saveHighScores(); // Lưu lại danh sách mới
    }

    /**
     * Kiểm tra xem một điểm có đủ cao để lọt vào top 5 hay không.
     */
    public boolean isHighScore(int score) {
        // Nếu danh sách chưa đủ 5 điểm, mọi điểm đều được tính là điểm cao
        if (highScores.size() < MAX_SCORES) {
            return true;
        }
        // Nếu danh sách đã đủ 5, kiểm tra xem điểm mới có lớn hơn điểm thấp nhất không
        return score > highScores.get(MAX_SCORES - 1);
    }

    /**
     * Trả về danh sách điểm cao.
     */
    public List<Integer> getHighScores() {
        return highScores;
    }
}
