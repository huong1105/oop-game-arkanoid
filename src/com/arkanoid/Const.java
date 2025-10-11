package com.arkanoid;

public enum Const {
    INSTANCE;
    private final int SCREEN_WIDTH = 1000;
    private final int SCREEN_HEGHT = 800;
    private final int BALL_MAXSPEED = 15;
    private final int PADDLE_WIDTH = 120;
    private final int PADDLE_HEIGHT = 20;
    private final int BALL_DIAMETER = 20;
    private final int BALL_SPEEDX = 5;
    private final int BALL_SPEEDY = 5;

    private final int DEFAULT_LIVES = 3;
    private final int DEFAULT_SCORES = 0;

    public int getDefaultLives(){
        return DEFAULT_LIVES;
    }

    public int getDefaultScores(){
        return DEFAULT_SCORES;
    }

    public int  getScreenWidth() {
        return SCREEN_WIDTH;
    }

    public int getScreenHeight() {
        return SCREEN_HEGHT;
    }

    public int getPaddleWidth() {
        return PADDLE_WIDTH;
    }

    public int getPaddleHeight() {
        return PADDLE_HEIGHT;
    }

    public int getBallMaxSpeed() {
        return BALL_MAXSPEED;
    }

    public int getBallSpeedX() {
        return BALL_SPEEDX;
    }

    public int getBallSpeedY() {
        return BALL_SPEEDY;
    }

    public int  getBallDiameter() {
        return BALL_DIAMETER;
    }
}
