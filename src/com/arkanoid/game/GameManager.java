package com.arkanoid.game;

import com.arkanoid.Const;
import com.arkanoid.core.GameObject;
import com.arkanoid.entities.Ball;
import com.arkanoid.entities.Brick;
import com.arkanoid.entities.Paddle;
import com.arkanoid.entities.*;
import javafx.geometry.Rectangle2D;
import com.arkanoid.ui.MainMenu;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class GameManager {
    private static GameManager instance;
    private int currentLevel = 1;
    private int savedLevel = 1;

    private List<GameObject> gameObjects = new CopyOnWriteArrayList<>();
    private Paddle paddle;
    private Ball ball;

    private MainMenu mainMenu;

    private GameState gameState = GameState.MENU;
    public void setGameState(GameState newState) {
        this.gameState = newState;
    }

    private int score;
    private int lives;

    private GameManager() {
        mainMenu = new MainMenu(Const.SCREEN_WIDTH, Const.SCREEN_HEIGHT);
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
        int paddleX = Const.PADDLE_DEFAULT_POS_X;
        int paddleY = Const.PADDLE_DEFAULT_POS_Y;
        paddle = new Paddle(paddleX, paddleY, Const.PADDLE_WIDTH, Const.PADDLE_HEIGHT);

        int ballX = Const.BALL_DEFAULT_POS_X;
        int ballY = Const.BALL_DEFAULT_POS_Y;
        ball = new Ball(ballX, ballY, Const.BALL_DIAMETER, Const.BALL_SPEEDX, -Const.BALL_SPEEDY);

        addGameObject(paddle);
        addGameObject(ball);

        lives = Const.DEFAULT_LIVES;
        score = Const.DEFAULT_SCORES;
        loadLevel(level);
        gameState = GameState.PLAYING;
    }

    public void loadLevel(int level) {
        int startX = (Const.SCREEN_WIDTH - 8 * Const.BRICK_WIDTH) / 2;
        int startY = 50;
        int[][] levelLayout;

        switch(level) {
            case 1:
                levelLayout = new int[][] {
                    {1, 1, 1, 3, 3, 1, 1, 1},
                    {1, 2, 2, 2, 2, 2, 2, 1},
                    {1, 2, 4, 2, 2, 4, 2, 1},
                    {1, 2, 4, 4, 4, 4, 2, 1},
                    {1, 2, 4, 2, 2, 4, 2, 1},
                    {1, 2, 4, 2, 2, 4, 2, 1},
                    {1, 2, 4, 2, 2, 4, 2, 1},
                    {1, 1, 1, 1, 1, 1, 1, 1}
                };
                break;
            case 2:
                levelLayout = new int[][] {
                    {1, 1, 1, 1, 1, 1, 1, 1},
                    {4, 2, 2, 2, 2, 2, 2, 4},
                    {4, 2, 1, 1, 1, 2, 2, 4},
                    {4, 2, 1, 3, 3, 1, 2, 4},
                    {4, 2, 1, 3, 3, 1, 2, 4},
                    {4, 2, 1, 1, 1, 2, 2, 4},
                    {4, 2, 2, 2, 2, 2, 2, 4},
                    {4, 4, 4, 4, 4, 4, 4, 4}
                };
                break;
            default:
                levelLayout = new int[][] {
                    {3, 1, 1, 3, 3, 1, 1, 3},
                    {1, 2, 2, 2, 2, 2, 2, 1},
                    {1, 2, 4, 2, 2, 4, 2, 1},
                    {1, 2, 4, 4, 4, 4, 2, 1},
                    {1, 2, 4, 2, 2, 4, 2, 1},
                    {1, 2, 4, 2, 2, 4, 2, 1},
                    {1, 2, 4, 2, 2, 4, 2, 1},
                    {3, 1, 1, 1, 1, 1, 1, 3}
                };
                break;
        }


        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                int x = startX + col * Const.BRICK_WIDTH;
                int y = startY + row * Const.BRICK_HEIGHT;
                Brick brick = null;
                switch (levelLayout[row][col]) {
                    case 1:
                        brick = new NormalBrick(x, y);
                        break;
                    case 2:
                        brick = new StrongBrick(x, y);
                        break;
                    case 3:
                        brick = new SpecialBrick(x, y);
                        break;
                    case 4:
                        brick = new Wall(x, y);
                        break;
                    default:
                        continue;
                }
                addGameObject(brick);
            }
        }
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

    public MainMenu getMainMenu() {
        return mainMenu;
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
        //System.err.println("gm update" );
        if (gameState != GameState.PLAYING) return;
        gameObjects.forEach(GameObject::update);
        checkCollisions();
        //gameObjects.removeIf(obj -> !obj.isActive());

        long remainingBricks = gameObjects.stream().filter(obj -> obj instanceof Brick).count();
//        if (remainingBricks == 0) {
//            if (currentLevel == savedLevel) {
//                savedLevel++; // Mở khóa màn tiếp theo
//            }
//            gameState = GameState.WIN;
//        }
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
        if (ball.getX() <= 0 || ball.getX() + ball.getWidth() >= Const.SCREEN_WIDTH) {
            ball.reverseX();
        }
        if (ball.getY() <= 0) {
            ball.reverseY();
        }
        if (ball.getY() >= Const.SCREEN_HEIGHT) {
            loseLife();
        }
        if (ball.getBounds().intersects(paddle.getBounds())) {
            double paddleCenterX = paddle.getX() + paddle.getWidth() / 2;
            double ballCenterX = ball.getX() + ball.getWidth() / 2;
            double offX = ballCenterX - paddleCenterX;
            // Set tốc độ phương y dựa theo tỷ lệ khoảng cách từ điểm rơi tới tâm / (chiều dài paddle/2)
            ball.setSpeedX(ball.getMaxSpeed() * 0.99 * offX / (paddle.getWidth() / 2));
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
