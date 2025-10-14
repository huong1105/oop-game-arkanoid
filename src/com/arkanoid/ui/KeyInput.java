package com.arkanoid.ui;

import com.arkanoid.entities.Paddle;
import com.arkanoid.game.GameManager;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;

public class KeyInput {
    private KeyInput() {}

    public static void setupInput(Scene scene) {
        GameManager gm = GameManager.getInstance();
        scene.setOnKeyPressed(event -> {
            System.out.println("Key Pressed: " + event.getCode());
            // ==========================================================
            Paddle paddle = gm.getPaddle();
            if (paddle == null) return;
            KeyCode code = event.getCode();
            switch (code) {
                case LEFT:
                case A:
                    paddle.setMovingLeft(true);
                    paddle.setMovingRight(false);
                    break;
                case RIGHT:
                case D:
                    paddle.setMovingRight(true);
                    paddle.setMovingLeft(false);
                    break;
                default:
                    break;
            }
        });
        scene.setOnKeyReleased(event -> {
            Paddle paddle = gm.getPaddle();
            if (paddle == null) return;

            KeyCode code = event.getCode();
            switch (code) {
                case LEFT:
                case A:
                    paddle.setMovingLeft(false);
                    break;
                case RIGHT:
                case D:
                    paddle.setMovingRight(false);
                    break;
                default:
                    break;
            }
        });
    }
}
