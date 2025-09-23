package com.arkanoid.game;

import com.arkanoid.entities.Ball;
import com.arkanoid.entities.Brick;
import com.arkanoid.entities.Paddle;
import com.arkanoid.entities.PowerUp;
import java.util.List;

public class GameManager {
    private Paddle paddle;
    private Ball ball;
    private List<Brick> bricks;
    private List<PowerUp> powerUps;
    private int score;
    private int lives;
    private String gameState; // Ví dụ: "PLAYING", "GAME_OVER"

    public void startGame() {}
    public void updateGame() {}
    public void handleInput() {}
    public void checkCollisions() {}
    public void gameOver() {}
}
