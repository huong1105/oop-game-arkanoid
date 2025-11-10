// Tạo file mới: ProgressManager.java (trong package com.arkanoid.game)

package com.arkanoid.game;

import java.io.*;

/**
 * Quản lý việc lưu và tải màn chơi cao nhất đã mở khóa.
 * Chỉ lưu một số Integer duy nhất.
 */
public class ProgressManager {

    private static final String PROGRESS_FILE = "progress.dat";

    /**
     * Lưu màn chơi cao nhất đã mở khóa ra file.
     * @param maxLevel Màn chơi cao nhất
     */
    public void saveProgress(int maxLevel) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(PROGRESS_FILE))) {
            oos.writeObject(maxLevel);
            System.out.println("Đã lưu tiến trình, màn cao nhất: " + maxLevel);
        } catch (IOException e) {
            System.err.println("Lỗi khi lưu tiến trình: " + e.getMessage());
        }
    }

    /**
     * Tải màn chơi cao nhất đã mở khóa.
     * @return Màn chơi cao nhất đã mở khóa. Trả về 1 nếu không có file.
     */
    public int loadProgress() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(PROGRESS_FILE))) {
            int maxLevel = (int) ois.readObject();
            System.out.println("Đã tải tiến trình, màn cao nhất: " + maxLevel);
            return maxLevel;
        } catch (FileNotFoundException e) {
            System.out.println("Không tìm thấy file tiến trình. Bắt đầu từ màn 1.");
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Lỗi khi tải tiến trình: " + e.getMessage());
        }
        return 1;
    }
}