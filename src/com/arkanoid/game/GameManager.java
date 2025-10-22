package com.arkanoid.game;

import com.arkanoid.BrickType;
import com.arkanoid.Const;
import com.arkanoid.core.GameObject;
import com.arkanoid.ui.PauseMenu;
import com.arkanoid.ui.SettingsMenu;
import com.arkanoid.entities.*;
import com.arkanoid.ui.MainMenu;
import javafx.animation.Animation;
import javafx.animation.PauseTransition;
import javafx.geometry.Rectangle2D;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;


public class GameManager {
    private static GameManager instance;
    private int currentLevel = 1;
    private int savedLevel = 1;
    private static final int MAX_LEVELS = 3;

    private final List<GameObject> gameObjects = new CopyOnWriteArrayList<>();
    private final List<Animation> activeTimers = new ArrayList<>();
    private Paddle paddle;
    private Ball ball;
    private boolean feverBallActive = false;

    private double levelTransitionTimer = 0;
    private static final double DELAY_SEC = 1.5;

    private double gameOverTimer = 0;

    private final MainMenu mainMenu;

    private final SettingsMenu settingsMenu;
    private final PauseMenu pauseMenu;

    private GameState gameState = GameState.MENU;

    private final HighScoreManager highScoreManager;

    public void setGameState(GameState newState) {
        this.gameState = newState;
    }

    public void setFeverBallActive(boolean active) {
        this.feverBallActive = active;
    }

    private int score = Const.DEFAULT_SCORES;
    private int lives = Const.DEFAULT_LIVES;

    private GameManager() {
        mainMenu = new MainMenu(Const.WINDOW_WIDTH, Const.SCREEN_HEIGHT);
        settingsMenu = new SettingsMenu(Const.SCREEN_WIDTH, Const.SCREEN_HEIGHT);
        pauseMenu = new PauseMenu(Const.SCREEN_WIDTH, Const.SCREEN_HEIGHT);
        SoundManager.playBackgroundMusic("background_music.mp3");
        highScoreManager = new HighScoreManager();
    }

    public static GameManager getInstance() {
        if (instance == null) {
            instance = new GameManager();
        }
        return instance;
    }

    public void startLevel(int level) {
        this.currentLevel = level;
        for (Animation timer : activeTimers) {
            timer.stop();
        }
        activeTimers.clear();
        gameObjects.clear();
        int paddleX = Const.PADDLE_DEFAULT_POS_X;
        int paddleY = Const.PADDLE_DEFAULT_POS_Y;
        paddle = new Paddle(paddleX, paddleY, Const.PADDLE_WIDTH, Const.PADDLE_HEIGHT);

        int ballX = Const.BALL_DEFAULT_POS_X;
        int ballY = Const.BALL_DEFAULT_POS_Y;
        ball = new Ball(ballX, ballY, Const.BALL_DIAMETER, 0, 0);
        ball.stop();
        addGameObject(paddle);
        addGameObject(ball);

        loadLevel(level);
        gameState = GameState.PLAYING;
    }

