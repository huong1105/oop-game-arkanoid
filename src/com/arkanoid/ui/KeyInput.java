package com.arkanoid.ui;

import com.arkanoid.entities.Paddle;
import com.arkanoid.game.GameManager;
import com.arkanoid.game.GameState;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;

public class KeyInput {

    private KeyInput() {
        // Lớp tiện ích không cần khởi tạo
    }

    /**
     * Thiết lập tất cả các trình lắng nghe sự kiện đầu vào (bàn phím và chuột) cho scene chính.
     * @param scene Scene của trò chơi.
     */
    public static void setupInput(Scene scene) {
        GameManager gm = GameManager.getInstance();

        // --- 1. XỬ LÝ SỰ KIỆN CHUỘT ---

        // Cập nhật hiệu ứng "hover" khi di chuyển chuột trên menu
        scene.setOnMouseMoved((MouseEvent event) -> {
            if (gm.getGameState() == GameState.MENU) {
                gm.getMainMenu().update(event.getX(), event.getY());
            }
        });

        // Xử lý khi click chuột
        scene.setOnMouseClicked((MouseEvent event) -> {
            if (gm.getGameState() == GameState.MENU) {
                for (MenuItem item : gm.getMainMenu().getMenuItems()) {
                    // Kiểm tra xem có click vào nút nào không
                    if (item.getBounds().contains(event.getX(), event.getY())) {
                        handleMenuClick(gm, item.getText());
                        break; // Dừng lại sau khi tìm thấy nút được click
                    }
                }
            }
        });


        // --- 2. XỬ LÝ SỰ KIỆN BÀN PHÍM ---

        // Xử lý khi nhấn phím
        scene.setOnKeyPressed(event -> {
            GameState currentState = gm.getGameState();
            KeyCode code = event.getCode();

            switch (currentState) {
                case MENU:
                    // Phím Enter cũng có thể bắt đầu game
                    if (code == KeyCode.ENTER) {
                        gm.startLevel(1);
                    }
                    break;

                case READY:
                    // Phím Space để bắt đầu lượt chơi mới
                    if (code == KeyCode.SPACE) {
                        gm.setGameState(GameState.PLAYING);
                    }
                    break;

                case PLAYING:
                    Paddle paddle = gm.getPaddle();
                    if (paddle != null) {
                        if (code == KeyCode.LEFT || code == KeyCode.A) {
                            paddle.setMovingLeft(true);
                        } else if (code == KeyCode.RIGHT || code == KeyCode.D) {
                            paddle.setMovingRight(true);
                        }
                    }
                    // Phím P để tạm dừng
                    if (code == KeyCode.P) {
                        gm.setGameState(GameState.PAUSED);
                    }
                    break;

                case PAUSED:
                    // Phím P để tiếp tục
                    if (code == KeyCode.P) {
                        gm.setGameState(GameState.PLAYING);
                    }
                    break;

                case GAME_OVER:
                case WIN:
                    // Phím Enter để quay về menu
                    if (code == KeyCode.ENTER) {
                        gm.setGameState(GameState.MENU);
                    }
                    break;
            }
        });

        // Xử lý khi nhả phím
        scene.setOnKeyReleased(event -> {
            if (gm.getGameState() == GameState.PLAYING) {
                Paddle paddle = gm.getPaddle();
                if (paddle != null) {
                    KeyCode code = event.getCode();
                    if (code == KeyCode.LEFT || code == KeyCode.A) {
                        paddle.setMovingLeft(false);
                    } else if (code == KeyCode.RIGHT || code == KeyCode.D) {
                        paddle.setMovingRight(false);
                    }
                }
            }
        });
    }

    /**
     * Xử lý các hành động tương ứng khi một mục trong menu được click.
     * @param gm Thể hiện của GameManager.
     * @param itemName Tên của mục được click (ví dụ: "New Game").
     */
    private static void handleMenuClick(GameManager gm, String itemName) {
        switch (itemName) {
            case "New Game":
                gm.startLevel(1);
                break;
            case "High Score":
                System.out.println("Chức năng High Score chưa được cài đặt!");
                // (Trong tương lai, bạn có thể chuyển game sang trạng thái GameState.HIGH_SCORE ở đây)
                break;
            case "Setting":
                System.out.println("Chức năng Setting chưa được cài đặt!");
                // (Trong tương lai, bạn có thể chuyển game sang trạng thái GameState.SETTINGS ở đây)
                break;
        }
    }
}
