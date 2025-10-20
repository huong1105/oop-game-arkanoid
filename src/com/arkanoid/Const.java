package com.arkanoid;

public final class Const {
    private Const() {
    }

    public static final int SCREEN_WIDTH = 1000;
    public static final int SCREEN_HEIGHT = 800;
    public static final double BALL_MAXSPEED = 75;
    public static final int PADDLE_WIDTH = 120;
    public static final int PADDLE_HEIGHT = 20;
    public static final int BALL_DIAMETER = 20;
    public static final int BALL_SPEEDX = 5;
    public static final int BALL_SPEEDY = 5;
    public static final int PADDLE_DEFAULT_SPEED = 15;
    public static final int PADDLE_DEFAULT_POS_X = SCREEN_WIDTH / 2 - PADDLE_WIDTH / 2;
    public static final int PADDLE_DEFAULT_POS_Y = SCREEN_HEIGHT - PADDLE_HEIGHT - 20;
    public static final int BALL_DEFAULT_POS_X = SCREEN_WIDTH / 2 - BALL_DIAMETER / 2;
    public static final int BALL_DEFAULT_POS_Y = PADDLE_DEFAULT_POS_Y - BALL_DIAMETER - 10;
    public static final int BRICK_WIDTH = 100;
    public static final int BRICK_HEIGHT = 50;

    public static final int DEFAULT_LIVES = 3;
    public static final int DEFAULT_SCORES = 0;
}