    public void loadLevel(int level) {
        int startX = (Const.SCREEN_WIDTH - 15 * Const.BRICK_WIDTH) / 2;
        int startY = 50;
        int[][] levelLayout;

        switch (level) {
            case 1:
                levelLayout = new int[][]{
                    {5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5},
                    {5, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 5},
                    {5, 1, 1, 1, 5, 1, 1, 1, 1, 1, 5, 1, 1, 1, 5},
                    {5, 1, 1, 1, 5, 1, 1, 1, 1, 1, 5, 1, 1, 1, 5},
                    {5, 1, 1, 1, 5, 1, 1, 1, 1, 1, 5, 1, 1, 1, 5},
                    {5, 1, 1, 1, 5, 1, 1, 1, 1, 1, 5, 1, 1, 1, 5},
                    {5, 1, 1, 1, 5, 1, 1, 1, 1, 1, 5, 1, 1, 1, 5},
                    {5, 1, 1, 1, 5, 5, 5, 5, 5, 5, 5, 1, 1, 1, 5},
                    {5, 1, 1, 1, 5, 1, 1, 1, 1, 1, 5, 1, 1, 1, 5},
                    {5, 1, 1, 1, 5, 1, 1, 1, 1, 1, 5, 1, 1, 1, 5},
                    {5, 1, 1, 1, 5, 1, 1, 1, 1, 1, 5, 1, 1, 1, 5},
                    {5, 1, 1, 1, 5, 1, 1, 1, 1, 1, 5, 1, 1, 1, 5},
                    {5, 1, 1, 1, 5, 1, 1, 1, 1, 1, 5, 1, 1, 1, 5},
                    {5, 1, 1, 1, 5, 1, 1, 1, 1, 1, 5, 1, 1, 1, 5},
                    {5, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 5},
                    {5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5}
                };
                break;
            case 2:
                levelLayout = new int[][]{
                        {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
                        {1, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 1},
                        {1, 2, 0, 0, 0, 0, 2, 2, 0, 0, 0, 0, 2, 2, 2, 1},
                        {1, 2, 0, 2, 2, 0, 2, 2, 0, 2, 2, 0, 2, 2, 2, 1},
                        {1, 2, 0, 2, 2, 0, 2, 2, 0, 2, 2, 0, 2, 2, 2, 1},
                        {1, 2, 0, 2, 2, 0, 0, 0, 0, 2, 2, 0, 2, 2, 2, 1},
                        {1, 2, 0, 2, 2, 2, 2, 2, 2, 2, 2, 0, 2, 2, 2, 1},
                        {1, 2, 0, 2, 2, 0, 0, 0, 0, 2, 2, 0, 2, 2, 2, 1},
                        {1, 2, 0, 2, 2, 0, 2, 2, 0, 2, 2, 0, 2, 2, 2, 1},
                        {1, 2, 0, 2, 2, 0, 2, 2, 0, 2, 2, 0, 2, 2, 2, 1},
                        {1, 2, 0, 0, 0, 0, 2, 2, 0, 0, 0, 0, 2, 2, 2, 1},
                        {1, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 1},
                        {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
                        {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
                        {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0}
                };
                break;
            default:
                levelLayout = new int[][]{
                        {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
                        {1, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 1},
                        {1, 2, 0, 0, 0, 0, 2, 2, 0, 0, 0, 0, 2, 2, 2, 1},
                        {1, 2, 0, 2, 2, 0, 2, 2, 0, 2, 2, 0, 2, 2, 2, 1},
                        {1, 2, 0, 2, 2, 0, 2, 2, 0, 2, 2, 0, 2, 2, 2, 1},
                        {1, 2, 0, 2, 2, 0, 0, 0, 0, 2, 2, 0, 2, 2, 2, 1},
                        {1, 2, 0, 2, 2, 2, 2, 2, 2, 2, 2, 0, 2, 2, 2, 1},
                        {1, 2, 0, 2, 2, 0, 0, 0, 0, 2, 2, 0, 2, 2, 2, 1},
                        {1, 2, 0, 2, 2, 0, 2, 2, 0, 2, 2, 0, 2, 2, 2, 1},
                        {1, 2, 0, 2, 2, 0, 2, 2, 0, 2, 2, 0, 2, 2, 2, 1},
                        {1, 2, 0, 0, 0, 0, 2, 2, 0, 0, 0, 0, 2, 2, 2, 1},
                        {1, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 1},
                        {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
                        {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
                        {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0}
                };
                break;
        }

        for (int row = 0; row < levelLayout.length; row++) {
            for (int col = 0; col < levelLayout[row].length; col++) {
                int x = startX + col * Const.BRICK_WIDTH;
                int y = startY + row * Const.BRICK_HEIGHT;
                Brick brick;
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
                    case 5:
                        brick = new ExplosiveBrick(x, y);
                        break;
                    default:
                        continue;
                }
                addGameObject(brick);
            }
        }
    }


    public int[] getBrickGridPosition(Brick brick) {
        int startX = (Const.SCREEN_WIDTH - 15 * Const.BRICK_WIDTH) / 2;
        int startY = 50;
        int brickRow = (int) ((brick.getY() - startY) / Const.BRICK_HEIGHT);
        int brickCol = (int) ((brick.getX() - startX) / Const.BRICK_WIDTH);
        return new int[]{brickRow, brickCol};
    }

    private void handleExplosion(int centerRow, int centerCol) {
        for (int row = Math.max(0, centerRow - 1); row <= Math.min(15, centerRow + 1); row++) {
            for (int col = Math.max(0, centerCol - 1); col <= Math.min(14, centerCol + 1); col++) {
                for (GameObject obj : gameObjects) {
                    if (obj instanceof Brick && ((Brick) obj).getType() != BrickType.WALL) {
                        int[] gridPos = getBrickGridPosition((Brick) obj);
                        int brickRow = gridPos[0];
                        int brickCol = gridPos[1];
                        if (brickRow == row && brickCol == col) {
                            boolean destroyed = ((Brick) obj).takeHit();
                            if (destroyed) {
                                onBrickDestroyed((Brick) obj);
                            }
                        }
                    }
                }
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
        lives = Const.DEFAULT_LIVES;
        score =  Const.DEFAULT_SCORES;
        startLevel(1);
    }

    public SettingsMenu getSettingsMenu() {
        return settingsMenu;
    }

    public MainMenu getMainMenu() {
        return mainMenu;
    }

    public void pauseGame() {
        if (gameState == GameState.PLAYING) {
            gameState = GameState.PAUSED;
        }

        for (Animation timer : activeTimers) {
            timer.pause();
        }
    }

    public void resumeGame() {
        if (gameState == GameState.PAUSED) {
            gameState = GameState.PLAYING;
        }

        for (Animation timer : activeTimers) {
            timer.play();
        }
    }

    public void quitToMainMenu() {
        gameState = GameState.MENU;
    }

    public void addGameObject(GameObject object) {
        gameObjects.add(object);
    }

    public void update(double deltaTimeSeconds) {
        if (gameState == GameState.LEVEL_TRANSITION) {
            levelTransitionTimer -= deltaTimeSeconds;
            if (levelTransitionTimer <= 0) {
                currentLevel++;
                startLevel(currentLevel);
                if (savedLevel < currentLevel) {
                    savedLevel = currentLevel;
                }
            }
            return; // Dừng update trong lúc chờ
        }

        if (gameState == GameState.GAME_OVER) {
            gameOverTimer -= deltaTimeSeconds;
            if (gameOverTimer <= 0) {
                setGameState(GameState.MENU); // Tự động chuyển về Menu
            }
            return; // Dừng game
        }

        if (gameState != GameState.PLAYING) return;

        gameObjects.forEach(obj -> obj.update(deltaTimeSeconds));

        checkCollisions();
        gameObjects.removeIf(obj -> !obj.isActive());

        long remainingBricks = gameObjects.stream()
                .filter(obj -> obj instanceof Brick)
                .filter(obj -> ((Brick) obj).getType() != BrickType.WALL)
                .count();

        if (remainingBricks == 0) {
            if (currentLevel >= MAX_LEVELS) {
                gameState = GameState.WIN;
            } else {
                gameState = GameState.LEVEL_TRANSITION;
                levelTransitionTimer = DELAY_SEC;
            }
        }
    }

    private void loseLife() {
        long remainingBalls = gameObjects.stream()
                .filter(obj -> obj instanceof Ball)
                .count();

        if (remainingBalls <= 1) {
            int lifePenalty = feverBallActive ? FeverBallPowerUp.getLifePenaltyMultiplier() : 1;
            if (lives <= lifePenalty) {
                lives = 0;
                gameState = GameState.GAME_OVER;
                highScoreManager.addScore(score);
                gameOverTimer = DELAY_SEC;
            } else {
                lives -= lifePenalty;
                gameObjects.removeIf(obj -> obj instanceof Ball);
                int ballX = Const.BALL_DEFAULT_POS_X;
                int ballY = Const.BALL_DEFAULT_POS_Y;
                ball = new Ball(ballX, ballY, Const.BALL_DIAMETER, 0, 0);
                addGameObject(ball);
                paddle.reset();
            }
        }
    }

    private void checkCollisions() {
        for (GameObject ballObj : gameObjects.stream()
                .filter(obj -> obj instanceof Ball)
                .toList()) {
            Ball ball = (Ball) ballObj;

            if (!ball.isStarted()) {
                ball.setSpeedX(paddle.getSpeedX());
                return;
            }

            if (ball.getX() <= 0 || ball.getX() + ball.getWidth() >= Const.SCREEN_WIDTH) {
                ball.reverseX();
                if (ball.getX() <= 0) {
                    ball.setX(0);
                } else {
                    ball.setX(Const.SCREEN_WIDTH - ball.getWidth());
                }
            }
            if (ball.getY() <= 0) {
                ball.reverseY();
                ball.setY(0);
            }
            if (ball.getY() >= Const.SCREEN_HEIGHT) {
                ball.setActive(false);
                loseLife();
                continue;
            }

            if (ball.getBounds().intersects(paddle.getBounds())) {
                Rectangle2D intersection = ball.intersection(paddle.getBounds());
                double ballCurrentSpeedX = getBallCurrentSpeedX(ball);
                ball.setSpeedX(ballCurrentSpeedX);
                double newSpeedY = Math.sqrt(Math.pow(ball.getMaxSpeed(), 2)
                        - Math.pow(ball.getSpeedX(), 2));
                ball.setSpeedY(-newSpeedY);
                if (intersection != null){
                    ball.setY(ball.getY() - intersection.getHeight());
                }
            }

            for (GameObject obj : gameObjects) {
                if (obj instanceof Brick) {
                    Rectangle2D brickBound = obj.getBounds();
                    if (ball.getBounds().intersects(brickBound)) {
                        Rectangle2D intersection = ball.intersection(brickBound);
                        if (intersection.getHeight() >= intersection.getWidth()) {
                            if (ball.getX() < brickBound.getMinX()) {
                                ball.setX(brickBound.getMinX() - ball.getWidth());
                            } else {
                                ball.setX(brickBound.getMaxX());
                            }
                            ball.reverseX();
                        } else {
                            if (ball.getY() < brickBound.getMinY()) {
                                ball.setY(brickBound.getMinY() - ball.getHeight());
                            } else {
                                ball.setY(brickBound.getMaxY());
                            }
                            ball.reverseY();
                        }
                        boolean destroyed = ((Brick) obj).takeHit();
                        if (destroyed) {
                            onBrickDestroyed((Brick) obj);
                            break;
                        }
                    }
                }
                else if (obj instanceof Shield){
                    Shield shield = (Shield) obj;
                    if (ball.getBounds().intersects(shield.getBounds())){
                        shield.hit();
                        ball.setY(ball.getY() - ball.getHeight() - 5);
                        ball.reverseY();
                    }
                }
            }
        }

        for (GameObject obj : gameObjects) {
            if (obj instanceof PowerUp && obj.getBounds().intersects(paddle.getBounds())) {
                ((PowerUp) obj).activate(paddle);
                // Xóa dòng setActive(false) để power-up tự xử lý khi hết duration
            }
        }
    }

    private double getBallCurrentSpeedX(Ball ball) {
        double paddleCenterX = paddle.getX() + paddle.getWidth() / 2;
        double ballCenterX = ball.getX() + ball.getWidth() / 2;
        double offX = ballCenterX - paddleCenterX;
        double ballCurrentSpeedX = ball.getMaxSpeed() * 0.99 * offX / (paddle.getWidth() / 2);
        double paddleCurrentSpeed = paddle.getSpeedX();
        ballCurrentSpeedX += paddleCurrentSpeed * 0.25;
        if (ballCurrentSpeedX > ball.getMaxSpeed()) {
            ballCurrentSpeedX = ball.getMaxSpeed() * 0.99;
        }
        if (ballCurrentSpeedX < -ball.getMaxSpeed()) {
            ballCurrentSpeedX = -ball.getMaxSpeed() * 0.99;
        }
        return ballCurrentSpeedX;
    }

    /**
     * Xử lý TẤT CẢ logic khi một viên gạch bị phá hủy (bởi bóng hoặc bởi nổ).
     */
    private void onBrickDestroyed(Brick brick) {
        score += (int) (brick.getScoreValue() * (feverBallActive ? FeverBallPowerUp.getScoreMultiplier() : 1));

        FireWorkEffect fire = new FireWorkEffect(
                (int) brick.getX(),
                (int) brick.getY(),
                Const.BRICK_WIDTH,
                Const.BRICK_HEIGHT
        );
        addGameObject(fire);

        if (brick.getType() == BrickType.EXPLOSIVE) {
            int[] gridPos = getBrickGridPosition(brick);
            int brickRow = gridPos[0];
            int brickCol = gridPos[1];

            PauseTransition delay = new PauseTransition(Duration.millis(150));
            delay.setOnFinished(e ->{
                handleExplosion(brickRow, brickCol);
                activeTimers.remove(delay);
            });
            activeTimers.add(delay);
            delay.play();
        }

        if (Math.random() < 0.05) {
            PowerUp powerUp;
            double rand = Math.random();

            if (rand < 0.2) {
                powerUp = new MultiBallPowerUp((int) brick.getX(), (int) brick.getY());
            } else if (rand < 0.4) {
                powerUp = new FastBallPowerUp((int) brick.getX(), (int) brick.getY());
            } else if (rand < 0.6) {
                powerUp = new ExpandPaddlePowerUp((int) brick.getX(), (int) brick.getY());
            } else if (rand < 0.8) {
                powerUp = new ShieldPowerUp((int) brick.getX(), (int) brick.getY()); // Đã thêm Shield
            } else {
                powerUp = new FeverBallPowerUp((int) brick.getX(), (int) brick.getY());
            }
            addGameObject(powerUp);
        }
    }

    /**
     * Reset tất cả các chỉ số của game về trạng thái ban đầu.
     */
    private void resetGame() {
        this.score = Const.DEFAULT_SCORES;
        this.lives = Const.DEFAULT_LIVES;
        this.currentLevel = 1;
        gameObjects.clear();
    }

    /**
     * Bắt đầu một lần chơi mới.
     */
    public void startNewGame() {
        resetGame();
        startLevel(this.currentLevel);
    }

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

    public HighScoreManager getHighScoreManager() {
        return highScoreManager;
    }

    public int getCurrentLevel() { return currentLevel; }

    public PauseMenu getPauseMenu() { return pauseMenu; }
}
