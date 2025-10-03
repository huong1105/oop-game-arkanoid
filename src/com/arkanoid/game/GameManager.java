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
        if (ball.getX() <= 0 || ball.getX() + ball.getWidth() >= 1000 ){
            ball.reverseX();
        }
        if (ball.getY() <= 0){
            ball.reverseY();
        }
        if (ball.getBounds().intersects(paddle.getBounds())){
            double paddleCenterX = paddle.getX() + paddle.getWidth() / 2;
            double ballCenterX = ball.getX() + ball.getWidth() / 2;
            double offX = ballCenterX - paddleCenterX;
            ball.setSpeedX(ball.getMaxSpeed() * offX / (paddle.getWidth() / 2));
            double newSpeedY = Math.sqrt(ball.getMaxSpeed() * ball.getMaxSpeed() - ball.getSpeedX() * ball.getSpeedX());
            ball.setSpeedY(-newSpeedY);
        }

    }

    // Getters for Renderer and KeyInput
    public List<GameObject> getGameObjects() { return gameObjects; }
    public GameState getGameState() { return gameState; }
    public int getScore() { return score; }
    public int getLives() { return lives; }
    public Paddle getPaddle() { return paddle; }
    public Ball getBall() { return ball; }
}
