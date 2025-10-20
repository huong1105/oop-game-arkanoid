package com.arkanoid.ui;

import com.arkanoid.Const;
import com.arkanoid.entities.Ball;
import com.arkanoid.entities.Paddle;
import com.arkanoid.game.GameManager;
import com.arkanoid.game.GameState;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;

public class KeyInput {

    private KeyInput() {
    }

    /**
     * Thiết lập tất cả các trình lắng nghe sự kiện đầu vào (bàn phím và chuột) cho scene chính.
     * @param scene Scene của trò chơi.
     */
    public static void setupInput(Scene scene) {
        GameManager gm = GameManager.getInstance();
        // Cập nhật hiệu ứng "hover" khi di chuyển chuột trên menu
        scene.setOnMouseMoved((MouseEvent event) -> {
            if (gm.getGameState() == GameState.MENU) {
                gm.getMainMenu().update(event.getX(), event.getY());
            }
            else if (gm.getGameState() == GameState.SETTINGS) { 
                gm.getSettingsMenu().update(event.getX(), event.getY());
            }
            // Điều khiển paddle bằng chuột
            if (gm.getGameState() == GameState.PLAYING) {
                Paddle paddle = gm.getPaddle();
                Ball ball = gm.getBall();
                double mouseX = event.getX();
                double newPaddleX = mouseX - paddle.getWidth() / 2;
                if (newPaddleX < 0) {
                    newPaddleX = 0;
                }
                if (newPaddleX + paddle.getWidth() > Const.SCREEN_WIDTH) {
                    newPaddleX = Const.SCREEN_WIDTH - paddle.getWidth();
                }
                paddle.setX(newPaddleX);
                if (ball.isStarted() == false) {
                    ball.setX(newPaddleX + (paddle.getWidth() / 2) - (ball.getWidth() / 2));
                }
            }
        });

        scene.setOnMouseDragged(event -> {
            if (gm.getGameState() == GameState.SETTINGS) {
                gm.getSettingsMenu().handleMouseDrag(event.getX(), event.getY());
            }
        });

        // Xử lý khi click chuột
        scene.setOnMousePressed(event -> {
            if (event.getButton() == MouseButton.PRIMARY) {
                double mouseX = event.getX();
                double mouseY = event.getY();

                // Logic cho trạng thái SETTINGS
                if (gm.getGameState() == GameState.SETTINGS) {
                    String action = gm.getSettingsMenu().handleClick(mouseX, mouseY);
                    if ("BACK_TO_MENU".equals(action)) {
                        gm.setGameState(GameState.MENU);
                    }
                }
                // Logic cho các trạng thái khác
                else if (gm.getGameState() == GameState.MENU) {
                    String clickedItem = gm.getMainMenu().getClickedItem(mouseX, mouseY);
                    if (clickedItem != null) {
                        handleMenuClick(gm, clickedItem);
                    }
                }
                else if (gm.getGameState() == GameState.READY) {
                    gm.getBall().start();
                    gm.setGameState(GameState.PLAYING);
                }
                else if (gm.getGameState() == GameState.HIGH_SCORE) {
                    gm.setGameState(GameState.MENU);
                }
            }
        });

        // Xử lý khi nhấn phím
        scene.setOnKeyPressed(event -> {
            GameState currentState = gm.getGameState();
            KeyCode code = event.getCode();

            if (gm.getGameState() == GameState.HIGH_SCORE) {
                if (event.getCode() == KeyCode.ESCAPE) {
                    gm.setGameState(GameState.MENU);
                }
            }

            switch (currentState) {
                case MENU:
                    // Phím Enter cũng có thể bắt đầu game
                    if (code == KeyCode.ENTER) {
                        gm.startLevel(1);
                    }
                    break;

                case PLAYING:
                    Paddle paddle = gm.getPaddle();
                    Ball ball = gm.getBall();
                    if (ball != null && ball.isStarted() == false) {
                        if (code == KeyCode.SPACE) {
                            ball.start();
                            ball.setSpeedY(Const.BALL_MAXSPEED);
                        }
                    }

                    if (paddle != null) {
                        if (code == KeyCode.LEFT || code == KeyCode.A) {
                            paddle.setMovingLeft(true);
                            paddle.setMovingRight(false);
                        } else if (code == KeyCode.RIGHT || code == KeyCode.D) {
                            paddle.setMovingRight(true);
                            paddle.setMovingLeft(false);
                        }
                    }// Phím P để tạm dừng
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
     */
    private static void handleMenuClick(GameManager gm, String itemName) {
        switch (itemName) {
            case "New Game":
                gm.startNewGame();
                break;
            case "High Score":
                gm.setGameState(GameState.HIGH_SCORE);
                break;
            case "Setting":
                gm.setGameState(GameState.SETTINGS);
                break;
            case "Quit Game":
                Platform.exit();
                break;
        }
    }
}
