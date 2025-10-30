package com.arkanoid.game;

import com.arkanoid.BrickType;
import com.arkanoid.Const;
import com.arkanoid.core.GameObject;
import com.arkanoid.ui.PauseMenu;
import com.arkanoid.entities.*;
import com.arkanoid.ui.MainMenu;
import com.arkanoid.ui.SpriteManager;
import javafx.animation.Animation;
import javafx.animation.PauseTransition;
import javafx.concurrent.Task;
import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;


public class GameManager {
    private static GameManager instance;
    private int currentLevel = 1;
    private int savedLevel = 1;
    private static final int MAX_LEVELS = 3;

    private final List<Ball> balls = new CopyOnWriteArrayList<>();
    private final List<Brick> bricks = new CopyOnWriteArrayList<>();
    private final List<PowerUp> powerUps = new CopyOnWriteArrayList<>();
    private final List<FireWorkEffect> effects = new CopyOnWriteArrayList<>();
    private final List<Shield> shields = new CopyOnWriteArrayList<>();
    private final List<Animation> activeTimers = new ArrayList<>();
    private Paddle paddle;
    private Ball ball;
    private boolean feverBallActive = false;

    private double levelTransitionTimer = 0;
    private static final double DELAY_SEC = 1.5;

    private double gameOverTimer = 0;

    private final MainMenu mainMenu;
    private Image preloadedBackgroundImage;

    private final SettingsMenu settingsMenu;
    private final PauseMenu pauseMenu;
    private final HighScoreMenu highScoreMenu;

    private GameState gameState = GameState.LOADING;

    private final HighScoreManager highScoreManager;

    public void setGameState(GameState newState) {
        if (this.gameState == newState) return;

        boolean wasInMenuZone = (this.gameState == GameState.MENU ||
                                this.gameState == GameState.SETTINGS ||
                                this.gameState == GameState.HIGH_SCORE);

        boolean isEnteringMenuZone = (newState == GameState.MENU ||
                                    newState == GameState.SETTINGS ||
                                    newState == GameState.HIGH_SCORE);

        if (isEnteringMenuZone && !wasInMenuZone) {
            SoundManager.playBackgroundMusic();
        } else if (!isEnteringMenuZone && wasInMenuZone) {
            SoundManager.stopBackgroundMusic();
        }

        this.gameState = newState;
    }

    public void setFeverBallActive(boolean active) {
        this.feverBallActive = active;
    }

    private int score = Const.DEFAULT_SCORES;
    private int lives = Const.DEFAULT_LIVES;

    private int levelStartScore;
    private int levelStartLives;

    private GameManager() {
        mainMenu = new MainMenu(Const.WINDOW_WIDTH, Const.SCREEN_HEIGHT);
        settingsMenu = new SettingsMenu(Const.WINDOW_WIDTH, Const.SCREEN_HEIGHT, mainMenu);
        pauseMenu = new PauseMenu(Const.SCREEN_WIDTH, Const.SCREEN_HEIGHT);
        SoundManager.playBackgroundMusic();
        highScoreManager = new HighScoreManager();
        highScoreMenu = new HighScoreMenu(Const.WINDOW_WIDTH, Const.SCREEN_HEIGHT, mainMenu, highScoreManager);

        loadAssets();
    }

    public static GameManager getInstance() {
        if (instance == null) {
            instance = new GameManager();
        }
        return instance;
    }

