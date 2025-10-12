package com.arkanoid.game;

import com.arkanoid.Const;
import com.arkanoid.core.GameObject;
import com.arkanoid.entities.Ball;
import com.arkanoid.entities.Brick;
import com.arkanoid.entities.Paddle;
import javafx.geometry.Rectangle2D;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class GameManager {
    private static GameManager instance;
    private int currentLevel = 1;
    private int savedLevel = 1;

    private List<GameObject> gameObjects = new CopyOnWriteArrayList<>();
    private Paddle paddle;
    private Ball ball;

    private GameState gameState = GameState.MENU;
    private int score;
    private int lives;

    private GameManager() {
    }

    public static GameManager getInstance() {
        if (instance == null) {
            instance = new GameManager();
        }
        return instance;
    }

    public void startLevel(int level) {
        this.currentLevel = level;
        gameObjects.clear();
        int paddleX = Const.INSTANCE.getPaddleDefaultPosX();
        int paddleY = Const.INSTANCE.getPaddleDefaultPosY();
        paddle = new Paddle(paddleX, paddleY, Const.INSTANCE.getPaddleWidth(), Const.INSTANCE.getPaddleHeight());

        int ballX = Const.INSTANCE.getBallDefaultPosX();
        int ballY = Const.INSTANCE.getBallDefaultPosY();
        ball = new Ball(ballX, ballY, Const.INSTANCE.getBallDiameter(), Const.INSTANCE.getBallSpeedX(), -Const.INSTANCE.getBallSpeedY());

        addGameObject(paddle);
        addGameObject(ball);

        lives = Const.INSTANCE.getDefaultLives();
        score = Const.INSTANCE.getDefaultScores();
        loadLevel(level);
        gameState = GameState.PLAYING;
    }

    public void loadLevel(int level) {
        //Của Khiêm;
    }

    public void continueGame() {
        gameState = GameState.LEVEL_SELECTION;
    }

    public void selectLevel(int level) {
        if (level <= savedLevel) {
            startLevel(level);
        }
    }

    public void newGame() {
        savedLevel = 1;
        startLevel(1);
    }

    public void pauseGame() {
        if (gameState == GameState.PLAYING) {
            gameState = GameState.PAUSED;
        }
    }

    public void resumeGame() {
        if (gameState == GameState.PAUSED) {
            gameState = GameState.PLAYING;
        }
    }

    public void quitToMainMenu() {
        gameState = GameState.MENU;
    }

    public void addGameObject(GameObject object) {
        gameObjects.add(object);
    }

    public void update() {
        if (gameState != GameState.PLAYING) return;

        gameObjects.forEach(GameObject::update);
        checkCollisions();
        gameObjects.removeIf(obj -> !obj.isActive());

        long remainingBricks = gameObjects.stream().filter(obj -> obj instanceof Brick).count();
        if (remainingBricks == 0) {
            if (currentLevel == savedLevel) {
                savedLevel++; // Mở khóa màn tiếp theo
            }
            gameState = GameState.WIN;
        }
    }

    private void loseLife() {
        lives--;
        if (lives <= 0) {
            gameState = GameState.GAME_OVER;
        } else {
            // Quả bóng và thanh về vị trí mặc định
            ball.reset(paddle);
            paddle.reset();
        }
    }

    /**
     * Kiểm tra va chạm giữa bóng với tường, paddle và brick
     */
    private void checkCollisions() {
        if (ball.getX() <= 0 || ball.getX() + ball.getWidth() >= Const.INSTANCE.getScreenWidth()) {
            ball.reverseX();
        }
        if (ball.getY() <= 0) {
            ball.reverseY();
        }
        if (ball.getY() >= Const.INSTANCE.getScreenHeight()) {
            loseLife();
        }
        if (ball.getBounds().intersects(paddle.getBounds())) {
            double paddleCenterX = paddle.getX() + paddle.getWidth() / 2;
            double ballCenterX = ball.getX() + ball.getWidth() / 2;
            double offX = ballCenterX - paddleCenterX;
            // Set tốc độ phương y dựa theo tỷ lệ khoảng cách từ điểm rơi tới tâm / (chiều dài paddle/2)
            ball.setSpeedX(ball.getMaxSpeed() * 0.9 * offX / (paddle.getWidth() / 2));
            // Set tốc độ phương x dựa theo tốc độ phương y
            double newSpeedY = Math.sqrt(ball.getMaxSpeed() * ball.getMaxSpeed()
                    - ball.getSpeedX() * ball.getSpeedX());
            ball.setSpeedY(-newSpeedY);
        }
        for (GameObject obj : gameObjects) {
            if (obj instanceof Brick) {
                Rectangle2D brickBound = obj.getBounds();
                if (ball.getBounds().intersects(brickBound)) {
                    Rectangle2D intersection = ball.intersection(brickBound);
                    if (intersection.getHeight() >= intersection.getWidth()) {
                        ball.reverseY();
                    } else {
                        ball.reverseX();
                    }
                    ((Brick) obj).takeHit();
                    break;
                }
            }
        }
    }

    // Getters for Renderer and KeyInput
    public List<GameObject> getGameObjects() {
        return gameObjects;
    }

    public GameState getGameState() {
        return gameState;
    }

    public int getScore() {
        return score;
    }

    public int getLives() {
        return lives;
    }

    public Paddle getPaddle() {
        return paddle;
    }

    public Ball getBall() {
        return ball;
    }
}
