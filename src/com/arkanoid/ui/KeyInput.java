package com.arkanoid.ui;

import com.arkanoid.game.GameManager;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class KeyInput extends KeyAdapter {
    private GameManager gameManager;

    public KeyInput(GameManager gameManager) {
        this.gameManager = gameManager;
    }

    @Override
    public void keyPressed(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