    public void loadAssets() {
        // Sử dụng Task của JavaFX để chạy nền
        Task<Void> loadingTask = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                System.out.println("Đang tải Sprites...");
                SpriteManager.preload();

                System.out.println("Đang tải Sounds...");
                SoundManager.preload();

                System.out.println("Đang tải Fonts...");
                FontManager.preload();

                System.out.println("Đang tải Background...");
                try {
                    var image = GameManager.class.getResource("/Images/background.jpg");
                    if (image == null) {
                        throw new Exception("Không tìm thấy /Images/background.jpg trong thư mục resources.");
                    }
                    preloadedBackgroundImage = new Image(image.toString());
                } catch (Exception e) {
                    System.err.println("Lỗi tải ảnh nền: " + e.getMessage());
                    preloadedBackgroundImage = null;
                }

                System.out.println("Tải xong!");
                return null;
            }
        };

        loadingTask.setOnSucceeded(e -> {
            mainMenu.setBackgroundImage(preloadedBackgroundImage);
            setGameState(GameState.MENU);
        });

        loadingTask.setOnFailed(e -> {
            System.err.println("Tải tài nguyên thất bại!");
            loadingTask.getException().printStackTrace();
        });

        new Thread(loadingTask).start();
    }

    public void startLevel(int level) {
        this.levelStartScore = this.score;
        this.levelStartLives = this.lives;

        this.currentLevel = level;
        for (Animation timer : activeTimers) {
            timer.stop();
        }
        activeTimers.clear();
        resetGameLists();

        for (int i = 0; i < 5; i++) {
            FireBallPowerUp pu = new FireBallPowerUp(0, 0);
            PowerUpPool.getInstance().returnPowerUp(pu);
        }

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
        setGameState(GameState.PLAYING);
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
                for (Brick brick : bricks) {
                    if (brick.getType() != BrickType.WALL) {
                        int[] gridPos = getBrickGridPosition(brick);
                        int brickRow = gridPos[0];
                        int brickCol = gridPos[1];
                        if (brickRow == row && brickCol == col) {
                            boolean destroyed = brick.takeHit();
                            if (destroyed) {
                                onBrickDestroyed(brick);
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
            setGameState(GameState.PAUSED);
        }

        for (Animation timer : activeTimers) {
            timer.pause();
        }
    }

    public void resumeGame() {
        if (gameState == GameState.PAUSED) {
            setGameState(GameState.PLAYING);
        }

        for (Animation timer : activeTimers) {
            timer.play();
        }
    }

    public void restartCurrentLevel() {
        this.score = this.levelStartScore;
        this.lives = this.levelStartLives;
        startLevel(this.currentLevel);
    }

    public void quitToMainMenu() {
        setGameState(GameState.MENU);
    }

    public void addGameObject(GameObject object) {
        if (object instanceof Ball) {
            balls.add((Ball) object);
        } else if (object instanceof Brick) {
            bricks.add((Brick) object);
        } else if (object instanceof PowerUp) {
            powerUps.add((PowerUp) object);
        } else if (object instanceof FireWorkEffect) {
            effects.add((FireWorkEffect) object);
        } else if (object instanceof Shield) {
            shields.add((Shield) object);
        }
    }

    private void removeInactiveObjects() {
        PowerUpPool powerUpPool = PowerUpPool.getInstance();
        FireWorkEffectPool effectPool = FireWorkEffectPool.getInstance();

        bricks.removeIf(obj -> !obj.isActive());
        balls.removeIf(obj -> !obj.isActive());
        shields.removeIf(obj -> !obj.isActive());

        powerUps.removeIf(powerUp -> {
            if (!powerUp.isActive()) {
                powerUpPool.returnPowerUp(powerUp);
                return true;
            }
            return false;
        });

        effects.removeIf(effect -> {
            if (!effect.isActive()) {
                effectPool.returnEffect(effect);
                return true;
            }
            return false;
        });
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

        paddle.update(deltaTimeSeconds);
        for (Ball b : balls) b.update(deltaTimeSeconds);
        for (PowerUp p : powerUps) p.update(deltaTimeSeconds);
        for (FireWorkEffect e : effects) e.update(deltaTimeSeconds);
        for (Shield s : shields) s.update(deltaTimeSeconds);

        checkCollisions();
        removeInactiveObjects();

        long remainingBricks = bricks.stream()
                .filter(brick -> brick.getType() != BrickType.WALL)
                .count();

        if (remainingBricks == 0) {
            if (currentLevel >= MAX_LEVELS) {
                setGameState(GameState.WIN);
            } else {
                setGameState(GameState.LEVEL_TRANSITION);
                levelTransitionTimer = DELAY_SEC;
            }
        }
    }

    private void loseLife() {
        for (PowerUp p : powerUps) {
            if (p.isActivated()) {
                p.removeEffect();
                p.setActive(false);
            }
        }
        setFeverBallActive(false);

        long activeBalls = balls.stream().filter(GameObject::isActive).count();

        if (activeBalls < 1) {
            int lifePenalty;
            if (feverBallActive) lifePenalty = FeverBallPowerUp.getLifePenaltyMultiplier();
            else lifePenalty = 1;
            if (lives <= lifePenalty) {
                lives = 0;
                setGameState(GameState.GAME_OVER);
                highScoreManager.addScore(score);
                gameOverTimer = DELAY_SEC;
            } else {
                lives -= lifePenalty;
                balls.clear();
                int ballX = Const.BALL_DEFAULT_POS_X;
                int ballY = Const.BALL_DEFAULT_POS_Y;
                ball = new Ball(ballX, ballY, Const.BALL_DIAMETER, 0, 0);
                addGameObject(ball);
                paddle.reset();
            }
        }
    }

    private void checkCollisions() {
        for (Ball ball : balls) {
            if (!ball.isActive()) continue; // Bỏ qua nếu bóng đã chết

            // Va chạm với tường
            if (ball.getX() <= 0 || ball.getX() + ball.getWidth() >= Const.SCREEN_WIDTH) {
                ball.reverseX();
                if (ball.getX() <= 0) ball.setX(0);
                else ball.setX(Const.SCREEN_WIDTH - ball.getWidth());
            }
            if (ball.getY() <= 0) {
                ball.reverseY();
                ball.setY(0);
            }
            // Rơi ra ngoài
            if (ball.getY() >= Const.SCREEN_HEIGHT) {
                ball.setActive(false);
                loseLife();
                continue;
            }

            // Va chạm với Paddle
            if (ball.getBounds().intersects(paddle.getBounds())) {
                Rectangle2D intersection = ball.intersection(paddle.getBounds());
                double ballCurrentSpeedX = getBallCurrentSpeedX(ball);
                ball.setSpeedX(ballCurrentSpeedX);
                double newSpeedY = Math.sqrt(Math.pow(ball.getMaxSpeed(), 2)
                        - Math.pow(ball.getSpeedX(), 2));
                ball.setSpeedY(-newSpeedY);
                if (intersection != null) {
                    ball.setY(ball.getY() - intersection.getHeight());
                }
            }

            // Va chạm với Gạch
            for (Brick brick : bricks) {
                if (!brick.isActive()) continue;

                if (ball.getBounds().intersects(brick.getBounds())) {
                    Rectangle2D intersection = ball.intersection(brick.getBounds());

                    boolean isFire = ball.isFireBall();  // THÊM: Kiểm tra trạng thái lửa

                    if (!isFire) {  // THÊM: Chỉ reverse và adjust vị trí nếu KHÔNG phải lửa
                        // Logic xác định hướng va chạm (gốc)
                        if (intersection.getHeight() >= intersection.getWidth()) {
                            if (ball.getX() < brick.getBounds().getMinX())
                                ball.setX(brick.getBounds().getMinX() - ball.getWidth());
                            else
                                ball.setX(brick.getBounds().getMaxX());
                            ball.reverseX();
                        } else {
                            if (ball.getY() < brick.getBounds().getMinY())
                                ball.setY(brick.getBounds().getMinY() - ball.getHeight());
                            else
                                ball.setY(brick.getBounds().getMaxY());
                            ball.reverseY();
                        }
                    }  // KẾT THÚC if (!isFire)

                    // Luôn phá hủy gạch (gốc, nhưng giờ áp dụng cho cả fire)
                    boolean destroyed = brick.takeHit();
                    if (destroyed) {
                        onBrickDestroyed(brick);
                        if (isFire) {
                            // THÊM: Đối với fire, không break để xuyên qua nhiều gạch cùng lúc
                            // (có thể comment break nếu muốn giới hạn)
                        } else {
                            break;  // Chỉ va chạm 1 gạch mỗi khung hình (gốc)
                        }
                    }
                }
            }

            // Va chạm với Khiên
            for (Shield shield : shields) {
                if (!shield.isActive()) continue;
                if (ball.getBounds().intersects(shield.getBounds())) {
                    shield.hit();
                    ball.setY(ball.getY() - ball.getHeight() - 5);
                    ball.reverseY();
                }
            }
        }

        for (PowerUp powerUp : powerUps) {
            if (!powerUp.isActive()) continue;

            if (paddle.getBounds().intersects(powerUp.getBounds())) {
                powerUp.activate(paddle);
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

        FireWorkEffect fire = FireWorkEffectPool.getInstance().getEffect();

        fire.reset(
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
            PowerUp powerUp = null;
            Class<? extends PowerUp> typeToCreate = null; // Lưu loại class cần tạo

            double rand = Math.random();

            if (rand < 0.15) {
                typeToCreate = MultiBallPowerUp.class;
            } else if (rand < 0.30) {
                typeToCreate = FastBallPowerUp.class;
            } else if (rand < 0.45) {
                typeToCreate = ExpandPaddlePowerUp.class;
            } else if (rand < 0.60) {
                typeToCreate = ShieldPowerUp.class;
            } else if (rand < 0.85) {
                typeToCreate = FireBallPowerUp.class;
            } else {
                typeToCreate = FeverBallPowerUp.class;
            }

            powerUp = PowerUpPool.getInstance().getPowerUp(typeToCreate);

            if (powerUp == null) {
                try {
                    powerUp = typeToCreate.getDeclaredConstructor(int.class, int.class)
                            .newInstance((int) brick.getX(), (int) brick.getY());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                powerUp.reset(brick.getX(), brick.getY());
            }
            if (powerUp != null) {
                addGameObject(powerUp);
            }
        }
    }

    private void resetGameLists() {
        balls.clear();
        bricks.clear();
        powerUps.clear();
        effects.clear();
        shields.clear();
    }

    /**
     * Reset tất cả các chỉ số của game về trạng thái ban đầu.
     */
    private void resetGame() {
        this.score = Const.DEFAULT_SCORES;
        this.lives = Const.DEFAULT_LIVES;
        this.currentLevel = 1;
        resetGameLists();
    }

    /**
     * Bắt đầu một lần chơi mới.
     */
    public void startNewGame() {
        resetGame();
        startLevel(this.currentLevel);
    }

    public List<Ball> getBalls() { return balls; }
    public List<Brick> getBricks() { return bricks; }
    public List<PowerUp> getPowerUps() { return powerUps; }
    public List<FireWorkEffect> getEffects() { return effects; }
    public List<Shield> getShields() { return shields; }

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

    public int getCurrentLevel() {
        return currentLevel;
    }

    public PauseMenu getPauseMenu() {
        return pauseMenu;
    }

    public HighScoreMenu getHighScoreMenu() {
        return highScoreMenu;
    }
}
