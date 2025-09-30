package com.arkanoid.game;

import com.arkanoid.core.GameObject;
import com.arkanoid.entities.*;
import com.arkanoid.ui.Renderer;

import java.awt.*;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class GameManager implements Runnable {
    private static GameManager instance;
    private Thread gameThread;
    private Renderer renderer;

    private List<GameObject> gameObjects = new CopyOnWriteArrayList<>();
    private Paddle paddle;
    private Ball ball;

    private GameState gameState = GameState.MENU;
    private int score = 0;
    private int lives = 3;

    private GameManager() {}

    public static GameManager getInstance() {
        if (instance == null) { instance = new GameManager(); }
        return instance;
    }

    public void initGame() {

    }

    public void addGameObject(GameObject object) { gameObjects.add(object); }

    public void startGame(Renderer renderer) {
        this.renderer = renderer;
        this.gameState = GameState.MENU;
        new Thread(this).start();
    }

    @Override
    public void run() {

    }

    private void update() {
        gameObjects.forEach(GameObject::update);
        checkCollisions();
        gameObjects.removeIf(obj -> !obj.isActive());

        long remainingBricks = gameObjects.stream().filter(obj -> obj instanceof Brick).count();
        if (remainingBricks == 0) {
            gameState = GameState.WIN;
        }
    }

    private void loseLife() {
        lives--;
        if (lives <= 0) {
            gameState = GameState.GAME_OVER;
        } else {
            // Quả bóng và thanh về vị trí mặc định
        }
    }

    private void checkCollisions() {

    }

    // Getters for Renderer and KeyInput
    public List<GameObject> getGameObjects() { return gameObjects; }
    public GameState getGameState() { return gameState; }
    public int getScore() { return score; }
    public int getLives() { return lives; }
    public Paddle getPaddle() { return paddle; }
    public Ball getBall() { return ball; }
}
